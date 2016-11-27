package instruction_set;

import interfaces.IConverter;

/**
 * 
 * @author Chester
 *
 */
public class SLTI extends ITypeArithmetic implements IConverter {

	@Override
	public int getOpcode(String statement) {
		return (0b001010 << 26) | super.getPartial(statement);
	}

}
