/**
 * Klasa Frame jest odpowiedzialna za utworzenie i konfigurację głównego okna aplikacji
 */


package snake.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;//reprezentuje wymiary obiektu (szerokość i wysokość).
import java.awt.Toolkit;
import java.io.Serial;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *  //jawa swimg ,  implementuje interfejs
 */
public class Frame extends JFrame
{
	@Serial
	private static final long serialVersionUID = 1L; //zapewnienia zgodności serializacji obiektu.
	private final MainPanel mainPanel = new MainPanel(this);//tworzy obiekt MainPanel (mainPanel) i przekazuje do niego referencję do bieżącego obiektu Frame.
	private final ControlsPanel controlsPanel = new ControlsPanel(mainPanel);

	/**
	 *  //publiczny i nie przyjmuje żadnych argumentów
	 */
	public Frame()
	{
		getContentPane().add(controlsPanel, BorderLayout.SOUTH);//Dodaje panel controlsPanel do kontenera głównego (getContentPane()) przy użyciu układu BorderLayout na południowej pozycji.
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //Pobiera wymiary ekranu (Dimension d) za pomocą klasy Toolkit i ustawia pozycję okna na środku ekranu.
		setLocation((d.width - getSize().width) / 2,
				(d.height - getSize().height) / 2);
		setTitle("Snake");
		setResizable(false);	
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 *
	 * @return Zwraca referencję do obiektu controlsPanel.
	 */

	public ControlsPanel getControlsPanel() {
		return controlsPanel;
	}

	/**
	 * Zwraca referencję do obiektu mainPanel.
	 * @return
	 */
	public MainPanel getMainPanel() {
		return mainPanel;
	}
}
