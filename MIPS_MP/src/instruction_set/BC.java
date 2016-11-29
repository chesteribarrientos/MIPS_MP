package instruction_set;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IExecutor;

public class BC implements IConverter, IExecutor {
	
	@Override
	public int getOpcode(String statement) {
		String[] words = statement.split("[,\\s]+");

		int offset = Integer.parseInt(words[1]);
		if (offset < 0) offset = offset & 0x0000ffff;
		
		int finalOpcode = (Opcode.BC << 26) | offset;
		return finalOpcode;
	}

	@Override
	public void execute(int opcode) {
		// TODO Auto-generated method stub

	}
}
