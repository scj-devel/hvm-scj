#include <stdlib.h>
#include <stdio.h>

typedef struct _random {
    long int m_w;
    long int m_z;
} Random;

typedef struct _wordGenerator {
    short numberOfWordsToGenerate;
    Random* rand;
} WordGenerator;

typedef struct _textString {
    unsigned char top;
    unsigned char length;
    char* characters;
} TextString;

typedef struct _dataSet {
    TextString** elements;
    short length;
    short top;
} DataSet;

DataSet* create_DataSet() {
    DataSet* ds = malloc(sizeof(struct _dataSet));

    ds -> length = 1;
    ds -> elements = malloc(sizeof(TextString*) * 1);
    ds -> top = 0;

    return ds;
}

TextString* create_TextString(unsigned char length) {
    TextString* ts = malloc(sizeof(struct _textString));
    ts -> top = 0;
    ts -> length = length;
    ts -> characters = malloc(sizeof(char) * length);
    return ts;
}

void add(TextString* str, char ascii) {
    if (str -> top < str -> length) {
        str -> characters[str -> top++] = ascii;
    }
}

Random* create_random() {
    Random* r = malloc(sizeof(struct _random));
    r -> m_w = 42;
    r-> m_z = 0xcafebabe;
    return r;
}

int getInt(Random* this) {
    this -> m_z = 36969 * (this -> m_z & 65535) + (this -> m_z >> 16);
    this -> m_w = 18000 * (this -> m_w & 65535) + (this -> m_w >> 16);
    return (this -> m_z << 16) + this -> m_w; /* 32-bit result */
}

unsigned char getByte(Random* this) {
    unsigned char result = (unsigned char) (getInt(this) & 0xff);
    return result;
}

unsigned char getNonNegativeByte(Random* this) {
    unsigned char b = getByte(this);
    if (b < 0) {
        b = (unsigned char) -b;
    }
    if (b < 0) {
        b = 0;
    }
    return b;
}

WordGenerator* create_wordGenerator(short numberOfWordsToGenerate) {
    WordGenerator* wg = malloc(sizeof(struct _wordGenerator));
    wg -> numberOfWordsToGenerate = numberOfWordsToGenerate;
    wg -> rand = create_random();
    return wg;
}

static TextString* next(WordGenerator* this) {
    TextString* str = 0;
    if (this -> numberOfWordsToGenerate > 0) {
        unsigned char length;

        do {
            unsigned char n = getNonNegativeByte(this -> rand);
            length = (unsigned char) (n % 10);
        } while (length == 0);

        str = create_TextString(length);

        while (length > 0) {
            unsigned char ascii = (unsigned char) ((getByte(this -> rand) % 28) + 'A');
            add(str, (char) ascii);
            length--;
        }
        this -> numberOfWordsToGenerate--;
    }
    return str;
}

static char* toString(TextString* this) {
    char* tmp = malloc(sizeof(char) * this -> top + 1);
    short count;
    for (count = 0; count < this -> top; count++) {
        tmp[count] = this -> characters[count];
    }
    tmp[count] = 0;

    return tmp;
}

signed char compareTo(TextString* this, TextString* right) {
    TextString* other = (TextString*) right;
    unsigned char index = 0;
    while ((index < this -> top) && (index < other->top)) {
        char thisc, otherc;
        thisc = this->characters[index];
        otherc = other->characters[index];
        if (thisc < otherc) {
            return -1;
        } else if (thisc > otherc) {
            return 1;
        }
        index++;
    }

    if (this->top < other->top) {
        return -1;
    } else if (this->top > other->top) {
        return 1;
    }

    return 0;
}

unsigned char equals(TextString* this, TextString* right) {
    return compareTo(this, right) == 0;
}

unsigned char binarySearch(DataSet* this, TextString* target, short from, short to) {
    if (from == to - 1) {
        return equals(this->elements[from], target);
    }

    short middle = (to - from) / 2 + from;

    if (equals(this -> elements[from], target)) {
        return 1;
    }

    if (compareTo(this->elements[middle], target) > 0) {
        // search left
        return binarySearch(this, target, from, middle);
    } else {
        // search right
        return binarySearch(this, target, middle, to);
    }
}

unsigned char contains(DataSet* this, TextString* target) {
    if (this -> top > 0) {
        return binarySearch(this, target, 0, this -> top);
    } else {
        return 0;
    }
}

void sort(DataSet* this) {
    short index = 0;

    while (index < this -> top - 1) {
        if (compareTo(this -> elements[index], this -> elements[index + 1]) <= 0) {
            index++;
        } else {
            TextString* temp = this -> elements[index];
            this -> elements[index] = this -> elements[index + 1];
            this -> elements[index + 1] = temp;
            if (index > 0) {
                index--;
            }
        }
    }
}

void dsadd(DataSet* this, TextString* nextElement) {
    if (this -> top < this -> length) {
        this -> elements[this -> top++] = nextElement;
        sort(this);
    } else {
        TextString** newArray = malloc(sizeof(TextString*) * this -> length * 2);
        short i;

        for (i = 0; i < this -> length; i++) {
            newArray[i] = this->elements[i];
        }
        this -> elements = newArray;
        this -> length = this -> length * 2;
        dsadd(this, nextElement);
    }
}

void print(DataSet* this) {
    int c;

    for (c = 0; c < this -> top; c++) {
        TextString* current = this->elements[c];
        printf("%s\n", toString(current));
    }
}
#if defined(REPORTINSTRUCTIONS)
extern void papi_mark(void);
extern void papi_start(void);
#endif

unsigned char test(DataSet* ds) {
#if defined(REPORTINSTRUCTIONS)
    papi_start();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
#endif
    WordGenerator* wordGen = create_wordGenerator(17);

    TextString* nextWord = next(wordGen);

    while (nextWord != 0) {
        //printf("next = %s\n", toString(nextWord));
        if (!contains(ds, nextWord)) {
            dsadd(ds, nextWord);
        }
        nextWord = next(wordGen);
    }
    // print(ds);
#if defined(REPORTINSTRUCTIONS)
    papi_mark();
    printf("words: %d\n", ds -> top);
#endif
    

    return ds -> top != 17;
}

int main(int argc, char** args) {
    int i;
	unsigned char* res;
	for (i = 0; i < 3; i++)
	{
       DataSet* ds = create_DataSet();
       res = test(ds);
     }
	 return  0;
}

