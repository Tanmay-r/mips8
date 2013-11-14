.data
five: .word 5
four: .word 4
.text
main:

la $t7,five
lw $t1, 0($t7)

la $t7,four
lw $t2, 0($t7)

add $t3,$t1,$t2
