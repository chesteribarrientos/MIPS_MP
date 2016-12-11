/**
 * 
 */
package machine;

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
}
