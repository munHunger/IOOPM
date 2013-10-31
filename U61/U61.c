#include<stdio.h>
#include<stdlib.h>

int fib(int n) {
  int *a = malloc(sizeof(int));
  int *b = malloc(sizeof(int));
  *a = 0;
  *b = 1;
  for (int i=1; i<=n; ++i) {
    int fib_i = *a + *b; 
    *a = *b; 
    *b = fib_i;
  }
  free(b);
  return a; 
}

int main(int argc, char **argv) {
  if (argc < 2) {
    puts("Usage: ./fib-iter 5");
  } else {
    int n = atoi(*(argv+1));
    int* fibon = fib(n);
    printf("fib(%d) = %d\n", n, *fibon);
    free(fibon);
  }
  return 0;
}
