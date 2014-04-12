import java.awt.Point;

public class EngineFactory {
	Point position; // Posição da fábrica
	Engine [] inventory; // Estoque da fábrica
	
	/**
	 * Cria uma nova fábrica
	 */
	public EngineFactory()
	{
		this.position = Positions.getEngineFactoryPosition();
		this.inventory = new Engine[Constants.inventoryLength];
		for(int i = 0; i < inventory.length; i++)
			inventory[i] = null;
	}
	
	/**
	 * Faz um pedido de um tipo específico de motor
	 * @param type tipo da motor
	 */
	public void newRequest(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] == null) inventory[i] = new Engine(type, this.getInventoryPositon(i));
	}
	
	/**
	 * Se a fábrica tem, no inventário um dado tipo de motor
	 * @param type Tipo de motor
	 * @return Retorna true se tem o motor, false caso contrário
	 */
	public boolean hasAEngine(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) 
				if(inventory[i].getType() == type) return true;
		return false;
	}
	
	/**
	 * Retira um motor da fábrica (retorna null se não tiver motor daquele tipo)
	 * @param type Tipo do motor desejado
	 * @return Motor do tipo desejado, se existente; null caso contrário
	 */
	public Engine takeProduct(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) 
				if(inventory[i].getType() == type)
				{
					Engine engine = inventory[i];
					inventory[i] = null;
					return engine;
				}
		return null;
	}
	
	/**
	 * Retorna a posição no cenário de um dado local do inventário
	 * @param i posição no inventário
	 * @return Retorna a posição no cenário de um dado local do inventário
	 */
	Point getInventoryPositon(int i)
	{
		return new Point(this.position.x + i%2, this.position.y + ((int)i/2));
	}
	
	/**
	 * Atualiza peças no inventário
	 */
	public void Update()
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) inventory[i].Update();
	}
	
	/**
	 * Imprime peças no inventário
	 * @param draw Imprime peças no inventário
	 */
	public void Draw(Draw draw)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) inventory[i].Draw(draw);
	}

}
