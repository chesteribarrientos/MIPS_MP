package instruction_set;

import config.Opcode;
import interfaces.IConverter;

/**
 * 
 * @author Chester
 *
 */
public class LB extends MemoryInstruction implements IConverter {

	@Override
	public int getOpcode(String statement) {
            return (Opcode.LB << 26) | super.getPartial(statement);
	}

}
