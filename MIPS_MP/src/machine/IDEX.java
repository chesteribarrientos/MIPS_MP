/**
 * 
 */
package machine;

import utils.Stringify;

/**
 * @author Chester
 *
 */
public class IDEX extends Pipeline{

	private long a;
	private long b;
	private long imm;
	
	public long A() {
		return a;
	}
	public long B() {
		return b;
	}
	public long Imm() {
		return imm;
	}
	
	public void setA(long a) {
		this.a = a;
	}
	public void setB(long b) {
		this.b = b;
	}
	public void setImm(short imm) { //sign extended
		this.imm = imm;
	}
	public String toString(){
		return "ID.IR: " + Stringify.as32bitHex(instructionRegister) 
			+ "\nA: " + Stringify.as64bitHex(a) + "\nB: " + Stringify.as64bitHex(b) 
			+ "\nImm: " + Stringify.as64bitHex(imm);
	}
}
