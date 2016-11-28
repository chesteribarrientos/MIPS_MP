package instruction_set;

import config.Opcode;
import interfaces.IConverter;

/**
 * @author laurencefoz
 */
public class XOR extends RType implements IConverter {
    @Override
    public int getOpcode(String statement) {
    	if (super.getRegisterOps(statement) == Opcode.NOP) return 0x0;
    	return (0b000000 << 26) | super.getRegisterOps(statement) | 0b00000 | Opcode.XOR;
    }
}
