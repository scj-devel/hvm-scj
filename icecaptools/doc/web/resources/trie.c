typedef struct _trie Trie;

struct _trie {
    char c;
    Trie* right;
    Trie* down;
    char wordEndingHere;
};

typedef struct _textString {
    unsigned char top;
    unsigned char length;
    char* characters;
} TextString;

#include <stdlib.h>
#include <string.h>
#include <stdio.h>

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

char charAt(TextString* str, char index) {
    return str -> characters[index];
}

static Trie* create_trie() {
    Trie* node = (Trie*) malloc(sizeof(struct _trie));
    node -> c = 0;
    node -> right = 0;
    node -> down = 0;
    node -> wordEndingHere = 0;
    return node;
}

static void _insert(Trie* this, TextString* str, unsigned short i) {
    char next = charAt(str, i);

    if (this -> c == 0) {
        this -> c = next;
    } else {
        if (this -> c != next) {
            if (this -> right == 0) {
                this -> right = create_trie();
            }
            _insert(this -> right, str, i);
            return;
        }
    }

    if (i + 1 == str -> top) {
        this -> wordEndingHere = 1;
    } else {
        if (this -> down == 0) {
            this -> down = create_trie();
        }
        _insert(this -> down, str, i + 1);
    }
}

static void insert(Trie* this, TextString* str) {
    if (str -> top > 0) {
        _insert(this, str, 0);
    }
}

static unsigned char _member(Trie* this, TextString* str, unsigned short i) {
    if (charAt(str, i) == this -> c) {
        if (i + 1 == str -> top) {
            return this -> wordEndingHere;
        } else {
            if (this -> down != 0) {
                return _member(this -> down, str, i + 1);
            }
        }
    } else {
        if (this -> right != 0) {
            return _member(this -> right, str, i);
        }
    }
    return 0;
}

static unsigned char member(Trie* this, TextString* str) {
    if (str -> top > 0) {
        return _member(this, str, 0);
    } else {
        return 0;
    }
}

#if defined(REPORTINSTRUCTIONS)
extern void papi_mark(void);
extern void papi_start(void);
#endif

static unsigned char test(Trie* root)
{
#if defined(REPORTINSTRUCTIONS)
    papi_start();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
    papi_mark();
#endif

    TextString* Abba = create_TextString(4);
    add(Abba, 'A');
    add(Abba, 'b');
    add(Abba, 'b');
    add(Abba, 'a');

    TextString* Abbx = create_TextString(4);
    add(Abbx, 'A');
    add(Abbx, 'b');
    add(Abbx, 'b');
    add(Abbx, 'x');

    TextString* Abb = create_TextString(3);
    add(Abb, 'A');
    add(Abb, 'b');
    add(Abb, 'b');

    TextString* Abbab = create_TextString(5);
    add(Abbab, 'A');
    add(Abbab, 'b');
    add(Abbab, 'b');
    add(Abbab, 'a');
    add(Abbab, 'b');

    TextString* Horse = create_TextString(5);
    add(Horse, 'H');
    add(Horse, 'o');
    add(Horse, 'r');
    add(Horse, 's');
    add(Horse, 'e');

    TextString* Ab = create_TextString(2);
    add(Ab, 'A');
    add(Ab, 'b');

    TextString* Abx = create_TextString(3);
    add(Abx, 'A');
    add(Abx, 'b');
    add(Abx, 'x');

    TextString* hello = create_TextString(5);
    add(hello, 'h');
    add(hello, 'e');
    add(hello, 'l');
    add(hello, 'l');
    add(hello, 'o');

    TextString* Abbabb = create_TextString(6);
    add(Abbabb, 'A');
    add(Abbabb, 'b');
    add(Abbabb, 'b');
    add(Abbabb, 'a');
    add(Abbabb, 'b');
    add(Abbabb, 'b');

    insert(root, Abba);

    insert(root, Abbx);

    insert(root, Abb);

    insert(root, Abbab);

    insert(root, Horse);

    if (member(root, Abba)) {
        if (member(root, Abbx)) {
            if (!member(root, Ab)) {
                if (!member(root, Abx)) {
                    if (!member(root, hello)) {
                        if (member(root, Abb)) {
                            if (member(root, Abbab)) {
                                if (!member(root, Abbabb)) {
                                    if (member(root, Horse)) {
#if defined(REPORTINSTRUCTIONS)
                                        papi_mark();
#endif
                                        return 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return 1;
}

int main(int argv, char** args) {
    int i;
	Trie* root;
    unsigned char* res;

	for (i = 0; i < 2; i++)
	{
	   root = create_trie();
       
	   res = test(root);

	}
    return 0;
}
