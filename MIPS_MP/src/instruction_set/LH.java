package instruction_set;

import interfaces.IConverter;

/**
 * 
 * @author Chester
 *
 */
public class LH extends MemoryInstruction implements IConverter {

	@Override
	public int getOpcode(String statement) {
		return (0b100001 << 26) | super.getPartial(statement);
	}

}
