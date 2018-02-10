package prc.unc.BuildingGame;
import java.util.Scanner;
public class ConsoleTest {
	Scanner scanner = new Scanner(System.in);
	String name = scanner.nextLine();
	public static boolean yes = false;
	
	public void tick(){
		if(yes){
		System.out.println("Type");
		System.out.println("." + name);
		}
	}
}
