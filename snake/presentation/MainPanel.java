package snake.presentation;

import java.awt.*;// zawiera klasy i interfejsy związane z tworzeniem graficznego interfejsu
import java.awt.event.ActionEvent;//
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.Serial;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;
import snake.utils.Utils;

import snake.domain.Block; //importowanie klass
import snake.domain.Food;
import snake.domain.Point;
import snake.domain.Snake;
import snake.domain.Sprite;


public class MainPanel extends JPanel implements Runnable
{
	@Serial
	private static final long serialVersionUID = 1L; //Zmienne i pola:
	private final BufferedImage buffer; //do buforowania grafik
	private Snake snake; //reprezentujący węża.

	private Food food; //reprezentujący pożywienie dla węża.
	private LinkedList<Block> blocks; // Lista bloków reprezentujących przeszkody w grze.
	private boolean gameOver;
	private int iteration = 0; // Licznik iteracji.
	private final Frame frame; //Referencja do obiektu Frame (głównego okna gry).
	private int blocksNumber = 20; //Liczba bloków/przeszkód w grze.
	private int speed = 5; //Prędkość poruszania się węża.
	private int squareSize = 10; //  Rozmiar kwadratowych elementów gry.
	private boolean title = true; //Flaga wskazująca, czy wyświetlany jest tytuł gry


	private boolean directionChanged;

	/**
	 *
	 * @param frame  //Inicjalizuje panel, ustawia rozmiar, tworzy bufor graficzny.
	 * @String key //akcje dla klawiszy sterujących (lewo, prawo, góra, dół, enter) i mapuje je na odpowiednie zdarzenia.
	 */
	public MainPanel(Frame frame)
	{
		this.frame = frame;
		setSize(new Dimension(500, 400));
		setPreferredSize(new Dimension(500, 400));
		buffer = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);

