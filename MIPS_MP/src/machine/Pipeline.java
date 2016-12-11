/**
 * 
 */
package machine;

/**
 * @author Chester
 *
 */
public class Pipeline {

	private int instructionRegister;
	public Pipeline(){
		
	}
	public void setIR(int opcode){
		instructionRegister = opcode;
	}
	public int IR(){
		return instructionRegister;
	}
}
