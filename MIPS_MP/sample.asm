XOR R5, R1, R3
S2: DADDIU R3,R0,0x0002
L1: SD R5, 3000(R4)
SLT R3, R1, R2
BEQC R3,R1,L1
NOP
LD R1,3000(R2)
BC S2
;Watermelawn
