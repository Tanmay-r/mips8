
package arch.mips8.instruction.twoR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class MultInstruction extends TwoRInstruction {
	
	private int multCounter = 4;

	public MultInstruction(Register r1, Register r2) {
		super(r1, r2);
		super.name="mult";
	}
	
	public MultInstruction(MultInstruction multInstruction) {
		super(multInstruction);
		super.name="mult";
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
		long mul = super.r1Val * super.r2Val;
		lo.setContent( mul & (0xffffffff) );
		hi.setContent( mul>>32 );
		if(multCounter > 1){
			multCounter--;
			return false;
		}
		else return true;
	}
	
	@Override
	public MultInstruction copy(){
		return new MultInstruction(this);
	}
	

}
