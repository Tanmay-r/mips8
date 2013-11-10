package arch.mips8.instruction.oneR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class MfhiInstruction  extends OneRInstruction{

	public MfhiInstruction(Register r1) {
		super(r1);
	}
	
	public MfhiInstruction(MfhiInstruction mfhiInstruction) {
		super(mfhiInstruction);
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();		
		Register hi =Globals.getRegister("hi");				
		super.r1Val=hi.getContent();		
		return true;
	}
	
	@Override
	public MfhiInstruction copy(){
		return new MfhiInstruction(this);
	}
	

}