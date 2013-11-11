package arch.mips8.instruction.twoRoneI;
import arch.mips8.Globals;
import arch.mips8.Register;

public class LwInstruction extends TwoRoneIInstruction {

	public LwInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name="lw";
	}
	
	public LwInstruction(LwInstruction lwInstruction) {
		super(lwInstruction);
		super.name="lw";
		// TODO Auto-generated constructor stub
	}

	//This should have two types of registers
	//1 - the registers on whom the instruction depends - r2, r3
	//2 - the registers which depend on this instruction - r1

	@Override
	public boolean executeEX() {
		super.executeEX();
//		super.r1Val=MEM[super.r2Val+super.immd];
		return true;
	}
	
	@Override
	public LwInstruction copy(){
		return new LwInstruction(this);
	}

}
