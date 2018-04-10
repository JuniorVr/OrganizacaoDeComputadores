#include "DataMemory.hpp"
#include "Matriz.hpp"
#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <ctime>

using namespace std;

struct dataMemory{
	Matriz* simplesMemory;
	int memorySize;
};

DataMemory* criarMemoria(int linhas,int colunas){
	DataMemory* aux = new DataMemory;

	srand (time(NULL));
	if(linhas <= 0 && colunas <=0){
		cout << "Não possivel criar a memoria!"<<endl;
	}else{
		aux->memorySize = linhas;
		aux->simplesMemory = criarMatriz(linhas,colunas);
		for(int i = 0; i < linhas; i++){
			for(int j = 0; j < colunas;j++){
				adicionarElemento(aux->simplesMemory,i,j,rand() % 1000000);
			}
		}
	}
	return aux;
}

int* getData (Matriz* matriz,int address,int linhas,int colunas){
	int* aux = new int[colunas];
	if(address > linhas){
		return nullptr;
	}
	else{
		for(int i = 0; i<colunas;i++){
		aux[i] = lerElemento(matriz,address,i);
		}
		return aux;
	}
}

void mostrarMemoria(DataMemory* memoria,int linhas,int colunas){
	for(int i=0; i< linhas; i++){
		for(int j=0; j< colunas; j++){
			cout << lerElemento(memoria->simplesMemory,i,j) << "|";
		}
		cout << endl;
	}
}