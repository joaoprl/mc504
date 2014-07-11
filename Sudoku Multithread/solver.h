int ***create_aux_table (void);

int idle_threads_available (void);

void init_locks (void);

void init_threads (void);

int **create_table (void);

void copy_table (int **source, int **target);

void copy_used_table (int **source, int **target);

void destroy_table (int **table);

void copy_aux_table (int ***source, int ***target);

void destroy_aux_table (int ***table);

int bpos (int x, int y);

int core_wrap (int **board, int ***aux_table);

void *turnon_thread (void *p);

void new_thread_solve(int next_x, int next_y, int **board, int **used_x, int **used_y, int **used_block);

int solved (int **board);

int core_sudoku (int x, int y, int **board, int **used_x, int **used_y, int **used_block);

void read (void);

void destroy_all (void);

int **solve(int **matriz);