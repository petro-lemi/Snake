

package snake.domain;

/**
 * klasa klonowana ustawia Pola
 */
public class Point implements Cloneable {
	private int x;
	private int y;

	/**
	 * //Konstruktor domyślny
	 */
	public Point()
	{

	}

	/**
	 *
	 * @param x Konstruktor, który przyjmuje argumenty
	 * @param y Konstruktor, który przyjmuje argumenty
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 *
	 * @param x Ustawia nowe wartości dla współrzędnych
	 * @param y Ustawia nowe wartości dla współrzędnych
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 *
	 * @return Zwraca wartość współrzędnej x punktu
	 */
	public int getX() {
		return x;
	}

	/**
	 *
	 * @param x Ustawia nową wartość dla współrzędnej x punktu.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 *
	 * @return Ustawia nową wartość dla współrzędnej y punktu.
	 */
	public int getY() {
		return y;
	}

	/**
	 *
	 * @param y Otrzymuje wartosci y
	 */
	public void setY(int y) {
		this.y = y;
	}


}
