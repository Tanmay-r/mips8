package arch.mips8.instruction.oneI;

public class JumpInstruction extends OneIInstruction{
	
	public JumpInstruction(JumpInstruction jumpInstruction) {
		super(jumpInstruction);
		super.name="j";	
	}
	public JumpInstruction(long immd) {
		super(immd);
		super.name="j";
	}

}



