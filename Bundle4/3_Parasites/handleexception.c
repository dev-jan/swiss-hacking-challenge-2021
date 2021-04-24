
// WARNING: Could not reconcile some variable overlaps
// WARNING: [r2ghidra] Failed to match type pid_t for variable arg1 to Decompiler type: Unknown type identifier pid_t
// WARNING: [r2ghidra] Failed to match type pid_t for variable pid to Decompiler type: Unknown type identifier pid_t
// WARNING: [r2ghidra] Failed to match type __ptrace_request for variable request to Decompiler type: Unknown type
// identifier __ptrace_request
// WARNING: [r2ghidra] Failed to match type long for variable var_3b0h to Decompiler type: Unknown type identifier long
// WARNING: [r2ghidra] Detected overlap for variable var_3b0h
// WARNING: [r2ghidra] Detected overlap for variable var_fh

void HandleException(int)(undefined8 arg1)
{
    undefined2 uVar1;
    int64_t iVar2;
    undefined8 *puVar3;
    int64_t in_FS_OFFSET;
    undefined8 pid;
    int64_t var_3a8h;
    undefined8 request;
    int64_t var_350h;
    int64_t var_330h;
    void *addr;
    int64_t var_10h;
    int64_t canary;

    // HandleException(int)
    canary = *(int64_t *)(in_FS_OFFSET + 0x28);
    iVar2 = 0x72;
    puVar3 = &request;
    while (iVar2 != 0) {
        iVar2 = iVar2 + -1;
        *puVar3 = 0;
        puVar3 = puVar3 + 1;
    }
    ptrace(0xc, (undefined4)arg1, 0, &request);
    uVar1 = ptrace(2, (undefined4)arg1, addr, 0);
    var_10h._0_1_ = (char)uVar1;
    var_10h._1_1_ = (char)((uint16_t)uVar1 >> 8);
    if (((char)var_10h == '\x0f') && (var_10h._1_1_ == '\v')) {
        var_350h = (int64_t)(int32_t)((uint32_t)var_330h ^ 0x4fb30a91);
        addr = (void *)((int64_t)addr + 2);
        ptrace(0xd, (undefined4)arg1, 0, &request);
    }
    if (canary != *(int64_t *)(in_FS_OFFSET + 0x28)) {
        __stack_chk_fail();
    }
    return;
}
