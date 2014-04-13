import java.awt.Point;

public class Request {
	int ID;
	int bodyType;
	int engineType;
	int tireType;
	
	boolean drawable;	
	String[] image;
	Point position;
		
	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the bodyType
	 */
	public int getBodyType() {
		return bodyType;
	}

	/**
	 * @return the engineType
	 */
	public int getEngineType() {
		return engineType;
	}	

	/**
	 * @return the tireType
	 */
	public int getTireType() {
		return tireType;
	}
	
	/**
	 * Cria um novo pedido
	 * @param ID ID do pedido na fila de pedidos correntes (começando por 0)
	 * @param bodyType tipo da carcaça
	 * @param engineType tipo do motor
	 * @param tireType tipo do pneu
	 */
	public Request(int ID, int bodyType, int engineType, int tireType)
	{	
		this.bodyType = bodyType;
		this.engineType = engineType;
		this.tireType = tireType;	
		this.drawable = true;
		newID(ID);
	}

	/**
	 * Coloca novo valor para ID e atualiza imagem e local de impressão
	 * @param ID novo valor de ID. Se -1 considera como flag para não imprimir pedido
	 */
	public void newID(int ID)
	{
		this.ID = ID;
		this.image = Images.getRequest(ID, bodyType, tireType, engineType);
		this.position = Positions.getRequestPosition(ID);
		if(ID == -1) drawable = false;
	}
	
	/**
	 * Imprime pedido na fila de pedidos
	 * @param draw Local para impressão
	 */
	public void Draw(Draw draw)
	{
		if(drawable) draw.addPrint(image, position); 
	}

}
