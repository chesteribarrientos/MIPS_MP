package instruction_set;

import java.util.List;

import interfaces.IConverter;
import interfaces.IDependencyCheck;
import interfaces.IExecutor;
import machine.Machine;

/**
 * 
 * @author Chester
 *
 */
public class NOP implements IConverter, IExecutor, IDependencyCheck {

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

	@Override
	public boolean hasWriteBack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasMemoryStore() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int HasDependency(int opcode, List<Integer> code) {
		// TODO Auto-generated method stub
		return 0;
	}
}
