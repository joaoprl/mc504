import java.awt.Point;


public class Worker {
	WorkSite workSite;
	Request request;
	Point position;
	String []image;
	int ID;
	BodyFactory bodyFactory;
	TireFactory tireFactory;
	EngineFactory engineFactory;
	
	/**
	 * Inicializa funcionário
	 * @param workSite Local de Trabalho do Funcionário
	 * @param ID ID do funcionário (deve ser a mesma do workSite)
	 */
	public Worker(WorkSite workSite, int ID, BodyFactory bodyFactory, TireFactory tireFactory, EngineFactory engineFactory)
	{
		this.workSite = workSite;
		request = null;
		this.ID = ID;
		this.position = Positions.getWorkerInitialPosition(ID);
		this.image = Images.getWorker();
		this.bodyFactory = bodyFactory;
		this.tireFactory = tireFactory;
		this.engineFactory = engineFactory;
	}
	
	/**
	 * Dá um pedido ao funcionário
	 * @param request Pedido a ser recebido
	 */
	public void receiveRequest(Request request)
	{
		this.request = request;
	}
	
	/**
	 * @return Se o funcionário está livre (sem pedidos) retorna true; caso contrário, false
	 */
	public boolean isFree()
	{
		if(request == null) return true;
		return false;
	}
	
	public void Update()
	{
		if(request != null)
		{
			if(bodyFactory.hasABody(request.getBodyType()))
				workSite.addPart(bodyFactory.takeProduct(request.getBodyType()));
			else bodyFactory.newRequest(request.getBodyType());
			
			if(tireFactory.hasATire(request.getTireType()))
				workSite.addPart(tireFactory.takeProduct(request.getTireType()));
			else tireFactory.newRequest(request.getTireType());
			
			if(engineFactory.hasAEngine(request.getEngineType()))
				workSite.addPart(engineFactory.takeProduct(request.getEngineType()));
			else engineFactory.newRequest(request.getEngineType());
		}
	}
	
	/**
	 * Imprime imagem do funcionário
	 * @param draw Local para impressão
	 */
	public void Draw(Draw draw)
	{
		draw.addPrint(image, position);
	}

}
