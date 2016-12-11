package instruction_set;

import config.Opcode;
import interfaces.IConverter;

/**
 * 
 * @author Chester
 *
 */
public class J extends JType implements IConverter {

	@Override
	public int getOpcode(String statement) {
		return (Opcode.J << 26) | super.getPartial(statement);
	}

}
