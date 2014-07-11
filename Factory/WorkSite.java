import java.awt.Point;


public class WorkSite {
	Body body; 
	Tire tire;
	Engine engine;
	int ID;
	Request request;
	
	/**
	 * Inicializa o workSite (todas as partes são null)
	 */
	public WorkSite(int ID)
	{
		clearWorkSite(); // Marca todas as partes como null
		this.ID = ID;
	}
	
	/**
	 * @param body Adiciona a carcaça ao workSite
	 */
	public void addPart(Body body)
	{
		this.body = body;
		body.setPosition(Positions.getWorkSitePosition(ID));
	}
	
	/**
	 * @param tire Adiciona o pneu ao workSite
	 */
	public void addPart(Tire tire)
	{
		this.tire = tire;
		Point position = Positions.getWorkSitePosition(ID);
		tire.setPosition(new Point(position.x + 1, position.y + 1));
	}
	
	/**
	 * @param engine Adiciona o motor ao workSite
	 */
	public void addPart(Engine engine)
	{
		this.engine = engine;
		Point position = Positions.getWorkSitePosition(ID);
		engine.setPosition(new Point(position.x + 5, position.y + 1));
	}
	
	/**
	 * Anexa pedido à prancheta do local de trabalho
	 * @param request Peiddo a ser anexado
	 */
	public void setRequest(Request request)
	{
		this.request = request;
	}
	
	/**
	 * @return Se o carro tiver todas as partes, retorna true; caso contrário, false
	 */
	public boolean carIsComplete()
	{
		if(body != null && tire != null && engine != null) return true;
		return false;
	}
	
	/**
	 * Limpa local de trabalho (todas as partes vão para null)
	 */
	public void clearWorkSite()
	{
		this.body = null;
		this.tire = null;
		this.engine = null;
		this.request = null;
	}
	
	/**
	 * Atualiza as partes de carro existentes no workSite
	 */
	public void Update()
	{
		if(body != null) body.Update();
		if(tire != null) tire.Update();
		if(engine != null) engine.Update();
	}
	
	/**
	 * @return Se tem um motor no local de trabalho retorna true; false caso contrário
	 */
	public boolean hasAnEngine()
	{
		if(this.engine != null) return true;
		return false;
	}
	
	/**
	 * @return Se tem um pneu no local de trabalho retorna true; false caso contrário
	 */
	public boolean hasATire()
	{
		if(this.tire != null) return true;
		return false;
	}
	
	/**
	 * @return Se tem uma carcaça no local de trabalho retorna true; false caso contrário
	 */
	public boolean hasABody()
	{
		if(this.body != null) return true;
		return false;
	}
	
	/**
	 * Imprime as partes de carro existentes no workSite (duas vezes as rodas)
	 * @param draw Local para impressão
	 */
	public void Draw(Draw draw)
	{
		if(body != null) body.Draw(draw);
		
		if(tire != null) 
		{
			tire.Draw(draw);
			tire.DrawSecondTire(draw);
		}
		
		if(engine != null) engine.Draw(draw);
		
		// Imprime prancheta com pedido
		if(request != null) draw.addPrint(Images.getLinearRequest(request.getBodyType(), request.getTireType(), request.getEngineType()), Positions.getWrokSiteRequestPosition(ID));
	}

}
