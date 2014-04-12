import java.awt.Point;

public class Background {
	String []image;
	Point position;
	
	/**
	 * Cria novo background
	 */
	public Background()
	{
		this.image = Images.getBackground();
		this.position = Positions.getBackgroundPosition();
	}
	
	/**
	 * Imprime background
	 * @param draw Local para impressão
	 */
	public void Draw(Draw draw)
	{
		draw.addPrint(image, position);
	}

}
