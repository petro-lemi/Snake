//Wazne:Klasa Snake dziedziczy po klasie Sprite i implementuje metodę paint() z interfejsu Drawable,
package snake.domain;

import java.awt.Color; //manipulacja z  kolorami
import java.awt.Graphics2D;//jest klasą do rysowania na ekranie
import java.util.Iterator;//jest interfejsem, który umożliwia iterację po elementach w kolekcji.
import java.util.LinkedList;// implementacją listy podwójnie powiązane(przechowuje segmenty weza)

/**
 * //reprezentacja możliwe kierunki ruchu węża.
 */
public class Snake extends Sprite
{
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	private LinkedList<Point> body = new LinkedList<Point>();
	private int direction; //  LEFT: 0 | RIGHT: 1 | UP: 2 | DOWN: 3

	/**
	 * Konstruktor, który tworzy obiekt Snake
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param direction
	 */
	public Snake(int x, int y, int width, int height, int direction)
	{
		Point p = new Point(x,y);
		body.add(p);
		
		this.width = width;
		this.height = height;
		this.direction = direction;
	}

	/**
	 * Ustawia nowy kierunek ruchu dla węża.
	 * @param direction
	 */
	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	/**
	 * Zwraca aktualny kierunek ruchu węża.
	 * @return
	 */
	public int getDirection()
	{
		return direction;
	}

	/**
	 * Sprawdza, czy wąż koliduje z danym obiektem Sprite (np. jedzeniem).
	 * @param sprite
	 * @return
	 */
	public boolean collide(Sprite sprite) {
		Iterator<Point> it = body.iterator();
		int i = 0;
		while(it.hasNext())
		{
			Point point = it.next();
			if (point.getX() + width <= sprite.getX() || sprite.getX() + sprite.getWidth() <= point.getX()
					|| point.getY() + width <= sprite.getY()
					|| sprite.getY() + sprite.getHeight() <= point.getY())
				i++;
		}

		return i != body.size();
	}

	/**
	 *
	 * @return Sprawdza, czy wąż koliduje ze swoim własnym ciałem.
	 */
	public boolean collideItself() {
		Iterator<Point> it = body.iterator();
		int i = 0;
		while(it.hasNext())
		{
			Iterator<Point> it2 = body.iterator();
			Point point = it.next();
			
			while(it2.hasNext())	
			{
				Point point2 = it2.next();
				
				if(point.getX() != point2.getX() || point.getY() != point2.getY())
				{
					if (point.getX() + width <= point2.getX() || point2.getX() + width <= point.getX()
							|| point.getY() + width <= point2.getY()
							|| point2.getY() + height <= point.getY())
						i++;
				}
			}
		}

		if((((body.size() * body.size()) - body.size())) == i)
			return false;
		
		return true;
	}

	/**
	 * Metoda odpowiedzialna za rysowanie węża na ekranie.
	 * @param g2
	 */
	@Override
	public void paint(Graphics2D g2) {
		Iterator<Point> it = body.iterator();
		while(it.hasNext())
		{
			Point point = it.next();
			g2.setColor(Color.black);
			g2.fillRect(point.getX(), point.getY(), width, height);
		}
	}

	/**
	 * Dodaje nowy segment do ciała węża, zgodnie z aktualnym kierunkiem ruchu.
	 */
	public void grow()
	{
		Point p =  new Point();
		Point last = body.getLast();

		if(direction == Snake.LEFT)
		{
			p.setLocation(last.getX() + width, last.getY());
		}
		else if(direction == Snake.RIGHT)
		{
			p.setLocation(last.getX() - width, last.getY());
		}
		else if(direction == Snake.UP)
		{
			p.setLocation(last.getX(), last.getY() + height);
		}
		else if(direction == Snake.DOWN)
		{
			p.setLocation(last.getX(), last.getY() - height);
		}
				
		body.addLast(p);		
	}

	/**
	 *  //Przesuwa węża o jeden krok w określonym kierunku. Aktualizuje pozycje segmentów ciała węża na podstawie poprzednich pozycji.
	 */
	public void move()
	{	
		if(body.size() > 0)
		{
			Point point = body.get(body.size() - 1);
			
			for(int i = body.size() - 2; i >= 0; i--)
			{
				Point point2 = body.get(i);

				point.setLocation(point2.getX(), point2.getY());
				
				point = point2;
			}
		}
		
		Point point = body.get(0);
		
		if (direction == Snake.LEFT)
		{
			point.setLocation(point.getX() - width, point.getY());
		}
		else if (direction == Snake.RIGHT)
		{
			point.setLocation(point.getX() + width, point.getY());
		}
		else if (direction == Snake.UP)
		{
			point.setLocation(point.getX(), point.getY() - width);
		}
		else if (direction == Snake.DOWN)
		{
			point.setLocation(point.getX(), point.getY() + width);
		}
	}

	/**
	 *
	 * @return
	 */
	public LinkedList<Point> getBody() {
		return body;
	}

	/**
	 *
	 * @param body
	 */
	public void setBody(LinkedList<Point> body) {
		this.body = body;
	}
}
