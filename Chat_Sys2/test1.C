#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int is_root(char* pass) {
	int root = 0;
	char s[8];
	strcpy(s, pass);
	if (strcmp(s, "$#@!") == 0) {
		root = 1;
	}
	printf("root=%d\n", root);
	return root;
}

int main(int argc, char** argv) {
	if (argc == 2) {
		if (is_root(argv[1]) == 1) {
			printf("ok\0");
		}
	}
	return 0;
}