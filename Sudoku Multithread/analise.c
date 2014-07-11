#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>
#include <string.h>
#include <pthread.h>
#include "analise.h"

int failed;
pthread_mutex_t f_mux;
pthread_t **thread_pool;

struct arg
{
	int **matriz;
	int ***aux_table;
	int pos;
	int pos_aux;
};

void init_mutexes (void)
{
	pthread_mutex_init(&f_mux, NULL);
}

void analiseInit()
{
	init_threads2();
	init_mutexes();
}

void init_threads2 (void)
{
	int i, j;
	
	thread_pool = malloc(3 * sizeof(pthread_t *));
	
	for (i = 0; i < 3; i++)
		thread_pool[i] = malloc(9 * sizeof(pthread_t));
}

void verifica(int **matriz) /* chama todas as rotinas de verificação */
{
    failed = 0;
    verificaColunas(matriz);
    verificaLinhas(matriz);
    verificaQuadrados(matriz);
    return;
}

void *turnonColuna (void * a)
{
	struct arg *p = (struct arg *) a;
	
	verificaColuna(p->matriz, p->pos);
}

void *turnonLinha (void * a)
{
	struct arg *p = (struct arg *) a;
	
	verificaLinha(p->matriz, p->pos);	
}

void *turnonQuadrado (void * a)
{
	struct arg *p = (struct arg *) a;
	
	verificaQuadrado(p->matriz, p->pos, p->pos_aux);
}

void thread_verificaColuna(int **matriz, int pos)
{
	struct arg *a;
	
	a = malloc(sizeof(struct arg));
	a->matriz = matriz;
	a->pos = pos;
	
	pthread_create(&thread_pool[0][pos], NULL, turnonColuna, (void *) a);
	
	pthread_join(thread_pool[0][pos], NULL);

	free(a);

	return;
}

void thread_verificaLinha(int **matriz, int pos)
{
	struct arg *a;
	
	a = malloc(sizeof(struct arg));
	a->matriz = matriz;
	a->pos = pos;
	
	pthread_create(&thread_pool[1][pos], NULL, turnonLinha, (void *) a);
	
	pthread_join(thread_pool[1][pos], NULL);

	free(a);

	return;	
}

void thread_verificaQuadrado(int **matriz, int pos1, int pos2)
{
	struct arg *a;
	
	a = malloc(sizeof(struct arg));
	a->matriz = matriz;
	a->pos = pos1;
	a->pos_aux = pos2;
	
	pos1 /= 3;
	pos2 /= 3;
	
	pthread_create(&thread_pool[2][pos1 * 3 + pos2], NULL, turnonQuadrado, (void *) a);
	
	pthread_join(thread_pool[2][pos1 * 3 + pos2], NULL);

	free(a);

	return;
}

void verificaColuna(int **matriz, int coluna) /* verifica a validade de uma dada coluna de um dado sudoku */
{
    int i, total = 0;
	int k;

	if (failed)
		return;

    for (i = 0; i < 9; i++)
        total += pow(2, matriz[i][coluna]); /* verifica presença de todos os valores */

    if(total == 1022) return; /* tem todos os valores naquela coluna */
	
	pthread_mutex_lock(&f_mux);
	if (failed)
	{
		pthread_mutex_unlock(&f_mux);
		return;
	}
	failed = 1;
	pthread_mutex_unlock(&f_mux);
	
    
    for (i = 0; i < 8; i++)
        for (k = i + 1; k < 9; k++)
            if(matriz[i][coluna] == matriz[k][coluna] && matriz[i][coluna] != 0)
                printf("A coluna %d tem um %d a mais\n", coluna + 1, matriz[i][coluna]);

    return;
}

void verificaLinha(int **matriz, int linha) /* verifica a validade de uma dada linha de um dado sudoku */
{
    int j, total = 0;
	
	if (failed)
		return;

    for(j=0;j<9;j++)
        total += pow(2,matriz[linha][j]); /* verifica presença de todos os valores */

    if(total == 1022) return; /* tem todos os valores naquela linha */
	
	pthread_mutex_lock(&f_mux);
	if (failed)
	{
		pthread_mutex_unlock(&f_mux);
		return;
	}
	failed = 1;
	pthread_mutex_unlock(&f_mux);

    int k;
    for(j=0;j<8;j++)
        for(k=j+1;k<9;k++)
            if(matriz[linha][j]==matriz[linha][k] && matriz[linha][j] != 0)
                printf("A linha %d tem um %d a mais\n", linha+1, matriz[linha][j]);

    return;
}

void verificaQuadrado(int **matriz, int linhaInicial, int colunaInicial) /* verifica a validade de um quadrado de um dado sudoku, dada sua linha e coluna iniciais */
{
    int total = 0;
    int i, j;
	
	if (failed)
		return;
	
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            total += pow(2,matriz[linhaInicial+i][colunaInicial+j]); /* verifica presença de todos os valores */

    if(total == 1022) return; /* tem todos os valores naquela coluna */

	pthread_mutex_lock(&f_mux);
	if (failed)
	{
		pthread_mutex_unlock(&f_mux);
		return;
	}
	failed = 1;
	pthread_mutex_unlock(&f_mux);
	
    /* verificação sistemática do quadrado 3x3 definido pelos valores de linhaInicial e colunaInicial */
    int k, l;
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            for (k = i; k < 3; k++)
            {
                if (k == i) l = j + 1; /* se não for uma linha diferente da da casa analisada, vá para a casa seguinte */
                else l = 0; /* se for, vá para o 0 */
                for (; l < 3; l++)
                    if(matriz[i + linhaInicial][j + colunaInicial] == matriz[k + linhaInicial][l + colunaInicial] && matriz[i + linhaInicial][j + colunaInicial] != 0)
                        printf("O quadrado %d tem um %d a mais\n", linhaInicial + colunaInicial/3 + 1, matriz[linhaInicial + i][colunaInicial + j]);
            }

    return;
}

