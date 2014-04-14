import java.awt.Point;
import java.util.concurrent.BrokenBarrierException;

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
	 * Se a fábrica tem, no inventário um dado tipo de motor
	 * @param type Tipo de motor
	 * @return Retorna true se tem o motor, false caso contrário
	 */
	public synchronized boolean hasAEngine(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) 
				if(inventory[i].getType() == type && inventory[i].isReady()) return true;
		return false;
	}
	
	/**
	 * Faz um pedido de um tipo específico de motor
	 * @param type tipo da motor
	 */
	public synchronized void newRequest(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] == null) inventory[i] = new Engine(type, this.getInventoryPositon(i));
	}
	
	/**
	 * Retira um motor da fábrica (retorna null se não tiver motor daquele tipo)
	 * @param type Tipo do motor desejado
	 * @return Motor do tipo desejado, se existente; null caso contrário
	 */
	public synchronized Engine takeProduct(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) 
				if(inventory[i].getType() == type && inventory[i].isReady())
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
		return new Point(this.position.x + 2*(i%2), this.position.y + ((int)i/2));
	}
	
	/**
	 * Atualiza peças no inventário
	 */
	public void Update()
	{
		synchronized (this)
		{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) inventory[i].Update();
		}
		
		try {
			Constants.barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
