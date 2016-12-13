/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import config.Config;
import interfaces.IDependencyCheck;
import machine.IFID;
import machine.MEMWB;
import machine.Machine;
import utils.InstructionUtils;
import utils.Stringify;

/**
 * @author Chester
 *
 */
public class HighLevelController {

	private Machine machine;
	private int EoFCode;
	private List<Integer> code;
	//private int lastFinishedIR;
	private List<Integer> finishedIRs;
	private CycleFlags cycleFlags; 
        private boolean isDone;
	//private
	//private IDEX idex;
	//private 
	
	public HighLevelController(Machine machine){
		this.machine = machine;
		EoFCode = Config.CODE_START;
		cycleFlags = new CycleFlags(); //set of flags for each cycle
		cycleFlags.IFactive(true); //remove later
        isDone = false;
        
	}
	
	
	public void loadCodeIntoMemory(List<Integer> code){
		//reset
		finishedIRs = new ArrayList<Integer>();
		machine.resetMachine();
		
		//load code
		this.code = code;
		int index = Config.CODE_START;
		for (Integer opcode: code){
			machine.storeWordToMemory(index, opcode);
			index+=4;
		}
		EoFCode = index;
		System.out.println("EOF: " + Stringify.as64bitHex(EoFCode));
	}
	
	public void runCode(){
		cycleFlags.IFactive(true);//set IF as waiting to run
		while(machine.getPC() != EoFCode){
			runCycle();
		}
	}
        
	private void setDone(){
            isDone = true;
        }
        
        public boolean getDone(){
            return isDone;
        }
        
	private boolean stalled = false;
	public boolean isStalled(){
		return stalled;
	}
	
	boolean dependencyNotYetChecked = true;
	int dependencyIR = 0;
	
	public void runCycle(){
		int tempIR = 0;
		if(!finishedIRs.isEmpty())
			System.out.print("Last IR Wb: " + Stringify.as32bitHex(finishedIRs.get(finishedIRs.size()-1)));
		System.out.println(" dependencyIR: " + Stringify.as32bitHex(dependencyIR));
		
		//System.out.println("Stalled: " + stalled);
                
                
		if(cycleFlags.WBisActive()){
			machine.doWBCycle();
			MEMWB memwb = (MEMWB) machine.getPipeline().get("MEM/WB");
			tempIR = memwb.IR();
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
			IFID ifid = (IFID) machine.getPipeline().get("IF/ID");
			IDependencyCheck ic = (IDependencyCheck) InstructionUtils.getInstructionEnum(ifid.IR()).getInstructionConverter();
			
			if(dependencyNotYetChecked) {
				System.out.println("Checking for dependency");
				dependencyIR = ic.HasDependency(ifid.IR(), code, machine);
				if(dependencyIR != 0){
					System.out.println("dependency found on: " + Stringify.as32bitHex(dependencyIR));
					stalled = true;
				}
				dependencyNotYetChecked = false;
			}
			
			if(finishedIRs.contains(dependencyIR)){ //if dependency has finished
				stalled = false;
				dependencyIR = 0;
			}
			System.out.println("Stalled: " + stalled);
			
			if(!stalled){
				machine.doIDCycle();
				cycleFlags.IDactive(false);
				cycleFlags.EXactive(true);
				dependencyNotYetChecked = true; //check for dependency of next ID Cycle again
			}
			else {
				System.out.println("Stalled ID: " + Stringify.as32bitHex(ifid.IR()));
			}
		}
		
		if(cycleFlags.IFisActive() && machine.getPC() < EoFCode){
			if(!stalled) {
				System.out.println("fetching...");
				machine.doIFCycle();
				if(!machine.hasBranchedCompact())
					cycleFlags.IDactive(true);
			}
			else{
				System.out.println("Stalled IF");
			}
		}
		finishedIRs.add(tempIR);
		
		//System.out.println("eof check");
		//System.out.println(machine.getPC());
		//System.out.println(EoFCode);
		if(machine.getPC() ==EoFCode){
			System.out.println("Last instruction fetched");
			
		}
		
		//System.out.println(code.size()-1 + " " + code.get(code.size()-1));
		if((finishedIRs.contains(code.get(code.size()-1)))){
			System.out.println("Code Done");
			setDone();
        }
	}
}
