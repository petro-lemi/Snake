/**
 * Deklaracja pakietu(organizacja grupowania klas)
 */
package snake.domain;
import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends Sprite  //dziedziczy Sprite
/**
 * @Override //oznaczania metody w klasie pochodnej
 * //rysowanie bloku w określonych koordynatach
 */
{
	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.green);
		g2.fillRect(x, y, width, height);
	}
}
