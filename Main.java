import java.util.Scanner;

public class Main{
	public static Scene scene = new Scene();
	public static Draw draw = new Draw(Constants.scenarioLength.y,Constants.scenarioLength.x);
	
	public static void main(String []args)
	{
		
		/* Seleciona modo de jogo modo =[0 para discreto, 1 para continuo] */
		int mode = -1; // não setado
		
		Scanner scanner = new Scanner(System.in);
		while(mode == -1)
		{	
			System.out.println("Digite o modo de jogo (Discreto/Continuo [mili-segundos]):");
			String str = scanner.next();
			if(str.equalsIgnoreCase("Discreto"))
			{
				mode = Constants.DiscreteMode;
				break;
			}			
			else if(str.equalsIgnoreCase("Continuo"))
			{
				Constants.sleepLength = scanner.nextInt();
				mode = Constants.ContinueMode;
				break;
			}
		}
		
		Constants.mode = mode;
		scene.Update();
		
		// scanner.close();
	}
	
}

