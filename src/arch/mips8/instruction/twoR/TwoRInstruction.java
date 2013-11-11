package arch.mips8.instruction.twoR;
import arch.mips8.Globals;
import arch.mips8.Register;
import arch.mips8.instruction.Instruction;

public class TwoRInstruction  implements Instruction {

	Register r1, r2;
	long r1Val, r2Val;
	int id;
	String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getInstructionName() {
		return this.name+" "+r1.name+" "+r2.name;
	}
	//r1 depends on this instruction
	//this instruction depends on r2 and immd

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TwoRInstruction(Register r1, Register r2) {
		this.r1 = r1;
		this.r2 = r2;		
	}

	public TwoRInstruction(TwoRInstruction instruction) {
		this.r1 = instruction.r1;
		this.r2 = instruction.r2;		
		this.r1Val = instruction.r1Val;
		this.r2Val = instruction.r2Val;	
		this.name = instruction.name;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean executeIF() {
		Register reg =Globals.getRegister("pc");
		long current_pc = reg.getContent();
		reg.setContent(current_pc+1);		
		return true;
	}

	@Override
	public boolean executeIS() {
		
		return true;
	}

	@Override
	public boolean executeID() {
		// TODO 
		if (r1.contentAvailable(id) && r2.contentAvailable(id)) {
			r1Val = r1.getContent();
			r2Val = r2.getContent();	
			return true;
		} else {
			return false;
		}

	}


	@Override
	public boolean executeEX() {
		// TODO In child class
		
		return true;
	}


	@Override
	public boolean executeDF() {
		// TODO what?
		
		return true;
	}

	@Override
	public boolean executeDS() {
		// TODO what?
		
		return true;
	}

	@Override
	public boolean executeTC() {
		// TODO what?
		
		return true;
	}

	@Override
	public boolean executeWB() {
		// TODO Not necessarily locked might be unlocked in a case when some
		// other instruction wrote in it after it was locked r1.isLocked()
		// should I check?
		return true;
	}
	
	@Override
	public TwoRInstruction copy(){
		return new TwoRInstruction(this);
	}
}
