        .globl   _yield
        .align   4
        
_yield:
    pushq       %rax
    pushq       %rbx 
    pushq       %rcx 
    pushq       %rdx 
    pushq       %rbp 
    pushq       %rsi 
    pushq       %rdi 
    pushq       %r8 
    pushq       %r9 
    pushq       %r10 
    pushq       %r11 
    pushq       %r12 
    pushq       %r13 
    pushq       %r14 
    pushq       %r15

    movq        %rsp, %rax
    movq        %rax, stackPointer(%rip)
        
    call        _transfer

    movq        stackPointer(%rip), %rax
    movq        %rax, %rsp
        
    popq        %r15
    popq        %r14
    popq        %r13
    popq        %r12
    popq        %r11
    popq        %r10
    popq        %r9
    popq        %r8
    popq        %rdi
    popq        %rsi
    popq        %rbp
    popq        %rdx
    popq        %rcx
    popq        %rbx
    popq        %rax
    ret

    .globl   set_stack_pointer
    .align   4
set_stack_pointer:
	movq	(%rsp), %rax
    movq    stackPointer(%rip), %rsp
	pushq   %rax
	ret

	.globl get_stack_pointer
	.align   4
get_stack_pointer:
	movq	%rsp, %rax
	addq	$8, %rax
	ret
