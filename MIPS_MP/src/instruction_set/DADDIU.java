package instruction_set;

import interfaces.IConverter;

/**
 * @author laurencefoz
 */
public class DADDIU extends ITypeArithmetic implements IConverter {
    
    @Override
    public int getOpcode(String statement) {
        return (0b011001 << 26) | super.getPartial(statement);
    }
    
}
