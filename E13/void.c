//kommenterar för att testa git
#include<stdio.h>

typedef struct node{
  void *value;
  struct node *next;
} *Node;

typedef struct{
  struct node *first;
  struct node *last;
  unsigned int size;
}LinkedList;

void initLinkedList(LinkedList *this){
  this->size = 0;
  this->last = NULL;
  this->first = NULL;
}

void addToList(LinkedList *this, void *value){

  struct node *node = malloc(sizeof(struct node));
  node->value = value;
  node->next = NULL;

  if (this->size == 0)
    this->first = this->last = node;
  else
    {
      this->last->next = node;
      this->last = node;
    }

  this->size++;
}

typedef struct{
  int baz;
} INT;

typedef struct{
  char baz;
} CHAR;

int main(int argc, char *argv[]){

  LinkedList list;
  INT bar;
  bar.baz = 65;

  CHAR barr;
  barr.baz = 65;

  //initierar en lista
  initLinkedList(&list);
  //lägger till en nod i listan
  addToList(&list, (void *)&bar);

  //lägger till en nod i listan
  addToList(&list, (void *)&barr);

  //plockar ut det första elementet
  INT *firstElement = (INT *)list.first->value;
  // ... och skriver ut det
  printf("%d\n", firstElement->baz);
  
  CHAR *nextElement = (CHAR *)list.last->value;
  printf("%c\n", nextElement->baz);

  return 0;
}
