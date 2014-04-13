import java.awt.Point;
import java.util.Random;

public abstract class Constants {
	/**
	 * Construtor privado, não criar classe (classe constante)
	 */
	private Constants(){}
	
	public static final Point scenarioLength = new Point(50,17); // Tamanho do cenário
	
	public static final int sleepLength = 1000; // Tempo que o modo cotinuo espera para iniciar um novo ciclo
	
	public static final int DiscreteMode = 1; // Modo de Execução discreto
	public static final int ContinueMode = 0; // Modo de Execução contínuo
	
	public static final int logLength = 20; // Tamanho do log
	
	public static final int inventoryLength = 6; // Número de partes máximo no estoque de cada fábrica (motores e rodas)
	public static final int bodyInventoryLenght = 3; // Número de partes máximo no estoque da fábrica de carcaças
	
	public static final int pendingRequestsMax = 6; // Número máximo de pedidos em espera
	
	public static final Random rand = new Random(10); // Gerador de números aleatórios (usado para criar novos pedidos por 'PendingRequests')
	
	public static final int numberOfBodyTypes = 3; // Número de tipos de carcaças
	public static final int numberOfEngineTypes = 3; // Número de tipos de motores
	public static final int numberOfTireTypes = 3; // Número de tipos de pneus
	
	public static final int numberOfWorkSites = 3; // Número de locais de trabalho (deve ser igual ao número de trabalhadores)
	public static final int numberOfWorkers = 3; // Número de trabalhadores (deve ser igual ao número de locais de trabalho)
}
