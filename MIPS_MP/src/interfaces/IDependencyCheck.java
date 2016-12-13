/**
 * 
 */
package interfaces;

import java.util.List;

import machine.Machine;

/**
 * @author Chester
 *
 */
public interface IDependencyCheck {
	public boolean hasWriteBack();
	public boolean hasMemoryStore();
	public int HasDependency(int opcode, List<Integer> code, Machine machine);
}
