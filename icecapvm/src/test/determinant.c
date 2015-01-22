#include <stdlib.h>

#include <stdio.h>

typedef struct _vector {
    short* vector;
    unsigned short length;
} Vector;

typedef struct _matrix {
    Vector** matrix;
    unsigned short dimension;
} Matrix;

static Vector* create_vector(unsigned short length) {
    Vector* vector = malloc(sizeof(struct _vector));
    vector -> vector = malloc(sizeof(short) * length);
    vector -> length = length;
    return vector;
}

static Matrix* create_matrix(unsigned short dimension) {
    unsigned char i;
    Matrix* matrix = (Matrix*) malloc(sizeof(struct _matrix));

    matrix -> matrix = (Vector**) malloc(sizeof(Vector*) * dimension);
    matrix -> dimension = dimension;

    for (i = 0; i < dimension; i++) {
        matrix -> matrix[i] = create_vector(dimension);
    }
    return matrix;
}

static void setPosition_v(Vector* this, short index, short value) {
    this -> vector[index] = value;
}

static void setPosition(Matrix* this, short row, short column, short value) {
    setPosition_v(this->matrix[row], column, value);
}

static unsigned short getDimension_v(Vector* b) {
    return b->length;
}

static unsigned short getDimension(Matrix* A) {
    return A->dimension;
}

static short getPosition_v(Vector* this, short index) {
    return this->vector[index];
}

static short getPosition(Matrix* this, short row, short column) {
    return getPosition_v(this->matrix[row], column);
}

static Matrix* copy(Matrix* A) {
    short i, j;
    Matrix* m = create_matrix(A->dimension);
    for (i = 0; i < getDimension(A); i++) {
        for (j = 0; j < getDimension(A); j++) {
            setPosition(m, i, j, getPosition(A, i, j));
        }
    }
    return m;
}

static void substituteColumn(Matrix* this, short col, Vector* b) {
    short i;
    for (i = 0; i < this -> dimension; i++) {
        setPosition(this, i, col, getPosition_v(b, i));
    }
}

static Matrix* createSubM(short i, Matrix* m) {
    Matrix* subM = create_matrix(getDimension(m) - 1);
    short row;
    short col;

    for (row = 1; row < getDimension(m); row++) {
        short index = 0;
        for (col = 0; col < getDimension(m); col++) {
            if (col == i) {
                ;
            } else {
                setPosition(subM, row - 1, index, getPosition(m, row, col));
                index++;
            }
        }
    }
    return subM;
}

static short determinant(Matrix* m) {
    if (getDimension(m) == 2) {
        return getPosition(m, 0, 0) * getPosition(m, 1, 1) - getPosition(m, 1, 0) * getPosition(m, 0, 1);
    } else {
        short sum = 0;
        short i;
        for (i = 0; i < getDimension(m); i++) {
            signed char sign;
            Matrix* subM = createSubM(i, m);
            if ((i & 1) == 1) {
                sign = -1;
            } else {
                sign = 1;
            }
            sum += getPosition(m, 0, i) * sign * determinant(subM);
        }
        return sum;
    }
}

static short* solutionsCramer(Matrix* A, Vector* b) {
    short i;
    short* result = malloc(sizeof(short) * getDimension_v(b));

    for (i = 0; i < getDimension(A); i++) {
        short num;
        Matrix* m = copy(A);
        substituteColumn(m, i, b);
        num = determinant(m) / determinant(A);
        result[i] = num;
    }
    return result;
}

#if defined(REPORTINSTRUCTIONS)
extern void papi_mark(void);
extern void papi_start(void);
#endif

unsigned char test(Matrix* A) {
    short *solutions;
    Vector* b;

    setPosition(A, 0, 0, 2);
    setPosition(A, 0, 1, 1);
    setPosition(A, 0, 2, -1);

    setPosition(A, 1, 0, -3);
    setPosition(A, 1, 1, -1);
    setPosition(A, 1, 2, 2);

    setPosition(A, 2, 0, -2);
    setPosition(A, 2, 1, 1);
    setPosition(A, 2, 2, 2);

    b = create_vector(3);
    setPosition_v(b, 0, 8);
    setPosition_v(b, 1, -11);
    setPosition_v(b, 2, -3);

    solutions = solutionsCramer(A, b);
    if (2 == solutions[0]) {
        if (3 == solutions[1]) {
            if (-1 == solutions[2]) {
                return 0;
            }
        }
    }
    return 1;
}

int main(int argv, char** args) {
    int i;

    unsigned char result;
#if defined(REPORTINSTRUCTIONS)
    papi_start();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
#endif
    for (i = 0; i < 4; i++)
    {
        Matrix* A = create_matrix(3);
        result = test(A);
        if (result == 0)
        {
#if defined(REPORTINSTRUCTIONS)
            papi_mark();
#endif
        }
        else
        {
            for (;;);
        }
    }
    return result;
}
