package instruction_set;

import config.Opcode;
import interfaces.IConverter;

/**
 * 
 * @author Chester
 *
 */

public enum Instruction {

	/** J Type**/
	J		(new J(), "J", Opcode.J,0),
    BC		(new BC(), "BC", Opcode.BC,0),
	
	/** I Type**/
    BEQC	(new BEQC(), "BEQC", Opcode.BEQC,0),
    DADDIU	(new DADDIU(), "DADDIU",Opcode.DADDIU,0),
	LD		(new LD(), "LD",Opcode.LD,0),
	SD		(new SD(), "SD",Opcode.SD,0),
	
	/** R Type**/
    DSUBU	(new DSUBU(), "DSUBU",Opcode.RType,Opcode.DSUBUfunc),
    XOR		(new XOR(), "XOR",Opcode.RType,Opcode.XORfunc),
    SLT		(new SLT(), "SLT",Opcode.RType,Opcode.SLTfunc),
	NOP		(new NOP(), "NOP",Opcode.RType,0);

	
	
	private IConverter converter;
	private String name;
	private int opcode6; //opcode[0-5]
	private int opcode21;//opcode[21-31]

	//params: converter class, name, opcode[0-5], opcode[21-31]
	Instruction(IConverter converter, String textEquivalent, int opcode6, int opcode21){
	      this.converter = converter;
	      name = textEquivalent;
	      this.opcode6 = opcode6;
	      this.opcode21 = opcode21;
	}

	public IConverter getInstructionConverter() {
		return this.converter;
	}
	public String getInstructionName(){ //for matching
		return this.name;
	}
	public int getOpcode6(){
		return opcode6;
	}
	public int getOpcode21(){
		return opcode21;
	}
	 
}
