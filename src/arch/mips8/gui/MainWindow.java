package arch.mips8.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import arch.mips8.FileParser;
import arch.mips8.Globals;
import arch.mips8.Simulator;

public class MainWindow extends JFrame {
	/** Panel That Contain others Component **/
	private JPanel emptyPanel;
	private JPanel ButtonCluster; 
	/** DrawPanel object to Draw simulation **/
	private DrawPanel simulationWindow = new DrawPanel();
	/** ClockCycles to keep track of current clock cycle of a process **/
	private int ClockCycles;
	/** Scroll pane in which DrawPanel is fixed **/
	private JScrollPane SimulationPane = new JScrollPane();
	/** File Chooser To open or Load a file **/
	private JFileChooser fileopen = new JFileChooser();
	// private Statistics Stats= new Statistics();
	private JTextPane statData = new JTextPane();

	Simulator simulator;

	public MainWindow() {
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1000, 700));
		/** Initializing Panel **/
		emptyPanel = new JPanel();
		/** Setting Layout and border for emptyPanel **/
		emptyPanel.setLayout(new BorderLayout());
		//emptyPanel.setPreferredSize(new Dimension(800,600));
		emptyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		SimulationPane.getViewport().add(simulationWindow);
		// statData.setPreferredSize(new Dimension(200,200));
		//JButton button = new JButton("Simulate");
		add(emptyPanel, BorderLayout.CENTER);
		add(groupButton(), BorderLayout.PAGE_END);
		setTitle("8 Stage Pipeline");
		/**Binding Space pressed for simulation**/


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		emptyPanel.add(SimulationPane);
		setJMenuBar(createMenuBar());

		pack();
		setVisible(true);
	}

	/**Creating Buttons**/
	private JPanel groupButton(){
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 20,10));
		pane.setLayout(new BorderLayout());
		JButton button = new JButton("Simulate");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				prin();
			}	
		});
		pane.add(button,BorderLayout.CENTER);

		button = new JButton("Load File");
		pane.add(button, BorderLayout.LINE_START);
		
		button = new JButton("QUIT");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}		
		});
		pane.add(button, BorderLayout.LINE_END);
		return pane;
	}
	/** Creating MenuBar **/
	private JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu File;
		JMenuItem File_menuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();
		File = new JMenu("File");
		File.setMnemonic(KeyEvent.VK_A);
		File.getAccessibleContext().setAccessibleDescription("File Options");
		menuBar.add(File);
		File_menuItem = new JMenuItem("Load File");
		File_menuItem.setMnemonic(KeyEvent.VK_T);
		File_menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		File_menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel FileChooser_panel = new JPanel();
				FileChooser_panel.setLayout(new BorderLayout());
				int ret = fileopen.showDialog(FileChooser_panel, "Open file");
				if (ret == JFileChooser.APPROVE_OPTION) {
					new FileParser(fileopen.getSelectedFile().getAbsolutePath());
					simulator = new Simulator();
				}

			}
		});

		File.add(File_menuItem);
		File_menuItem = new JMenuItem("Reset");
		File_menuItem.setMnemonic(KeyEvent.VK_B);
		File_menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int confirmation = JOptionPane.showConfirmDialog(emptyPanel,
						"Do You really Want To Reset?", "An Inane Question",
						JOptionPane.YES_NO_OPTION);
				if (confirmation != 1) {
					ClockCycles = 0;
					simulationWindow.resetAll();
					Globals.reset();
				}

			}
		});
		File.add(File_menuItem);
		File.addSeparator();
		File_menuItem = new JMenuItem("Quit...");
		File_menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
		});
		File_menuItem.setMnemonic(KeyEvent.VK_D);

		File.add(File_menuItem);

		File = new JMenu("Windows");
		File_menuItem = new JMenuItem("Statistics");
		File_menuItem.setMnemonic(KeyEvent.VK_E);
		File_menuItem.addActionListener(new statAction());
		File.setMnemonic(KeyEvent.VK_N);
		File.getAccessibleContext().setAccessibleDescription("Windows");
		File.add(File_menuItem);
		menuBar.add(File);

		return menuBar;

	}

	public void prin() {
		simulator.nextStep();
		simulationWindow.setDrawEnable(true);
		simulationWindow.repaint();
		revalidate();
	}

	public void showStatistics() {
		System.out.println("Statistics");
		statData = new JTextPane();
		statData.setContentType("text/html");
		statData.setText("alok");
		statData.setEditable(false);
		JScrollPane mypane = new JScrollPane();
		// mypane.setPreferredSize(new Dimension(100,100));
		mypane.getViewport().add(statData);
		Object[] objarr = { new JLabel("Total Cycles:"), mypane, };

		JOptionPane Optpane = new JOptionPane(objarr, JOptionPane.PLAIN_MESSAGE);
		JDialog dialog = Optpane.createDialog(emptyPanel, "Statistics");
		dialog.setResizable(true);
		dialog.setVisible(true);

	}

	/** Listener CLasses **/

	public class statAction implements ActionListener {
		public statAction() {
			super();
		}

		public void actionPerformed(ActionEvent e) {
			showStatistics();
		}
	}

}
