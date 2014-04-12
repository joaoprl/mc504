
public class Images {
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
		// TODO tipificar
		String [][]str = new String[5][1];
		str[0][0] = ".";
		str[0][0] = "ͦ ";
		str[0][0] = "°";
		str[0][0] = "º";
		str[0][0] = "o";
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
		// TODO criar sequencia de construção e tipificar
		str[0][0] = "1";
		str[1][0] = "2";
		str[2][0] = "¥";
		
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
		
		// TODO criar tipos diferentes
		str[0][0] = "       ";
		str[0][1] = "˪ --  ˩";
		
		str[1][0] = "_ ̅ ̅  _";
		str[1][1] = "˪ --  ˩";
		
		str[2][0] = "_/̅ ̅ \\_";
		str[2][1] = "˪ --  ˩";
		
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
	public static String[] getRequest(int quest, int car, int tire, int engine)
	{
		String []str = new String[4]; // 4 linhas
		str[0] = " " + quest  + " " + "|";
		str[1] = " " + car    + " " + "|";
		str[2] = " " + tire   + " " + "|";
		str[3] = " " + engine + " " + "|";
		return str;
	}
}
