package arch.mips8.instruction.branch;
import arch.mips8.Globals;
import arch.mips8.Register;
import arch.mips8.instruction.Instruction;

public class branch implements Instruction {

	Register r1, r2;
	long r1Val, r2Val, immd;
	int id;
	String name;
	boolean branchTaken;
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getInstructionName() {
		return this.name+" "+r1.name+" "+r2.name+" "+immd;
	}

	//r1 depends on this instruction
	//this instruction depends on r2 and immd

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public branch(Register r1, Register r2, long immd) {
		this.r1 = r1;
		this.r2 = r2;
		this.immd = immd;
	}

	public branch(branch instruction) {
		this.r1 = instruction.r1;
		this.r2 = instruction.r2;
		this.immd = instruction.immd;
		this.r1Val = instruction.r1Val;
		this.r2Val = instruction.r2Val;	
		this.name = instruction.name;
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
		if (r2.contentAvailable(id) && r1.contentAvailable(id)) {
			r2Val = r2.getContent();
			r1Val = r1.getContent();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean executeEX() {
		// TODO In child class
		System.out.println("this shit");
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
	public branch copy(){
		return new branch(this);
	}
	
	public boolean getBranchTaken(){
		return branchTaken;
	}
}
