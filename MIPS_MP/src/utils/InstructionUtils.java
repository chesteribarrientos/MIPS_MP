package utils;

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
			if (i.name().equals(name)) {
				return i;
			}
		}
		return Instruction.NOP;// default nop
	}
}
