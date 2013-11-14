package arch.mips8.instruction.twoR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class DivInstruction extends TwoRInstruction {
	
	private int divCounter = 4;

	public DivInstruction(Register r1, Register r2) {
		super(r1, r2);
		super.name="div";
	}
	
	public DivInstruction(DivInstruction divInstruction) {
		super(divInstruction);
		super.name="div";
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();
		Register lo =Globals.getRegister("lo");
		Register hi =Globals.getRegister("ho");		
		lo.setContent( (long)((int)super.r1Val / (int)super.r2Val) );
		hi.setContent( (long)((int)super.r1Val % (int)super.r2Val) );	
		if(divCounter>1){
			divCounter--;
			return false;
		}
		else return true;
	}
	
	@Override
	public DivInstruction copy(){
		return new DivInstruction(this);
	}
	

}
