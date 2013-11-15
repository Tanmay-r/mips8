.data
five: .word 5
four: .word 4
.text
main:
		la $t1, five
		la $t2, four
		lw $t1, 0($t1)
		lw $t2, 0($t2)
		add $t1, $t1, $t2

		jal label
label:
		addi $sp, $sp, -8
		sw $t1, 0($sp)
		sw $t2, 4($sp)
		lui $t1, 450
		lui $t2, 500
		lw $t1, 0($sp)
		lw $t2, 4($sp)
		addi $sp, $sp, 8
		jr $ra

