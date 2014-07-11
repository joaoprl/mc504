#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include "solver.h"

/*Matriz do sudoku*/
/*Guarda, respectivamente, linhas e colunas*/
int **sudoku;
int **final;
int used_threads;
int is_solved = 0;
int max_threads;
int *active_threads;
int ****thread_used_tables;
int ***thread_used_boards;
int **thread_pos;
pthread_t *thread_vec;
pthread_mutex_t m_idle, m_solved;

int ***create_aux_table (void)
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

int idle_threads_available (void)
{
	return (used_threads < max_threads);
}

void init_locks (void)
{
	used_threads = 0;
	max_threads = 8;
	is_solved = 0;
	pthread_mutex_init(&m_idle, NULL);
	pthread_mutex_init(&m_solved, NULL);
}

void init_threads (void)
{
	int i;

	active_threads = malloc(max_threads * sizeof(int));
	thread_vec = malloc(max_threads * sizeof(pthread_t));
	
	for (i = 0; i < max_threads; i++)
		active_threads[i] = 0;
		
	thread_used_tables = malloc(max_threads * sizeof(int ***));
	for (i = 0; i < max_threads; i++)
		thread_used_tables[i] = create_aux_table();
		
	thread_used_boards = malloc(max_threads * sizeof(int **));
	for (i = 0; i < max_threads; i++)
		thread_used_boards[i] = create_table();
	
	thread_pos = malloc(max_threads * sizeof(int *));	
	for (i = 0; i < max_threads; i++)
		thread_pos[i] = malloc(2 * sizeof(int));
}

int **create_table (void)
{
	int **new_table;
	int i;
	
	new_table = malloc(9 * sizeof(int *));
	for (i = 0; i < 9; i++)
		new_table[i] = malloc(9 * sizeof(int));
		
	return new_table;
}

void copy_table (int **source, int **target)
{
	int i, j;
	
	for (i = 0; i < 9; i++)
		for (j = 0; j < 9; j++)
			target[i][j] = source[i][j];
}

void copy_used_table (int **source, int **target)
{
	int i, j;
	
	for (i = 0; i < 9; i++)
		for (j = 0; j < 10; j++)
			target[i][j] = source[i][j];
}

void destroy_table (int **table)
{
	int i;
	
	for (i = 0; i < 9; i++)
		free(table[i]);
		
	free(table);
}

void copy_aux_table (int ***source, int ***target)
{
	int i, j, k;
	
	for (i = 0; i < 3; i++)
		for (j = 0; j < 9; j++)
			for (k = 0; k < 10; k++)
				target[i][j][k] = source[i][j][k];
}

void destroy_aux_table (int ***table)
{
	int i, j;
	
	for (i = 0; i < 3; i++)
	{
		for (j = 0; j < 9; j++)
			free(table[i][j]);
			
		free(table[i]);
	}
		
	free(table);
}

int bpos (int x, int y)
{
	return (x / 3) * 3 + y / 3;
}

int core_wrap (int **board, int ***aux_table)
{
	int i, j;
	
	for (i = 0; i < 9; i++)
		for (j = 0; j < 9; j++)
		{
			aux_table[0][i][board[i][j]] = 1;
			aux_table[1][j][board[i][j]] = 1;
			aux_table[2][bpos(i, j)][board[i][j]] = 1;		
		}
	
	return core_sudoku(0, 0, board, aux_table[0], aux_table[1], aux_table[2]);
}

void *turnon_thread (void *p)
{
	int i = (int) p;
	
//	printf("i number: %d\n", i);	
	
	core_sudoku(thread_pos[i][0], thread_pos[i][1], thread_used_boards[i], thread_used_tables[i][0], thread_used_tables[i][1], thread_used_tables[i][2]);
	pthread_mutex_lock(&m_idle);
	used_threads--;
	active_threads[i] = 0;
	pthread_mutex_unlock(&m_idle);

	return NULL;
}

