#include <stdio.h>

#include "rom_access.h"

unsigned char rom_writeable(void) {
    return 1;
}

static void assert(int32 expected, int32 actual) {
    if (expected != actual) {
        printf("ERROR");
        exit(1);
    }
}

int main(int argNo, char** args) {
    int32 num = 1, actual;

    unsigned char heap[8];

    set_rom_byte(heap, num);
    actual = get_rom_byte(heap);
    assert(1, actual);

    num = -1;
    set_rom_byte(heap, num);
    actual = (int8) get_rom_byte(heap);
    assert(-1, actual);

    num = 1;
    set_rom_word(heap, num);
    actual = get_rom_word(heap);
    assert(1, actual);

    num = -1;
    set_rom_word(heap, num);
    actual = (int16) get_rom_word(heap);
    assert(-1, actual);

    num = 1;
    set_rom_dword(heap, num);
    actual = get_rom_dword(heap);
    assert(1, actual);

    num = -1;
    set_rom_dword(heap, num);
    actual = (int32)get_rom_dword(heap);
    assert(-1, actual);

    printf("SUCCESS");
}
