package guiClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class mainWindow extends JFrame{
	private final JPanel emptyLabel;
	private JTextArea output;
	//private Graphics g; 
	private drawShapes shapes;
	private int c;
	private Rectangle myRect= new Rectangle();
	private int ClockCycles;
	private JFileChooser fileopen = new JFileChooser();
	
	public mainWindow (){
		
		emptyLabel = new JPanel();
		c=0;
		myRect.setPreferredSize(new Dimension(800,600));
		setTitle("8 Stage Pipeline");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		output = new JTextArea(5, 30);
	    output.setEditable(false);
	    setPreferredSize(new Dimension(800,600));
		//emptyLabel.setPreferredSize(new Dimension(800,600));
		this.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		setJMenuBar(createMenuBar());
		pack();
		setVisible(true);
		
	}
	public JMenuBar createMenuBar(){
		
		JMenuBar menuBar;
        JMenu File, Window;
        JMenuItem File_menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();
        File = new JMenu("File");
        File.setMnemonic(KeyEvent.VK_A);
        File.getAccessibleContext().setAccessibleDescription(
                "File Options");
        menuBar.add(File);
        File_menuItem = new JMenuItem("Load File");
        File_menuItem.setMnemonic(KeyEvent.VK_T);
        File_menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        File_menuItem.addActionListener(new FileAction());
        
        File.add(File_menuItem);
        File_menuItem = new JMenuItem("Reset");
        File_menuItem.setMnemonic(KeyEvent.VK_B);
        //File_menuItem.addActionListener(this;
        File.add(File_menuItem);
        File.addSeparator();
        File_menuItem= new JMenuItem(new QuitAction());
        File_menuItem.setMnemonic(KeyEvent.VK_D);
        //File_menuItem.addActionListener(this);
        File.add(File_menuItem);
        
        File = new JMenu("Windows");
        File.setMnemonic(KeyEvent.VK_N);
        File.getAccessibleContext().setAccessibleDescription(
                "Windows");
        menuBar.add(File);
        
		return menuBar;
		
	}
	public void prin(){
		FileWriter fStream;
		ClockCycles++;
        try {
        	
            fStream = new FileWriter("/home/alok29/cs305/8_STAGE_MIPS_PIPELINE/src/guiClass/input.txt",false);
            String str="";
            int k;
            if(ClockCycles>8){
            	k=ClockCycles-8;
            }
            else{
            	k=0;
            }
            for(int i=k;i<ClockCycles;i++){
            	str+=","+(i+1)+":"+(((ClockCycles-(i+1))%8)+1)+":N";
            }
            if(ClockCycles==2){
            	str+="FWD1:1:2:1";
            }
            fStream.write(str.substring(1));
            fStream.flush();
            fStream.close();
        } catch (IOException ex) {
            
        }
    	myRect.repaint();
    	emptyLabel.add(myRect);
		revalidate();
	}
	public class FileAction implements ActionListener {
	    public FileAction() {
	        super();
	    }

	    public void actionPerformed(ActionEvent e) {
	    	
	    	prin();
	    	//System.out.println(LoadFile());
	    	
	    
	    	//shapes.paint(g);//(100,100,200,100);
	    	//output.setText(LoadFile());
	        // Button pressed logic goes here
	    }
	}
	public class QuitAction extends AbstractAction {
	    public QuitAction() {
	        super("Quit..");
	    }

	    public void actionPerformed(ActionEvent e) {
	    	
	    	System.exit(0);
	        // Button pressed logic goes here
	    }
	}
	public String LoadFile(){
		JPanel FileChooser_panel = new JPanel();
		FileChooser_panel.setLayout(new BorderLayout());
        StringBuffer fileBuffer = null;
        String fileString = null;
        String line = null;
		int ret = fileopen.showDialog(FileChooser_panel, "Open file");
		 if (ret == JFileChooser.APPROVE_OPTION) {
             File file = fileopen.getSelectedFile();
             //String text = readFile(file);
             try {
                 FileReader in = new FileReader(file);
                 BufferedReader brd = new BufferedReader(in);
                 fileBuffer = new StringBuffer();

                 while ((line = brd.readLine()) != null) {
                     fileBuffer.append(line).append(
                             System.getProperty("line.separator"));
                 }

                 in.close();
                 fileString = fileBuffer.toString();

             }
             catch (IOException e) {
                 return null;
             }
            
             //area.setText(text);
         }
		 return fileString;
		
	}   

}
