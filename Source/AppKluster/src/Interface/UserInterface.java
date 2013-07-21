package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * This class constructs the user interface for AppKluster.
 * @author Paul Hutchinson
 *
 */
public class UserInterface extends JFrame	{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4808662780829526189L;
	static JTabbedPane pane = new JTabbedPane();
	/**
	 * Constructor method for the user interface frame.
	 */
	public UserInterface()	{
		setTitle("AppKluster");
		setBackground(Color.WHITE);
		setIconImage(null);
		setSize(800,600);
		setLayout(new BorderLayout());
		centerFrame();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(pane);
		pane.addTab("Data Entry", new EntryPanel());
		pane.addTab("Results", new ResultsPanel());
	
	}
	/**
	 * Centers the JFrame on the screen.
	 */
	public void centerFrame()	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		Dimension screenSize = toolkit.getScreenSize(); 
		int x = (screenSize.width - getWidth()) / 2;  
		int y = (screenSize.height - getHeight()) / 2;   
		setLocation(x, y); 
	}
	/**
	 * Refreshes the data in the "Results" tab.
	 */
	public static void refreshResults()	{
		pane.removeTabAt(1);
		pane.addTab("Results", new ResultsPanel());
		pane.setSelectedIndex(1);
	}
	
}
