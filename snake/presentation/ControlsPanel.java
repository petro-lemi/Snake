
package snake.presentation;

import java.awt.Dimension; //reprezentuje wymiary komponentu.
import java.awt.FlowLayout; //zarządza rozmieszczeniem komponentów
import java.awt.Font;//prezentuje czcionkę używaną do renderowania tekstu.
import java.awt.event.ActionEvent;// reprezentuje zdarzenie akcji.
import java.awt.event.ActionListener;//nasłuchuje zdarzeń akcji.
import java.io.Serial;// kontrolowania serializacji.
import java.util.Calendar;//a funkcjonalność związane z datą i czasem.

import javax.swing.*;// tworzeniu interfejsu użytkownika.
import javax.swing.border.BevelBorder;//kontrolowania serializacji obiektu.
import javax.swing.event.ChangeEvent;// zdarzenie zmiany, które jest generowane, gdy stan komponentu zmienia się
import javax.swing.event.ChangeListener;//zmiennia poprzedznie

/**
 *  //javax.swing
 */
public class ControlsPanel extends JPanel {
	@Serial
	private static final long serialVersionUID = 1L;
	private final JButton startButton = new JButton(); // Przycisk "START" służący do rozpoczęcia gry.
	private final JSlider blocksSlider = new JSlider(); // Suwak do wyboru liczby bloków w grze.
	private final JSlider speedSlider = new JSlider(); // Suwak do wyboru prędkości poruszania się węża.
	private final JSlider sizeSlider = new JSlider(); //Suwak do wyboru rozmiaru planszy.
	private final JLabel scoreLabel = new JLabel(); //  Etykieta wyświetlająca aktualny wynik gracza.

		/**
		 * inicjalizuje wszystkie elementy panelu sterowania, ustawia ich preferowane wymiary oraz dodaje je do panelu.
		 * @param panel
		 */
	public ControlsPanel(final MainPanel panel)
	{
		FlowLayout f = new FlowLayout();
		f.setHgap(10); // przerwa między komponentami na 10 pikseli.
		this.setLayout(f);
		this.setBorder(new BevelBorder(1)); // obramowanie panelu
		
		startButton.setPreferredSize(new Dimension(85, 40)); //Ustawia preferowane wymiary przycisku
		startButton.setText("START");
		startButton.addActionListener(new ActionListener(){ //Dodaje słuchacza zdarzeń typu który reaguje na kliknięcie i wykonuje odpowiednie akcje w zależności od stanu panelu MainPanel
			/**
			 * //obsługa przycisku start
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panel.isTitle())
				{
					panel.setTitle(false);
					panel.init();
				}
				else if(panel.isGameOver())
					panel.init();
			}
		});
		
		speedSlider.setPreferredSize(new Dimension(60, 40));//wybierasz rozmiar
		speedSlider.setMinimum(1);
		speedSlider.setMaximum(10);
		speedSlider.setMajorTickSpacing(2);
		speedSlider.setToolTipText("Speed");
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setValue(panel.getSpeed());
		speedSlider.addChangeListener(new ChangeListener(){
			/**
			 *
			 * @param arg0  a ChangeEvent object
			 */
			@Override
			public void stateChanged(ChangeEvent arg0) {
				panel.setSpeed(speedSlider.getValue());
				startButton.requestFocus();				
			}	
		});

		blocksSlider.setPreferredSize(new Dimension(80, 40));
		blocksSlider.setMinimum(0);
		blocksSlider.setMaximum(60);
		blocksSlider.setMajorTickSpacing(20);
		blocksSlider.setToolTipText("Blocks");
		blocksSlider.setPaintTicks(true);
		blocksSlider.setPaintLabels(true);
		blocksSlider.setValue(panel.getBlocksNumber());
		blocksSlider.addChangeListener(new ChangeListener(){
			/**
			 *
			 * @param arg0  a ChangeEvent object
			 */
			@Override
			public void stateChanged(ChangeEvent arg0) {
				panel.setBlocksNumber(blocksSlider.getValue());
				startButton.requestFocus();
			}	
		});

		sizeSlider.setPreferredSize(new Dimension(50, 40));
		sizeSlider.setMinimum(1);
		sizeSlider.setMaximum(3);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setToolTipText("Board size");
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		
		if(panel.getSquareSize() == 5)
			sizeSlider.setValue(1);
		else if(panel.getSquareSize() == 10)
			sizeSlider.setValue(2);
		else if(panel.getSquareSize() == 20)
			sizeSlider.setValue(3);
		else
			throw new IllegalArgumentException("Square size not allowed.");
				
		sizeSlider.addChangeListener(new ChangeListener(){
			/**
			 * Zmiana i aktualizacjia suwaków
			 * @param arg0  a ChangeEvent object
			 */
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int value;
				if(sizeSlider.getValue() == 1)
					value = 5;
				else if(sizeSlider.getValue() == 2)
					value = 10;
				else
					value = 20;
				
				panel.setSquareSize(value);
				startButton.requestFocus();
			}	
		});
		
		scoreLabel.setPreferredSize(new Dimension(70, 40));
		scoreLabel.setText("000");
		scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 30));

		JButton helpButton = new JButton();
		helpButton.setPreferredSize(new Dimension(55, 40));
		helpButton.setText("PWR");
		helpButton.addActionListener(new ActionListener(){
			/**
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
			    JOptionPane.showMessageDialog(panel,"Snake" + Calendar.getInstance().get(Calendar.YEAR) + "Politechnika Wroclawska \n Programowamie Objectowe" + JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		this.add(startButton);
		this.add(speedSlider);
		this.add(blocksSlider);
		this.add(sizeSlider);
		this.add(scoreLabel);
		this.add(helpButton);
	}

	/**
	 *
	 * @return Metoda zwraca referencję do etykiety scoreLabel, która wyświetla aktualny wynik gracza.
	 */
	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	/**
	 * //Metoda wyłącza możliwość interakcji użytkownika z elementami panelu sterowania.
	 */
	public void disableControls()
	{
		startButton.setEnabled(false);
		blocksSlider.setEnabled(false);
		speedSlider.setEnabled(false);
		sizeSlider.setEnabled(false);
	}

	/**
	 * //Metoda włącza możliwość interakcji użytkownika z elementami panelu sterowania.
	 */
	public void enableControls()
	{
		startButton.setEnabled(true);
		blocksSlider.setEnabled(true);
		speedSlider.setEnabled(true);
		sizeSlider.setEnabled(true);
	}
}
