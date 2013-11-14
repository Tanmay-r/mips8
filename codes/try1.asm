.data
five: .word 5
four: .word 4
.text
main:
		lui $t1, 35
		sw $t1, 0($t0)
		lw $t2, 0($t0)
label:	add $t4,$t5,$t1
		add $t7,$t7,$t1
		beq $t1, $t2, label
		sltu $t4,$t5,$t1
		sub $t4,$t5,$t1
		and $t7,$t5,$t1
