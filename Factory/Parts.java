import java.awt.Point;

public abstract class Parts {
	
	String[][] image; // Imagens da peça
	int fase; // Fase atual da peça 
	int maxOfFases; // Número de fases totais
	Point position; // Posição da peça no cenário
	int type; // Tipo da peça
	
	/**
	 * Inicializa uma parte
	 * @param type Tipo da parte
	 * @param position Posição no cenário
	 */
	protected Parts(int type, Point position)	
	{	
		this. position = position;
		this.type = type;
		fase = 0;			
	}

	/**
	 * Atualiza fase
	 */
	public void Update()
	{
		if(fase < maxOfFases) fase++;
	}
	
	/**
	 * Se a construção foi finalizada
	 */
	public boolean isReady()
	{
		if(fase == maxOfFases) return true;
		else return false;
	}
	
	/**
	 * @param position Posição de impressão no cenário
	 */
	public void setPosition(Point position)
	{
		this.position = position;
	}
	
	/**
	 * @return Retorna o tipo da peça
	 */
	public int getType()
	{
		return this.type;
	}
	
	/**
	 * Imprime a carcaça na posição que ela está atualmente
	 * @param draw Local de desenho
	 */
	public void Draw(Draw draw)
	{
		draw.addPrint(image[fase], position);
	}
}
