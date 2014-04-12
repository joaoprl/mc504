import java.awt.Point;


public class Engine {
	String[][] image;
	int fase; // Fase atual do motor 
	int maxOfFases; // Número de fases totais
	Point position;
	int type;
	
	/**
	 * Inicializa um motor
	 * @param type Tipo do motor [0,2]
	 * @param position Posição do motor
	 */
	public Engine(int type, Point position)
	{
		image = Images.getEngineImage(type);
		fase = 0;
		maxOfFases = image.length;
		this. position = position;
		this.type = type;
	}
	
	/**
	 * @return Tipo da peça
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
	 * Imprime o motor na posição que ele está agora
	 * @param draw Local de desenho
	 */
	public void Draw(Draw draw)
	{
		draw.addPrint(image[fase], position);
	}

}
