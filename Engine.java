import java.awt.Point;


public class Engine extends Parts{
	/**
	 * Inicializa um motor
	 * @param type Tipo do motor [0,2]
	 * @param position Posição do motor
	 */
	public Engine(int type, Point position)
	{
		super(type, position);
		image = Images.getEngineImage(type);
		maxOfFases = image.length - 1;
	}	
}
