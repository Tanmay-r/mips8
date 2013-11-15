package arch.mips8.instruction.oneR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class MfhiInstruction extends OneRInstruction {
	Register hi;
	long hiVal;
	public MfhiInstruction(Register r1) {
		super(r1);
		super.name = "mfhi";
		hi = Globals.getRegister("hi");
	}

	public MfhiInstruction(MfhiInstruction mfhiInstruction) {
		super(mfhiInstruction);
		super.name = "mfhi";
		hi = Globals.getRegister("hi");
	}
	
	@Override
	public boolean executeID(){
		super.executeID();
		if(hi.contentAvailable(id)){
			hiVal = hi.getContent();
			return true;
		}else{
			return false;
		}
	}
	@Override
	public boolean executeEX() {
		super.executeEX();
		super.r1Val = hiVal;
		return true;
	}

	@Override
	public MfhiInstruction copy() {
		return new MfhiInstruction(this);
	}

}