		String key;
		KeyStroke keyStroke;
		AbstractAction leftAction = new AbstractAction() {
			/**
			 *
			 * @param e the event to be processed (left)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(snake.getDirection() == Snake.UP || snake.getDirection() == Snake.DOWN)
				{
					snake.setDirection(Snake.LEFT);	
					snake.move();
					directionChanged = true;
				}			
			}};
		 key = "LEFT";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, leftAction);

		AbstractAction rightAction = new AbstractAction() {
			/**
			 *
			 * @param e the event to be processed (prawo)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(snake.getDirection() == Snake.UP || snake.getDirection() == Snake.DOWN)
				{
					snake.setDirection(Snake.RIGHT);
					snake.move();
					directionChanged = true;
				}		
			}};
		 key = "RIGHT";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, rightAction);
		 
		AbstractAction upAction = new AbstractAction() {
			/**
			 *
			 * @param e the event to be processed (góra)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(snake.getDirection() == Snake.LEFT || snake.getDirection() == Snake.RIGHT)
				{
					snake.setDirection(Snake.UP);
					snake.move();
					directionChanged = true;
				}			
			}};
		 key = "UP";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, upAction);
				 
		AbstractAction downAction = new AbstractAction() {
			/**
			 *
			 * @param e the event to be processed (dół)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(snake.getDirection() == Snake.LEFT || snake.getDirection() == Snake.RIGHT)
				{
					snake.setDirection(Snake.DOWN);
					snake.move();
					directionChanged = true;
				}	
			}};
		 key = "DOWN";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, downAction);
		 
		AbstractAction enterAction = new AbstractAction() {
			/**
			 * Wywołuje metodę repaint().
			 * @param e the event to be processed(Enter)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(title)
				{
					title = false;
					init();
				}
				else if(gameOver)
					init();
			}};
		 key = "ENTER";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, enterAction);




		repaint(); //Wywołuje metodę repaint().
	}

	/**
	 * Inicjalizuje grę, ustawia początkowe wartości.
	 */
	public void init()
	{
		gameOver = false;
		blocks = new LinkedList<Block>();
		
		for(int i = 1; i <= blocksNumber; i++)
		{
			while(true) //Tworzy bloki/przeszkody w losowych pozycjach.
			{
				Block block = new Block();
				block.setX(Utils.random(0, (getWidth() - 1) / squareSize) * squareSize);
				block.setY(Utils.random(0, (getHeight() - 1) / squareSize) * squareSize);
				block.setWidth(squareSize);
				block.setHeight(squareSize);
				
				if(collideBlocks(block))
					continue;
				
				blocks.add(block);
				break;
			}
		}		
		
		while(true) //worzy węża w losowej pozycji, sprawdza, czy wąż nie koliduje z blokami.
		{
			boolean snakeOK = true;
			
			snake = new Snake(Utils.random(0, (getWidth() - 1) / squareSize) * squareSize, 
					Utils.random(0, (getHeight() - 1) / squareSize) * squareSize, squareSize, squareSize,
					Utils.random(0, 3));

			for (Block block : blocks) {
				if (snake.collide(block))
					snakeOK = false;
			}
			
			if(snakeOK) //Tworzy pożywienie w losowej pozycji, sprawdza, czy nie koliduje z blokami.
				break;
		}

		do {
			food = new Food();
			food.setWidth(squareSize);
			food.setHeight(squareSize);
			food.setX(Utils.random(0, (getWidth() - 1) / squareSize) * squareSize);
			food.setY(Utils.random(0, (getHeight() - 1) / squareSize) * squareSize);

		} while (collideBlocks(food)); //Aktualizuje wyświetlane wyniki.
		
		frame.getControlsPanel().getScoreLabel().setText(String.format("%03d", snake.getBody().size() - 1));
		frame.getControlsPanel().disableControls();

		Thread th = new Thread(this);//Włącza wątek Thread do obsługi logiki gry.
		th.start();
	}

	/**
	 * Odpowiada za rysowanie grafiki gry na panelu.
	 * @param g  the <code>Graphics</code> context in which to paint
	 */
	public void paint(Graphics g)
	{	
		Graphics2D g2 = (Graphics2D) buffer.createGraphics();
		
		if(title)
		{
			// Paint background
			g2.setColor(Color.orange);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			

			g2.setColor(Color.black);
			g2.setFont(new Font("Verdana", Font.BOLD, 20));
			g2.drawString(" Gra 'SNAKE'", 180, 170);
			g2.setFont(new Font("Verdana", Font.BOLD, 13));
			g2.drawString("Petro Lemishko,Yelizaveta Shumskaya,Savitskaya Anastasiya\n", 0, 190);
			g2.setFont(new Font("Verdana", Font.BOLD, 10));
			g2.drawString("Hit enter to start", 200, 350);			
		}
		/**
		 * W zależności od aktualnego stanu gry, rysuje tło, bloki, pożywienie, węża i informacje o końcu gry.
		 * // Paint background
		 * // Paint food
		 * // Paint snake
		 */
		else
		{

			g2.setColor(Color.white);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			// Paint blocks
			Iterator<Block> it = blocks.iterator();
			while(it.hasNext())
			{
				Block block = it.next();
				block.paint(g2);
			}
			
			// Paint food
			if(iteration % 2 == 0)
				food.paint(g2);
			
			// Paint snake
			snake.paint(g2);
			
			// Paint game over
			if(gameOver)
			{
				g2.setColor(new Color(200,0,255));
				g2.setFont(new Font("Verdana", Font.BOLD, 70));
				g2.drawString("GAME OVER", 16, this.getHeight() / 2);	
				g2.setFont(new Font("Verdana", Font.BOLD, 30));
				g2.drawString("Hit enter to start", 120, this.getHeight() / 2 + 50);			
			}
		}
		g.drawImage(buffer, 0, 0, this);
	}

	/**
	 * Główna pętla gry, wykonywana w oddzielnym wątku.
	 * Wykonuje ruch węża, sprawdza kolizje i warunki zakończenia gry.
	 */
	@Override
	public void run()
	{		
		while(!gameOver)
		{
			iteration++;
			if(iteration == 10)
				iteration = 0;
			
			if(!directionChanged)
				snake.move();
			else
				directionChanged = false;

			Point point = snake.getBody().get(0);
			if(point.getX() < 0 || point.getX() + snake.getWidth() > this.getWidth() || 
					point.getY() < 0 || point.getY() + snake.getHeight() > this.getHeight())
			{

				gameOver = true;
			}
			
			Iterator<Block> it = blocks.iterator();
			while(it.hasNext())
			{
				Block block = it.next();
				if(snake.collide(block))
				{

					gameOver = true;
				}
			}
			
			if(snake.collide(food))  //Aktualizuje grafikę, opóźnia wykonanie o odpowiednią prędkość.
			{ 

				snake.grow();
				frame.getControlsPanel().getScoreLabel().setText(String.format("%03d", snake.getBody().size() - 1));
				
				while(true)
				{					
					food = new Food();
					food.setWidth(squareSize);
					food.setHeight(squareSize);
					food.setX(Utils.random(0, (getWidth() - 1) / squareSize) * squareSize);
					food.setY(Utils.random(0, (getHeight() - 1) / squareSize) * squareSize);
	
					if(!collideBlocks(food))
						break;
				}
			}
			else if(snake.collideItself())
			{
				gameOver = true;
			}
			
			try {
				Thread.sleep(400 / speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			repaint();
		}
		
		frame.getControlsPanel().enableControls();
	}

	/**
	 *
	 * @param sprite prywatna metoda, która sprawdza kolizję między obiektem sprite a blokami przechowywanymi w liście blocks
	 * @return
	 */
	private boolean collideBlocks(Sprite sprite)
	{
		Iterator<Block> it = blocks.iterator();
		while(it.hasNext())
		{
			Block block = it.next();
			if(sprite.collide(block))
				return true;
		}
		
		return false;
	}

	/**
	 *
	 * @return metoda, która zwraca wartość logiczną określającą, czy tytuł gry jest wyświetlany.
	 */
	public boolean isTitle() {
		return title;
	}

	/**
	 *
	 * @param title metoda, która ustawia wartość logiczną określającą, czy tytuł gry ma być wyświetlany.
	 */
	public void setTitle(boolean title) {
		this.title = title;
	}

	/**
	 *
	 * @return metoda, która zwraca liczbę bloków w grze.
	 */
	public int getBlocksNumber() {
		return blocksNumber;
	}

	/**
	 *
	 * @param blocksNumber metoda, która ustawia liczbę bloków w grze na podstawie przekazanego argumentu
	 */
	public void setBlocksNumber(int blocksNumber) {
		this.blocksNumber = blocksNumber;
	}

	/**
	 *  metoda, która zwraca wartość logiczną określającą, czy gra się zakończyła.
	 * @return
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 *
	 * @return Jest to metoda, która zwraca aktualną prędkość gry.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Jest to metoda, która ustawia prędkość gry na podstawie przekazanego argumentu.
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * metoda, która zwraca rozmiar kwadratu używanego w grze.
	 * @return
	 */
	public int getSquareSize() {
		return squareSize;
	}

	/**
	 *
	 * @param squareSize  Jest to metoda, która ustawia rozmiar kwadratu używany w grze na podstawie przekazanego argumentu.
	 */
	public void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}
}
