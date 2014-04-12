import java.awt.Point;

public class Positions {
	/**
	 * Construtor privado, não criar classe (classe constante)
	 */
	private Positions()	{}
	
	/**
	 * @return Retorna posição do background (0,0)(?)
	 */
	public static Point getBackgroundPosition()
	{
		return new Point(0,0);
	}
	
	/**
	 * @return Posição do scheduler na matriz cenário
	 */
	public static Point getSchedulerPosition()
	{
		return new Point(0,9);
	}
	
	/**
	 * @return Posição do vetor de pedidos na matriz cenário
	 */
	public static Point getPendingRequestsPosition()
	{
		return new Point(0,13);
	}
	
	/**
	 * Posição do pedido de número 'n' na matriz cenário
	 * @param number Número da quest (começa com 0)
	 * @return Posição do pedido na matriz cenário
	 */
	public static Point getRequestPosition(int number)
	{
		Point pendingRequests = getPendingRequestsPosition();
		return new Point(pendingRequests.x + 6 + 4 * number,pendingRequests.y);
	}
	
	/**
	 * Posição inicial do funcionário na matriz cenário
	 * @param number Número do funcinário
	 * @return Posição inicial do funcionário na matriz cenário
	 */
	public static Point getWorkerInitialPosition(int number)
	{
		Point scheduler = getSchedulerPosition();
		return new Point(scheduler.x + 6 + 7 * number, scheduler.y - 1);
	}
	
	/**
	 * Posição do Local de Trabalho (onde ficam os carros sendo montados) na matriz cenário
	 * @param number Número do local
	 * @return Posição do Local de trabalho
	 */
	public static Point getWorkSitePosition(int number)
	{
		Point scheduler = getSchedulerPosition();
		return new Point(6 + 8 * number + scheduler.x, scheduler.y);
	}
	
	/**
	 * Posição da fábrica de carcaças
	 * @return retorna posição da fábrica de carcaças
	 */
	public static Point getBodyFactoryPosition()
	{
		return new Point(19, 0);
	}
	
	/**
	 * Posição da fábrica de pneus
	 * @return retorna posição da fábrica de pneus
	 */
	public static Point getTireFactoryPosition()
	{
		return new Point(7, 0);
	}
	
	/**
	 * Posição da fábrica de motores
	 * @return retorna posição da fábrica de motores
	 */
	public static Point getEngineFactoryPosition()
	{
		return new Point(13, 0);
	}
}
