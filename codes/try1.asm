.text
main:
lui $t1, 35
sw $t1, 0($t0)
lw $t2, 0($t0)
add $t4,$t5,$t1
add $t7,$t7,$t1
beq $t1, $t2, -2
sltu $t4,$t5,$t1
sub $t4,$t5,$t1
and $t7,$t5,$t1