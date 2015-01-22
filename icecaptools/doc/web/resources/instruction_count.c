#include <stdio.h>
#include <papi.h>

static long long ins_count;

void papi_start(void) {
    int ins = PAPI_TOT_INS;
    if (PAPI_start_counters(&ins, 1) != PAPI_OK) {
        printf("error starting instruction count\n");
    }
}

void papi_mark(void) {
    if (PAPI_read_counters(&ins_count, 1) != PAPI_OK) {
        printf("error reading instruction count\n");
    } else {
        printf("ins_count = %ld\n", (long int)ins_count);
    }
}

#if 0
int main(int c, char** args)
{
    int i;

    printf("starting...\n");

    start();

    stop();

    printf("done\n");
}
#endif
