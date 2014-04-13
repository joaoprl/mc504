import java.awt.Point;


public class Tire extends Parts{
	/**
	 * Inicializa um pneu
	 * @param type Tipo do pneu [0,2]
	 * @param position Posição do pneu
	 */
	public Tire(int type, Point position)
	{
		super(type, position);
		image = Images.getTireImage(type);
		maxOfFases = image.length - 1;
	}
	
	/**
	 * Imprime segunda roda (quando o carro está sendo montado)
	 * @param draw Local de impressão
	 */
	public void DrawSecondTire(Draw draw)
	{
		draw.addPrint(image[fase], new Point(position.x + 3, position.y));
	}
}
