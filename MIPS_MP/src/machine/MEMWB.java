/**
 * 
 */
package machine;

import utils.Stringify;

/**
 * @author Chester
 *
 */
public class MEMWB extends Pipeline{

	private long LMD;
	private long ALUOutput;
	
	public long LMD() {
		return LMD;
	}
	public long ALUOutput() {
		return ALUOutput;
	}
	public void setLMD(long lMD) {
		LMD = lMD;
	}
	public void setALUOutput(long aLUOutput) {
		ALUOutput = aLUOutput;
	}
	public String toString(){
		return "MEM.IR: " + Stringify.as32bitHex(instructionRegister) 
			+ "\nLMD: " + Stringify.as64bitHex(LMD) + "\nALU: " + Stringify.as64bitHex(ALUOutput);
	}
}
