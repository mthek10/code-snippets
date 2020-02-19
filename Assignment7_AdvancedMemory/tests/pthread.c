#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <malloc.h>

// Our shared data structure to hold the strings
char** shared_data;
pthread_mutex_t mutex1 = PTHREAD_MUTEX_INITIALIZER;

void *some_thread1(int index_to_shared_data){		

	pthread_mutex_lock(&mutex1);
	// Allocates some memory for an individual string
	char* testString = malloc(sizeof(char) * 100);
	// Generate some random data/string to put in shared_data
	// e.g. shared_data[index_to_shared_data] "some_thread1";
	strcpy(testString, "some_thread1");
	shared_data[index_to_shared_data] = testString;	
	
	pthread_mutex_unlock(&mutex1);		

	return;
}

void *some_thread2(int index_to_shared_data){
		
	pthread_mutex_lock(&mutex1);
	// Allocates some memory for an individual string
	char* testStringA = malloc( sizeof(char) * 100 );	
	// Generate some random data/string to put in shared_data
	// e.g. shared_data[index_to_shared_data] "some_thread2";
	strcpy(testStringA, "some_thread2");
	shared_data[index_to_shared_data] = testStringA;
	
	pthread_mutex_unlock(&mutex1);

	return;	
}



int main(){
	// set num threads defined
	int NUM_THREADS = 2;
	// Make space for number of strings
	shared_data = malloc(sizeof(char*) * NUM_THREADS);
	// save threads
	pthread_t tids[NUM_THREADS];
	
	int i;

	// Launch NUM_THREADS number of threads using different functions
	pthread_create(&tids[0], NULL, some_thread1, 0);
	pthread_create(&tids[1], NULL, some_thread2, 1);	
	
	// (3) Join some number of threads
	int j;
	for (j = 0; j < NUM_THREADS; j++) {
		pthread_join(tids[j], NULL);
	}	

	// (4) Print the results of shared data (i.e. this is done sequentially)
	int l;
	for (l = 0; l < NUM_THREADS; l++) {
		printf("%s\n", shared_data[l]);
	}
	
	// (5) Cleanup your program
	// 'free shared_data'
	int m;
	for (m=0; m< NUM_THREADS; m++) {
		free(shared_data[m]);
	}

	free(shared_data);
	
	return 0;
}
