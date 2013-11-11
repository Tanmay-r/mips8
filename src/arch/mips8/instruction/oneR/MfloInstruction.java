package arch.mips8.instruction.oneR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class MfloInstruction  extends OneRInstruction{

	public MfloInstruction(Register r1) {
		super(r1);
		super.name="mflo";
	}
	
	public MfloInstruction(MfloInstruction mfloInstruction) {
		super(mfloInstruction);
		super.name="mflo";
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();		
		Register lo =Globals.getRegister("lo");				
		super.r1Val=lo.getContent();		
		return true;
	}
	
	@Override
	public MfloInstruction copy(){
		return new MfloInstruction(this);
	}
	

}