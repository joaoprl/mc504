import java.awt.Point;

public class Worker implements Runnable {
	public void run()
	{
		while(true)
		{
			this.Update();
			this.Draw();
		}
	}
	
	WorkSite workSite;
	Request request;
	Point position;
	String []image;
	int ID;
	BodyFactory bodyFactory;
	TireFactory tireFactory;
	EngineFactory engineFactory;
	Draw draw;
	
	/**
	 * Inicializa funcionário
	 * @param draw Local de impressão
	 * @param workSite Local de Trabalho do Funcionário
	 * @param ID ID do funcionário (deve ser a mesma do workSite)
	 */
	public Worker(Draw draw, WorkSite workSite, int ID, BodyFactory bodyFactory, TireFactory tireFactory, EngineFactory engineFactory)
	{
		this.workSite = workSite;
		request = null;
		this.ID = ID;
		this.position = Positions.getWorkerInitialPosition(ID);
		this.image = Images.getWorker();
		this.bodyFactory = bodyFactory;
		this.tireFactory = tireFactory;
		this.engineFactory = engineFactory;
		this.draw = draw;
	}
	
	/**
	 * Dá um pedido ao funcionário (e funcionário anexa pedido no local de trabalho)
	 * @param request Pedido a ser recebido
	 */
	public void receiveRequest(Request request)
	{
		this.request = request;
		workSite.setRequest(request);
	}
	
	/**
	 * @return Se o funcionário está livre (sem pedidos) retorna true; caso contrário, false
	 */
	public boolean isFree()
	{
		if(request == null) return true;
		return false;
	}
	
	/**
	 * Funcionário faz seu trabalho
	 */
	public void Update()
	{
		outSideIf:
		if(request != null) // Se tem algo a fazer
		{
			if(!workSite.hasABody()) // Seu local de trabalho não tem uma carcaça
			{
				if(bodyFactory.hasABody(request.getBodyType())) // Tem a peça na fábrica
					workSite.addPart(bodyFactory.takeProduct(request.getBodyType())); // Retira a peça
				else bodyFactory.newRequest(request.getBodyType()); // Não tem peça, realiza um pedido
				
				break outSideIf; // Uma ação por vez
			}
			
			if(!workSite.hasATire()) // Seu local de trabalho não tem pneu
			{ 
				if(tireFactory.hasATire(request.getTireType())) // Tem a peça na fábrica
					workSite.addPart(tireFactory.takeProduct(request.getTireType())); // Retira a peça
				else tireFactory.newRequest(request.getTireType()); // Não tem peça, realiza um pedido
				
				break outSideIf; // Uma ação por vez
			}
			
			if(!workSite.hasAnEngine()) // Seu local de trabalho não tem motor
			{
				if(engineFactory.hasAEngine(request.getEngineType())) // Tem a peça na fábrica
					workSite.addPart(engineFactory.takeProduct(request.getEngineType())); // Retira a peça
				else engineFactory.newRequest(request.getEngineType()); // Não tem peça, realiza um pedido
				
				break outSideIf; // Uma ação por vez
			}
			
			if(workSite.carIsComplete()) 
			{
				workSite.clearWorkSite();
				this.request = null; // apaga pedido
			}
		}
	}
	
	/**
	 * Imprime imagem do funcionário
	 * @param draw Local para impressão
	 */
	public void Draw()
	{
		draw.addPrint(image, position);
	}

}