void new_thread_solve(int next_x, int next_y, int **board, int **used_x, int **used_y, int **used_block)
{
	int i;
	
	for (i = 0; i < max_threads; i++)
		if (!active_threads[i])
			break;
	
	used_threads++;
	active_threads[i] = 1;
	pthread_mutex_unlock(&m_idle);
	copy_table(board, thread_used_boards[i]);
	copy_used_table(used_x, thread_used_tables[i][0]);
	copy_used_table(used_y, thread_used_tables[i][1]);
	copy_used_table(used_block, thread_used_tables[i][2]);
	thread_pos[i][0] = next_x;
	thread_pos[i][1] = next_y;
	
	pthread_create(&thread_vec[i], NULL, turnon_thread, (void *) i);

	return;
}

int solved (int **board)
{
	pthread_mutex_lock(&m_solved);
	if (!is_solved)
		is_solved = 1;
	else
	{
		pthread_mutex_unlock(&m_solved);
		return 0;
	}
	pthread_mutex_unlock(&m_solved);	
	
	final = create_table();
	copy_table(board, final);

	return 1;
}

int core_sudoku (int x, int y, int **board, int **used_x, int **used_y, int **used_block)
{
	int next_x, next_y, attempt;
	
	if (is_solved)
		return 0;
	
	/*Determinam-se as coordenadas da próxima célula por analisar*/
	if (x < 8)
		next_x = x + 1, next_y = y;
	else
		next_x = 0, next_y = y + 1;
	/*Caso base da recursão -- todo o tabuleiro pôde ser preenchido*/
	if (y > 8)
		return solved(board);
	
//	printf("tentei alguma coisa\n");
	
	/*Teste a garantir que as células da entrada jamais sejam sobrescritas*/
	if (!board[x][y])
	{	
		/*Loop de tentativas*/
		for (attempt = 1; attempt < 10; attempt++)
		{
			/*Testa se o número tencionado já foi utilizado na linha, coluna e bloco*/
			if (!used_x[x][attempt] && !used_y[y][attempt] && !used_block[bpos(x, y)][attempt])
			{			
				board[x][y] = attempt;
				used_x[x][attempt] = 1;
				used_y[y][attempt] = 1;
				used_block[bpos(x, y)][attempt] = 1;
				//Inserir aqui chamada de thread
				pthread_mutex_lock(&m_idle);
				if (!idle_threads_available())
				{
					pthread_mutex_unlock(&m_idle);

					if (core_sudoku(next_x, next_y, board, used_x, used_y, used_block))	//Avanço tentativo
						return 1;
					else																//Retrocesso
					{
						board[x][y] = 0;
						used_x[x][attempt] = 0;
						used_y[y][attempt] = 0;
						used_block[bpos(x, y)][attempt] = 0;
					}
				}
				else
				{					
					new_thread_solve(next_x, next_y, board, used_x, used_y, used_block);
					board[x][y] = 0;
					used_x[x][attempt] = 0;
					used_y[y][attempt] = 0;
					used_block[bpos(x, y)][attempt] = 0;
					continue;
				}
			}
		}
		/*Caso não mais haja possibilidades para certa célula, não existe solução*/
		return 0;
	}
	/*No caso de células dadas pela entrada, simplesmente avança para a seguinte*/
	else
		return core_sudoku(next_x, next_y, board, used_x, used_y, used_block);
}

void destroy_all (void)
{
	int i;
	
	free(active_threads);
	
	for (i = 0; i < max_threads; i++)
		free(thread_pos[i]);
	free(thread_pos);
	
	for (i = 0; i < max_threads; i++)
	{
		destroy_table(thread_used_boards[i]);
		destroy_aux_table(thread_used_tables[i]);
	}
	
	free(thread_used_boards);
	free(thread_used_tables);	
}

int **solve(int **matriz)
{
	int ***aux_table;
	int i;
	
	aux_table = create_aux_table();
	init_locks();
	init_threads();
	
	sudoku = matriz;
	
	core_wrap(sudoku, aux_table);
	while(used_threads != 0);
	
	for (i = 0; i < max_threads; i++)
		pthread_join(thread_vec[i], NULL);
	
	/*
	printf("%d\n", is_solved);	
	if (is_solved)
		printM(final);
	*/
	
	/* Salva solução */
	int **result;
	result = create_table();
	copy_table(final, result);

	destroy_all();
	
	return result;
}
