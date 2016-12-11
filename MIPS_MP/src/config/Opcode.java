package config;

/**
 * 
 * @author Chester
 *
 */
public class Opcode {

	/** J-Type **/
	public static final int J 		= 0b000010;
	public static final int BC 		= 0b110010;
	
	/** I Type **/
	public static final int BEQ 	= 0b000100;
	public static final int SLTI 	= 0b001010;
	public static final int BEQC 	= 0b001000;
	public static final int LUI 	= 0b001111;
	public static final int LB 		= 0b100000;
	public static final int DADDIU 	= 0b011001;
	public static final int LD 		= 0b110111;
	public static final int SD 		= 0b111111;

	/** R Type **/
	public static final int RType	= 0b000000;
	
	public static final int DSUBU 	= 0b101111; //remove, use DSUBUfunc instead, update classes as necessary
												//misleading as main opcode(6) is expected, 
	public static final int DSUBU21 = 0b00000;
	public static final int DSUBUfunc = 0b101111;
												//for r types, change into opcode[20-31] 
												//e.g. dsubu[21-25] and dsubufunc(6)
	public static final int XOR 	= 0b100110;//remove
	public static final int XOR21 	= 0b00000;
	public static final int XORfunc = 0b100110;
	
	public static final int SLT 	= 0b101010;//remove
	public static final int SLT21	= 0b00000;
	public static final int SLTfunc	= 0b101010;

	public static final int NOP 	= 0x0;
	
	
}
