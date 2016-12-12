/**
 * 
 */
package interfaces;

import java.util.List;

/**
 * @author Chester
 *
 */
public interface IDependencyCheck {
	public boolean hasWriteBack();
	public boolean hasMemoryStore();
	public boolean HasDependency(int opcode, List<Integer> code);
}
