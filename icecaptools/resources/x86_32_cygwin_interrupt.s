.globl   __yield
.align   4
__yield:
    pusha

    movl        %esp, %eax
    movl        %eax, _stackPointer

    call        __transfer

    movl        _stackPointer, %eax
    movl        %eax, %esp
        
    popa
    ret

    .globl   _set_stack_pointer
    .align   4
_set_stack_pointer:
	movl	(%esp), %eax
	movl	_stackPointer, %esp
	pushl	%eax
	ret

	.globl _get_stack_pointer
	.align   4
_get_stack_pointer:
	movl	%esp, %eax
	addl	$4, %eax
	ret
