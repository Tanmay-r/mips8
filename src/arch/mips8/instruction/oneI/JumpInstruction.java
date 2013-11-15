package arch.mips8.instruction.oneI;

import arch.mips8.Globals;

public class JumpInstruction extends OneIInstruction {

	public JumpInstruction(JumpInstruction jumpInstruction) {
		super(jumpInstruction);
		super.name = "j";
	}

	public JumpInstruction(long immd) {
		super(immd);
		super.name = "j";
	}

	@Override
	public boolean executeEX() {
		Globals.getRegister("pc").setContent(immd);
		return true;
	}

}
