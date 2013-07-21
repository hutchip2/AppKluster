package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import com.stromberglabs.cluster.Cluster;
import com.stromberglabs.cluster.KMeansClusterer;
import com.stromberglabs.cluster.Point;

/**
 * This class builds the data entry tab on the user interface.
 * @author Paul Hutchinson
 *
 */
public class EntryPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6685586137194533228L;
	static ArrayList<String>names   = new ArrayList<String>();
	static ArrayList<String> eachAll = new ArrayList<String>();
	int clusterCount = 0;
	int numApplications = 0;
	JButton clearInputs = new JButton("Clear Inputs");
	JButton loadDefault = new JButton("Use Default Data/Inputs...");
	JButton generate = new JButton("GENERATE");
	JLabel df = new JLabel("Difference Index: ");
	JLabel sd = new JLabel("Standard Deviation: ");
	JLabel nc = new JLabel("Number Of Centroids: ");
	JLabel it = new JLabel("Number To Pick: ");
	JLabel la = new JLabel("Load Applications: ");
	JLabel attr1 = new JLabel("Attribute 1: ");
	JLabel attr2 = new JLabel("Attribute 2: ");
	JLabel distanceMeasure = 	new JLabel("          Distance Measure: Euclidean");
	JLabel dimension = 			new JLabel("                      Dimensions: 2");
	JLabel attra = 				new JLabel("                        X Attribute: Age");
	JLabel attrb = 				new JLabel("                        Y Attribute: GPA");
	JLabel numApps = 			new JLabel("Number of Applications: ");
	JLabel num =				new JLabel("0");
	JTextField diffIndex = new JTextField("0");
	JTextField standardDev = new JTextField("0");
	JTextField numClusters = new JTextField("0");
	JTextField numToPick = new JTextField("0");
	ArrayList<Point> values = new ArrayList<Point>();
	ArrayList<String> applications = new ArrayList<String>();
	ArrayList<Point> selections = new ArrayList<Point>();
	ArrayList<String>races   = new ArrayList<String>();
	ArrayList<String>genders = new ArrayList<String>();
	ArrayList<Integer> gpas   = new ArrayList<Integer>();
	ArrayList<Integer>ages   = new ArrayList<Integer>();
	ArrayList<Integer>ids	= new ArrayList<Integer>();
	JButton browse = new JButton("Browse...");
	String appStr;
	KMeansClusterer kmeans = new KMeansClusterer();
	Cluster[] results;
	/**
	 * Constructor method for the data entry panel.
	 */
	public EntryPanel()	{
		setLayout(new MigLayout());
		diffIndex.setPreferredSize(new Dimension(100, 25));
		standardDev.setPreferredSize(new Dimension(100, 25));
		numClusters.setPreferredSize(new Dimension(100, 25));
		numToPick.setPreferredSize(new Dimension(100, 25));
		browse.addActionListener(this);
		loadDefault.addActionListener(this);
		clearInputs.addActionListener(this);
		generate.addActionListener(this);
		browse.setPreferredSize(new Dimension(100,25));
		BuildPanel();
		setBackground(Color.WHITE);
	}
	/**
	 * Helper method for building the entry panel.
	 */
	public void BuildPanel()	{
		add(df);
		add(diffIndex, "span 10");
		add(distanceMeasure, "wrap");
		add(sd);
		add(standardDev, "span 10");
		add(dimension, "wrap");
		add(nc);
		add(numClusters, "span 10");
		add(attra, "wrap");
		add(it);
		add(numToPick, "span 10");
		add(attrb, "wrap");
		add(la);
		add(browse, "span 10");
		add(numApps);
		add(num, "wrap");
		add(loadDefault);
		add(clearInputs, "wrap");
		add(generate, "wrap");
	}
	/**
	 * Empties data structures and prepares for new clustering.
	 */
	public void clearAll()	{
		clusterCount = 0;
		appStr = "";
		kmeans = new KMeansClusterer();
		values = new ArrayList<Point>();
		UserInterface.refreshResults();
		ResultsPanel.clusters.clear();
		ResultsPanel.clusterLabels.clear();
		ResultsPanel.selectionLabels.clear();
		ResultsPanel.selectionNames.clear();
		values.clear();
		results = new Cluster[0];
		applications.clear();
		selections.clear();
		names.clear();   
		races.clear();
		genders.clear();
		gpas.clear();
		ages.clear();
		ids.clear();
		eachAll .clear();
		num.setText("0");
		UserInterface.refreshResults();
	}
	/**
	 * Adds values to the data set for clustering.
	 */
	public void addValues()	{
		for (int i = 0; i < names.size(); i++)	{
			values.add(new Point(ages.get(i), gpas.get(i)));
		}
	}
	/**
	 * Sets the distance tolerance for the clustering algorithm.
	 * @param d
	 */
	public void setDistanceTolerance(double d)	{
		KMeansClusterer.DISTANCE_TOLERANCE = d;
	}
	/**
	 * Performs the clustering on the data set.
	 */
	public void doClustering()	{
		results = kmeans.cluster(values, Integer.parseInt(numClusters.getText()));
		for (int i = 0; i < results.length; i++)	{
			String temp = results[i].getItems().toString();
			ResultsPanel.clusters.add(new JLabel(temp));
		}

	}
	/**
	 * Randomly picks applications from each cluster.
	 * @param numToPick
	 */
	public void makeSelections(int numToPick)	{
		for (int i = 0,j = 0; i <numToPick; i++,j++)	{	//loop through all clusters
			if (j > results.length-1)	{
				j = 0;
			}
			if (results[j].getItems().size() == 0)	{
				return;
			}
			int max = results[j].getItems().size();
			int randChoice = (int)(Math.random()*(max));
			String choice = results[j].getItems().get(randChoice).toString();
			ResultsPanel.selectionLabels.add(new JLabel(choice));
			results[j].getItems().remove(randChoice);
		}
	}
	/**
	 * Reads in the applications from text files.
	 */
	public void readApplications()	{
		Scanner scan = null;
		File file = new File("Applications");
		appStr = new String("");
		int size1 = 0;
		if (file.isDirectory()){
			String[] files = file.list();
			size1 = files.length;
			numApplications = size1;
			for (int i = 0; i < size1; i++)	{
				File cur = new File("Applications/" + files[i]);
				try {
					scan = new Scanner(cur);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				while (scan.hasNext())	{
					String curr = scan.nextLine();
					appStr = appStr + curr;
					if (curr.contains("Name:"))	{
						names.add(curr.substring(5, curr.length()));
					}
					if (curr.contains("Race:"))	{
						races.add(curr.substring(5, curr.length()));
					}
					if (curr.contains("Gender:"))	{
						genders.add(curr.substring(7, curr.length()));
					}
					if (curr.contains("Age:"))	{
						ages.add(Integer.parseInt(curr.substring(4, curr.length())));
					}
					if (curr.contains("GPA:"))	{
						double temp = Double.parseDouble(curr.substring(4, curr.length()))*100;
						int temp2 = (int) temp;
						gpas.add(temp2);
					}
					if (curr.contains("ID:"))	{
						ids.add(Integer.parseInt(curr.substring(3, curr.length())));
						eachAll.add(appStr);
						appStr = "";
					}
				}
			}
		}
		if (Integer.parseInt(numToPick.getText()) > numApplications)	{
			JOptionPane.showMessageDialog(null, "Invalid Input!  \nPlease enter a number of applications to choose that is less than or equal to the total number of applications.");
		}
	}
	/**
	 * Action performer for the data entry panel.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == browse)	{
			JFileChooser choose = new JFileChooser();
			int option = choose.showOpenDialog(this);
			if (option == 0){
				readApplications();
				num.setText(Integer.toString(numApplications));
			}
		}
		if (e.getSource() == loadDefault)	{
			diffIndex.setText(".5296");
			standardDev.setText("13");
			numClusters.setText("4");
			numToPick.setText("4");
			readApplications();
			num.setText(Integer.toString(numApplications));
			this.validate();
		}
		if (e.getSource() == clearInputs){
			diffIndex.setText("");
			standardDev.setText("");
			numClusters.setText("");
			numToPick.setText("");
			clearAll();
		}
		if (e.getSource() == generate)	{
			clearAll();
			clusterCount = Integer.parseInt(numClusters.getText());
			readApplications();
			addValues();
			setDistanceTolerance(Double.parseDouble(diffIndex.getText()));
			doClustering();
			makeSelections(Integer.parseInt(numToPick.getText()));
			UserInterface.refreshResults();
		}
	}
}
