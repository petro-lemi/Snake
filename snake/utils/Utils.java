/**
 *Klasa Utils jest przykładem klasy narzędziowej (utility class). Tego rodzaju klasy zawierają zbiór statycznych metod, które
 * wykonują konkretne operacje ogólnego zastosowania. Często są one używane do wykonywania powtarzających się czynności
 * lub operacji pomocniczych, które nie są związane bezpośrednio z żadnym konkretnym obiektem.
 */
package snake.utils;
import java.util.Random;

public class Utils
{
	/**
	 * Generuje losową liczbę całkowitą z zakresu podanego jako argumenty min i max
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max)
	{
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}






}
//java.doc documentacja