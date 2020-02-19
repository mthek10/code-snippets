#include <stdio.h> // Any other headers we need here
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/mman.h>

struct block{
    size_t size; // How many bytes beyond this block have been allocated in the heap
    struct block* next; // Where is the next block
    int free; // Is this memory free?
  };

// Represents the base of the linked list of blocks
void *base = NULL;
// init has not been initialized
int init = 0;

struct block **blockArray;


// Lock
pthread_mutex_t lock;
void* mymalloc(size_t s);


// Used to find a block in the linked list that fits the size requirement and is free
struct block *get_free_block(struct block **last, size_t size) {
	
	// Iterate through the list to find a block that fits
	struct block *current_block = base;
	while ( current_block && !(current_block->free && current_block->size >= size)) {
		*last = current_block;
		current_block = current_block->next;
	}
	
	return current_block;
}


// used to create a new block onto the list with the required size
struct block *get_new_block(struct block* last, size_t size) {
	struct block *block_new;
	size_t remainder;

	if (size >= 4096) {
		printf("Used MMAP\n");	
		block_new = mmap(0, size + sizeof(struct block), PROT_READ | PROT_WRITE, MAP_ANONYMOUS | MAP_PRIVATE, -1, 0);
		// Split up blocks of mmap recursively to ensure not wasting space
		block_new->size = 4096;
		
		remainder = size - 4096;
		if (remainder > 0) {		
			return mymalloc(remainder);
		}
	} else {
		printf("Used SBRK\n");
		block_new = sbrk(size + sizeof(struct block));
		block_new->size = size;		
	}

	// Link the List together
	if(last) {
		last->next = block_new;
	}

	// Set properties of block;
	block_new->next = NULL;
	block_new->free = 0;

	return block_new;
	
}


// Converts pointer back one to get the hidden block struct from memory pointer
struct block *get_struct_block_ptr(void* ptr) {
	return (struct block*)ptr - 1;
}


void init_method() {
	init = 1;
	// Setup the free list according to num cpu
	int i;
	
	blockArray = sbrk ( get_nprocs() * sizeof (struct block)  );		
	
	// intialize head of each list to NULL base
	for (i = 0; i < get_nprocs(); i++) {
		blockArray[i] = NULL;
	}

}


// used to free previously malloc'd memory
void myfree(void *ptr){
	pthread_mutex_lock(&lock);
	// invalid pointer
	if(!ptr){
		return;
	}	
	
	
	// set block to free
	struct block* block_ptr = get_struct_block_ptr(ptr);
	block_ptr->free = 1;
	pthread_mutex_unlock(&lock);
	printf("Freed %zu bytes\n", block_ptr->size);
}


void* mymalloc(size_t s) {

	// check if this is the first call to malloc
	if (init == 0) {
		init_method();
	}
	
	printf("CPU: %d - ", sched_getcpu());

	struct block *block1;
	// Check if size is 0
	if(s <= 0){
		return NULL;
	}

	// check if base is unset
	if (!blockArray[sched_getcpu()]) {
		// if so, add new block
		block1 = get_new_block(NULL, s);
		if(!block1){
			return NULL;
		}
		// and reset to new starting block of linked list
		blockArray[sched_getcpu()] = block1;
	} else {
		// checks what to link the new block onto
		struct block *last = blockArray[sched_getcpu()];
		block1 = get_free_block(&last, s);
		if(!block1){
			// Lock when asking for new memory
			pthread_mutex_lock(&lock);
			block1 = get_new_block(last, s);
			pthread_mutex_unlock(&lock);
			if(!block1){
				return NULL;
			}
		} else {
			block1->free = 0;
		}
	}
	
	printf("malloc %zu bytes\n",s);	
	return (block1+1);	
	
}
	


void* mycalloc(size_t nmemb, size_t s){
	// set size and memset with 0
	size_t size =  nmemb * s;
	void *ptr = mymalloc(size);
	memset(ptr, 0, size);
	printf("calloc %zu bytes\n",s);
	return ptr;
}
	
