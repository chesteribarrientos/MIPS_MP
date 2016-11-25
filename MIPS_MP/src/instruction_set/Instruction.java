package instruction_set;

import interfaces.IConverter;

public enum Instruction {

	/** J Type**/
	J		(new J(), "J"),
	
	/** I Type**/
	BEQ		(new BEQ(), "BEQ"),
	
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
