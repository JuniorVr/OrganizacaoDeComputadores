//
//  Matriz.hpp
//  
//
//
//

#ifndef Matriz_hpp
#define Matriz_hpp


typedef struct matriz Matriz;

Matriz* criarMatriz(int, int);

void criarMatriz(Matriz**, int, int);
void deletarMatriz(Matriz**);
void adicionarElemento(Matriz*, int, int, int);
int lerElemento(Matriz*, int, int);
void imprimir(Matriz*);

#endif /* Matriz_hpp */
