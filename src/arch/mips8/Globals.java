package arch.mips8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import arch.mips8.gui.CalculateCoordinate;
import arch.mips8.instruction.Instruction;

public class Globals {
	public static Map<String, Register> registers;
	public static ArrayList<Instruction> instructions;
	public static CalculateCoordinate instructionPipeLine;
	public static Memory memory;
	public static Map<String, DataType> Data;
	public static Map<String, Integer> Labels;
	public static ArrayList<Integer> StallArray;

	public static ArrayList<ArrayList<Integer> > forwardings;
	public static boolean forwardingEnable;
	
	public Globals() {
		String registersNames[] = "$zero, $at, $v0, $v1, $a0, $a1, $a2, $a3, $t0, $t1, $t2, $t3, $t4, $t5, $t6, $t7, $s0, $s1, $s2, $s3, $s4, $s5, $s6, $s7, $t8, $t9, $k0, $k1, $gp, $sp, $fp, $ra, pc, hi, lo"
				.split(",");
		registers = new HashMap<String, Register>();
		for (String rName : registersNames) {
			Register r = new Register(rName.trim());
			registers.put(rName.trim(), r);

		}
		instructions = new ArrayList<Instruction>();
		instructionPipeLine = new CalculateCoordinate();
		memory = new Memory();
		StallArray = new ArrayList<Integer>(3);
		for (int i = 0; i < 3; i++) {
			StallArray.add(i, 0);
		}
		Data = new HashMap<String, DataType>();
		Labels = new HashMap<String, Integer>();
		registers.get("$sp").setContent(16303);
		forwardings = new ArrayList<ArrayList<Integer> >();
		forwardingEnable = true;
	}

	public static Register getRegister(String rName) {
		// System.out.println(registers.get(rName).getContent());
		return registers.get(rName);
	}

	public static void reset() {
		String registersNames[] = "$zero, $at, $v0, $v1, $a0, $a1, $a2, $a3, $t0, $t1, $t2, $t3, $t4, $t5, $t6, $t7, $s0, $s1, $s2, $s3, $s4, $s5, $s6, $s7, $t8, $t9, $k0, $k1, $gp, $sp, $fp, $ra, pc, hi, lo"
				.split(",");
		for (String rName : registersNames) {
			registers.get(rName.trim()).setContent(0);
		}
		instructions.clear();
		memory = new Memory();
		Labels = new HashMap<String, Integer>();
		for (int i = 0; i < 3; i++) {
			StallArray.set(i, 0);
		}
		registers.get("$sp").setContent(16303);
		forwardings = new ArrayList<ArrayList<Integer> >();
		// instructionPipeLine = new CalculateCoordinate();
	}
}
