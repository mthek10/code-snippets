// Basic test

#include <stdio.h>
#include <malloc.h>

int main(){

  // int* data = (int*)malloc(1024);
  // switched to 4096 to test MMAP 
  int* data = (int*) malloc (4096);
  free(data);

  return 0;
}
