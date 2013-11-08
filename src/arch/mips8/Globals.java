package arch.mips8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import arch.mips8.instruction.Instruction;

public class Globals {
	Map<String, Register> registers;
	ArrayList<Instruction> instructions;
	public Globals() {
		String registersNames[] = "$zero, $at, $v0, $v1, $a0, $a1, $a2, $a3, $t0, $t1, $t2, $t3, $t4, $t5, $t6, $t7, $s0, $s1, $s2, $s3, $s4, $s5, $s6, $s7, $t8, $t9, $k0, $k1, $gp, $sp, $fp, $ra, pc, hi, lo"
				.split(",");
		registers = new HashMap<String, Register>();
		for(String rName : registersNames){
			Register r = new Register(rName.trim());
			registers.put(rName.trim(), r);
			
		}
		instructions = new ArrayList<Instruction>();
	}
	
	public Register getRegister(String rName){
		System.out.println(registers.get(rName).getContent());
		return registers.get(rName);
	}
}
