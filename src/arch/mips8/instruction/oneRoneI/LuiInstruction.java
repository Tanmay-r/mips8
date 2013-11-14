package arch.mips8.instruction.oneRoneI;
import arch.mips8.Register;

public class LuiInstruction extends OneRoneIInstruction {		

	public LuiInstruction(Register r1, long immd) {
		super(r1, immd);
		super.name="lui";
	}
	
	public LuiInstruction(LuiInstruction luiInstruction) {
		super(luiInstruction);
		super.name="lui";
		
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val =  (long)( ((int)super.immd)<<  16 );
		return true;
	}
	
	@Override
	public LuiInstruction copy(){
		return new LuiInstruction(this);
	}

}
