.data
five: .word 5
four: .word 4
.text
main:
		lui $t1, 35
		sw $t1, 0($t0)
		lw $t2, 0($t0)
		j label
		
label:	
		add $t4,$t5,$t1
		sw $t2,8($t0)
		
		sub $t4,$t5,$t1
		and $t7,$t5,$t1