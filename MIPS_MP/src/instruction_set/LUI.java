package instruction_set;

import config.Opcode;
import interfaces.IConverter;

/**
 * 
 * @author Chester
 *
 */
public class LUI implements IConverter {

	@Override
	public int getOpcode(String statement) {
		String[] words = statement.split("[,\\s]+");

		int rt = Integer.parseInt(words[1].substring(1));
		int imm = Integer.parseInt(words[2], 16);

		int finalOpcode = (Opcode.LUI << 26) | (0b00000 << 21) | (rt << 16) | imm;
		return finalOpcode;
	}

}
