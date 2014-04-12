import java.awt.Point;


public class Body {
	String[][] image;
	int fase; // Fase atual da carcaça 
	int maxOfFases; // Número de fases totais
	Point position;
	int type;
	
	/**
	 * Inicializa uma carcaça
	 * @param type Tipo da caraça [0,2]
	 * @param position Posição da carcaça
	 */
	public Body(int type, Point position)
	{
		image = Images.getBodyImage(type);
		fase = 0;
		maxOfFases = image.length;
		this. position = position;
		this.type = type;
	}
	
	/**
	 * @return Retorna o tipo de carcaça
	 */
	public int getType()
	{
		return this.type;
	}
	
	public void Update()
	{
		
	}
	
	public void setPosition(Point position)
	{
		this.position = position;
	}
	
	/**
	 * Imprime a carcaça na posição que ela está agora
	 * @param draw Local de desenho
	 */
	public void Draw(Draw draw)
	{
		draw.addPrint(image[fase], position);
	}

}
