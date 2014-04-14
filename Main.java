import java.io.IOException;
import java.util.Scanner;

public class Main{
	public static Scene scene = new Scene();
	public static Draw draw = new Draw(Constants.scenarioLength.y,Constants.scenarioLength.x);
	
	public static void main(String []args)
	{
		/* Seleciona modo de jogo modo =[0 para discreto, 1 para continuo] */
		int modo = -1; // não setado
		
		Scanner scanner = new Scanner(System.in);
		while(modo == -1)
		{	
			System.out.println("Digite o modo de jogo (Discreto/Continuo):");
			String str = scanner.next();
			if(str.equalsIgnoreCase("Discreto"))
			{
				modo = Constants.DiscreteMode;
				break;
			}			
			else if(str.equalsIgnoreCase("Continuo"))
			{
				modo = Constants.ContinueMode;
				break;
			}
		}
		
			if(modo == Constants.DiscreteMode) // Modo discreto 
			{
				System.out.println("Waiting:");
				try{ // Dois read pra poder ler um '\n' (formado por dois bytes).
				System.in.read();
				System.in.read();
				}catch(IOException e) {draw.addLog(e.getMessage());}
				
			}
			
			// Atualização da cena
			scene.Update();
			//scene.Draw(draw);
			
			if(modo == Constants.ContinueMode) // Modo continuo (dorme por sleepLength milisegundos)
			{
				try{
					Thread.sleep(Constants.sleepLength); // Modo continuo, tempo de espera
				
				}catch(InterruptedException e){draw.addLog(e.getMessage());}
			}
	}
}

