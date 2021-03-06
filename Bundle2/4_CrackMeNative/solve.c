#include <stdio.h>

int main() {
    int result[50] = {};
    int chunk1[] = {128, 227, 218, 199, 46,241,162,145,107,220,107,181,229,175,63,185,238,91,38,146,102,197,203,222,129,121,218};
    int chunk2[] = {97,108,0x66,0x2E,0x65,0x68,0x74,0x2E,0x54,0x4F,0x4E,0x2E,0x73,0x69,0x2E,0x73,0x69,0x68,0x74,0x2E,0x79,0x72,0x72,0x6F,0x73,0x7B,0x4C,0x48,0xC4,0xED,0x54,0x0A,0x2D,0x35,0xD4,0x8F,0x29,0x95,0x42,0xD6,0xE0,0x57,0x64,0x9F,0xFB};
    int chunk3[] = {208,69,40,118,111,243,90,244,199,206,251,195,127,72,206,60,58,11,241,83,177,75,185,94,162,101,119,165,129,149,0xca,0x31,0x18,0x88,0xee,0xdd,0x38};
    int javaKey[] = {121, 134, 239, 213, 16, 28, 184, 101, 150, 60, 170, 49, 159, 189, 241, 146, 141, 22, 205, 223, 218, 210, 99, 219, 34, 84, 156, 237, 26, 94, 178, 230, 27, 180, 72, 32, 102, 192, 178, 234, 228, 38, 37, 142, 242, 142, 133, 159, 142, 33};
    int counter = -0x6c; // -108
    int i = 0;

    do {
        result[i] = chunk1[i] ^  chunk3[i] ^ chunk2[i] ^ javaKey[i];
        counter = counter + 4;
        i++;
    } while (counter != 0);

    printf("Result: ");
    for(int i = 0;i<50-1;i++) {
        printf("%d, ", result[i]);
    }
    printf("\nFlag: ");
    for(int i = 0;i<50-1;i++) {
        printf("%c", result[i]);
    }


    return 0;
}
