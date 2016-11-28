package instruction_set;

import interfaces.IConverter;

/**
 * 
 * @author Chester
 *
 */

public enum Instruction {

	/** J Type**/
	J		(new J(), "J"),
        BC		(new BC(), "BC"),
	
	/** I Type**/
        BEQC		(new BEQC(), "BEQC"),
        DADDIU		(new DADDIU(), "DADDIU"),
	LD		(new LD(), "LD"),
	SD		(new SD(), "SD"),
	BEQ		(new BEQ(), "BEQ"),
	SLTI            (new SLTI(), "SLTI"),
	LUI		(new LUI(), "LUI"),
	LB		(new LB(), "LB"),
	LH		(new LH(), "LH"),
	
	/** R Type**/
        DSUBU		(new DSUBU(), "DSUBU"),
        XOR		(new XOR(), "XOR"),
        SLT		(new SLT(), "SLT"),
	NOP		(new NOP(), "NOP");

	
	
	private IConverter converter;
	private String name;

	Instruction(IConverter converter, String textEquivalent){
	      this.converter = converter;
	}

	public IConverter getInstructionConverter() {
		return this.converter;
	}
	public String getInstructionName(){ //for matching
		return this.name;
	}
	 
}
