package arch.mips8.instruction.oneI;

import arch.mips8.Globals;
import arch.mips8.Register;
import arch.mips8.instruction.Instruction;

public class OneIInstruction implements Instruction {
	
	
	int id;
	long immd;
	String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getInstructionName() {
		return this.name+" "+this.immd;
	}
	
	
	public OneIInstruction(long immd){
		this.immd=immd;
	}
	public OneIInstruction(OneIInstruction instruction) {
		
		this.name = instruction.name;
		this.immd=instruction.immd;
	}
	@Override
	public boolean executeIF() {
		Register reg = Globals.getRegister("pc");
		long current_pc = reg.getContent();
		reg.setContent(current_pc + 1);
		return true;
	}

	@Override
	public boolean executeIS() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeID() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeEX() {
		Register reg = Globals.getRegister("pc");
		long current_pc = reg.getContent();
		reg.setContent(current_pc - 1 + (immd/4));
		return true;
	}

	@Override
	public boolean executeDF() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeDS() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeTC() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeWB() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id=id;

		
	}

	@Override
	public Instruction copy() {
		// TODO Auto-generated method stub
		return null;
	}
	

}