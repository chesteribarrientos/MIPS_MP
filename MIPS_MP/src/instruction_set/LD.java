package instruction_set;

import config.Opcode;
import interfaces.IConverter;

/**
 * @author laurencefoz
 */

public class LD extends MemoryInstruction implements IConverter {
    @Override
    public int getOpcode(String statement) {
        return (Opcode.LD << 26) | super.getPartial(statement);
    }
}
