
// WARNING: Could not reconcile some variable overlaps
// WARNING: [r2ghidra] Failed to match type signed int64_t for variable var_88h to Decompiler type: Unknown type
// identifier signed
// WARNING: [r2ghidra] Failed to match type signed int64_t for variable var_84h to Decompiler type: Unknown type
// identifier signed
// WARNING: [r2ghidra] Failed to match type signed int64_t for variable var_80h to Decompiler type: Unknown type
// identifier signed
// WARNING: [r2ghidra] Failed to match type signed int64_t for variable var_7ch to Decompiler type: Unknown type
// identifier signed
// WARNING: [r2ghidra] Failed to match type signed int64_t for variable var_78h to Decompiler type: Unknown type
// identifier signed
// WARNING: [r2ghidra] Failed to match type signed int64_t for variable var_74h to Decompiler type: Unknown type
// identifier signed
// WARNING: [r2ghidra] Failed to match type long for variable var_44h to Decompiler type: Unknown type identifier long
// WARNING: [r2ghidra] Failed to match type long for variable var_4ch to Decompiler type: Unknown type identifier long
// WARNING: [r2ghidra] Failed to match type long for variable var_54h to Decompiler type: Unknown type identifier long
// WARNING: [r2ghidra] Failed to match type long for variable var_5ch to Decompiler type: Unknown type identifier long
// WARNING: [r2ghidra] Failed to match type long for variable var_64h to Decompiler type: Unknown type identifier long
// WARNING: [r2ghidra] Failed to match type long for variable c to Decompiler type: Unknown type identifier long
// WARNING: [r2ghidra] Detected overlap for variable var_88h
// WARNING: [r2ghidra] Detected overlap for variable var_84h
// WARNING: [r2ghidra] Detected overlap for variable var_80h
// WARNING: [r2ghidra] Detected overlap for variable var_7ch
// WARNING: [r2ghidra] Detected overlap for variable var_78h
// WARNING: [r2ghidra] Detected overlap for variable var_74h
// WARNING: [r2ghidra] Detected overlap for variable var_44h
// WARNING: [r2ghidra] Detected overlap for variable var_4ch
// WARNING: [r2ghidra] Detected overlap for variable var_54h
// WARNING: [r2ghidra] Detected overlap for variable var_5ch
// WARNING: [r2ghidra] Detected overlap for variable var_64h
// WARNING: [r2ghidra] Detected overlap for variable c

