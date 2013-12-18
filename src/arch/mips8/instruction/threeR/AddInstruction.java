package arch.mips8.instruction.threeR;

import arch.mips8.Globals;
import arch.mips8.Register;

public class AddInstruction extends ThreeRInsruction {

	public AddInstruction(Register r1, Register r2, Register r3) {
		super(r1, r2, r3);
		super.name = "add";
	}

	public AddInstruction(AddInstruction addInstruction) {
		super(addInstruction);
		super.name = "add";
	}

	@Override
	public boolean executeEX() {
		//Assuming it comes here only when some value was available in previous step 
		//case include only when values are properly available 
		//special case only in case of sw as discussed
		super.executeEX();
		super.r1Val = super.r2Val + super.r3Val;
		
		if (r2.contentAvailable(id) && r3.contentAvailable(id)) {
			//no forwarding used here so nothing to set
		} else if (Globals.forwardingEnable && r2.forwardAvailable()
				&& r3.forwardAvailable()) {
			//both forwarded so setting forward to teeling both forwarded to ex of this id
			//Whenever set forward to is set add one forward line in forward table
			r2.setForwardTo(id, 4);
			r3.setForwardTo(id, 4);
		} else if (Globals.forwardingEnable && r2.forwardAvailable()
				&& r3.contentAvailable(id)) {
			r2.setForwardTo(id, 4);
		} else if (Globals.forwardingEnable && r3.forwardAvailable()
				&& r2.contentAvailable(id)) {
			r3.setForwardTo(id, 4);
		}
		if(Globals.forwardingEnable){
			//Forwarding value of r1 telling it is forwarded from here
			//this won't affect if r1 is same as r2 or r3 as forward to is set before and after that value is being changed
			//TODO still needed to be checked
			r1.setForward(r1Val, id, 4);
		}
		return true;
	}

	@Override
	public AddInstruction copy() {
		return new AddInstruction(this);
	}

}
