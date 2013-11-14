package arch.mips8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import arch.mips8.instruction.threeR.*;
import arch.mips8.instruction.twoRoneI.*;
import arch.mips8.instruction.twoR.*;
//import arch.mips8.instruction.oneRoneI.*;
import arch.mips8.instruction.oneR.*;
import arch.mips8.instruction.oneI.*;

public class FileParser {
	private ArrayList<String> code;
	private String threeR, twoRoneI, twoR, oneR, oneRoneIoneR, oneI, oneRoneI;
	Map<String, Integer> labelIndex;
	int instrIndex;

	public FileParser(String filePath) {
		Globals.reset();
		threeR = " add sub addu subu and or slt sltu mul ";
		twoRoneI = " addi addiu andi ori sll srl beq bne slti sltiu ";
		twoR = " mult multu div divu move ";
		oneR = " mfhi mflo jr ";
		oneRoneIoneR = " lw sw ";
		oneRoneI = " lui li la ";
		oneI = " j jal ";
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
				// System.out.println("Syntax Error");
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
					Globals.Labels.put(label, 0);
				} else {
					instr = line;
				}
				// parseInstruction(instr);

			}
		}
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
			}
			if (text) {
				String instr;
				if (line.contains(":")) {
					String label = line.substring(0, line.indexOf(':'));
					instr = line.substring(line.indexOf(':') + 1);
					System.out.println("label " + label);
					Globals.Labels.put(label, Globals.instructions.size());
				} else {
					instr = line;
				}
				parseInstruction(instr);

			}

		}
		// System.out.print(Globals.instructions);
	}

	private void parseInstruction(String instr) throws Exception {
		instr = instr.replaceAll(",", " ");
		while (instr.contains("  "))
			instr = instr.replaceAll("  ", " ");
		instr = instr.trim();
		if (!instr.equals("")) {
			String[] s = instr.split(" ");
			for (int i = 0; i < s.length; i++)
				s[i] = s[i].trim();
			// System.out.println(instr);
			if (s.length > 4)
				throw new Exception("Syntax Error");
			String type = " " + s[0] + " ";
			if (threeR.contains(type)) {
				String r1 = s[1];
				String r2 = s[2];
				String r3 = s[3];
				// TODO Three Register Call
				// System.out.println(type.trim() + " 3R " + r1 + r2 + r3);
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
				// System.out.println(type.trim() + " 2R1I " + r1 + r2 + i1);

				switch (type.trim()) {
				case "addi":
					Globals.instructions.add(new AddiInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				case "addiu":
					Globals.instructions.add(new AddiuInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				case "andi":
					Globals.instructions.add(new AndiInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				case "ori":
					Globals.instructions.add(new OriInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				case "beq":
					try {
						Globals.instructions.add(new BeqInstruction(Globals
								.getRegister(r1), Globals.getRegister(r2),
								(long) Integer.parseInt(i1)));
					} catch (NumberFormatException e) {
						Globals.instructions.add(new BeqInstruction(Globals
								.getRegister(r1), Globals.getRegister(r2),
								Globals.Labels.get(i1)
										- Globals.instructions.size() - 1));

					}
					break;
				case "bne":
					try {
						Globals.instructions.add(new BneInstruction(Globals
								.getRegister(r1), Globals.getRegister(r2),
								(long) Integer.parseInt(i1)));
					} catch (NumberFormatException e) {
						Globals.instructions.add(new BneInstruction(Globals
								.getRegister(r1), Globals.getRegister(r2),
								Globals.Labels.get(i1)
										- Globals.instructions.size() - 1));

					}
					break;
				case "sll":
					Globals.instructions.add(new SllInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				case "slt":
					Globals.instructions.add(new SltiInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				case "sltu":
					Globals.instructions.add(new SltiuInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				case "srl":
					Globals.instructions.add(new SrlInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				}

			} else if (twoR.contains(type)) {
				String r1 = s[1];
				String r2 = s[2];
				// System.out.println(type.trim() + " 2R " + r1 + r2);
				switch (type.trim()) {
				case "div":
					Globals.instructions.add(new DivInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2)));
					break;
				case "mult":
					Globals.instructions.add(new MultInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2)));
					break;
				}

			} else if (oneR.contains(type)) {
				String r1 = s[1];
				// System.out.println(type.trim() + " 2R " + r1);
				switch (type.trim()) {
				case "jr":
					Globals.instructions.add(new JrInstruction(Globals
							.getRegister(r1)));
					break;
				case "mfhi":
					Globals.instructions.add(new MfhiInstruction(Globals
							.getRegister(r1)));
					break;
				case "mflo":
					Globals.instructions.add(new MfloInstruction(Globals
							.getRegister(r1)));
					break;
				}

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
				// System.out.println(type.trim() + " 1R1I1R " + r1 + i1 + r2);
				switch (type.trim()) {
				case "lw":
					Globals.instructions.add(new LwInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				case "sw":
					Globals.instructions.add(new SwInstruction(Globals
							.getRegister(r1), Globals.getRegister(r2),
							(long) Integer.parseInt(i1)));
					break;
				}

			} else if (oneI.contains(type)) {
				String i1 = s[1];
				// System.out.println(type.trim() + " 1I " + i1);
				switch (type.trim()) {
				case "jal":
					try {
						Globals.instructions.add(new JumpAndLinkInstruction(
								(long) Integer.parseInt(i1)));
					} catch (NumberFormatException e) {
						Globals.instructions.add(new JumpAndLinkInstruction(
								Globals.Labels.get(i1)));
					}
					break;
				case "j":
					try {
						Globals.instructions.add(new JumpInstruction(
								(long) Integer.parseInt(i1)));
					} catch (NumberFormatException e) {
						Globals.instructions.add(new JumpInstruction(
								Globals.Labels.get(i1)));
					}
					break;
				}

			} else if (oneRoneI.contains(type)) {
				String r1 = s[1];
				String i1 = s[2];
				// System.out.println(type.trim() + " 1R1I " + r1 + i1);
			} else if (type.equals(" syscall ")) {
				// System.out.println(type.trim());
			} else {
				System.out.println("Not Considered " + type);
				// throw new Exception();
			}
		}
	}

	private void parseData(String[] s) {
		String tag = s[0].trim();
		String type = s[1].trim();
		String value = "";
		for (int j = 2; j < s.length; j++)
			value = value + " " + s[j];
		tag = tag.split(":")[0];
		System.out.println("Tag " + tag);
		System.out.println("Type " + type);
		System.out.println("Value " + value + "\n");
		value = value.trim();
		switch (type.trim()) {
		case ".ascii": {
			byte[] byteString = value.getBytes();
			int index = Globals.memory.dataMemory.storeByte(byteString[0]);
			for (int i = 1; i < byteString.length; i++) {
				Globals.memory.dataMemory.storeByte(byteString[i]);
			}
			Globals.Data.put(tag, new DataType(tag, type, index,
					byteString.length));
			break;
		}
		case ".asciiz": {
			byte[] byteString = value.getBytes();
			int index = Globals.memory.dataMemory.storeByte(byteString[0]);
			for (int i = 1; i < byteString.length; i++) {
				Globals.memory.dataMemory.storeByte(byteString[i]);
			}
			Globals.memory.dataMemory.storeByte((byte) 0);
			Globals.Data.put(tag, new DataType(tag, type, index,
					byteString.length));
			break;
		}
		case ".byte": {
			String[] bytes = value.split(",");
			int intbyte = Integer.parseInt(bytes[0].trim());
			int index = Globals.memory.dataMemory.storeByte((byte) intbyte);
			for (int i = 1; i < bytes.length; i++) {
				intbyte = Integer.parseInt(bytes[i].trim());
				Globals.memory.dataMemory.storeByte((byte) intbyte);
			}
			Globals.Data.put(tag, new DataType(tag, type, index, bytes.length));
			break;
		}
		case ".halfword": {
			String[] bytes = value.split(",");
			int intbyte = Integer.parseInt(bytes[0].trim());
			int index = Globals.memory.dataMemory.storeByte((byte) intbyte);
			Globals.memory.dataMemory.storeByte((byte) (intbyte >>> 8));
			for (int i = 1; i < bytes.length; i++) {
				intbyte = Integer.parseInt(bytes[i].trim());
				Globals.memory.dataMemory.storeByte((byte) intbyte);
				Globals.memory.dataMemory.storeByte((byte) (intbyte >>> 8));
			}
			Globals.Data.put(tag, new DataType(tag, type, index,
					2 * bytes.length));
			break;
		}
		case ".word": {
			String[] bytes = value.split(",");
			int intbyte = Integer.parseInt(bytes[0].trim());
			int index = Globals.memory.dataMemory.storeByte((byte) intbyte);
			Globals.memory.dataMemory.storeByte((byte) (intbyte >>> 8));
			Globals.memory.dataMemory.storeByte((byte) (intbyte >>> 16));
			Globals.memory.dataMemory.storeByte((byte) (intbyte >>> 24));
			for (int i = 1; i < bytes.length; i++) {
				intbyte = Integer.parseInt(bytes[i].trim());
				Globals.memory.dataMemory.storeByte((byte) intbyte);
				Globals.memory.dataMemory.storeByte((byte) (intbyte >>> 8));
				Globals.memory.dataMemory.storeByte((byte) (intbyte >>> 16));
				Globals.memory.dataMemory.storeByte((byte) (intbyte >>> 24));
			}
			Globals.Data.put(tag, new DataType(tag, type, index,
					4 * bytes.length));
			break;
		}
		case ".space": {
			int index = Globals.memory.dataMemory.storeByte((byte) 0);
			for (int i = 0; i < Integer.parseInt(value) - 1; i++) {
				Globals.memory.dataMemory.storeByte((byte) 0);
			}
			Globals.Data.put(tag,
					new DataType(tag, type, index, Integer.parseInt(value)));
			break;
		}
		}
	}
}