package instruction_set;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IExecutor;

/**
 * 
 * @author Chester
 *
 */
public class BEQ implements IConverter, IExecutor {

	@Override
	public int getOpcode(String statement) {
		String[] words = statement.split("[,\\s]+");

		int rs = Integer.parseInt(words[1].substring(1));
		int rt = Integer.parseInt(words[2].substring(1));
		int offset = Integer.decode(words[3]);

		int finalOpcode = (Opcode.BEQ << 26) | (rs << 21) | (rt << 16) | offset;

		return finalOpcode;
	}

	@Override
	public void execute(int opcode) {
		// TODO Auto-generated method stub

	}

}
