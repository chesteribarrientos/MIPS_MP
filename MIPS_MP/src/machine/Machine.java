package machine;

public class Machine {

	private Registers registers;
	private Memory memory;
	private int IR;
	private int NPC;
	private int A;
	private int B;
	private int Imm;
	private int ALUOutput;
	private int Cond;
	
	public Registers getRegisters() {
		return registers;
	}
	public Memory getMemory() {
		return memory;
	}
	
	public int getIR() {
		return IR;
	}
	public void setIR(int iR) {
		IR = iR;
	}
	public int getNPC() {
		return NPC;
	}
	public void setNPC(int nPC) {
		NPC = nPC;
	}
	public int getA() {
		return A;
	}
	public void setA(int a) {
		A = a;
	}
	public int getB() {
		return B;
	}
	public void setB(int b) {
		B = b;
	}
	public int getImm() {
		return Imm;
	}
	public void setImm(int imm) {
		Imm = imm;
	}
	public int getALUOutput() {
		return ALUOutput;
	}
	public void setALUOutput(int aLUOutput) {
		ALUOutput = aLUOutput;
	}
	public int getCond() {
		return Cond;
	}
	public void setCond(int cond) {
		Cond = cond;
	}
	
}
