
public abstract class Images {
	/**
	 * Construtor privado, não criar classe (classe constante)
	 */
	private Images(){}
	
	/**
	 * Primeiro colchete indica fase de print e o segundo a linha
	 * @param type tipo do pneu (1,2,3)
	 * @return Matriz onde a linha indica a fase do print e a coluna a sequencia de linhas do print
	 */
	public static String[][] getTireImage(int type) 
	{
		String [][]str;
		if(type == 1)
		{
			str = new String[6][1];			
			str[0][0] = ".";
			str[1][0] = "°";
			str[2][0] = "º";
			str[3][0] = "o";
			str[4][0] = "O";
			str[5][0] = "ϴ";
		}
		else if(type == 2)
		{
			str = new String[5][1];			
			str[0][0] = "Δ";
			str[1][0] = "O";
			str[2][0] = "o";
			str[3][0] = "0";
			str[4][0] = "ʘ";
		}
		else
		{
			str = new String[5][1];			
			str[0][0] = ".";
			str[1][0] = "°";
			str[2][0] = "º";
			str[3][0] = "o";
			str[4][0] = "O";
		}
		return str;
	}
	
	/**
	 * Primeiro colchete indica fase de print e o segundo a linha
	 * @param type tipo do motor (1,2,3)
	 * @return Matriz onde a linha indica a fase do print e a coluna a sequencia de linhas do print
	 */
	public static String[][] getEngineImage(int type) 
	{
		String [][]str = new String[3][1];
		if(type == 1)
		{
			str = new String[3][1];
			str[0][0] = "N";
			str[1][0] = "NJ";
			str[2][0] = "Ǌ";
		}
		else if(type == 2)
		{
			str = new String[5][1];
			str[0][0] = "d";
			str[1][0] = "D";
			str[2][0] = "Dz";
			str[3][0] = "DZ";
			str[4][0] = "Ǆ";			
		}
		else
		{
			str = new String[3][1];
			str[0][0] = "i";
			str[1][0] = "Y";
			str[2][0] = "¥";
		}
		
		return str;
	}
	
	/**
	 * Primeiro colchete indica fase de print e o segundo a linha de print
	 * @param type tipo da carcaça (1,2,3)
	 * @return Matriz onde a linha indica a fase do print e a coluna a sequencia de linhas do print
	 */
	public static String[][] getBodyImage(int type) 
	{
		String [][]str = new String[3][2];
		if(type == 1)
		{
			str[0][0] = "       ";
			str[0][1] = "˪ __  ˩";
			
			str[1][0] = "_ ДД ʌ";
			str[1][1] = "˪ __  ˩";
			
			str[2][0] = "_/ДД\\ʌ";
			str[2][1] = "˪ __  ˩";
			
		}
		else if(type == 2)
		{
			str[0][0] = "       ";
			str[0][1] = "˪ --  ˩";
			
			str[1][0] = "_  |__";
			str[1][1] = "˪ --  ˩";
			
			str[2][0] = "_ʎ |__";
			str[2][1] = "˪ --  ˩";
		}
		else
		{
			str[0][0] = "       ";
			str[0][1] = "˪ --  ˩";
			
			str[1][0] = "_ ΠΠ _";
			str[1][1] = "˪ --  ˩";
			
			str[2][0] = "_/ΠΠ\\_";
			str[2][1] = "˪ --  ˩";
		}		
		return str;
	}
	
	/**
	 * Imagem de fundo
	 * @return Retorna strings da imagem de fundo
	 */
	public static String[] getBackground()
	{
		String str[] = new String[Constants.scenarioLength.y];
		for(int i = 0; i < str.length; i++)
			str[i] = "                    ";
		return str;
	}
	
	/**
	 * Retorna imagem do sheduler (contém linha de produção)
	 * @return Vetor de strings contendo imagem do scheduler
	 * ɱɱɱ   (carro1)(carro2)(carro3)
	 * ɮɮɮ   (site1) (site2) (site3)
	 * ɯɯɯ__|_______|_______|_______|__
	 */
	public static String[] getScheduler()
	{
		String []str = new String[3];
		str[0] = "ɱɱɱ";
		str[1] = "ɮɮɮ";
		str[2] = "ɯɯɯ__|_______|_______|_______|__";
		return str;
	}
	
	/**
	 * Retorna imagem do trabalhador (\o/)
	 * @return Imagem de um trabalhador
	 */
	public static String[] getWorker()
	{
		String[] str = new String[1];
		str[0] = "\\o/";
		return str;
	}
	
	/**
	 * Imagem da prancheta de pedidos
	 * |Ques|
	 * |Car |
	 * |Tire|
	 * |Engi|
	 * @return
	 */
	public static String[] getPendingRequests()
	{
		String []str = new String[4];
		str[0] = "|Ques|";
		str[1] = "|Car |";
		str[2] = "|Tire|";
		str[3] = "|Engi|";
		return str;
	}
	
	/**
	 * Imagem contendo um request
	 *  3 |
	 *  2 |
	 *  1 |
	 *  2 |
	 * @param quest digito do pedido
	 * @param car digito do tipo de carcaça
	 * @param tire digito do tipo de pneu
	 * @param engine digito do tipo de motor
	 * @return Vetor de string Request contendo tudo isso
	 */
	public static String[] getRequest(int quest, int body, int tire, int engine)
	{
		String []str = new String[4]; // 4 linhas
		str[0] = " " + quest  + " " + "|";
		str[1] = " " + body   + " " + "|";
		str[2] = " " + tire   + " " + "|";
		str[3] = " " + engine + " " + "|";
		return str;
	}

	public static String[] getLinearRequest(int body, int tire, int engine)
	{
		String []str = new String[1];
		str[0] = new String(body + " " + tire + " " + engine + " ");
		return str;
	}
}
