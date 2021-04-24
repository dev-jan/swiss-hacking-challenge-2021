
// WARNING: Could not reconcile some variable overlaps
// WARNING: [r2ghidra] Failed to match type pid_t for variable pid to Decompiler type: Unknown type identifier pid_t
// WARNING: [r2ghidra] Failed to match type pid_t for variable var_14h to Decompiler type: Unknown type identifier pid_t
// WARNING: [r2ghidra] Detected overlap for variable wstatus
// WARNING: [r2ghidra] Detected overlap for variable var_14h
// WARNING: [r2ghidra] Detected overlap for variable var_10h
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
// WARNING: [r2ghidra] Failed to match type pid_t for variable arg1 to Decompiler type: Unknown type identifier pid_t
// WARNING: [r2ghidra] Failed to match type __ptrace_request for variable request to Decompiler type: Unknown type
// identifier __ptrace_request
// WARNING: [r2ghidra] Failed to match type long for variable var_3b0h to Decompiler type: Unknown type identifier long
// WARNING: [r2ghidra] Detected overlap for variable var_3b0h
// WARNING: [r2ghidra] Detected overlap for variable var_fh

undefined8 Parent()(void)
{
    undefined8 uVar1;
    int64_t in_FS_OFFSET;
    uint32_t var_1dh;
    undefined8 pid;
    uint32_t var_10h;
    uint32_t var_ch;
    int64_t canary;

    // Parent()
    canary = *(int64_t *)(in_FS_OFFSET + 0x28);
    pid._0_4_ = fork();
    if ((uint32_t)pid == 0) {
        ptrace(0, 0, 0, 0);
        uVar1 = Child()();
    } else {
        var_1dh._0_1_ = '\0';
        while ((char)var_1dh == '\0') {
            stack0xffffffffffffffdc = 0;
            pid._4_4_ = waitpid((uint32_t)pid, (int64_t)&var_1dh + 1, 0, (int64_t)&var_1dh + 1);
            if ((stack0xffffffffffffffdc & 0x7f) == 0) {
                var_1dh._0_1_ = '\x01';
            } else {
                if ((char)(((uint8_t)stack0xffffffffffffffdc & 0x7f) + 1) >> 1 < '\x01') {
                    if (((stack0xffffffffffffffdc & 0xff) == 0x7f) &&
                       (var_ch = (int32_t)(stack0xffffffffffffffdc & 0xff00) >> 8, var_ch == 4)) {
                        HandleException(int)((uint64_t)(uint32_t)pid);
                    }
                } else {
                    var_10h = stack0xffffffffffffffdc & 0x7f;
                    var_1dh._0_1_ = '\x01';
                }
            }
            ptrace(7, (uint32_t)pid, 0, 0);
        }
        uVar1 = 0;
    }
    if (canary != *(int64_t *)(in_FS_OFFSET + 0x28)) {
        uVar1 = __stack_chk_fail();
    }
    return uVar1;
}
