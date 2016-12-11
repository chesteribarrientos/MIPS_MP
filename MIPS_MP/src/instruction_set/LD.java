package instruction_set;

import config.Opcode;
import interfaces.IConverter;
import interfaces.IExecutor;
import machine.Machine;

/**
 * @author laurencefoz
 */

public class LD extends MemoryInstruction implements IConverter, IExecutor {
    @Override
    public int getOpcode(String statement) {
        return (Opcode.LD << 26) | super.getPartial(statement);
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
		super.doWriteBack(opcode, machine);
	}
}
