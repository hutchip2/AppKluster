package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;

/**
 * This class constructs the results tab for AppKluster.
 * @author Paul Hutchinson
 *
 */
public class ResultsPanel extends JPanel	{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3123276137909261275L;
	static ArrayList<JLabel> clusters = new ArrayList<JLabel>();
	static ArrayList<JLabel> clusterLabels = new ArrayList<JLabel>();
	static ArrayList<JLabel> selectionLabels = new ArrayList<JLabel>();
	static ArrayList<JLabel> selectionNames = new ArrayList<JLabel>();
	JLabel selections = new JLabel();
	JLabel clusterPerm = new JLabel("CLUSTERS");
	JLabel resultsPerm = new JLabel("RESULTS");
	JLabel allPerm = new JLabel("ALL APPLICANTS");
	JLabel hrLine = new JLabel("------------------------------------------------------------------------------------------------" +
	"---------------------------------------------------------------------");;
	JPanel inner= new JPanel();
	JScrollPane pane;
	/**
	 * Constructor method for the "Results" tab.
	 */
	public ResultsPanel()	{
		inner.setLayout(new MigLayout());
		clusterPerm.setForeground(Color.RED);
		resultsPerm.setForeground(Color.RED);
		allPerm.setForeground(Color.BLUE);
		inner.add(clusterPerm, "wrap");
		for (int i = 0; i < clusters.size(); i++)	{
			clusters.get(i).setText("Cluster " + (i+1) + ": " + clusters.get(i).getText());
			inner.add(clusters.get(i), "wrap");
		}
		inner.add(resultsPerm, "wrap");
		for (int i = 0; i < selectionLabels.size(); i++)	{
			String curAge = selectionLabels.get(i).getText().substring(selectionLabels.get(i).getText().indexOf('(')+1,selectionLabels.get(i).getText().indexOf('.'));
			String curGPA = selectionLabels.get(i).getText().substring(selectionLabels.get(i).getText().indexOf(',')+1,selectionLabels.get(i).getText().lastIndexOf('.'));
			String temp = curGPA.substring(0);
			String gpa = Double.toString(Double.parseDouble(temp)/100);
			String curName;
			for (int j = 0; j < EntryPanel.eachAll.size(); j++)	{
				if (EntryPanel.eachAll.get(j).contains(curAge)&&EntryPanel.eachAll.get(j).contains(gpa))	{
					curName = EntryPanel.eachAll.get(j).substring(EntryPanel.eachAll.get(j).indexOf("Name:")+5, EntryPanel.eachAll.get(j).indexOf("Race:"));
					selectionNames.add(new JLabel(curName));
				}
			}
			selectionLabels.get(i).setText("Selection " + (i+1) + ": " + selectionLabels.get(i).getText());
			inner.add(new JLabel(selectionLabels.get(i).getText()+ " = " + selectionNames.get(i).getText()), "wrap");
		}
		inner.add(hrLine, "wrap");
		inner.add(allPerm, "wrap");
		for (int i = 0; i < EntryPanel.names.size(); i++)	{
			inner.add(new JLabel(EntryPanel.names.get(i)), "wrap");
		}
		pane = new JScrollPane(inner);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		setLayout(new BorderLayout());
		add(pane, BorderLayout.CENTER);
		inner.setBackground(Color.WHITE);
	}
}
