package arch.mips8;

public class Mips8 {

	public static void main(String[] args) {
		Globals globals = new Globals();
		FileParser fileParser = new FileParser(
				"/home/tanmay/ecc2.asm", globals);
		Simulator simulator = new Simulator(globals);
		simulator.simulate();
	}
}

