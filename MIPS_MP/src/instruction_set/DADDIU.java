package instruction_set;

import interfaces.IConverter;
import interfaces.IExecutor;
import machine.Machine;

/**
 * @author laurencefoz
 */
public class DADDIU extends ITypeArithmetic implements IConverter, IExecutor {
    
    @Override
    public int getOpcode(String statement) {
        return (0b011001 << 26) | super.getPartial(statement);
    }

	@Override
	public void execute(int opcode, Machine machine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute_memory(int opcode, Machine machine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute_writeback(int opcode, Machine machine) {
		// TODO Auto-generated method stub
		super.doWriteback(opcode, machine);
	}
    
}
