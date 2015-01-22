        .globl   _yield
        .align   4
        
_yield:
    pusha

    movl        %esp, %eax
    movl        %eax, stackPointer

    call        _transfer

    movl        stackPointer, %eax
    movl        %eax, %esp
        
    popa
    ret

    .globl   set_stack_pointer
    .align   4
set_stack_pointer:
	movl	(%esp), %eax
	movl	stackPointer, %esp
	pushl	%eax
	ret

	.globl get_stack_pointer
	.align   4
get_stack_pointer:
	movl	%esp, %eax
	addl	$4, %eax
	ret
