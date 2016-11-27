package instruction_set;

import interfaces.IConverter;

/**
 * 
 * @author Chester
 *
 */
public class J extends JType implements IConverter {

	@Override
	public int getOpcode(String statement) {
		return (0b000010 << 26) | super.getPartial(statement);
	}

}
