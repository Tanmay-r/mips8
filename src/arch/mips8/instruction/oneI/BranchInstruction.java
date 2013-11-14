package arch.mips8.instruction.oneI;

public class BranchInstruction extends OneIInstruction{
	
	public BranchInstruction(BranchInstruction branchInstruction) {
		super(branchInstruction);
		super.name="b";	
	}

	public BranchInstruction(long immd) {
		super(immd);
		super.name="b";
	}

}



