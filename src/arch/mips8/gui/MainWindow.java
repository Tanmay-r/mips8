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

import arch.mips8.Globals;

public class MainWindow extends JFrame implements KeyListener {
	/** Panel That Contain others Component **/
	private JPanel emptyPanel;
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

	Globals globals;
	public MainWindow(Globals globals) {
		this.globals = globals;
		/** Initializing Panel **/
		emptyPanel = new JPanel();
		/** Setting Layout and border for emptyPanel **/
		emptyPanel.setLayout(new BorderLayout());
		emptyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		SimulationPane.getViewport().add(simulationWindow);

		// statData.setPreferredSize(new Dimension(200,200));

		setTitle("8 Stage Pipeline");

		setLayout(new BorderLayout());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setPreferredSize(new Dimension(800, 600));

		emptyPanel.add(SimulationPane);
		addKeyListener(this);
		this.getContentPane().add(emptyPanel, BorderLayout.CENTER);

		setJMenuBar(createMenuBar());

		pack();
		setVisible(true);
	}

	/** Creating MenuBar **/
	public JMenuBar createMenuBar() {
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
		File_menuItem.addActionListener(new FileAction());

		File.add(File_menuItem);
		File_menuItem = new JMenuItem("Reset");
		File_menuItem.setMnemonic(KeyEvent.VK_B);
		File_menuItem.addActionListener(new resetAction());
		File.add(File_menuItem);
		File.addSeparator();
		File_menuItem = new JMenuItem(new QuitAction());
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
		System.out.println("In prin");
		ClockCycles++;

		String str = "";
		int k;
		if (ClockCycles > 8) {
			k = ClockCycles - 8;
		} else {
			k = 0;
		}
		for (int i = k; i < ClockCycles; i++) {
			str += "," + (i + 1) + ":" + (((ClockCycles - (i + 1)) % 8) + 1)
					+ ":N";
		}
		if (ClockCycles == 4) {
			str += "FWD1:1:4:1";
		}
		System.out.println(str);
		int W = simulationWindow.getCurrentCycleX();
		System.out.println("Current X in mainWindow"
				+ simulationWindow.getCurrentCycleX());
		Dimension d = simulationWindow.getPreferredSize();
		int H = simulationWindow.getCurrentCycleY() + 25;
		simulationWindow.setDrawEnable(true);
		// System.out.println(d.width +":"+d.height+":"+H+":"+W);
		simulationWindow.setInputToDraw(str.substring(1));
		simulationWindow.repaint();
		// emptyPanel.add(myRect);
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
	public void keyTyped(KeyEvent e) {
		displayInfo(e, "KEY TYPED: ");
	}

	/** Handle the key-pressed event from the text field. */
	public void keyPressed(KeyEvent e) {
		displayInfo(e, "KEY PRESSED: ");
	}

	/** Handle the key-released event from the text field. */
	public void keyReleased(KeyEvent e) {
		displayInfo(e, "KEY RELEASED: ");
	}

	public void displayInfo(KeyEvent e, String keyStatus) {

		// You should only rely on the key char if the event
		// is a key typed event.
		int id = e.getID();
		String keyString = "";
		if (id == KeyEvent.KEY_PRESSED) {
			char c = e.getKeyChar();
			keyString = "key character = '" + c + "' ";
			int keyCode = e.getKeyCode();
			keyString += "key code = " + keyCode + " ("
					+ KeyEvent.getKeyText(keyCode) + ")";
			if (keyCode == 118) {
				prin();
			}
		}
	}

	public class FileAction implements ActionListener {
		public FileAction() {
			super();
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println(LoadFile());
		}
	}

	public class statAction implements ActionListener {
		public statAction() {
			super();
		}

		public void actionPerformed(ActionEvent e) {
			showStatistics();
		}
	}

	public class resetAction implements ActionListener {
		public resetAction() {
			super();
		}

		public void actionPerformed(ActionEvent e) {
			int confirmation = JOptionPane.showConfirmDialog(emptyPanel,
					"Do You really Want To Reset?", "An Inane Question",
					JOptionPane.YES_NO_OPTION);
			if (confirmation != 1) {
				ClockCycles = 0;
				simulationWindow.resetAll();
			}
		}
	}

	public class QuitAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public QuitAction() {
			super("Quit..");
		}

		public void actionPerformed(ActionEvent e) {

			System.exit(0);
			// Button pressed logic goes here
		}
	}

	public String LoadFile() {
		JPanel FileChooser_panel = new JPanel();
		FileChooser_panel.setLayout(new BorderLayout());
		int ret = fileopen.showDialog(FileChooser_panel, "Open file");
		if (ret == JFileChooser.APPROVE_OPTION) {
			return fileopen.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

}
