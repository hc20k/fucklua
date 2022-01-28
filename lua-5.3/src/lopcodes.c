/*
** $Id: lopcodes.c,v 1.55.1.1 2017/04/19 17:20:42 roberto Exp $
** Opcodes for Lua virtual machine
** See Copyright Notice in lua.h
*/

#define lopcodes_c
#define LUA_CORE

#include "lprefix.h"


#include <stddef.h>

#include "lopcodes.h"


/* ORDER OP */

LUAI_DDEF const char *const luaP_opnames[NUM_OPCODES+1] = {
"ADD",
"SUB",
"MUL",
"MOD",
"POW",
"DIV",
"IDIV",
"BAND",
"BOR",
"BXOR",
"SHL",
"SHR",

"UNM",
"BNOT",
"NOT",
"LEN",

"CONCAT",
"JMP",
"EQ",
"LT",
"LE",

"MOVE",
"LOADK",
"EXTRAARG",
"LOADBOOL",

"RETURN",
"GETUPVAL",
"SETLIST",
"LOADKX",
"SELF",
"FORLOOP",
"SETTABLE",
"TAILCALL",
"TFORLOOP",
"SETTABUP",
"TESTSET",
"GETTABUP",
"VARARG",
"LOADNIL",
"TFORCALL",
"SETUPVAL",
"GETTABLE",
"FORPREP",
"NEWTABLE",
"CALL",
"CLOSURE",
"TEST",
  NULL
};


#define opmode(t,a,b,c,m) (((t)<<7) | ((a)<<6) | ((b)<<4) | ((c)<<2) | (m))

LUAI_DDEF const lu_byte luaP_opmodes[NUM_OPCODES] = {
/*       T  A    B       C     mode		   opcode	*/
  opmode(0, 1, OpArgK, OpArgK, iABC)		/* ADD */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* SUB */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* MUL */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* MOD */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* POW */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* DIV */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* IDIV */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* BAND */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* BOR */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* BXOR */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* SHL */
 ,opmode(0, 1, OpArgK, OpArgK, iABC)		/* SHR */

 ,opmode(0, 1, OpArgR, OpArgN, iABC)		/* UNM */
 ,opmode(0, 1, OpArgR, OpArgN, iABC)		/* BNOT */
 ,opmode(0, 1, OpArgR, OpArgN, iABC)		/* NOT */
 ,opmode(0, 1, OpArgR, OpArgN, iABC)		/* LEN */

 ,opmode(0, 1, OpArgR, OpArgR, iABC)		/* CONCAT */
 ,opmode(0, 0, OpArgR, OpArgN, iAsBx)		/* JMP */
 ,opmode(1, 0, OpArgK, OpArgK, iABC)		/* EQ */
 ,opmode(1, 0, OpArgK, OpArgK, iABC)		/* LT */
 ,opmode(1, 0, OpArgK, OpArgK, iABC)		/* LE */

 ,opmode(0, 1, OpArgR, OpArgN, iABC)		/* MOVE */
 ,opmode(0, 1, OpArgK, OpArgN, iABx)		/* LOADK */
 ,opmode(0, 0, OpArgU, OpArgU, iAx)		    /* EXTRAARG */
 ,opmode(0, 1, OpArgU, OpArgU, iABC)		/* LOADBOOL */

 ,opmode(0, 0, OpArgU, OpArgN, iABC)		/* RETURN */
 ,opmode(0, 1, OpArgU, OpArgN, iABC)		/* GETUPVAL */
 ,opmode(0, 0, OpArgU, OpArgU, iABC)		/* SETLIST */
 ,opmode(0, 1, OpArgN, OpArgN, iABx)		/* LOADKX */
 ,opmode(0, 1, OpArgR, OpArgK, iABC)		/* SELF */
 ,opmode(0, 1, OpArgR, OpArgN, iAsBx)		/* FORLOOP */
 ,opmode(0, 0, OpArgK, OpArgK, iABC)		/* SETTABLE */
 ,opmode(0, 1, OpArgU, OpArgU, iABC)		/* TAILCALL */
 ,opmode(0, 1, OpArgR, OpArgN, iAsBx)		/* TFORLOOP */
 ,opmode(0, 0, OpArgK, OpArgK, iABC)		/* SETTABUP */
 ,opmode(1, 1, OpArgR, OpArgU, iABC)		/* TESTSET */
 ,opmode(0, 1, OpArgU, OpArgK, iABC)		/* GETTABUP */
 ,opmode(0, 1, OpArgU, OpArgN, iABC)		/* VARARG */
 ,opmode(0, 1, OpArgU, OpArgN, iABC)		/* LOADNIL */
 ,opmode(0, 0, OpArgN, OpArgU, iABC)		/* TFORCALL */
 ,opmode(0, 0, OpArgU, OpArgN, iABC)		/* SETUPVAL */
 ,opmode(0, 1, OpArgR, OpArgK, iABC)		/* GETTABLE */
 ,opmode(0, 1, OpArgR, OpArgN, iAsBx)		/* FORPREP */
 ,opmode(0, 1, OpArgU, OpArgU, iABC)		/* NEWTABLE */
 ,opmode(0, 1, OpArgU, OpArgU, iABC)		/* CALL */
 ,opmode(0, 1, OpArgU, OpArgN, iABx)		/* CLOSURE */
 ,opmode(1, 0, OpArgN, OpArgU, iABC)		/* TEST */

};

