package instruction_set;

import interfaces.IConverter;
import interfaces.IExecutor;

/**
 * @author laurencefoz
 */

public class BEQC implements IConverter, IExecutor {

    @Override
    public int getOpcode(String statement) {
        String[] words = statement.split("[,\\s]+");
        
        int opcode = 0b001000;
        int rs = Integer.parseInt(words[1].substring(1));
        int rt = Integer.parseInt(words[2].substring(1));
        int offset = Integer.decode(words[3]);
        
        int finalOpcode = (opcode << 26) | (rs << 21) | (rt << 16) | offset;
        return finalOpcode;
    }

    @Override
    public void execute(int opcode) {
        // TODO Auto-generated method stub
    }
    
}
