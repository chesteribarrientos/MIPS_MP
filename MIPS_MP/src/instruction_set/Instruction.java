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
	
	/** I Type**/
	BEQ		(new BEQ(), "BEQ"),
        BEQC		(new BEQC(), "BEQC"),
        DADDIU		(new DADDIU(), "DADDIU"),
        DSUBU		(new DSUBU(), "DSUBU"),
	SLTI            (new SLTI(), "SLTI"),
        XOR		(new XOR(), "XOR"),
        SLT		(new SLT(), "SLT"),
	LUI		(new LUI(), "LUI"),
	LD		(new LD(), "LD"),
	SD		(new SD(), "SD"),
	LB		(new LB(), "LB"),
	LH		(new LH(), "LH"),
	
	/** R Type**/
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
