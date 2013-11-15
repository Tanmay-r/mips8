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
import arch.mips8.instruction.branch.*;
import arch.mips8.instruction.oneRoneI.*;
import arch.mips8.instruction.oneR.*;
import arch.mips8.instruction.oneI.*;

public class FileParser {
	private ArrayList<String> code;
	private String threeR, twoRoneI, twoR, oneR, oneRoneIoneR, oneI, oneRoneI;
	private Map<Integer, String> LabelsLocation = new HashMap<Integer, String>();
	int instrIndex;

	public FileParser(String filePath) throws Exception {
		Globals.reset();
		threeR = " add sub addu subu and or slt sltu ";
		twoRoneI = " addi addiu andi ori sll srl beq bne slti sltiu ";
		// beq, bne --> Branch Intruction
		twoR = " mult div ";
		oneR = " mfhi mflo jr ";
		oneRoneIoneR = " lw sw "; // -->TwoROneIInstruction
		oneRoneI = " lui li la ";
		oneI = " j jal ";

		instrIndex = 0;
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
			parseCode();

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
				if (line.contains(":")) {
					String label = line.substring(0, line.indexOf(':'));
					label.trim();
					Globals.Labels.put(label, 0);
				}
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
					label.trim();
					instr = line.substring(line.indexOf(':') + 1);
					Globals.Labels.put(label, Globals.instructions.size());
				} else {
					instr = line;
				}
				parseInstruction(instr);

			}

		}
		for (Integer key : LabelsLocation.keySet()) {
			if (Globals.instructions.get(key).getInstructionName().split(" ")[0]
					.equals("beq")
					|| Globals.instructions.get(key).getInstructionName()
							.split(" ")[0].equals("bne")) {
				((BranchInstruction) (Globals.instructions.get(key))).immd = (Globals.Labels
						.get(LabelsLocation.get(key)) - key);
			} else if (Globals.instructions.get(key).getInstructionName()
					.split(" ")[0].equals("jal")
					|| Globals.instructions.get(key).getInstructionName()
							.split(" ")[0].equals("j")) {
				((OneIInstruction) (Globals.instructions.get(key))).immd = (long) Globals.Labels
						.get(LabelsLocation.get(key));
			}
		}
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
			if (s.length > 4)
				throw new Exception("Syntax Error : " + instr);
			String type = " " + s[0] + " ";
			if (threeR.contains(type)) {
				String r1 = s[1];
				String r2 = s[2];
				String r3 = s[3];
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
										- Globals.instructions.size()));
						LabelsLocation.put(Globals.instructions.size() - 1, i1);

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
										- Globals.instructions.size()));
						LabelsLocation.put(Globals.instructions.size() - 1, i1);

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
					i1 = s[2].split("\\(")[0];
					r2 = s[2].split("\\(")[1].replace(")", "");
				} else {
					i1 = s[2];
					r2 = s[3].replace(")", "");
				}
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
				switch (type.trim()) {
				case "jal":
					try {
						Globals.instructions.add(new JumpAndLinkInstruction(
								(long) Integer.parseInt(i1)));
					} catch (NumberFormatException e) {
						Globals.instructions.add(new JumpAndLinkInstruction(
								Globals.Labels.get(i1)));
						LabelsLocation.put(Globals.instructions.size() - 1, i1);
					}
					break;
				case "j":
					try {
						Globals.instructions.add(new JumpInstruction(
								(long) Integer.parseInt(i1)));
					} catch (NumberFormatException e) {
						Globals.instructions.add(new JumpInstruction(
								Globals.Labels.get(i1)));
						LabelsLocation.put(Globals.instructions.size() - 1, i1);
					}
					break;
				}

			} else if (oneRoneI.contains(type)) {
				String r1 = s[1];
				String i1 = s[2];
				switch (type.trim()) {
				case "lui":
					Globals.instructions.add(new LuiInstruction(Globals
							.getRegister(r1), (long) Integer.parseInt(i1)));
					break;
				case "li":
					Globals.instructions.add(new LuiInstruction(Globals
							.getRegister(r1),
							(long) ((Integer.parseInt(i1)) >> 16)));
					Globals.instructions.add(new OriInstruction(Globals
							.getRegister(r1), Globals.getRegister(r1),
							(long) ((Integer.parseInt(i1)) & 0xffff)));
					break;
				case "la":

					int value = Globals.Data.get(i1).getIndex();

					Globals.instructions.add(new LuiInstruction(Globals
							.getRegister(r1), (long) (value >> 16)));
					Globals.instructions.add(new OriInstruction(Globals
							.getRegister(r1), Globals.getRegister(r1),
							(value & 0xffff)));
					break;
				}
			} else {
				throw new Exception("Syntax Error : " + "Not Considered "
						+ type);
			}
		}
	}

	private void parseData(String[] s) throws Exception {
		String tag = s[0].trim();
		String type = s[1].trim();
		String value = "";
		for (int j = 2; j < s.length; j++)
			value = value + " " + s[j];
		tag = tag.split(":")[0];
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
			int index = Globals.memory.dataMemory.storeHalfword(intbyte);
			for (int i = 1; i < bytes.length; i++) {
				intbyte = Integer.parseInt(bytes[i].trim());
				Globals.memory.dataMemory.storeHalfword(intbyte);
			}
			Globals.Data.put(tag, new DataType(tag, type, index,
					2 * bytes.length));
			break;
		}
		case ".word": {
			String[] bytes = value.split(",");
			int intbyte = Integer.parseInt(bytes[0].trim());
			int index = Globals.memory.dataMemory.storeInt(intbyte);
			for (int i = 1; i < bytes.length; i++) {
				intbyte = Integer.parseInt(bytes[i].trim());
				Globals.memory.dataMemory.storeInt(intbyte);
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
		default:
			throw new Exception("Error in data part: " + type.trim()
					+ " not considered");
		}
	}
}