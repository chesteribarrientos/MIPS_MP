package instruction_set;

import config.Opcode;
import interfaces.IConverter;

/**
 * @author lawrencefoz
 */
public class SD extends MemoryInstruction implements IConverter {

    @Override
    public int getOpcode(String statement) {
        return (Opcode.SD << 26) | super.getPartial(statement);
    }
    
}
