XOR R5, R1, R3
SLT R3, R1, R2
BEQC R3,R1,L1
NOP
DADDIU R3,R0,0x0002
LD R1,1000(R2)
L1:SD R5, 1000(R4)
BC L1
Watermelawn