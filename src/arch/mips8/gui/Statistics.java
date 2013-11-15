package arch.mips8.gui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Statistics extends JOptionPane {

	Statistics() {

		String msg = "<html>this is a really long message<br>this is a really long message this is a really long message this is a really long message this is a really long message this is a really long message this is a really long message";

		setMessage(msg);
		setMessageType(JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = createDialog(null, "Width 100");
		dialog.setVisible(true);
	}

}