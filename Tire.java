import java.awt.Point;


public class Tire {
	String[][] image;
	int fase; // Fase atual do pneu 
	int maxOfFases; // Número de fases totais
	Point position;
	int type;
	
	/**
	 * Inicializa um pneu
	 * @param type Tipo do pneu [0,2]
	 * @param position Posição do pneu
	 */
	public Tire(int type, Point position)
	{
		image = Images.getTireImage(type);
		fase = 0;
		maxOfFases = image.length;
		this. position = position;
		this.type = type;
	}

	/**
	 * @return Retorna o tipo do pneu
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
	 * Imprime o pneu na posição que ele está agora
	 * @param draw Local de desenho
	 */
	public void Draw(Draw draw)
	{
		draw.addPrint(image[fase], position);
	}

}
