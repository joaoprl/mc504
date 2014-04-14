import java.awt.Point;
import java.util.concurrent.BrokenBarrierException;

public class PendingRequests extends Thread{	
	Request []pendingRequests;
	String[] image;
	Point position;
	private static Object lock[] = new Object[3];
	
	
	
	/**
	 * Cria nova fila de pedidos
	 */
	public PendingRequests()
	{	
		this.image = Images.getPendingRequests();
		this.position = Positions.getPendingRequestsPosition();
		pendingRequests = new Request[Constants.pendingRequestsMax];
		for(int i = 0; i < pendingRequests.length; i++)
			pendingRequests[i] = null;
		for (int i = 0; i < 3; i++)
			lock[i] = new Object();
	}
	
	/**
	 * Usado para analisar um pedido da fila. Não deve ser usado para retirar o pedido da fila
	 * @param ID Qual posição deseja-se analisar
	 * @return Retorna pedido naquela posição
	 */
	public Request peekRequest(int ID)
	{
		synchronized (lock[0])
		{
			return pendingRequests[ID];	
		}
	}
	
	/**
	 * Retira um pedido da fila de espera e envia o pedido marcado como não printável no retorno
	 * @param ID ID do pedido desejado
	 * @return Pedido desejado
	 */
	public Request getRequest(int ID)
	{
		synchronized (lock[0])
		{
			synchronized (lock[1])
			{
				synchronized (lock[2])
				{
					Request request = pendingRequests[ID];
					for(int i = ID; i < pendingRequests.length - 1; i++)
					{
						pendingRequests[i] = pendingRequests[i+1];
						if(pendingRequests[i] != null)pendingRequests[i].newID(i);
					}
					pendingRequests[pendingRequests.length - 1] = null;
					request.newID(-1);
					return request;						
				}		
			}
		}
	}
	
	/**
	 * Cria novos pedidos aleatórios
	 */
	private void Update()
	{
		synchronized (lock[0])
		{
			synchronized (lock[1])
			{
				synchronized (lock[2])
				{
					generateRequest();			
				}		
			}
		}
		try {
			Constants.barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Cria um novo pedido aleatório e o adiciona à fila de espera
	 */
	private void generateRequest()
	{
		for(int i = 0; i < pendingRequests.length; i++)
		{
			if(pendingRequests[i] == null)
			{
				pendingRequests[i] = new Request(i, Constants.rand.nextInt(Constants.numberOfBodyTypes), 
						Constants.rand.nextInt(Constants.numberOfBodyTypes), Constants.rand.nextInt(Constants.numberOfBodyTypes));
				break;
			}
		}
	}
	
	/**
	 * @return Número máximo de pedidos na fila de espera
	 */
	public int getSize()
	{
		synchronized (lock[1])
		{
			return pendingRequests.length;
		}
	}
	
	/**
	 * Imprime lista de espera de pedidos
	 * @param draw Local de impressão
	 */
	public void Draw(Draw draw)
	{
		draw.addPrint(image, position);
		for(int i = 0; i < pendingRequests.length; i++)
			if(pendingRequests[i] != null) pendingRequests[i].Draw(draw);
	}
	
	public void run()
	{
		while (true)
			Update();
	}
}
