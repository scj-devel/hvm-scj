	.section	.text
	.align	2
	.globl	_get_stack_pointer
	.type	_get_stack_pointer, @function
_get_stack_pointer:
	movd	(sp), (r1,r0)
	jump	(ra)
	.size	_get_stack_pointer, .-_get_stack_pointer
	.align	2
	.globl	_set_stack_pointer
	.type	_set_stack_pointer, @function
_set_stack_pointer:
	movd	(r3,r2), (sp)
	jump	(ra)
	.size	_set_stack_pointer, .-_set_stack_pointer
	.align	2
	.globl	__yield
	.type	__yield, @function
__yield:
	push    ra
	push    $0x4, r0

	sprd	isp, (r1,r0)
	loadw   0(r1,r0), r2
	push    $0x1, r2
	loadw   1(r1,r0), r2
	push    $0x1, r2

	push    $0x4, r4
	push    $0x4, r8
	push    $0x2, r12

	movd	$_stackPointer@l, (r1,r0)
	movd	(sp), (r3,r2)
	stord	(r3,r2), 0(r1,r0)

	bal     (ra), __interrupt_handler@c

	movd	$_stackPointer@l, (r3,r2)
	loadd   0(r3,r2), (r3,r2)

	movd	(r3,r2), (sp)

	pop     $0x2, r12
	pop     $0x4, r8
	pop     $0x4, r4

	sprd	isp, (r1,r0)
	pop     $0x1, r2
	storw   r2, 1(r1,r0)
	pop     $0x1, r2
	storw   r2, 0(r1,r0)

	pop     $0x4, r0
	pop     ra

    jump	(ra)
	.size	__yield, .-__yield
	.align	2
