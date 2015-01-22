#include <stdlib.h>
#include <stdio.h>

typedef struct _array {
    short* buffer;
    short length;
} IntArray;

char **test(char** args);
void quicksort(short* array, short left, short right);

int main(int argv, char** args) {
   short i;
#if defined(REPORTINSTRUCTIONS)
    papi_start();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
#endif
   for (i = 0; i < 20; i++)
   {
      test(args);
#if defined(REPORTINSTRUCTIONS)
      papi_mark();
#endif
   }
   return 0;
}

char **test(char** args) {
    IntArray* numberSet;
    short i;

    numberSet = (IntArray*) malloc(sizeof(struct _array));
    numberSet -> buffer = (short*) malloc(sizeof(short) * 20);

    numberSet -> length = 20;
    for (i = 0; i < numberSet -> length; i++) {
        numberSet->buffer[i] = numberSet -> length - i;
    }

    quicksort(numberSet->buffer, 0, numberSet -> length - 1);

    for (i = 0; i < numberSet -> length - 1; i++) {
       // printf("%d ", numberSet->buffer[i]);
        if (numberSet->buffer[i] > numberSet->buffer[i + 1]) {
            return args;
        }
    }

    return 0;
}

void quicksort(short* array, short left, short right) {
    if (array != 0) {
        short low = left;
        short high = right;
        short pivot;

        if (low >= right) {
            return;
        }

        pivot = array[low + ((high - low) >> 1)];

        while (low <= high) {

            while (array[low] < pivot) {
                low++;
            }

            while (array[high] > pivot) {
                high--;
            }

            if (low <= high) {
                short temp = array[low];
                array[low] = array[high];
                array[high] = temp;

                low++;
                high--;
            }
        }

        if (high < low) {
            short T = high;
            high = low;
            low = T;
        }

        if (left < low)
            quicksort(array, left, low);
        if (high < right)
            quicksort(array, low == left ? low + 1 : low, right);
    }
}

