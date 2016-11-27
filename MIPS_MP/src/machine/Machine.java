package machine;

import config.Config;

/**
 * 
 * @author Chester
 *
 */
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

	public Machine() {
		registers = new Registers();
		memory = new Memory(Config.MEMORY_SIZE);
	}

	public Registers getRegisters() {
		return registers;
	}

	public Memory getMemory() {
		return memory;
	}

	/**
	 * simplified controls for registers
	 **/
	public void loadToGPR(int gprNum, long value) {
		registers.storeToGPR(gprNum, value);
	}

	public void loadToGPRunsigned(int gprNum, int value) {
		registers.storeToGPR(gprNum, Integer.toUnsignedLong(value));
	}

	public void loadToGPRunsigned(int gprNum, short value) {
		registers.storeToGPR(gprNum, Short.toUnsignedLong(value));
	}

	public void loadToFPR(int fprNum, long value) {
		registers.storeToFPR(fprNum, value);
	}

	public void loadToFPRunsigned(int fprNum, int value) {
		registers.storeToFPR(fprNum, Integer.toUnsignedLong(value));
	}

	public void loadToFPRunsigned(int fprNum, short value) {
		registers.storeToFPR(fprNum, Short.toUnsignedLong(value));
	}

	/**
	 * simplified controls for memory
	 **/
	public void loadDoubleWordToMemory(int address, long value) {
		memory.loadToMemory(address, 8, value);
	}

	public void loadWordToMemory(int address, long value) {
		memory.loadToMemory(address, 4, value);
	}

	public void loadHalfWordToMemory(int address, long value) {
		memory.loadToMemory(address, 2, value);
	}

	public void loadByteToMemory(int address, long value) {
		memory.loadToMemory(address, 1, value);
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