void verificaColunas(int **matriz) /* verifica a validade de todas as colunas de um dado sudoku */
{
    int i;
	
    for(i = 0; i < 9; i++)
		thread_verificaColuna(matriz, i);
    return;
}

void verificaLinhas(int **matriz) /* verifica a validade de todas as linhas de um dado sudoku */
{
    int i;
    for(i = 0; i < 9; i++)
		thread_verificaLinha(matriz, i);
    return;
}

void verificaQuadrados(int **matriz) /* verifica a validade de todos os quadrados de um dado sudoku */
{
    int i, j;
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            thread_verificaQuadrado(matriz, 3 * i, 3 * j);

    return;
}

void dica(int **matriz) /* Analisa e imprime dicas */
{
    int i, j;

    void analiseInit();

    int ***matrizPossibilidades; /* Uma matriz 9x9 de ponteiros que contem as possibilidades para aquela posição */
    
    matrizPossibilidades = analisaDicas(matriz);
    imprimeDicas(matriz, matrizPossibilidades);

    destroy_all2(matrizPossibilidades);
}

int ***create_aux_table2 (void)
{
	int ***new_table;
	int i, j, k;
	
	new_table = malloc(3 * sizeof(int **));
	for (i = 0; i < 3; i++)
	{
		new_table[i] = malloc(9 * sizeof(int *));
		for (j = 0; j < 9; j++)
		{
			new_table[i][j] = malloc(10 * sizeof(int));
			for (k = 0; k < 10; k++)
				new_table[i][j][k] = 0;
		}
	}
		
	return new_table;
}

int bpos2 (int x, int y)
{
	return (x / 3) * 3 + y / 3;
}

void analisaDicas_linha (int **matriz, int ***aux_table, int i)
{
	int j;
	
	for (j = 0; j < 9; j++)
		aux_table[0][i][matriz[i][j]] = 1;

}

void analisaDicas_coluna (int **matriz, int ***aux_table, int i)
{
	int j;
	
	for (j = 0; j < 9; j++)
		aux_table[1][i][matriz[j][i]] = 1;	
}

void analisaDicas_quadrado (int **matriz, int ***aux_table, int i)
{
	int x_init, y_init, j, k;

	x_init = (i % 3) * 3;
	y_init = (i / 3) * 3;
		
	for (j = 0; j < 3; j++)
		for (k = 0; k < 3; k++)
			aux_table[2][bpos2(x_init + j, y_init + k)][matriz[x_init + j][y_init + k]] = 1;
}

void *turnon_analisaDicas (void *p)
{
	struct arg *a = (struct arg *) p;

	analisaDicas_linha(a->matriz, a->aux_table, a->pos);
	analisaDicas_coluna(a->matriz, a->aux_table, a->pos);
	analisaDicas_quadrado(a->matriz, a->aux_table, a->pos);	
}

void thread_analisaDicas (int **matriz, int ***aux_table, int pos)
{
	struct arg *p = malloc(sizeof(struct arg));
	
	p->matriz = matriz;
	p->aux_table = aux_table;
	p->pos = pos;
	
	pthread_create(&thread_pool[0][pos], NULL, turnon_analisaDicas, (void *) p);
}

int ***analisaDicas(int **matriz)
{
	int i, j, k, x_init, y_init;
	int ***aux_table = create_aux_table2();
	
	for (i = 0; i < 9; i++)
		thread_analisaDicas(matriz, aux_table, i);

	for (i = 0; i < 3; i++)
		for( j = 0; j < 9; j++)
		pthread_join(thread_pool[i][j], NULL);
		
	return aux_table;
}

void imprimeDicas(int **matriz, int ***aux_table) /* Imprime matriz/dicas, ambas dadas */
{
    int i, j, m;	
    
    for (i = 0; i < 9; i++)
    {
        for (j = 0; j < 9; j++)
        {
            if(matriz[i][j] == 0)
            { /* É um X, deve-se imprimir as dicas */
                printf("(");
				for (m = 1; m < 10; m++)
					if (!aux_table[0][i][m] && !aux_table[1][j][m] && !aux_table[2][bpos2(i, j)][m])
						printf("%d", m);
                printf(") ");
            }
            else printf("%d ", matriz[i][j]); /* Não é um X, deve-se imprimir o valor inicial */
        }
        printf("\n");
    }
}

void destroy_all2(int ***aux_table)
{
	int i, j;
	for (i = 0; i < 3; i++)
	{
		for (j = 0; j < 9; j++)
		{
			free(aux_table[i][j]);
		}
		free(aux_table[i]);
	}
	free(aux_table);
	
	for (i = 0; i < 3; i++)
		free(thread_pool[i]);
	free(thread_pool);
}


