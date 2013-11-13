package arch.mips8.instruction.oneI;

import arch.mips8.Globals;
import arch.mips8.Register;

public class JumpInstruction extends OneIInstruction{
	
	public JumpInstruction(JumpInstruction jumpInstruction) {
		super(jumpInstruction);
		super.name="j";	
	}
	public JumpInstruction(long immd) {
		super(immd);
		super.name="j";
	}
	
	@Override
	public boolean executeEX() {
		Register reg = Globals.getRegister("pc");
		long current_pc = reg.getContent();
		//reg.setContent(current_pc - 1 + ( (int)immd/4));
		//In jump addressing is absolute
		//need to fetch from memory , i guess
		return true;
	}

}



