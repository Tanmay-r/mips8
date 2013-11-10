package arch.mips8;

import arch.mips8.gui.MainWindow;

public class Mips8 {

	public static void main(String[] args) {
		Globals globals = new Globals();
		FileParser fileParser = new FileParser("/home/tanmay/mips8/codes/try1.asm", globals);
		Simulator simulator = new Simulator(globals);
		simulator.simulate();
		MainWindow m = new MainWindow();
	}
}

