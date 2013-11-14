package arch.mips8.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

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
	private JPanel rightPanel;
	private JPanel memoryPanel;
	/** Scroll pane in which DrawPanel is fixed **/
	private JScrollPane SimulationPane = new JScrollPane();

	private JScrollPane RegisterPane = new JScrollPane();
	private JScrollPane MemoryPane = new JScrollPane();

	/** File Chooser To open or Load a file **/
	private JFileChooser fileopen = new JFileChooser();
	// private Statistics Stats= new Statistics();
	private JTextPane statData = new JTextPane();

	Simulator simulator;

	public MainWindow() {

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1300, 754));
		/** Initializing Panel **/
		rightPanel = new JPanel(new BorderLayout());
		memoryPanel = new JPanel(new BorderLayout());
		emptyPanel = new JPanel();
		/** Setting Layout and border for emptyPanel **/
		emptyPanel.setLayout(new BorderLayout());
		// emptyPanel.setPreferredSize(new Dimension(800,600));
		emptyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		SimulationPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		SimulationPane.getViewport().add(simulationWindow);
		RegisterPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		RegisterPane.getViewport().add(simulationWindow.getRegistersGui());

		memoryPanel.setPreferredSize(new Dimension(800, 200));
		//MemoryPane.getViewport().add(simulationWindow.getMemoryGui());
		memoryPanel.add(simulationWindow.getMemoryGui(), BorderLayout.CENTER);
		memoryPanel.add(groupButton1(), BorderLayout.PAGE_END);

		JLabel label = new JLabel("  REGISTERS  ", JLabel.CENTER);
		Border b = BorderFactory.createRaisedBevelBorder();
		label.setBorder(b);
		rightPanel.add(label, BorderLayout.PAGE_START);
		rightPanel.add(RegisterPane, BorderLayout.CENTER);

		memoryPanel.setBorder(b);
		emptyPanel.add(SimulationPane, BorderLayout.CENTER);
		emptyPanel.add(memoryPanel, BorderLayout.PAGE_END);

		add(rightPanel, BorderLayout.LINE_END);
		add(emptyPanel, BorderLayout.CENTER);
		add(groupButton(), BorderLayout.PAGE_END);
		setTitle("8 Stage Pipeline");
		/** Binding Space pressed for simulation **/

		setJMenuBar(createMenuBar());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/** Creating Buttons **/
	private JPanel groupButton() {
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		pane.setLayout(new BorderLayout());
		JButton button = new JButton("Simulate");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				prin();
			}
		});
		pane.add(button, BorderLayout.CENTER);

		button = new JButton("Load File");
		pane.add(button, BorderLayout.LINE_START);

		button = new JButton("QUIT");
		button.setMnemonic(KeyEvent.VK_D);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pane.add(button, BorderLayout.LINE_END);
		return pane;
	}

	/** Creating Groups Button **/
	private JPanel groupButton1() {
		JPanel pane = new JPanel();
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);
		JButton b1 = new JButton("PREV");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sI=simulationWindow.getMemoryGui().getStartIndex();
				int eI=simulationWindow.getMemoryGui().getEndIndex();
				if(sI>0){
					simulationWindow.getMemoryGui().setStartIndex(sI-8);
					simulationWindow.getMemoryGui().setEndIndex(eI-8);
					simulationWindow.getMemoryGui().repaint();
				}
				
			}
		});
		JButton b2 = new JButton("NEXT");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sI=simulationWindow.getMemoryGui().getStartIndex();
				int eI=simulationWindow.getMemoryGui().getEndIndex();
				if(sI<10000){
					simulationWindow.getMemoryGui().setStartIndex(sI+8);
					simulationWindow.getMemoryGui().setEndIndex(eI+8);
					simulationWindow.getMemoryGui().repaint();
				}
				
			}
		});
		JButton b3 = new JButton("SHOW STACK MEMORY");
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = ((JButton) e.getSource());
				if (b.getText().equals("SHOW STACK MEMORY")) {
					b.setText("SHOW DATA MEMORY");
					simulationWindow.getMemoryGui().setStackOrData("S");
					simulationWindow.getMemoryGui().setStartIndex(0);
					simulationWindow.getMemoryGui().setEndIndex(31);
					
				} else {
					b.setText("SHOW STACK MEMORY");
					simulationWindow.getMemoryGui().setStackOrData("D");
					simulationWindow.getMemoryGui().setStartIndex(0);
					simulationWindow.getMemoryGui().setEndIndex(31);

				}
				simulationWindow.getMemoryGui().repaint();
			}
		});
		JButton b4 = new JButton("SHOW DEC FORMAT");
		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = ((JButton) e.getSource());
				if (b.getText().equals("SHOW HEX FORMAT")) {
					b.setText("SHOW DEC FORMAT");
					simulationWindow.getMemoryGui().setHexOrDec(true);
				} else {
					b.setText("SHOW HEX FORMAT");
					simulationWindow.getMemoryGui().setHexOrDec(false);
				}
				simulationWindow.getMemoryGui().repaint();
			}
		});

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(b1).addComponent(b2).addComponent(b3)
				.addComponent(b4)

		);
		layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(b1).addComponent(b2).addComponent(b3)
						.addComponent(b4))

		);
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
		// File_menuItem
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
						Reset();
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
	public void Reset(){
		ClockCycles = 0;
		simulationWindow.resetAll();
		Globals.reset();

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
