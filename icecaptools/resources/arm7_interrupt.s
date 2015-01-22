	.text
	.thumb
	.syntax unified
	.align	2
	.word	stackPointer
	.global	_yield
	.type	_yield, %function
_yield:
	push {r0}
	push {r1}
	push {r2}
	push {r3}
	push {r4}
	push {r5}
	push {r6}
	push {r7}
	push {r8}
	push {r9}
	push {r10}	
	push {r11}
	push {r12}
	push {lr}	
	
	ldr  r0, =stackPointer 
	str	 sp,[r0]

	bl	_transfer

	ldr  r0, =stackPointer
	ldr	sp, [r0]
	
	pop {lr}
	pop {r12}
	pop {r11}
	pop {r10}
	pop {r9}
	pop {r8}
	pop {r7}
	pop {r6}
	pop {r5}
	pop {r4}
	pop {r3}	
	pop {r2}
	pop {r1}
	pop {r0}	

	bx 	lr
	
	.text
	.align	2
	.global	get_stack_pointer
	.type	get_stack_pointer, %function
get_stack_pointer:
	mov r0, sp
	bx lr

	.text
	.align	2
	.global	set_stack_pointer
	.type	set_stack_pointer, %function	
set_stack_pointer:
	ldr  r0, =stackPointer
	ldr  sp, [r0]
	bx lr