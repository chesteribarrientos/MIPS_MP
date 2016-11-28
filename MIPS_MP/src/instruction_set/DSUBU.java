package instruction_set;

import config.Opcode;
import interfaces.IConverter;

/**
 * @author laurencefoz
 */
public class DSUBU extends RType implements IConverter {
    @Override
    public int getOpcode(String statement) {
        return (0b000000 << 26) | super.getRegisterOps(statement) | 0b00000 | Opcode.DSUBU;
    }
}
