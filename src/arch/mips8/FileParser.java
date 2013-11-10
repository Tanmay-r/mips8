package arch.mips8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import arch.mips8.instruction.threeR.AddInstruction;
import arch.mips8.instruction.threeR.AdduInstruction;
import arch.mips8.instruction.threeR.AndInstruction;
import arch.mips8.instruction.threeR.MulInstruction;
import arch.mips8.instruction.threeR.OrInstruction;
import arch.mips8.instruction.threeR.SltInstruction;
import arch.mips8.instruction.threeR.SltuInstruction;
import arch.mips8.instruction.threeR.SubInstruction;
import arch.mips8.instruction.threeR.SubuInstruction;

public class FileParser {
	private ArrayList<String> code;
	private String threeR, twoRoneI, twoR, oneR, oneRoneIoneR, oneI, oneRoneI;
	Map<String, Integer> labelIndex;
	int instrIndex;

	public FileParser(String filePath) {
		Globals.reset();
		threeR = " add sub addu subu and or slt sltu mul ";
		twoRoneI = " addi addiu andi ori sll srl beq bne slti sltiu blt ";
		twoR = " mult multu div divu move ";
		oneR = " mfhi mflo jr ";
		oneRoneIoneR = " lw sw ";
		oneRoneI = " lui li la bltz bgez";
		oneI = " j jal b ";
		instrIndex = 0;
		labelIndex = new HashMap<String, Integer>();
		BufferedReader br = null;
		code = new ArrayList<String>();
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(filePath));

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.trim();
				if (!sCurrentLine.equals("")) {
					if (!sCurrentLine.startsWith("#")) {
						code.add(sCurrentLine);
					}
				}
			}
			try {
				parseCode();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Syntax Error");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void parseCode() throws Exception {
		boolean data = false, text = false;
		for (int i = 0; i < code.size(); i++) {
			String line = code.get(i);
			while (line.contains("#")) {
				if (!line.contains("\"")) {
					line = line.substring(0, line.indexOf('#'));
					line = line.trim();
				}
			}

			if (line.equals(".data")) {
				data = true;
				text = false;
				continue;
			}
			if (line.equals(".text")) {
				text = true;
				data = false;
				continue;
			}
			if (data) {
				String[] s = line.split(" ");
				if (s.length < 3)
					continue;
				parseData(s);

			}
			if (text) {
				String instr;
				if (line.contains(":")) {
					String label = line.substring(0, line.indexOf(':'));
					instr = line.substring(line.indexOf(':') + 1);
					System.out.println("label " + label);
					labelIndex.put(label, instrIndex);

				} else {
					instr = line;
				}
				parseInstruction(instr);

			}

		}
		System.out.print(Globals.instructions);
	}

	private void parseInstruction(String instr) throws Exception {
		instr = instr.replaceAll(",", " ");
		while (instr.contains("  "))
			instr = instr.replaceAll("  ", " ");
		instr = instr.trim();
		if (!instr.equals("")) {
			String[] s = instr.split(" ");
			// System.out.println(instr);
			if (s.length > 4)
				throw new Exception("Syntax Error");
			String type = " " + s[0] + " ";
			if (threeR.contains(type)) {
				String r1 = s[1];
				String r2 = s[2];
				String r3 = s[3];
				// TODO Three Register Call
				System.out.println(type.trim() + " 3R " + r1 + r2 + r3);
				switch (type.trim()) {
				case "add":
					Globals.instructions.add(new AddInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;
				
				case "sub":
					Globals.instructions.add(new SubInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;
					
				case "addu":
					Globals.instructions.add(new AdduInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;
					
				case "subu":
					Globals.instructions.add(new SubuInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;

				case "and":
					Globals.instructions.add(new AndInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;

				case "or":
					Globals.instructions.add(new OrInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;
					
				case "mul":
					Globals.instructions.add(new MulInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;
					
				case "slt":
					Globals.instructions.add(new SltInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;
					
				case "sltu":
					Globals.instructions.add(new SltuInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2), Globals
							.getRegister(r3)));
					break;

					
				}

			} else if (twoRoneI.contains(type)) {
				String r1 = s[1];
				String r2 = s[2];
				String i1 = s[3];
				System.out.println(type.trim() + " 2R1I " + r1 + r2 + i1);
			} else if (twoR.contains(type)) {
				String r1 = s[1];
				String r2 = s[2];
				System.out.println(type.trim() + " 2R " + r1 + r2);
			} else if (oneR.contains(type)) {
				String r1 = s[1];
				System.out.println(type.trim() + " 2R " + r1);
			} else if (oneRoneIoneR.contains(type)) {
				String r1 = s[1];
				String i1, r2;
				if (s.length == 3) {
					i1 = s[2].split("(")[0];
					r2 = s[2].split("(")[1].replace(")", "");
				} else {
					i1 = s[2];
					r2 = s[3];
				}
				System.out.println(type.trim() + " 1R1I1R " + r1 + i1 + r2);
			} else if (oneI.contains(type)) {
				String i1 = s[1];
				System.out.println(type.trim() + " 1I " + i1);
			} else if (oneRoneI.contains(type)) {
				String r1 = s[1];
				String i1 = s[2];
				System.out.println(type.trim() + " 1R1I " + r1 + i1);
			} else if (type.equals(" syscall ")) {
				System.out.println(type.trim());
			} else {
				System.out.println("Not Considered " + type);
				// throw new Exception();
			}
		}
		System.out.println("\n");

	}

	private void parseData(String[] s) {
		String tag = s[0].trim();
		String type = s[1].trim();
		String value = "";
		for (int j = 2; j < s.length; j++)
			value = value + " " + s[j];
		System.out.println("Tag " + tag);
		System.out.println("Type " + type);
		System.out.println("Value " + value + "\n");
	}
}