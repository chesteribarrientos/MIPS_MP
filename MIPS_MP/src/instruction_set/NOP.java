package instruction_set;

import interfaces.IConverter;
import interfaces.IExecutor;
import machine.Machine;

/**
 * 
 * @author Chester
 *
 */
public class NOP implements IConverter, IExecutor {

	@Override
	public int getOpcode(String statement) {
		// TODO Auto-generated method stub
		return 0x0;
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

	}

}
