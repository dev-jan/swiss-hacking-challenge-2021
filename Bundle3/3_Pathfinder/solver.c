#include <stdio.h>

int main()
{
    char encodedPassword[] = "CYHSZZBU";
    int i = 0;
    while (i < 8) {
        char c;
        for (c = 'A'; c <= 'Z'; ++c) {
            int tmp = c + -0x41 + (i + 8) * 0x1f;
            char result = (char)tmp + (char)(tmp / 0x1a) * -0x1a + 'A';
            printf("%d ", result);
            if (result == encodedPassword[i]) {
                printf("found: %c", c);
                encodedPassword[i] = c;
                break;
            }
        }
        printf("\n");
        i = i+1;
    }

    printf("Solution: %s\n", encodedPassword);
    return 0;
}
