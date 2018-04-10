#ifndef DATA_MEMORY_HPP
#define DATA_MEMORY_HPP

typedef struct dataMemory DataMemory;

DataMemory* criarMemoria(int linhas,int colunas);
void mostrarMemoria(DataMemory*,int,int);

#endif // DATA_MEMORY_HPP
