package utils;

import config.Opcode;
import instruction_set.Instruction;

/**
 * 
 * @author Chester
 *
 */

public class InstructionUtils {

	public static String getInstructionName(String text) {
		if (text.indexOf(' ') > -1) {
			return text.substring(0, text.indexOf(' '));
		} else {
			return text;
		}
	}

	public static Instruction getInstructionEnum(String statement) {
		String name = getInstructionName(statement);
		for (Instruction i : Instruction.values()) {
			if (i.name().equalsIgnoreCase(name)) {
				return i;
			}
		}
		System.out.println("Parser intrusion. Instruction not found. Defaulting to NOP");
		return Instruction.NOP;// default nop
	}
	
	public static Instruction getInstructionEnum(int opcode){
		int opcode6 = OpcodeUtils.opcode6(opcode);
		
		if(opcode6 == Opcode.RType){
			int opcode21 = OpcodeUtils.opcode21(opcode);
			for(Instruction i: Instruction.values()){
				if(opcode6 == i.getOpcode6() && opcode21 == i.getOpcode21())
					return i;
			}
		}
		else{
			for(Instruction i: Instruction.values()){
				if(opcode6 == i.getOpcode6())
					return i;
			}
		}
		System.out.println("Parser intrusion. Instruction not found. Defaulting to NOP");
		return Instruction.NOP;
	}
}
