package arch.mips8.instruction.oneI;

import arch.mips8.Globals;
import arch.mips8.Register;

public class JumpAndLinkInstruction extends OneIInstruction{
	
	
	public JumpAndLinkInstruction(long immd){
		super(immd);
		super.name="jal";
	}
	public JumpAndLinkInstruction (JumpAndLinkInstruction jumpandlinkInstruction){
		super(jumpandlinkInstruction);
		super.name="jal";
	}
	
	@Override
	public boolean executeEX() {
		Register reg = Globals.getRegister("ra");
		long current_pc = reg.getContent();
		reg.setContent(current_pc - 1 + (super.immd/4));
		return true;
	}

}