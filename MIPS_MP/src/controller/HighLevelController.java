/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import config.Config;
import interfaces.IConverter;
import interfaces.IDependencyCheck;
import machine.IDEX;
import machine.MEMWB;
import machine.Machine;
import utils.InstructionUtils;

/**
 * @author Chester
 *
 */
public class HighLevelController {

	private Machine machine;
	private int EoFCode;
	private List<Integer> code;
	private int lastFinishedIR;
	private CycleFlags cycleFlags; 
	//private
	//private IDEX idex;
	//private 
	
	public HighLevelController(Machine machine){
		this.machine = machine;
		EoFCode = Config.CODE_START;
		//code = new ArrayList<Integer>();
		cycleFlags = new CycleFlags();
		//IDEX idex = machine.getPipeline().get("ID/EX");
		
		cycleFlags.IFactive(true); //remove later
	}
	
	
	public void loadCodeIntoMemory(List<Integer> code){
		this.code = code;
		int index = Config.CODE_START;
		for (Integer opcode: code){
			machine.storeWordToMemory(index, opcode);
			index+=4;
		}
		EoFCode = index;
	}
	
	public void runCode(){
		cycleFlags.IFactive(true);//set IF as waiting to run
		while(machine.getPC() != EoFCode){
			runCycle();
		}
	}
	
	boolean stalled = false;
	public void runCycle(){
		boolean dependencyNotYetChecked = true;
		
		if(cycleFlags.WBisActive()){
			machine.doWBCycle();
			MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
			lastFinishedIR = memwb.IR();
			cycleFlags.WBactive(false);
		}
		if(cycleFlags.MEMisActive()){
			machine.doMemCycle();
			cycleFlags.MEMactive(false);
			cycleFlags.WBactive(true);
		}
		if(cycleFlags.EXisActive()){
			machine.doExCycle();
			cycleFlags.EXactive(false);
			cycleFlags.MEMactive(true);
		}
		if(cycleFlags.IDisActive()){
			IDEX idex = (IDEX) machine.getPipeline().get("ID/EX");
			IDependencyCheck ic = (IDependencyCheck) InstructionUtils.getInstructionEnum(idex.IR()).getInstructionConverter();
			
			if(dependencyNotYetChecked) {
				if(ic.HasDependency(idex.IR(), code)){
					System.out.println("dependency found");
					stalled = true;
				}
				dependencyNotYetChecked = false;
			}
			
			if(lastFinishedIR == idex.IR()){
				stalled = false;
			}
			
			if(!stalled){
				machine.doIDCycle();
				cycleFlags.IDactive(false);
				cycleFlags.EXactive(true);
				
			}
		}
		if(cycleFlags.IFisActive()){
			machine.doIFCycle();
			//cycleFlags.IFactive(false);
			cycleFlags.IDactive(true);
		}
	}
}
