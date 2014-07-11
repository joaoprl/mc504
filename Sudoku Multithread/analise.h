
void init_mutexes (void);

void analiseInit();

void init_threads2 (void);

void verifica(int **matriz);

void *turnonColuna (void * a);

void *turnonLinha (void * a);

void *turnonQuadrado (void * a);

void thread_verificaColuna(int **matriz, int pos);

void thread_verificaLinha(int **matriz, int pos);

void thread_verificaQuadrado(int **matriz, int pos1, int pos2);

void verificaColuna(int **matriz, int coluna);

void verificaLinha(int **matriz, int linha);

void verificaQuadrado(int **matriz, int linhaInicial, int colunaInicial);

void verificaColunas(int **matriz);

void verificaLinhas(int **matriz);

void verificaQuadrados(int **matriz);

void dica(int **matriz);

int ***create_aux_table2 (void);

int bpos2 (int x, int y);

void analisaDicas_linha (int **matriz, int ***aux_table, int i);

void analisaDicas_coluna (int **matriz, int ***aux_table, int i);

void analisaDicas_quadrado (int **matriz, int ***aux_table, int i);

void *turnon_analisaDicas (void *p);

void thread_analisaDicas (int **matriz, int ***aux_table, int pos);

int ***analisaDicas(int **matriz);

void imprimeDicas(int **matriz, int ***aux_table);

void destroy_all2(int ***aux_table);


