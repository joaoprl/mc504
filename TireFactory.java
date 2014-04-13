import java.awt.Point;

public class TireFactory implements Runnable {
	public void run()
	{
		while(true)
		{
			this.Update();
			this.Draw();
		}
	}
	
	Point position; // Posição da fábrica
	Tire [] inventory; // Estoque da fábrica
	Draw draw;
	
	/**
	 * Cria uma nova fábrica
	 * @param draw Local de impressão
	 */
	public TireFactory(Draw draw)
	{
		this.position = Positions.getTireFactoryPosition();
		this.inventory = new Tire[Constants.inventoryLength];
		for(int i = 0; i < inventory.length; i++)
			inventory[i] = null;
		this.draw = draw;
	}
	
	/**
	 * Faz um pedido de um tipo específico de pneu
	 * @param type tipo da pneu
	 */
	public void newRequest(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] == null) inventory[i] = new Tire(type, this.getInventoryPositon(i));
	}
	
	/**
	 * Se a fábrica tem, no inventário um dado tipo da pneu
	 * @param type Tipo de pneu
	 * @return Retorna true se tem a pneu ou false caso contrário
	 */
	public boolean hasATire(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) 
				if(inventory[i].getType() == type && inventory[i].isReady()) return true;
		return false;
	}
	
	/**
	 * Retira um pneu da fábrica (retorna null se não tiver pneu daquele tipo)
	 * @param type Tipo do pneu desejado
	 * @return Pneu do tipo desejado, se existente; null caso contrário
	 */
	public Tire takeProduct(int type)
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) 
				if(inventory[i].getType() == type && inventory[i].isReady())
				{
					Tire tire = inventory[i];
					inventory[i] = null;
					return tire;
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
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) inventory[i].Update();
	}
	
	/**
	 * Imprime peças no inventário
	 * @param draw Imprime peças no inventário
	 */
	public void Draw()
	{
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] != null) inventory[i].Draw(draw);
	}

}
