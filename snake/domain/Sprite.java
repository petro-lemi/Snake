/**
 * //Deklaracja pakietu(organizacja grupowania klas)
 */
package snake.domain;

public abstract class Sprite implements Drawable {  //Implementyje interface
	protected int x;   //pobierania i ustawiania pozycji sprite'a
	protected int y;
	protected int width;
	protected int height;

	/**
	 *
	 * @return pobierania i ustawiania rozmiaru sprite'a.
	 */
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 *
	 * @param sprite
	 * @return sprawdzenia kolizji między dwoma sprite'ami.
	 */
	public boolean collide(Sprite sprite) {
		if (x + width < sprite.getX() || sprite.getX() + sprite.getWidth() < x
				|| y + width < sprite.getY()
				|| sprite.getY() + sprite.getHeight() < y)
			return false;

		return true;
	}
}
