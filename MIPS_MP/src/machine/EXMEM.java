/**
 * 
 */
package machine;

/**
 * @author Chester
 *
 */
public class EXMEM extends Pipeline{

	private long ALUOutput;
	private boolean cond;
	private long b;
	
	public long ALUOutput() {
		return ALUOutput;
	}
	public boolean Cond() {
		return cond;
	}
	public long B() {
		return b;
	}
	public void setALUOutput(long aLUOutput) {
		ALUOutput = aLUOutput;
	}
	public void setCond(boolean cond) {
		this.cond = cond;
	}
	public void setB(long b) {
		this.b = b;
	}
	
	
}
