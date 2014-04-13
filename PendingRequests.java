import java.awt.Point;

public class PendingRequests {	
	Request []pendingRequests;
	String[] image;
	Point position;
	
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
	}
	
	/**
	 * Usado para analisar um pedido da fila. Não deve ser usado para retirar o pedido da fila
	 * @param ID Qual posição deseja-se analisar
	 * @return Retorna pedido naquela posição
	 */
	public Request peekRequest(int ID)
	{
		return pendingRequests[ID];		
	}
	
	/**
	 * Retira um pedido da fila de espera e envia o pedido marcado como não printável no retorno
	 * @param ID ID do pedido desejado
	 * @return Pedido desejado
	 */
	public Request getRequest(int ID)
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
	
	/**
	 * Cria novos pedidos aleatórios
	 */
	public void Update()
	{
		generateRequest();
	}
	
	/**
	 * Cria um novo pedido aleatório e o adiciona à fila de espera
	 */
	public void generateRequest()
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
		return pendingRequests.length;
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

}
