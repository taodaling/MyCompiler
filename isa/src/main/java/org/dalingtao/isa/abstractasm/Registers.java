package org.dalingtao.isa.abstractasm;

import org.dalingtao.ArrayUtils;

import java.util.List;

public class Registers {
    //return val
    public static final String rax = "rax";
    public static final String rbx = "rbx";
    public static final String rcx = "rcx";
    public static final String rdx = "rdx";
    public static final String rsi = "rsi";
    public static final String rdi = "rdi";
    public static final String rbp = "rbp";
    //stack point
    public static final String rsp = "rsp";
    public static final String r8 = "r8";
    public static final String r9 = "r9";
    public static final String r10 = "r10";
    public static final String r11 = "r11";
    public static final String r12 = "r12";
    public static final String r13 = "r13";
    public static final String r14 = "r14";
    public static final String r15 = "r15";

    public static final String[] volatileRegisters =
            new String[]{rax, rcx, rdx, r8, r9, r10, r11};

    public static final String[] nonVolatileRegisters =
            new String[]{rbx, rbp, rdi, rsi, r12, r13, r14, r15};

    public static final String[] allUsableRegisters = new String[]{rax, rcx, rdx, r8, r9, r10, r11, rbx, rbp, rdi, rsi, r12, r13, r14, r15};

    //The first four integer or pointer parameters are passed in the rcx, rdx, r8, and r9 registers.
    public static final String[] arguments = new String[]{rcx, rdx, r8, r9};
}