undefined8 Child()(void)
{
    int32_t iVar1;
    undefined8 uVar2;
    int64_t in_FS_OFFSET;
    int64_t var_8ch;
    int32_t var_84h;
    int32_t var_80h;
    int32_t var_7ch;
    int32_t var_78h;
    int32_t var_74h;
    uint32_t var_70h;
    undefined4 c;
    uint32_t var_68h;
    undefined4 var_64h;
    uint32_t var_60h;
    undefined4 var_5ch;
    uint32_t var_58h;
    undefined4 var_54h;
    uint32_t var_50h;
    undefined4 var_4ch;
    uint32_t var_48h;
    undefined4 var_44h;
    char *str;
    char *s;
    int64_t canary;

    // Child()
    canary = *(int64_t *)(in_FS_OFFSET + 0x28);
    puts("~~~WeLcOmE t0\' b0t-Os 2.4.1~~~");
    printf("\\<¦PlE4se EntEr t_he FIrsT AUtheNtIcaT1on: ");
    iVar1 = scanf(0x4014dd, &var_8ch);
    if (iVar1 == 1) {
        var_70h = Dispatch((undefined4)var_8ch, 0x499602d2);
        if (var_70h != 0x4babd7ac) {
            printf("AcCess DEnIED...");
            exit(0x539);
            goto code_r0x00400d66;
        }
        sprintf(&s, 0x4014e0, (undefined4)var_8ch);
        var_8ch._4_4_ = 0;
        while (var_8ch._4_4_ < 8) {
            sprintf(&str, "%c%c", (int32_t)*(char *)((int64_t)&s + (int64_t)var_8ch._4_4_),
                    (int32_t)*(char *)((int64_t)&s + (int64_t)(var_8ch._4_4_ + 1)));
            c = strtol(&str, 0, 10);
            putchar(c);
            fflush(_reloc.stdout);
            var_8ch._4_4_ = var_8ch._4_4_ + 2;
        }
    } else {
code_r0x00400d66:
        printf("**Erro*r ge*tti*ng va*lue-----PrOv1dE Int3333333g3R Ple4SE");
        exit(0x539);
    }
    printf("\n.::.::.PlE4se EntEr AUtheNtIcaT1on PaRT 2: ");
    iVar1 = scanf(0x4014dd, &var_8ch);
    if (iVar1 == 1) {
        var_68h = Dispatch((undefined4)var_8ch, 0x499602d2);
        if (var_68h != 0x4c49c202) {
            printf("AcCess DEnIED...");
            exit(0x539);
            goto code_r0x00400e9a;
        }
        sprintf(&s, 0x4014e0, (undefined4)var_8ch);
        var_84h = 0;
        while (var_84h < 8) {
            sprintf(&str, "%c%c", (int32_t)*(char *)((int64_t)&s + (int64_t)var_84h),
                    (int32_t)*(char *)((int64_t)&s + (int64_t)(var_84h + 1)));
            var_64h = strtol(&str, 0, 10);
            putchar(var_64h);
            fflush(_reloc.stdout);
            var_84h = var_84h + 2;
        }
    } else {
code_r0x00400e9a:
        printf("**Erro*r ge*tti*ng va*lue-----PrOv1dE Int3333333g3R Ple4SE");
        exit(0x539);
    }
    printf("\nPlE4se EntEr AUtheNtIcaT1on PaRT 3: ");
    iVar1 = scanf(0x4014dd, &var_8ch);
    if (iVar1 == 1) {
        var_60h = Dispatch((undefined4)var_8ch, 0x499602d2);
        if (var_60h != 0x4abfde42) {
            printf("AcCess DEnIED...");
            exit(0x539);
            goto code_r0x00400fbc;
        }
        sprintf(&s, 0x4014e0, (undefined4)var_8ch);
        var_80h = 0;
        while (var_80h < 8) {
            sprintf(&str, "%c%c", (int32_t)*(char *)((int64_t)&s + (int64_t)var_80h),
                    (int32_t)*(char *)((int64_t)&s + (int64_t)(var_80h + 1)));
            var_5ch = strtol(&str, 0, 10);
            putchar(var_5ch);
            fflush(_reloc.stdout);
            var_80h = var_80h + 2;
        }
    } else {
code_r0x00400fbc:
        printf("**Erro*r ge*tti*ng va*lue-----PrOv1dE Int3333333g3R Ple4SE");
        exit(0x539);
    }
    printf("\n^3----ç*PlE4se EntEr AUtheNtIcaT1on PaRT 4: ");
    iVar1 = scanf(0x4014dd, &var_8ch);
    if (iVar1 == 1) {
        var_58h = Dispatch((undefined4)var_8ch, 0x499602d2);
        if (var_58h != 0x4b0333c6) {
            printf("AcCess DEnIED...");
            exit(0x539);
            goto code_r0x004010de;
        }
        sprintf(&s, 0x4014e0, (undefined4)var_8ch);
        var_7ch = 0;
        while (var_7ch < 8) {
            sprintf(&str, "%c%c", (int32_t)*(char *)((int64_t)&s + (int64_t)var_7ch),
                    (int32_t)*(char *)((int64_t)&s + (int64_t)(var_7ch + 1)));
            var_54h = strtol(&str, 0, 10);
            putchar(var_54h);
            fflush(_reloc.stdout);
            var_7ch = var_7ch + 2;
        }
    } else {
code_r0x004010de:
        printf("**Erro*r ge*tti*ng va*lue-----PrOv1dE Int3333333g3R Ple4SE");
        exit(0x539);
    }
    printf("\n---------PlE4se EntEr AUtheNtIcaT1on PaRT 5: ");
    iVar1 = scanf(0x4014dd, &var_8ch);
    if (iVar1 == 1) {
        var_50h = Dispatch((undefined4)var_8ch, 0x499602d2);
        if (var_50h != 0x4b113b74) {
            printf("AcCess DEnIED...");
            exit(0x539);
            goto code_r0x00401200;
        }
        sprintf(&s, 0x4014e0, (undefined4)var_8ch);
        var_78h = 0;
        while (var_78h < 8) {
            sprintf(&str, "%c%c", (int32_t)*(char *)((int64_t)&s + (int64_t)var_78h),
                    (int32_t)*(char *)((int64_t)&s + (int64_t)(var_78h + 1)));
            var_4ch = strtol(&str, 0, 10);
            putchar(var_4ch);
            fflush(_reloc.stdout);
            var_78h = var_78h + 2;
        }
    } else {
code_r0x00401200:
        printf("**Erro*r ge*tti*ng va*lue-----PrOv1dE Int3333333g3R Ple4SE");
        exit(0x539);
    }
    printf("\n*~+*^*PlE4se EntEr F1NAl AUtheNtIcaT1on:");
    iVar1 = scanf(0x4014dd, &var_8ch);
    if (iVar1 == 1) {
        var_48h = Dispatch((undefined4)var_8ch, 0x499602d2);
        if (var_48h == 0x4ab20669) {
            sprintf(&s, 0x4014e0, (undefined4)var_8ch);
            var_74h = 0;
            while (var_74h < 8) {
                sprintf(&str, "%c%c", (int32_t)*(char *)((int64_t)&s + (int64_t)var_74h),
                        (int32_t)*(char *)((int64_t)&s + (int64_t)(var_74h + 1)));
                var_44h = strtol(&str, 0, 10);
                putchar(var_44h);
                fflush(_reloc.stdout);
                var_74h = var_74h + 2;
            }
            goto code_r0x0040133b;
        }
        printf("AcCess DEnIED...");
        exit(0x539);
    }
    printf("**Erro*r ge*tti*ng va*lue-----PrOv1dE Int3333333g3R Ple4SE");
    exit(0x539);
code_r0x0040133b:
    puts("\nUnLocKed B0tS cOntroL SySTem!");
    puts("[*] D1sAble AutonOmous DeFen5e Bot5 and opEN d0oRS");
    printf("\nUnLock1ng DooRs...iN PROgrEs");
    sleep(5);
    printf("\nUNLocKing doOrs...SucCessFUL!");
    sleep(2);
    printf("\nDiSarm1ng BoTS...in ProgreS");
    sleep(8);
    printf("\ndisArming bOts...succEssFul!");
    sleep(3);
    printf("\nYoU aRe fREE to LeAve HuMan");
    uVar2 = 0;
    if (canary != *(int64_t *)(in_FS_OFFSET + 0x28)) {
        uVar2 = __stack_chk_fail();
    }
    return uVar2;
}
