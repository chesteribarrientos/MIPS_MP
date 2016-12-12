package interfaces;

import machine.Machine;

/**
 * 
 * @author Chester
 *
 */
public interface IExecutor {

	public void execute(int opcode, Machine machine);
	public void execute_memory(int opcode, Machine machine);
	public void execute_writeback(int opcode, Machine machine);
}
