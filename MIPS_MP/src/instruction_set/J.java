package instruction_set;

import interfaces.IConverter;

public class J implements IConverter{

	
	@Override
	public int getOpcode(String statement) {
		
		String [] words = statement.split("[,\\s]+");
		
		int finalOpcode = 0;
		
		int opcode = 0b000010;
		int instr_index = Integer.decode(words[1]);
		
		finalOpcode = (opcode << 26) | instr_index;
		return finalOpcode;
	}
	
}
