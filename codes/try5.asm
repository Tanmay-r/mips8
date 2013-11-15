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