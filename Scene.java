import java.io.IOException;
import java.util.Scanner;

public class Scene implements Runnable {
	public void run()
	{
		while(true)
		{
			
			this.Draw();
			
			this.Update();
			
		}
	}
	
	//Background background; // Contem o fundo de tela (margem por enquanto?)
	WorkSite []workSites; // Local de trabalho dos trabalhadores (contem peças já alocadas)
	Worker []workers; // Contem os trabalhadores (um para cada workSite) que possui pedidos a serem feitos
	PendingRequests pendingRequests; // Contem fila de espera de pedidos
	TireFactory tireFactory; // Constrói pneus
	BodyFactory bodyFactory; // Constrói carcaças de carros
	EngineFactory engineFactory; // Constrói motores
	Scheduler scheduler; // Analisa estoque, trabalhadores e requests
	
	Draw draw;
	int mode;
	
	/**
	 * Cria todos os objetos do cenário
	 */
	public Scene()
	{
		selectMode(); // seleciona metodo (discreto/continuo)	
		
		draw = new Draw(Constants.scenarioLength.y,Constants.scenarioLength.x);		
		
		//background = new Background();
		
		workSites = new WorkSite[Constants.numberOfWorkSites];
		for(int i = 0; i < workSites.length; i++)
			workSites[i] = new WorkSite(i);
		
		(new Thread(tireFactory = new TireFactory(draw))).start();
		(new Thread(bodyFactory = new BodyFactory(draw))).start();
		(new Thread(engineFactory = new EngineFactory(draw))).start();
		
		workers = new Worker[Constants.numberOfWorkers];
		for(int i = 0; i < workers.length; i++)
			(new Thread(workers[i] = new Worker(draw, workSites[i], i, bodyFactory, tireFactory, engineFactory))).start();
		
		(new Thread(pendingRequests = new PendingRequests(draw))).start();
		
		(new Thread(scheduler = new Scheduler(draw, pendingRequests, engineFactory, bodyFactory, tireFactory, workSites, workers))).start();
	}
	
	/**
	 * Seleciona modo de execução
	 */
	public void selectMode()
	{
		/* Seleciona modo de jogo modo =[0 para discreto, 1 para continuo] */
		mode = -1; // não setado
		
		Scanner scanner = new Scanner(System.in);
		while(mode == -1)
		{	
			System.out.println("Digite o modo de jogo (Discreto/Continuo):");
			String str = scanner.next();
			if(str.equalsIgnoreCase("Discreto"))
			{
				mode = Constants.DiscreteMode;
				break;
			}			
			else if(str.equalsIgnoreCase("Continuo"))
			{
				mode = Constants.ContinueMode;
				break;
			}
		}
	}
	
	/**
	 * Atualiza todo o cenário
	 */
	public void Update()
	{			
		/* Sequencia de cena */
		if(mode == Constants.DiscreteMode) // Modo discreto 
		{
			//Scanner scanner = new Scanner(System.in);
			System.out.println("Waiting:");
			try{ // Dois read pra poder ler um '\n' (formado por dois bytes).
			System.in.read();
			System.in.read();
			}catch(IOException e) {draw.addLog(e.getMessage());}
			//finally{scanner.close();}				
		}
		
		if(mode == Constants.ContinueMode) // Modo continuo (dorme por sleepLength milisegundos)
		{
			try{
				Thread.sleep(Constants.sleepLength); // Modo continuo, tempo de espera
			
			}catch(InterruptedException e){draw.addLog(e.getMessage());}
		}	
	}	
		
	/**
	 * Imprime cenário
	 */
	public void Draw()
	{
		draw.print();
	}

}
