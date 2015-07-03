	.text
	.align	2
	.global	_yield
	.type	_yield, %function
_yield:
	stmfd	sp!, {r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, lr}
	.save   {r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, lr}

	ldr	r0, .L0
	str	sp, [r0, #0]
	bl	_transfer

	ldr	r0, .L0
	ldr	sp, [r0, #0]
	
	ldmfd	sp!, {r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, lr} 
	bx 	lr
.L0:
	.word	stackPointer
	
	.text
	.align	2
	.global	get_stack_pointer
	.type	get_stack_pointer, %function
get_stack_pointer:
	mov	r0, sp
	bx 	lr

	.text
	.align	2
	.global	set_stack_pointer
	.type	set_stack_pointer, %function	
set_stack_pointer:
	ldr	r0, .L0
	ldr     r0, [r0, #0]
	mov	sp, r0
	bx 	lr
	
