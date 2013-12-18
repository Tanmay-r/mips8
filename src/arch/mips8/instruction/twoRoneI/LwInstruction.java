package arch.mips8.instruction.twoRoneI;

import arch.mips8.Globals;
import arch.mips8.Register;

public class LwInstruction extends TwoRoneIInstruction {

	private int addr;

	public LwInstruction(Register r1, Register r2, long immd) {
		super(r1, r2, immd);
		super.name = "lw";
	}

	public LwInstruction(LwInstruction lwInstruction) {
		super(lwInstruction);
		super.name = "lw";
	}

	@Override
	public boolean executeEX() {
		super.executeEX();
		addr = (int) (super.r2Val + (int) super.immd);
		if (Globals.forwardingEnable && r2.forwardAvailable()) {
			r2.setForwardTo(id, 4);
		}		
		return true;
	}

	@Override
	public boolean executeDS() {
		super.executeDS();
		if (super.r2.name.equals("$sp")) {
			super.r1Val = (long) (Globals.memory.stackMemory.getInt(addr));
		} else {
			super.r1Val = (long) (Globals.memory.dataMemory.getInt(addr));
		}
		if(Globals.forwardingEnable){
			//Forwarding happens from DS to EX/DS not from TC, confirmed - 
			//https://www.inkling.com/read/computer-architecture-hennessy-5th/appendix-c/section-c-6 Figure C.43
			r1.setForward(r1Val, id, 6);
		}
		return true;
	}

	@Override
	public LwInstruction copy() {
		return new LwInstruction(this);
	}

	@Override
	public String getInstructionName() {
		return this.name + " " + r1.name + " " + immd + " (" + r2.name + ")";
	}

}
