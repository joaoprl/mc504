import java.awt.Point;


public class Body extends Parts{	
	/**
	 * Inicializa uma carcaça
	 * @param type Tipo da caraça [0,2]
	 * @param position Posição da carcaça
	 */
	public Body(int type, Point position)
	{
		super(type, position);
		image = Images.getBodyImage(type);
		maxOfFases = image.length - 1;
	}
}
