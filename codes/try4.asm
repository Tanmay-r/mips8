.data
five: .word 5
four: .word 4
.text
main:
		lui $t1, 35
		sw $t1, 0($t0)
		lw $t2, 0($t0)
		jal label
			
		add $t4,$t5,$t1
		sw $t2,8($t0)
label:		
		sub $t4,$t5,$t1
		and $t7,$t5,$t1
		jr $ra