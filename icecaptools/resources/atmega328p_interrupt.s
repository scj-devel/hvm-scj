__SP_H__ = 0x3e
__SP_L__ = 0x3d
	.text
.global	_yield
	.type	_yield, @function
_yield:
	push r0
	push r1
	push r2
	push r3
	push r4
	push r5
	push r6
	push r7
	push r8
	push r9
	push r10
	push r11
	push r12
	push r13
	push r14
	push r15
	push r16
	push r17
	push r18
	push r19
	push r20
	push r21
	push r22
	push r23
	push r24
	push r25
	push r26
	push r27
	push r28
	push r29
	push r30
	push r31

	in r16, __SP_H__
	in r17, __SP_L__
	sts stackPointer+1,r16
	sts stackPointer,r17

	call _transfer	

	lds r16,  stackPointer+1
	lds r17,  stackPointer
	out __SP_H__, r16
	out __SP_L__, r17
	
	pop r31
	pop r30
	pop r29
	pop r28
	pop r27
	pop r26
	pop r25
	pop r24
	pop r23
	pop r22
	pop r21
	pop r20
	pop r19
	pop r18
	pop r17
	pop r16
	pop r15
	pop r14
	pop r13
	pop r12
	pop r11
	pop r10
	pop r9
	pop r8
	pop r7
	pop r6
	pop r5
	pop r4
	pop r3
	pop r2
	pop r1
	pop r0
	ret
	.size	_yield, .-_yield

.global	get_stack_pointer
	.type	get_stack_pointer, @function
get_stack_pointer:
	in r24, __SP_L__
	in r25, __SP_H__
	adiw R24, 2 
	ret
	.size	get_stack_pointer, .-get_stack_pointer

.global	set_stack_pointer
	.type	set_stack_pointer, @function
set_stack_pointer:
	sts workingRegister1, r22
	sts workingRegister2, r23
	
	pop r23
	pop r22			

	sts workingRegister3, r16
	sts workingRegister4, r17
	
	lds r16,  stackPointer+1
	lds r17,  stackPointer

 	out __SP_H__, r16
	out __SP_L__, r17	

	push r22
	push r23		

	lds r22, workingRegister1
	lds r23, workingRegister2
	lds r16, workingRegister3
	lds r17, workingRegister4
	
	ret
	.size	set_stack_pointer, .-set_stack_pointer
