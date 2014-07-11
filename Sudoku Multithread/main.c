#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>
#include <string.h>
#include <pthread.h>
#include "main.h"
#include "analise.h"
#include "solver.h"

#define cmdLength 25

int main(void)
{
	analiseInit(); /* inicializa variáveis de thread presentes na analise.c */
	
	int **matriz;
	    bool exit = false;
	    char command[cmdLength];

	    /* --- Testa comandos --- */
	    /*
	    matriz = loadM();
	    verificaColunas(matriz);
	    verificaLinhas(matriz);
	    verificaQuadrados(matriz);
	    printM(matriz);
	    dica(matriz);*/
	    /* --- --- */

	    /* -- Console de entrada, ir para consoleHelp() para ver os comandos, ou digitar ajuda no programa */
	    while(!exit)
	    {
		   printf(" > ");
		   scanf("%s", command);

		   if(strcmp(command, "lerM") == 0) matriz = loadManual();

		   if(strcmp(command, "lerA") == 0) matriz = loadFile();

		   if(strcmp(command, "imprimirSudoku") == 0) printM(matriz);

		   if(strcmp(command, "ajuda") == 0) consoleHelp();

		   if(strcmp(command, "verificar") == 0) verifica(matriz);

		   if(strcmp(command, "dica") == 0) dica(matriz);
		   
		   if(strcmp(command, "resolver") == 0) printM(solve(matriz));
		   
		   if(strcmp(command, "limpar") == 0) 
			if(matriz != NULL) unload(matriz);

		   if(strcmp(command, "sair") == 0) exit = true;

		   else printf("Digite 'ajuda' para saber mais sobre os comandos\n");
	    }
	    /* -- */

	    return 0;
}

void consoleHelp() /* Imprime ajuda sobre os comandos do console */
{
    printf("\n\n");
    printf("    lerM                  Le sudoku. A seguir, e necessario digitar 81 inteiros.\n\n");
    printf("    lerA                  Le sudoku de arquivo. A seguir, e necessario digitar o endereco do arquivo que contenha o sudoku\n\n");

    printf("    imprimirSudoku       Imprime o sudoku que foi lido pelo comando 'ler'\n\n");

    printf("    ajuda                Imprime esse menu de ajuda na tela\n\n");

    printf("    verificar            Imprime todos os erros, se presentes, no sudoku\n\n");
   
    printf("    dica                 Imprime dicas para posições marcadas com 'X' no sudoku\n\n");
    
    printf("    resolver             Imprime resolução de sudoku lido. Deve ser válido\n\n");
    
    printf("    limpar               Apaga sudoku lido\n\n");

    printf("    sair                 Sai do programa\n\n");
}

void printM(int **matriz) /* printa a matriz inteira */
		{
		    int i, j;
		    for (i = 0; i < 9; i++)
		    {
			   for (j = 0; j < 9; j++)
			   {
				  if (matriz[i][j] != 0) printf("%d ", matriz[i][j]);
				  else printf("X ");
			   }
			   printf("\n");
		    }
		}

int **loadFile()
{
    int i, j, **matriz;

    char fileName[cmdLength];
    printf("Nome do arquivo:");
    scanf("%s", fileName);

    FILE *f;
    f = fopen(fileName, "r");

    char string[50];
    matriz = malloc(sizeof(int*) * 9);
    for(i = 0; i < 9; i++)
    {
	   matriz[i] = malloc(sizeof(int) * 9);
	   for (j = 0; j < 9; j++)
	   {
		  fscanf(f, "%s", string);
		  if (string[0] >= '1' && string[0] <= '9') matriz[i][j] = string[0] - '0'; /* Armazena inteiro na Matriz */
		  else matriz[i][j] = 0; /* Caso não seja um inteiro, considera que é X, que é definido pelo valor 0 na matriz*/
	   }
    }
    fclose(f);

    return matriz;
}

int **loadManual() /* retorna matriz dinâmica para sudoku, lendo seus valores */
{
    int i, j, **matriz;
    char string[10];
    matriz = malloc(sizeof(int*)*9);

    for (i = 0; i < 9; i++)
    {
	   matriz[i] = malloc(sizeof(int)*9);
	   for (j = 0; j < 9; j++)
	   {
		  scanf("%s", string);
		  if (string[0] >= '1' && string[0] <= '9') matriz[i][j] = string[0] - '0'; /* Armazena inteiro na Matriz */
		  else matriz[i][j] = 0; /* Caso não seja um inteiro, considera que é X, que é definido pelo valor 0 na matriz*/
	   }
    }
    return matriz;
}

void unload(int **matriz)
{
	int i;
	for(i = 0; i < 9; i++)
		free(matriz[i]);
	free(matriz);
}
