import java.awt.Point;

public class Draw {
	char[][] scenario; // matriz de cenário do jogo
	String[] log; // log do jogo
	int logPosition; // posição corrente no log
	
	/**
	 * Constrói Draw inicializando um cenário (matriz de caracters da tabela UFT-8) com espaços em branco (' ');
	 * @param linhas Número de linhas do cenário
	 * @param colunas Número de colunas do cenário
	 */
	public Draw(int linhas, int colunas) 
	{
		scenario = new char[linhas][colunas]; // Nova matriz
		for(int i = 0; i < linhas; i++)
			for(int j = 0; j < colunas; j++)
				scenario[i][j] = ' '; // Com caracteres formados por espaços em branco
		
		logPosition = 0; // posição inicial no log é zero
		log = new String[Constants.logLength];
		for(int i = 0; i < log.length; i++)
			log[i] = "";
	}
	
	/**
	 * Adiciona uma sequencia de strings à imagem que sera printada no fim de um ciclo. Se sobrepor qualquer outro caractere é gerado uma linha no log
	 * @param image Sequencia de string. Cada string pertence a uma linha. O local do print é determinado por 'Point position'
	 * @param position Posição no scenario onde deve-se printar a image (dada por uma sequencia de strings)
	 */
	public void addPrint(String []image, Point position)
	{
		for(int i = 0; i < image.length; i++) // String i
			for(int j = 0; j < image[i].length(); j++) // Caractere j
			{
				
				if(scenario[i + position.y][j + position.x] != ' ') // Foi sobreposto um caractere 
					addLog("Caractere '" + scenario[i + position.y][j + position.x] +
							"' sobreposto por '" + image[i].charAt(j) + "' na posição (x,y): ("	+ position.x + "," + position.y + ");"); // Armazena que foi sobreposto um caractere
				scenario[i + position.y][j + position.x] = image[i].charAt(j); // Armazena caractere na matriz cenário
			}
	}
	
	/**
	 * Transforma todos os caracteres do cenário em ' '
	 */
	public void clearScenario()
	{
		for(int i = 0; i < scenario.length; i++)
			for(int j = 0; j < scenario[i].length; j++)
				scenario[i][j] = ' ';
	}
	
	/**
	 * Adiciona uma linha ao log que será printado após o cenário
	 * @param logString String a ser printada
	 */
	public void addLog(String logString)
	{
		if(logPosition < log.length) // Existe posição para printar 
		{
			log[logPosition] = logString;
			logPosition++;
		}
		else // Log no limite, cria mensagem de erro na última posição
		{
			log[log.length - 1] = "Erro: log sobreposto"; 
		}		
	}
	
	/**
	 * Limpa todas as posições do log e reinicializa a constante de posicionamento
	 */
	public void clearLog()
	{
		for(int i = 0; i < log.length; i++)
			log[i] = "";
		logPosition = 0;
	}
	
	/**
	 * Printa cenário e em seguida o log armazenado. A seguir, limpa o log e o cenário
	 */
	public void print()
	{
		for(int i = 0; i < scenario.length; i++)
		{
			for(int j = 0; j < scenario[i].length; j++)
				System.out.print(scenario[i][j]);
			System.out.print("\n");
		}
		
		System.out.printf("\n\nLog:\n");
		for(int i = 0; i < log.length; i++)
			if(!log[i].equals("")) System.out.println(log[i]); 
		
		System.out.println("-------------------------");
		System.out.println();
		
		clearLog();
		clearScenario();
	}
}
