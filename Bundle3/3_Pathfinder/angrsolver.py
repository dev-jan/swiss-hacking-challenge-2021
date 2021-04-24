#!/usr/bin/env python

import angr
import claripy
import sys

base_addr = 0x08048000
find_addr = 0x08049894
avoid_addr = 0x0804987f

project = angr.Project("./pathfinder", main_opts = {"base_addr": base_addr})
state = project.factory.entry_state()
simgr = project.factory.simulation_manager(state)
simgr.explore(find=find_addr, avoid=avoid_addr)

if len(simgr.found) > 0:
    for found in simgr.found:
        print(found.posix.dumps(sys.stdin.fileno()))
else:
    print('not found :(')