package machine;

import java.util.HashMap;

import config.Config;
import interfaces.IExecutor;
import utils.InstructionUtils;
import utils.OpcodeUtils;
import utils.Print;

/**
 * 
 * @author Chester
 *
 */
public class Machine {

	private Registers registers;
	private Memory memory;
	private HashMap<String, Pipeline> pipeline;
	private long PC;

	public Machine() {
		registers = new Registers();
		memory = new Memory(Config.MEMORY_SIZE);
		initPipeline();
		PC = Config.CODE_START;
	}

	private void initPipeline(){
		pipeline = new HashMap<String, Pipeline>();
		pipeline.put("IF/ID", new IFID());
		pipeline.put("ID/EX", new IDEX());
		pipeline.put("EX/MEM", new EXMEM());
		pipeline.put("MEM/WB", new MEMWB());
	}
	public Registers getRegisters() {
		return registers;
	}

	public Memory getMemory() {
		return memory;
	}

	public long getPC(){
		return PC;
	}
	
	public void setPC(long value){
		PC = value;
	}
	public HashMap<String, Pipeline> getPipeline(){
		return this.pipeline;
	}
	/**
	 * simplified controls for registers
	 **/
	public void storeToGPR(int gprNum, long value) {
		registers.storeToGPR(gprNum, value);
	}

	public void storeToGPRunsigned(int gprNum, int value) {
		registers.storeToGPR(gprNum, Integer.toUnsignedLong(value));
	}

	public void storeToGPRunsigned(int gprNum, short value) {
		registers.storeToGPR(gprNum, Short.toUnsignedLong(value));
	}

	public void storeToFPR(int fprNum, long value) {
		registers.storeToFPR(fprNum, value);
	}

	public void storeToFPRunsigned(int fprNum, int value) {
		registers.storeToFPR(fprNum, Integer.toUnsignedLong(value));
	}

	public void storeToFPRunsigned(int fprNum, short value) {
		registers.storeToFPR(fprNum, Short.toUnsignedLong(value));
	}
	public long loadFromGPR(int gprNum){
		return registers.getFromGPR(gprNum);
	}
	public long loadFromFPR(int fprNum){
		return registers.getFromFPR(fprNum);
	}
	
	/**
	 * simplified controls for memory, 
	 * java list is limited to int.max_size, need to downcast long to int type
	 **/
	public void storeDoubleWordToMemory(int address, long value) {
		memory.loadToMemory(address, 8, value);
	}

	public void storeWordToMemory(int address, long value) {
		memory.loadToMemory(address, 4, value);
	}

	public void storeHalfWordToMemory(int address, long value) {
		memory.loadToMemory(address, 2, value);
	}

	public void storeByteToMemory(int address, long value) {
		memory.loadToMemory(address, 1, value);
	}
	
	public long loadDoubleFromMemory(int address){
		return memory.getFromMemory(address, 8);
	}
	
	public int loadWordFromMemory(int address){
		return (int) memory.getFromMemory(address, 4);
	}
	
	public long loadHalfWordFromMemory(int address){
		return memory.getFromMemory(address, 2);
	}
	
	public long loadByteFromMemory(int address){
		return memory.getFromMemory(address, 1);
	}
	
	/**
	 * pipeline control
	 **/
	public void doIFCycle(){
		//System.out.println("***IF***");
		IFID ifid = (IFID) pipeline.get("IF/ID");
		int old = ifid.IR();
		
		int opcode = loadWordFromMemory((int)PC); //fetch
		ifid.setIR(opcode);
		//check branch
		if(OpcodeUtils.isBranch(old) && OpcodeUtils.opOutput(old)){
			PC = ifid.NPC() + OpcodeUtils.imm(old) << 2;
		}
		else{
			PC += 4;
		}
		ifid.setNPC(PC);
	}
	
	public void doIDCycle(){
		//System.out.println("***ID***");
		IDEX idex = (IDEX) pipeline.get("ID/EX");
		IFID ifid = (IFID) pipeline.get("IF/ID");
		
		idex.setA(loadFromGPR(OpcodeUtils.rs(ifid.IR())));
		idex.setB(loadFromGPR(OpcodeUtils.rt(ifid.IR())));
		idex.setImm(OpcodeUtils.imm(ifid.IR()));
		idex.setIR(ifid.IR());
	}
	
	public void doExCycle(){
		//System.out.println("***EX***");
		IDEX idex = (IDEX) pipeline.get("ID/EX");
		EXMEM exmem = (EXMEM) pipeline.get("EX/MEM");

		IExecutor executor = (IExecutor) InstructionUtils.getInstructionEnum(idex.IR()).getInstructionConverter();
		executor.execute(idex.IR(), this);
		
		exmem.setIR(idex.IR());
		exmem.setB(idex.B());
	}
	
	public void doMemCycle(){
		//System.out.println("***MEM***");
		EXMEM exmem = (EXMEM) pipeline.get("EX/MEM");
		MEMWB memwb = (MEMWB) pipeline.get("MEM/WB");
		memwb.setIR(exmem.IR());
		
		IExecutor executor = (IExecutor) InstructionUtils.getInstructionEnum(exmem.IR()).getInstructionConverter();
		executor.execute_memory(exmem.IR(), this);
	}
	
	public void doWBCycle(){
		//System.out.println("***WB***");
		MEMWB memwb = (MEMWB) pipeline.get("MEM/WB");
		IExecutor executor = (IExecutor) InstructionUtils.getInstructionEnum(memwb.IR()).getInstructionConverter();
		executor.execute_writeback(memwb.IR(), this);		
	}
}
