
public class Scene {
	Background background; // Contem o fundo de tela (margem por enquanto?)
	WorkSite []workSites; // Local de trabalho dos trabalhadores (contem peças já alocadas)
	Worker []workers; // Contem os trabalhadores (um para cada workSite) que possui pedidos a serem feitos
	PendingRequests pendingRequests; // Contem fila de espera de pedidos
	TireFactory tireFactory; // Constrói pneus
	BodyFactory bodyFactory; // Constrói carcaças de carros
	EngineFactory engineFactory; // Constrói motores
	Scheduler scheduler; // Analisa estoque, trabalhadores e requests
	
	/**
	 * Cria todos os objetos do cenário
	 */
	public Scene()
	{
		background = new Background();
		
		workSites = new WorkSite[Constants.numberOfWorkSites];
		for(int i = 0; i < workSites.length; i++)
			workSites[i] = new WorkSite(i);
		
		tireFactory = new TireFactory();
		bodyFactory = new BodyFactory();
		engineFactory = new EngineFactory();
		
		workers = new Worker[Constants.numberOfWorkers];
		for(int i = 0; i < workers.length; i++)
			workers[i] = new Worker(workSites[i], i, bodyFactory, tireFactory, engineFactory);
		
		pendingRequests = new PendingRequests();
		
		scheduler = new Scheduler(pendingRequests, engineFactory, bodyFactory, tireFactory, workSites, workers);
	}
	
	/**
	 * Atualiza todo o cenário
	 */
	public void Update()
	{
		bodyFactory.start();
		engineFactory.start();
		tireFactory.start();
		
		pendingRequests.start();
		
		scheduler.start();
		
		for(int i = 0; i < workers.length; i++)
			workers[i].start();
	}
	
	public void Draw(Draw draw)
	{
		bodyFactory.Draw(draw);
		engineFactory.Draw(draw);
		tireFactory.Draw(draw);
		
		pendingRequests.Draw(draw);
		
		scheduler.Draw(draw);
		
		for(int i = 0; i < workers.length; i++)
			workers[i].Draw(draw);
		
		draw.print();
	}

}
