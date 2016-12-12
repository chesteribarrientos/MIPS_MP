/**
 * 
 */
package machine;

import utils.Stringify;

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
	public String toString(){
		return "EX.IR: " + Stringify.as32bitHex(instructionRegister) 
			+ "\nALU: " + Stringify.as64bitHex(ALUOutput) 
			+ "\nCond: " + cond + "\nB: " + Stringify.as64bitHex(b);
	}
	
}
