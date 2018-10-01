#include "../ostypes.h"
#include "io.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>

static FILE *fd;
extern int byteCount;

void openOutput() {
	fd = fopen(OUTPUTFILE, "wb");
	if (!fd) {
		char cwd[1024];
		perror("failed to open file for writing");
		if (getcwd(cwd, sizeof(cwd)) != NULL) {
			printf("Current working dir: %s\n", cwd);
		} else {
			perror("getcwd() error");
		}
		exit(1);
	}
}

void openInput() {
	fd = fopen(OUTPUTFILE, "rb");
	if (!fd) {
		char cwd[1024];
		perror("failed to open file for reading");
		if (getcwd(cwd, sizeof(cwd)) != NULL) {
			printf("Current working dir: %s\n", cwd);
		} else {
			perror("getcwd() error");
		}
		exit(1);
	}
}

void closeIO(void) {
	if (fclose(fd)) {
		perror("failed to close file");
		exit(1);
	}
}

void dumpByte(unsigned char b) {
	unsigned char buf[1];
	buf[0] = b;

	if (fwrite(buf, 1, 1, fd) != 1) {
		perror("failed to write to file");
		exit(1);
	}

	byteCount++;
}

unsigned char readByte() {
	unsigned char buf[1];

	while (1) {
		int res = fread(buf, 1, 1, fd);
		if (res == -1) {
			perror("failed to read from file");
			exit(1);
		} else {
			byteCount++;
			return buf[0];
		}
	}
	return -1;
}

void* _malloc_(int size) {
	return malloc(size);
}

