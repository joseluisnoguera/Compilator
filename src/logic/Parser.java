//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






<<<<<<< HEAD
<<<<<<< HEAD
//#line 2 "gramatica (12).y"
=======
//#line 2 "gramatica.y"
>>>>>>> 1ce0652... comentario
	package logic;
	
	import java.util.Hashtable;
	import logic.Lexicon;
	import utils.ElementoTS;
	import utils.ParserVal;
	import utils.MsgStack;
	import utils.Token;
<<<<<<< HEAD
<<<<<<< HEAD
	import java.util.Stack;
	import utils.SintacticTree;
	import utils.SintacticTreeCommon;
	import utils.SintacticTreeConver;
	import utils.SintacticTreeCtrl;
	import utils.SintacticTreeLeaf;
	import utils.SintacticTreeMemory;
	import utils.SintacticTreeUnary;
//#line 35 "Parser.java"
=======
//#line 2 "gramatica.y"
package logic;
import java.util.ArrayList;
import java.util.Hashtable;
import logic.Lexicon;
import utils.ElementoTS;
import utils.ParserVal;
import utils.MsgStack;
import utils.Token;
import java.util.Stack;
import utils.SintacticTree;
import utils.SintacticTreeCommon;
import utils.SintacticTreeConver;
import utils.SintacticTreeCtrl;
import utils.SintacticTreeLeaf;
import utils.SintacticTreeMemory;
import utils.SintacticTreeUnary;
//#line 33 "Parser.java"
>>>>>>> 87e6250... comentario
=======
import utils.sintacticTree.SintacticTree;
import utils.sintacticTree.SintacticTreeCommon;
import utils.sintacticTree.SintacticTreeConver;
import utils.sintacticTree.SintacticTreeCtrl;
import utils.sintacticTree.SintacticTreeLeaf;
import utils.sintacticTree.SintacticTreeMemory;
import utils.sintacticTree.SintacticTreeUnary;

import java.util.Stack;
>>>>>>> 34ec829... _
=======
	import java.util.Stack;
	import utils.syntacticTree.SyntacticTree;
	import utils.syntacticTree.SyntacticTreeCommon;
	import utils.syntacticTree.SyntacticTreeConver;
	import utils.syntacticTree.SyntacticTreeCtrl;
	import utils.syntacticTree.SyntacticTreeLeaf;
	import utils.syntacticTree.SyntacticTreeMemory;
	import utils.syntacticTree.SyntacticTreeUnary;
//#line 34 "Parser.java"
>>>>>>> cf97fd0... Arreglos en ventana




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short BEGIN=257;
public final static short END=258;
public final static short ID=259;
public final static short CTE=260;
public final static short INT=261;
public final static short LONG=262;
public final static short IF=263;
public final static short ELSE=264;
public final static short END_IF=265;
public final static short FOREACH=266;
public final static short IN=267;
public final static short PRINT=268;
public final static short ASSIGN=269;
public final static short LET=270;
public final static short GET=271;
public final static short EQ=272;
public final static short DIF=273;
public final static short CAD=274;
public final static short SIZE=275;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
<<<<<<< HEAD
    0,    1,    1,    1,    2,    2,    4,    4,    4,    5,
    8,    8,    6,    6,    7,    7,    9,    9,    9,    9,
<<<<<<< HEAD
    3,   11,   11,   11,   11,   10,   13,   12,   12,   12,
   12,   12,   12,   14,   14,   21,   22,   19,   20,   24,
   24,   24,   24,   24,   24,   15,   26,   26,   25,   16,
   27,   27,   28,   28,   28,   29,   29,   29,   23,   23,
   23,   23,   17,   30,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,
=======
    3,   12,   12,   13,   13,   13,   13,   10,   11,   11,
   14,   14,   14,   14,   14,   14,   15,   15,   20,   21,
   23,   23,   23,   23,   23,   23,   16,   24,   17,   25,
   25,   26,   26,   26,   27,   27,   27,   22,   22,   22,
   22,   18,   28,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   19,
>>>>>>> 87f0bc1... cambios pre-entrega
};
final static short yylen[] = {                            2,
    1,    2,    1,    1,    1,    2,    3,    3,    2,    1,
    1,    1,    1,    3,    4,    6,    1,    1,    3,    3,
<<<<<<< HEAD
    1,    1,    2,    2,    1,    4,    1,    2,    1,    2,
    2,    2,    2,    6,    8,    1,    1,    1,    3,    1,
    1,    1,    1,    1,    1,    5,    1,    1,    1,    3,
    1,    4,    3,    3,    1,    3,    3,    1,    1,    1,
    4,    2,    4,    1,    2,    2,    5,    3,    4,    4,
    2,    3,    3,    4,    2,    2,    2,    5,    2,
=======
    0,    1,    1,    1,    2,    2,    4,    4,    4,    4,
    5,    8,    8,    6,    6,    7,    7,    7,    7,    9,
    9,    9,    9,    3,   11,   11,   10,   12,   12,   12,
   12,   12,   12,   13,   13,   20,   21,   18,   19,   23,
   23,   23,   23,   23,   23,   14,   25,   25,   24,   15,
   26,   26,   26,   27,   27,   27,   28,   28,   28,   22,
   22,   22,   22,   22,   16,   29,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   17,
};
final static short yylen[] = {                            2,
    1,    2,    1,    1,    1,    2,    3,    3,    1,    2,
    1,    1,    1,    1,    3,    4,    6,    6,    8,    1,
<<<<<<< HEAD
    1,    3,    3,    1,    1,    2,    2,    1,    4,    1,
    2,    1,    2,    2,    2,    2,    6,    8,    1,    1,
    1,    3,    1,    1,    1,    1,    1,    1,    5,    1,
    1,    1,    3,    1,    4,    3,    3,    1,    3,    3,
    1,    1,    1,    4,    2,    4,    1,    2,    2,    5,
    3,    4,    4,    2,    3,    3,    4,    2,    2,    2,
    5,    2,
>>>>>>> 733ee4b... Agregado size y TS
=======
    1,    3,    3,    4,    2,    1,    1,    2,    1,    2,
    2,    2,    2,    6,    8,    1,    1,    1,    3,    1,
    1,    1,    1,    1,    1,    5,    1,    1,    1,    3,
<<<<<<< HEAD
    1,    4,    3,    3,    1,    3,    3,    1,    1,    1,
    4,    2,    4,    1,    2,    2,    5,    3,    4,    4,
    2,    3,    3,    4,    2,    2,    2,    5,    2,
>>>>>>> fcadff2... coment
=======
    1,    4,    4,    3,    3,    1,    3,    3,    1,    1,
    1,    4,    4,    2,    4,    1,    2,    2,    5,    3,
    4,    4,    2,    3,    3,    4,    2,    2,    2,    5,
    2,
>>>>>>> cf97fd0... Arreglos en ventana
};
final static short yydefred[] = {                         0,
    0,   27,   12,   13,    0,    1,    0,    4,    5,    0,
   11,    0,   10,    2,    6,    0,    0,    0,    0,    0,
    0,   38,   49,   66,   42,   43,   44,   45,    0,   40,
   41,    0,    0,    0,    0,    0,   29,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    7,    0,    8,    0,
   33,   78,    0,    0,   77,    0,   73,   79,   67,    0,
   25,   28,   30,   31,   32,    0,    0,   61,   81,    0,
   68,    0,    0,    0,    0,   20,    0,   21,    0,   15,
    0,   75,    0,    0,   74,   24,    0,    0,    0,    0,
   64,    0,    0,   70,   59,    0,    0,    0,    0,    0,
   16,    0,   53,    0,   76,   52,    0,    0,    0,    0,
    0,    0,   71,   72,    0,    0,    0,    0,   65,    0,
   22,   23,    0,    0,   80,   69,   36,    0,   39,   63,
   62,   47,    0,   48,   46,    0,    0,   57,   58,   18,
    0,   17,    0,   34,    0,   37,    0,   19,   35,
};
final static short yydgoto[] = {                          5,
    6,    7,    8,    9,   10,   17,   18,   32,   79,   12,
   34,   35,   36,   37,   38,   39,   40,   41,   88,  128,
  147,   95,   42,   43,  135,   44,   96,   97,   45,
};
final static short yysindex[] = {                      -200,
   -4,    0,    0,    0,    0,    0, -200,    0,    0, -233,
    0,    9,    0,    0,    0,  -49,  -10,   -9,   -1,  -78,
 -197,    0,    0,    0,    0,    0,    0,    0,  -77,    0,
    0, -176, -182, -173,    9,   27,    0,   28,   30,   31,
   52,  -45, -219, -175,   56,  -86,    0, -166,    0, -162,
    0,    0,    5, -192,    0,    6,    0,    0,    0,   42,
    0,    0,    0,    0,    0,  -34,   14,    0,    0, -154,
    0, -158, -194,  -40, -163,    0,   54,    0,  -20,    0,
   22,    0,   23,  -81,    0,    0, -142,   76,  -43, -181,
    0, -193, -142,    0,    0,   32,   12,   77, -141,  -80,
    0,  -85,    0,   29,    0,    0, -145,    9,  -40,   35,
   36,    9,    0,    0,  -40,  -40,  -40,  -40,    0,   37,
    0,    0,   63,  -19,    0,    0,    0, -184,    0,    0,
    0,    0,  -21,    0,    0,   12,   12,    0,    0,    0,
 -137,    0,    9,    0,   38,    0, -140,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  124,    0,    0,    1,
    0,    0,    0,    0,    0,   -8,    0,    0,    0, -136,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -220,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -39,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   73,  -16,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -13,  -12,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   15,  127,    0,    0,    0,   53,   34,    2,
  -17,   25,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -14,   49,    0,    0,    0,    0,  -33,    0,
};
final static int YYTABLESIZE=282;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         70,
    9,   60,   60,   60,   70,   60,   87,   60,   78,   78,
   70,  106,   54,   33,  122,   57,   30,   61,   31,   60,
   60,   14,   60,  100,  100,   16,   56,   71,   56,   55,
   54,   55,   54,   48,   50,   14,   33,   26,   30,   72,
   31,   46,   56,   26,   26,   55,   54,   73,   47,   49,
   14,   89,   11,  117,   13,    1,    2,   51,  118,   11,
    3,    4,    2,    2,   93,  112,   83,   84,   30,   29,
   31,   55,  101,  142,  116,   59,  115,  110,  111,  143,
  144,  136,  137,   58,   60,   62,   63,   94,   64,   65,
  127,   66,   80,   74,  129,   75,   81,   82,   85,   29,
   86,  107,  138,  139,   90,   91,  113,  114,   92,   33,
   98,   99,  102,  133,    2,  103,  108,  119,  120,  126,
  141,  125,  145,    3,  149,  146,  132,  130,  131,  140,
  148,   50,   51,   15,   33,  124,  134,  109,    0,    0,
    0,    0,    0,    0,   33,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   76,   76,    0,    0,    0,  104,  121,
   52,   53,   56,    0,    0,    0,    0,  105,   77,  123,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
<<<<<<< HEAD
    0,    0,    0,   70,   71,    0,    0,    0,   70,   71,
    0,    0,    0,   72,   70,   71,   26,   27,   28,   29,
   62,   62,   62,   62,   20,    2,   61,   21,   22,    3,
    4,   23,    0,    0,   24,    0,   25,    0,   26,   27,
   28,   29,    0,    0,    0,    0,    9,    9,    0,    0,
    0,    9,    9,    0,   20,    2,   64,   21,   22,    3,
    4,   23,    0,    0,   24,    0,   25,    0,   26,   27,
   28,   29,   20,    2,    0,   21,   22,    3,    4,   23,
    0,    0,   24,    0,   25,    0,   26,   27,   28,   29,
<<<<<<< HEAD
   20,    2,   64,   21,   22,    3,    4,   23,    0,    0,
   24,    0,   25,    0,   26,   27,   28,   29,   20,    2,
    0,   21,   22,    3,    4,   23,    0,    0,   24,    0,
   25,    0,   26,   27,   28,   29,   26,   27,   28,   29,
=======
    4,    1,    2,    4,    4,    5,    5,    1,    1,    2,
    2,    2,    2,    2,    2,    2,    6,    8,    1,    3,
    1,    1,    1,    1,    1,    1,    5,    1,    3,    1,
    4,    3,    3,    1,    3,    3,    1,    1,    1,    4,
    2,    4,    1,    2,    2,    5,    3,    4,    4,    2,
    3,    3,    4,    2,    2,    2,    5,    2,
};
final static short yydefred[] = {                         0,
    0,   28,   11,   12,    0,    1,    0,    4,    5,    0,
   10,    0,    9,    2,    6,    0,    0,    0,    0,    0,
    0,   39,   48,   63,   43,   44,   45,   46,    0,   41,
   42,    0,    0,    0,   29,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    7,    0,    8,    0,
   36,   75,    0,    0,   74,    0,   70,   76,   64,    0,
   30,   31,   32,   33,   34,   35,    0,    0,   59,   78,
    0,   65,    0,    0,    0,    0,   17,   18,    0,   14,
    0,   72,    0,   71,   21,    0,    0,    0,    0,   61,
    0,    0,    0,   67,   57,    0,    0,    0,    0,   15,
    0,    0,   73,   51,    0,    0,    0,    0,    0,   68,
   69,    0,    0,    0,   22,    0,    0,    0,    0,   62,
   19,   20,    0,   77,   66,    0,   40,   60,   47,    0,
    0,    0,    0,   23,    0,    0,   55,   56,   16,    0,
   37,   24,    0,   25,    0,    0,   27,   26,   38,
};
final static short yydgoto[] = {                          5,
    6,    7,    8,    9,   10,   17,   18,   32,   79,   93,
  113,  114,  115,   35,   36,   37,   38,   39,   40,   41,
   87,   95,   42,   43,   44,   96,   97,   45,
};
final static short yysindex[] = {                      -180,
  -31,    0,    0,    0,    0,    0, -180,    0,    0, -206,
    0,   67,    0,    0,    0,  -32,   -9,   -8,   15,  -81,
 -189,    0,    0,    0,    0,    0,    0,    0,  -80,    0,
    0, -176, -169,  -23,    0,   36,   39,   40,   41,   42,
   50,  -42, -207, -167,   63,  -84,    0, -154,    0, -153,
    0,    0,   14, -152,    0,   19,    0,    0,    0,   51,
    0,    0,    0,    0,    0,    0,  -36,   26,    0,    0,
 -147,    0, -149, -217,  -38, -155,    0,    0,  -28,    0,
   29,    0,  -85,    0,    0, -136,   82,   71, -135,    0,
 -195, -136,   67,    0,    0,   24,    7,   83,  -83,    0,
  -84,   33,    0,    0, -137, -136,  -38,   37, -136,    0,
    0,   -5,   13,   31,    0,  -38,  -38,  -38,  -38,    0,
    0,    0,  -21,    0,    0, -194,    0,    0,    0,   73,
 -179,   78,   49,    0,    7,    7,    0,    0,    0, -136,
    0,    0,   79,    0,   80, -124,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  142,    0,    0,    0,
    0,    0,    0,    0,    0,    4,    0,    0,    0, -126,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -41,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   85,  -18,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -13,  -12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  138,  139,    0,    0,    0,   17,   46,   22,
    2,   35,  -48,  -19,    0,    0,    0,    0,    0,    0,
    0,  -22,   61,    0,    0,    0,  -29,    0,
};
final static int YYTABLESIZE=344;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         58,
   58,   58,   71,   58,   86,   58,   71,  104,   71,   54,
   78,  122,   57,   34,   61,   99,   11,   58,   58,   72,
   58,   12,   99,   11,   54,   94,   54,   13,   12,   53,
   52,   53,   52,   33,   48,   50,   30,  105,   31,    2,
   54,   92,  110,  111,   88,   53,   52,   13,  118,   47,
   49,   73,   16,  119,   30,   33,   31,  126,   46,   74,
  129,    2,   13,  109,  100,  134,  117,   29,  116,  140,
  141,  139,   30,   51,   31,    1,    2,    2,  143,   55,
    3,    4,  134,   58,  127,   29,  135,  136,   59,   67,
   30,  146,   31,   61,   62,  137,  138,   63,   64,   65,
   66,   75,   76,   29,   80,   81,   82,   83,   30,   85,
   31,   84,   90,   61,  112,  133,   89,   91,   98,  101,
    2,   29,  106,  120,  108,  124,   30,  125,   31,  128,
   30,  142,   31,  112,  112,  112,  144,  147,  148,   29,
  149,    3,   50,   49,   14,   15,  123,  131,  107,    0,
    0,    0,    0,    0,   33,    0,    0,   29,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  102,   77,  121,   52,   53,   56,
    0,    0,    0,  103,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   68,   69,    0,    0,
   68,   69,   68,   69,    0,    0,   70,    0,   58,   58,
   58,   58,   19,    2,   60,   20,   21,    3,    4,   22,
    0,    0,   23,    0,   24,    0,   25,   26,   27,   28,
   19,    2,   59,   20,   21,    3,    4,   22,    0,    0,
   23,    0,   24,    0,   25,   26,   27,   28,   19,    2,
  130,   20,   21,    3,    4,   22,    0,    0,   23,    0,
   24,    0,   25,   26,   27,   28,   19,    2,  132,   20,
   21,    3,    4,   22,    0,    0,   23,    0,   24,    0,
   25,   26,   27,   28,   19,    2,  145,   20,   21,    3,
    4,   22,    0,    0,   23,    0,   24,    0,   25,   26,
   27,   28,   19,    2,    0,   20,   21,    3,    4,   22,
    0,    0,   23,    0,   24,    0,   25,   26,   27,   28,
   25,   26,   27,   28,
>>>>>>> 87f0bc1... cambios pre-entrega
=======
>>>>>>> 733ee4b... Agregado size y TS
=======
    0,    0,    0,   67,   68,    0,    0,    0,   67,   68,
    0,    0,    0,   69,   67,   68,   25,   26,   27,   28,
   60,   60,   60,   60,   19,    2,   59,   20,   21,    3,
    4,   22,    0,    0,   23,    0,   24,    0,   25,   26,
   27,   28,    0,    0,    0,    0,    9,    9,    0,    0,
    0,    9,    9,    0,   19,    2,    0,   20,   21,    3,
    4,   22,    0,    0,   23,    0,   24,    0,   25,   26,
   27,   28,
>>>>>>> fcadff2... coment
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
<<<<<<< HEAD
yycheck = new short[] {                         41,
   42,   43,   45,   45,   41,   47,   45,   93,   45,   91,
<<<<<<< HEAD
   95,   95,   93,   35,    0,   44,   44,   59,   60,   44,
   62,    7,  259,    0,  107,   43,   43,   45,   45,   43,
    7,   45,   44,   44,   42,   44,   60,    0,   62,   47,
   69,   59,   59,  259,    7,   59,   59,   59,   59,   35,
   59,  267,  135,  257,   60,  259,   62,  256,  257,  257,
   59,  259,  261,  262,   43,   93,   45,   91,   93,  264,
  265,   91,   60,  269,   62,  260,   60,   59,   62,  108,
  264,  265,  113,  114,   59,   91,  115,  116,  110,   59,
   76,   59,   40,  269,   40,  259,  259,   93,  260,   76,
   93,  123,   88,   91,   59,  260,   91,   93,   94,  267,
   91,   88,  257,  274,   41,  137,   93,   94,  260,   41,
   93,  265,   93,    0,  110,  265,  269,   59,  265,    7,
  102,    7,   90,   -1,   -1,   -1,   -1,  123,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  137,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
=======
   95,   95,   93,   12,   34,   44,    0,   59,   60,   42,
   62,    0,   44,    7,   43,   74,   45,   59,    7,   43,
   43,   45,   45,   12,   44,   44,   60,   86,   62,  257,
   59,  259,   91,   92,   67,   59,   59,   44,   42,   59,
   59,  259,  259,   47,   60,   34,   62,  106,   91,  267,
  109,  257,   59,  259,   93,  114,   43,   91,   45,  264,
  265,   93,   60,   59,   62,  256,  257,  257,  258,  269,
  261,  262,  131,  260,  107,   91,  116,  117,  258,   40,
   60,  140,   62,  113,   59,  118,  119,   59,   59,   59,
   59,  269,   40,   91,  259,  259,   93,  260,   60,   59,
   62,   93,  260,  133,   93,  114,   91,  267,  274,   91,
  257,   91,   41,   41,  260,   93,   60,  265,   62,   93,
   60,   59,   62,  112,  113,  114,   59,   59,   59,   91,
  265,    0,  269,   59,    7,    7,  101,  113,   88,   -1,
   -1,   -1,   -1,   -1,  133,   -1,   -1,   91,   -1,   -1,
>>>>>>> 87f0bc1... cambios pre-entrega
=======
yycheck = new short[] {                         45,
    0,   41,   42,   43,   45,   45,   41,   47,   95,   95,
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
   45,   93,   91,   44,   95,   93,   60,    0,   62,   59,
   60,   35,   62,   44,    7,    0,   44,   44,   43,  109,
   45,   43,    7,   45,   43,  259,   45,  259,   60,   44,
   62,   44,   59,  267,   59,  260,    0,   59,   69,  257,
   59,  259,   35,    7,   59,   42,   59,   59,  256,  257,
   47,  141,   93,  261,  262,  257,   91,  259,   60,   91,
   62,   43,  269,   45,   59,   93,  264,  265,  264,  265,
   59,  115,  116,   59,   40,  269,   60,   40,   62,  110,
   59,   59,  259,   76,  259,   93,  117,  118,  112,   91,
  260,   76,   93,   91,   59,  260,   89,   58,  267,   91,
  274,   94,   95,  127,   89,  257,   41,   91,   41,   94,
   95,  260,  260,   93,  265,   58,   93,   93,  260,  112,
  144,   93,  265,    0,  269,  265,    7,    7,   -1,  104,
   -1,   91,   -1,   -1,  127,   -1,   -1,   -1,   -1,   -1,
>>>>>>> 733ee4b... Agregado size y TS
=======
   45,   93,   91,    7,   95,   93,   60,   42,   62,   59,
   60,   12,   62,   44,   44,   59,   43,   43,   45,   45,
   43,  259,   45,   44,   44,   44,  112,  113,   60,  267,
   62,   66,   59,   59,   35,    0,   59,  259,   59,   59,
   59,   59,    7,  256,  257,   42,  264,  265,  261,  262,
   47,  257,  257,  259,  259,   43,  260,   45,   60,   91,
   62,  269,   93,   93,  258,  258,   91,   59,   59,   73,
   59,   40,  107,   59,  269,  259,   40,  259,   93,  114,
  115,  260,   86,   59,   93,   91,  260,   91,   92,   91,
  267,  274,   58,   91,  257,   41,  260,   41,  260,   93,
  265,   58,  106,   93,  260,  109,   93,  265,  109,   93,
    0,  258,  269,    7,   59,   35,  101,   88,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  109,   -1,  129,   -1,
   -1,   -1,   -1,   -1,   -1,  139,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
>>>>>>> fcadff2... coment
=======
   45,   93,   91,   12,   95,   93,   60,    7,   62,   59,
   60,   59,   62,   44,   44,   42,   44,   43,   43,   45,
   45,   43,    0,   45,  259,   44,   35,   44,   60,    7,
   62,   59,  267,   59,   59,  259,   42,   59,   91,   66,
   59,   47,   59,  256,  257,  257,   59,  259,  261,  262,
  257,   43,  259,   45,  259,  260,  259,  260,   60,   91,
   62,  269,   93,   93,  264,  265,  115,  116,  260,  258,
  258,   59,   59,   73,   59,   59,   40,   40,  269,  259,
  259,   93,  109,   59,   91,   93,  260,   87,  274,   91,
  117,  118,   92,   93,  267,   91,   58,  257,   93,   41,
   41,  260,   93,  112,  265,   58,  260,   93,  108,  265,
   93,   93,  112,   93,    0,  258,  269,    7,   35,   59,
   89,  102,  112,   -1,  133,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  143,   -1,   -1,   -1,   -1,   -1,   -1,
>>>>>>> cf97fd0... Arreglos en ventana
=======
   45,   93,   91,   12,   95,   93,   60,   35,   62,   59,
   60,    7,   62,   44,   44,  259,   43,   42,   45,   43,
   43,   45,   45,   44,   44,   44,   35,  258,   60,  259,
   62,   91,   59,  264,  265,   59,   59,  267,   59,   59,
   59,   66,    0,   42,   59,  256,  257,   59,   47,    7,
  261,  262,  257,  257,  259,  259,  259,  260,   60,   91,
   62,  269,   93,   93,   43,  258,   45,  259,  260,  264,
  265,  115,  116,  260,  258,   59,   59,   73,   59,   59,
  108,   40,  259,  269,  109,   40,  259,   93,   93,   91,
   59,   87,  117,  118,   91,  260,   92,   93,  267,  108,
  274,   58,   91,  112,  257,   93,   41,   41,  260,  265,
   58,   93,  260,    0,  265,  143,  112,   93,   93,   93,
   93,   59,  269,    7,  133,  102,  112,   89,   -1,   -1,
   -1,   -1,   -1,   -1,  143,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
>>>>>>> bca257b... resueltos problemas en common
=======
   45,   93,   91,   12,   95,   93,   60,   35,   62,   59,
   60,    7,   62,   44,   44,  259,   43,   42,   45,   43,
   43,   45,   45,   44,   44,   44,   35,  258,   60,  259,
   62,   91,   59,  264,  265,   59,   59,  267,   59,   59,
   59,   66,    0,   42,   59,  256,  257,   59,   47,    7,
  261,  262,  257,  257,  259,  259,  259,  260,   60,   91,
   62,  269,   93,   93,   43,  258,   45,  259,  260,  264,
  265,  115,  116,  260,  258,   59,   59,   73,   59,   59,
  108,   40,  259,  269,  109,   40,  259,   93,   93,   91,
   59,   87,  117,  118,   91,  260,   92,   93,  267,  108,
  274,   58,   91,  112,  257,   93,   41,   41,  260,  265,
   58,   93,  260,    0,  265,  143,  112,   93,   93,   93,
   93,   59,  269,    7,  133,  102,  112,   89,   -1,   -1,
   -1,   -1,   -1,   -1,  143,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
>>>>>>> 1ce0652... comentario
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  260,  260,   -1,   -1,   -1,  260,  260,
  259,  260,  260,   -1,   -1,   -1,   -1,  269,  275,  275,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
<<<<<<< HEAD
   -1,   -1,   -1,   -1,   -1,   -1,  259,  260,   -1,   -1,
  259,  260,  259,  260,   -1,   -1,  269,   -1,  270,  271,
  272,  273,  256,  257,  258,  259,  260,  261,  262,  263,
<<<<<<< HEAD
   -1,   -1,  266,   -1,  268,   -1,  270,  271,  272,  273,
  256,  257,  258,  259,  260,  261,  262,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  271,  272,  273,  256,  257,
   -1,  259,  260,  261,  262,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,  271,  272,  273,  270,  271,  272,  273,
=======
   -1,   -1,  266,   -1,  268,   -1,  270,  271,  272,  273,
  256,  257,  258,  259,  260,  261,  262,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  271,  272,  273,  256,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,  271,  272,  273,  256,  257,  258,  259,
  260,  261,  262,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,  271,  272,  273,  256,  257,  258,  259,  260,  261,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  271,
  272,  273,  256,  257,   -1,  259,  260,  261,  262,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,  271,  272,  273,
  270,  271,  272,  273,
>>>>>>> 87f0bc1... cambios pre-entrega
=======
   -1,   -1,   -1,  259,  260,   -1,   -1,   -1,  259,  260,
   -1,   -1,   -1,  269,  259,  260,  270,  271,  272,  273,
  270,  271,  272,  273,  256,  257,  258,  259,  260,  261,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  271,
  272,  273,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,
   -1,  261,  262,   -1,  256,  257,   -1,  259,  260,  261,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  271,
<<<<<<< HEAD
  272,  273,  256,  257,   -1,  259,  260,  261,  262,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,  271,  272,  273,
>>>>>>> 733ee4b... Agregado size y TS
=======
  272,  273,
>>>>>>> fcadff2... coment
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=275;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,"'_'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"BEGIN","END","ID","CTE","INT","LONG","IF",
"ELSE","END_IF","FOREACH","IN","PRINT","ASSIGN","LET","GET","EQ","DIF","CAD",
"SIZE",
};
final static String yyrule[] = {
"$accept : programa_completo",
"programa_completo : programa",
"programa : bloque_decl bloque_ejec",
"programa : bloque_decl",
"programa : bloque_ejec",
"bloque_decl : sent_decl",
"bloque_decl : bloque_decl sent_decl",
"sent_decl : comienzo_decl lista_vars ';'",
"sent_decl : comienzo_decl lista_colecciones ';'",
"sent_decl : comienzo_decl",
"sent_decl : error ';'",
"comienzo_decl : tipo",
"tipo : INT",
"tipo : LONG",
"lista_vars : ID",
"lista_vars : lista_vars ',' ID",
"lista_colecciones : ID '[' lista_valores_inic ']'",
"lista_colecciones : lista_colecciones ',' ID '[' lista_valores_inic ']'",
"lista_colecciones : ID '[' SIZE ':' CTE ']'",
"lista_colecciones : lista_colecciones ',' ID '[' SIZE ':' CTE ']'",
"lista_valores_inic : CTE",
"lista_valores_inic : '_'",
"lista_valores_inic : lista_valores_inic ',' CTE",
"lista_valores_inic : lista_valores_inic ',' '_'",
<<<<<<< HEAD
<<<<<<< HEAD
"bloque_ejec : bloque_unico",
"conjunto_bloques : bloque_unico",
"conjunto_bloques : conjunto_bloques bloque_unico",
"conjunto_bloques : conjunto_bloques sent_ejec",
"conjunto_bloques : sent_ejec",
"bloque_unico : comienzo_bloque conjunto_bloques END ';'",
=======
"bloque_ejec : comienzo_bloque conj_sent END ';'",
"conj_sent : sent_ejec conj_sent",
"conj_sent : sent_ejec",
>>>>>>> fcadff2... coment
"comienzo_bloque : BEGIN",
=======
"bloque_ejec : comienzo_bloque conj_sent_ejec END ';'",
"conjunto_bloques : bloque_unico",
"conjunto_bloques : conjunto_bloques bloque_unico",
"bloque_unico : comienzo_bloque conj_sent_ejec END ';'",
"bloque_unico : comienzo_bloque conjunto_bloques END ';'",
"bloque_unico : comienzo_bloque conjunto_bloques conj_sent_ejec END ';'",
"bloque_unico : comienzo_bloque conj_sent_ejec conjunto_bloques END ';'",
"comienzo_bloque : BEGIN",
"conj_sent_ejec : sent_ejec",
"conj_sent_ejec : conj_sent_ejec sent_ejec",
>>>>>>> 87f0bc1... cambios pre-entrega
"sent_ejec : sent_cond ';'",
"sent_ejec : sent_ctrl",
"sent_ejec : sent_asig ';'",
"sent_ejec : sent_print ';'",
"sent_ejec : error_p ';'",
"sent_ejec : error ';'",
<<<<<<< HEAD
<<<<<<< HEAD
"sent_cond : comienzo_if '(' cond ')' bloque_then END_IF",
"sent_cond : comienzo_if '(' cond ')' bloque_then ELSE bloque_else END_IF",
<<<<<<< HEAD
"bloque_then : conjunto_bloques",
"bloque_else : conjunto_bloques",
=======
"sent_cond : comienzo_if '(' cond ')' bloque_unico END_IF",
"sent_cond : comienzo_if '(' cond ')' bloque_unico ELSE bloque_unico END_IF",
>>>>>>> 87f0bc1... cambios pre-entrega
=======
=======
"sent_cond : comienzo_if '(' cond_if ')' bloque_then END_IF",
"sent_cond : comienzo_if '(' cond_if ')' bloque_then ELSE bloque_else END_IF",
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 1375c5c... arreglos varios
"bloque_then : bloque_ejec",
"bloque_else : bloque_ejec",
>>>>>>> fcadff2... coment
=======
"bloque_then : conj_sent",
"bloque_else : conj_sent",
>>>>>>> bca257b... resueltos problemas en common
=======
"bloque_then : conj_sent",
"bloque_else : conj_sent",
>>>>>>> 1ce0652... comentario
"comienzo_if : IF",
"cond_if : factor comparador factor",
"comparador : '<'",
"comparador : '>'",
"comparador : LET",
"comparador : GET",
"comparador : EQ",
"comparador : DIF",
<<<<<<< HEAD
"sent_ctrl : comienzo_foreach ID IN ID bloque_foreach",
"bloque_foreach : bloque_ejec",
"bloque_foreach : sent_ejec",
=======
"sent_ctrl : comienzo_foreach ID IN ID bloque_unico",
>>>>>>> 87f0bc1... cambios pre-entrega
"comienzo_foreach : FOREACH",
"sent_asig : inic_sent_asig ASSIGN expression",
"inic_sent_asig : ID",
"inic_sent_asig : ID '[' CTE ']'",
"inic_sent_asig : ID '[' ID ']'",
"expression : expression '+' term",
"expression : expression '-' term",
"expression : term",
"term : term '*' factor",
"term : term '/' factor",
"term : factor",
"factor : ID",
"factor : CTE",
"factor : ID '[' CTE ']'",
"factor : ID '[' ID ']'",
"factor : '-' CTE",
"sent_print : comienzo_print '(' CAD ')'",
"comienzo_print : PRINT",
"error_p : comienzo_bloque END",
"error_p : comparador factor",
"error_p : comienzo_if '(' ')' bloque_ejec END_IF",
"error_p : comienzo_foreach IN bloque_ejec",
"error_p : comienzo_foreach ID IN bloque_ejec",
"error_p : comienzo_foreach IN ID bloque_ejec",
"error_p : '[' ']'",
"error_p : '[' CTE ']'",
"error_p : ID CTE ']'",
"error_p : ID '[' CTE ASSIGN",
"error_p : CTE ASSIGN",
"error_p : ID ID",
"error_p : tipo CTE",
"error_p : ID '[' CTE CTE ']'",
"error_p : comparador ASSIGN",
};

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 222 "gramatica_2.y"
=======
//#line 213 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 251 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
//#line 264 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 278 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 287 "gramatica.y"
>>>>>>> f75def1... comentario
=======
//#line 371 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 440 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 441 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 445 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 448 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
//#line 457 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 492 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 502 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 502 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 503 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 502 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 506 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 508 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 512 "gramatica.y"
>>>>>>> fde75a9... comentario

private Lexicon lex;
private Hashtable<String,ElementoTS> symbolTable;
private MsgStack msgStack; // Stack de mensajes de error y warning
private MsgStack semanticStructStack;
Stack<Integer> stackOfLines = new Stack<Integer>(); // Stack para guardar los números de línea (para anidamiento de for/if)
private int numLineaDecl; // Guarda el número de líneal del comienzo de declaración
private int countSize; // Cuenta cantidad de elementos inicializados en el arreglo
private String elems; // Guarda los elementos iniciales de la colección
private String tipovar; // Almacena el tipo de la variable/colección para su seteo posterior en TS
private SyntacticTreeCtrl nodoRaiz; // Raiz del árbol sintáctico
private boolean hasError;

public Parser(Lexicon lex, Hashtable<String, ElementoTS> symbolTable, MsgStack msgStack, MsgStack semanticStructStack){
	this.lex = lex;
	this.symbolTable = symbolTable;
	this.msgStack = msgStack;
	this.semanticStructStack = semanticStructStack;
	countSize = 0;
	hasError = false;
}

private int yylex(){
	Token token = lex.getNewToken();
	String lexeme = token.getLexeme();
	if (lexeme != null)
		yylval = new ParserVal(lexeme);
	return token.getTokenValue();
}

private void yyerror(String msg){
	msgStack.addMsg("Línea "+ lex.getNewLineCounter() + ": " + msg);
}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 396 "Parser.java"
=======
//#line 410 "Parser.java"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 395 "Parser.java"
>>>>>>> f713e33... comentario generico
=======
//#line 407 "Parser.java"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 409 "Parser.java"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 413 "Parser.java"
>>>>>>> f75def1... comentario
=======

public SyntacticTreeCtrl getRaiz(){
	return nodoRaiz;
}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 416 "Parser.java"
>>>>>>> fcadff2... coment
=======
//#line 415 "Parser.java"
>>>>>>> 0507715... Update Parser.java
=======
//#line 416 "Parser.java"
>>>>>>> 1d41d94... Up
=======
//#line 417 "Parser.java"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 416 "Parser.java"
>>>>>>> 87ae9f5... comentario
=======
//#line 418 "Parser.java"
>>>>>>> 4269743... generador de datos
=======
//#line 421 "Parser.java"
>>>>>>> cf97fd0... Arreglos en ventana
=======

public boolean hasErrors() { return hasError; }
//#line 425 "Parser.java"
>>>>>>> 1375c5c... arreglos varios
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 27 "gramatica.y"
{ nodoRaiz = new SyntacticTreeCtrl("PROGRAMA",(SyntacticTree)val_peek(0)); }
break;
case 2:
//#line 30 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 3:
//#line 31 "gramatica.y"
{ yyval = new SyntacticTreeCommon("SENTENCIA", null, null); msgStack.addMsg("Warning: Programa sin codigo ejecutable"); }
break;
case 4:
//#line 32 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 39 "gramatica.y"
{ semanticStructStack.addMsg("Linea "+ numLineaDecl +": Sentencia declarativa"); }
break;
case 8:
//#line 40 "gramatica.y"
{ semanticStructStack.addMsg("Linea "+ numLineaDecl +": Sentencia declarativa"); }
break;
case 10:
//#line 42 "gramatica.y"
{ msgStack.addMsg("Error genérico en el bloque declarativo no contemplado cerca de línea " + lex.getNewLineCounter()); }
break;
case 11:
//#line 45 "gramatica.y"
{ numLineaDecl = lex.getNewLineCounter(); }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 22:
//#line 62 "gramatica_2.y"
{ declaracion = false; }
=======
case 21:
//#line 59 "gramatica_2.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque principal de ejecuci�n"); }
break;
case 22:
//#line 62 "gramatica_2.y"
{ declaracion = false; semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecuci�n"); }
>>>>>>> 87f0bc1... cambios pre-entrega
break;
case 23:
//#line 63 "gramatica_2.y"
{ declaracion = false; }
=======
case 13:
//#line 43 "gramatica.y"
=======
case 14:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 44 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 44 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 46 "gramatica.y"
>>>>>>> f75def1... comentario
=======
case 12:
//#line 48 "gramatica.y"
{tipovar = ElementoTS.INT;}
break;
case 13:
//#line 49 "gramatica.y"
{tipovar = ElementoTS.LONG;}
break;
case 14:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 51 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 52 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 53 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
//#line 52 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 52 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 53 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 52 "gramatica.y"
>>>>>>> d209296... comentario
{
					if(!symbolTable.get(val_peek(0).sval).isDeclared()){
						symbolTable.get(val_peek(0).sval).setDeclared();
						symbolTable.get(val_peek(0).sval).setVariableType(tipovar);
						symbolTable.get(val_peek(0).sval).setIdentifierClass(ElementoTS.VAR);
					} else {
						msgStack.addMsg("Error: variable "+ val_peek(0).sval+" re-declarada en el número de línea " + lex.getNewLineCounter());
						hasError = true;
					}
				}
break;
case 15:
//#line 62 "gramatica.y"
{
					if(!symbolTable.get(val_peek(0).sval).isDeclared()){
						symbolTable.get(val_peek(0).sval).setDeclared();
						symbolTable.get(val_peek(0).sval).setVariableType(tipovar);
						symbolTable.get(val_peek(0).sval).setIdentifierClass(ElementoTS.VAR);
					} else {
						msgStack.addMsg("Error: variable "+ val_peek(0).sval+" re-declarada en el número de línea " + lex.getNewLineCounter());
						hasError = true;
					}
				}
break;
case 16:
//#line 74 "gramatica.y"
{
				if(!symbolTable.get(val_peek(3).sval).isDeclared()) {
					symbolTable.get(val_peek(3).sval).setDeclared();
					symbolTable.get(val_peek(3).sval).setCSize(countSize);
					symbolTable.get(val_peek(3).sval).setElemsCollection(elems);
					symbolTable.get(val_peek(3).sval).setVariableType(tipovar);
					symbolTable.get(val_peek(3).sval).setIdentifierClass(ElementoTS.COL);
					elems = "";
					countSize = 0;
				} else {
					msgStack.addMsg("Error: colección "+ val_peek(3).sval+" re-declarada en el número de línea " + lex.getNewLineCounter());
					hasError = true;
				}
			}
break;
case 17:
//#line 88 "gramatica.y"
{
				if(!symbolTable.get(val_peek(3).sval).isDeclared()) {
					symbolTable.get(val_peek(3).sval).setDeclared();
					symbolTable.get(val_peek(3).sval).setCSize(countSize);
					symbolTable.get(val_peek(3).sval).setElemsCollection(elems);
					symbolTable.get(val_peek(3).sval).setVariableType(tipovar);
					symbolTable.get(val_peek(3).sval).setIdentifierClass(ElementoTS.COL);
					elems = "";
					countSize = 0;
				} else {
					msgStack.addMsg("Error: colección "+ val_peek(3).sval+" re-declarada en el número de línea " + lex.getNewLineCounter());
					hasError = true;
				}
			}
break;
case 18:
//#line 102 "gramatica.y"
{
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		if(!symbolTable.get(val_peek(5).sval).getDeclaracion())
		{
			symbolTable.get(val_peek(5).sval).setDeclaracion(true);
			symbolTable.get(val_peek(5).sval).setCSize(val_peek(1).sval);
			symbolTable.get(val_peek(5).sval).setTipoAtributo(tipovar);
		}
		else
			msgStack.addMsg("Error: coleccion"+ val_peek(5).sval+" re-declarada en el n?mero de l?nea " + lex.getNewLineCounter());;
		}
>>>>>>> f713e33... comentario generico
=======
			if(!symbolTable.get(val_peek(5).sval).getDeclaracion())
=======
			if(!symbolTable.get(val_peek(5).sval).isDeclarada())
>>>>>>> 34ec829... _
			{
				symbolTable.get(val_peek(5).sval).setDeclarada(true);
				int size = Integer.parseInt(val_peek(1).sval);
				symbolTable.get(val_peek(5).sval).setCSize(size);
				for (int i = 0; i < size - 1; i++) {
					elems += "_,";
=======
				if(!symbolTable.get(val_peek(5).sval).isDeclarada()) {
					symbolTable.get(val_peek(5).sval).setDeclarada(true);
=======
				if(!symbolTable.get(val_peek(5).sval).isDeclared()) {
					symbolTable.get(val_peek(5).sval).setDeclared();
>>>>>>> 0fcca1b... varios
					int size = Integer.parseInt(val_peek(1).sval);
					symbolTable.get(val_peek(5).sval).setCSize(size);
					for (int i = 0; i < size - 1; i++) {
						elems += "_,";
					}
					elems += "_";
					symbolTable.get(val_peek(5).sval).setElemsCollection(elems);
					elems = "";
					symbolTable.get(val_peek(5).sval).setVariableType(tipovar);
				} else {
					msgStack.addMsg("Error: colección"+ val_peek(5).sval+" re-declarada en el número de línea " + lex.getNewLineCounter());
					hasError = true;
>>>>>>> 1375c5c... arreglos varios
				}
			}
>>>>>>> 4269743... generador de datos
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 26:
//#line 88 "gramatica.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecuci�n"); }
=======
case 19:
//#line 119 "gramatica.y"
{
				if(!symbolTable.get(val_peek(5).sval).isDeclared()){
					symbolTable.get(val_peek(5).sval).setDeclared();
					int size = Integer.parseInt(val_peek(1).sval);
					symbolTable.get(val_peek(5).sval).setCSize(size);
					for (int i = 0; i < size - 1; i++) {
						elems += "_,";
					}
					elems += "_";
					symbolTable.get(val_peek(5).sval).setElemsCollection(elems);
					elems = "";
					symbolTable.get(val_peek(5).sval).setVariableType(tipovar);
				} else {
					msgStack.addMsg("Error: colección "+ val_peek(5).sval+" re-declarada en el número de línea " + lex.getNewLineCounter());
					hasError = true;
				}
			}
break;
case 20:
//#line 138 "gramatica.y"
{ elems= val_peek(0).sval; countSize++;}
break;
case 21:
//#line 139 "gramatica.y"
{ elems= "_"; countSize++;}
break;
case 22:
//#line 140 "gramatica.y"
{ elems = elems + "," + val_peek(0).sval; countSize++;}
break;
case 23:
//#line 141 "gramatica.y"
{ elems = elems + ",_"; countSize++; }
break;
case 24:
//#line 144 "gramatica.y"
{ yyval = val_peek(2); semanticStructStack.addMsg("Línea "+ stackOfLines.peek().intValue() +": Bloque de ejecución"); }
break;
case 25:
//#line 147 "gramatica.y"
{ yyval = new SyntacticTreeCommon("SENTENCIA", (SyntacticTree)val_peek(1), (SyntacticTree)val_peek(0));	}
break;
case 26:
//#line 148 "gramatica.y"
{ yyval = new SyntacticTreeCommon("SENTENCIA", (SyntacticTree)val_peek(0), null); }
break;
case 27:
//#line 151 "gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 28:
//#line 154 "gramatica.y"
{ yyval = val_peek(1); }
break;
case 29:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 101 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecuci󮢩"); }
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 115 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
=======
//#line 117 "gramatica.y"
>>>>>>> f75def1... comentario
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecución"); }
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 140 "gramatica.y"
=======
//#line 141 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
{yyval=val_peek(0);}
>>>>>>> fcadff2... coment
=======
//#line 154 "gramatica (12).y"
=======
//#line 153 "gramatica (12).y"
<<<<<<< HEAD
>>>>>>> cf97fd0... Arreglos en ventana
{ yyval=val_peek(0); }
>>>>>>> 4269743... generador de datos
=======
=======
//#line 155 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 155 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 156 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 155 "gramatica.y"
>>>>>>> d209296... comentario
{ yyval = val_peek(0); }
>>>>>>> 45299ea... visualización de árbol sintáctico
break;
case 30:
//#line 156 "gramatica.y"
{ yyval = val_peek(1); }
break;
case 31:
//#line 157 "gramatica.y"
{ yyval = val_peek(1); }
break;
case 32:
//#line 158 "gramatica.y"
{
				yyval = new SyntacticTreeCommon("ERROR", null, null);
				/* Hay un warning en error_p, por eso hasError se setea true abajo y no acá */
			}
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 34:
<<<<<<< HEAD
//#line 84 "gramatica_2.y"
=======
case 28:
//#line 72 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); /*Para debugging*/}
break;
case 36:
//#line 84 "gramatica_2.y"
{ msgStack.addMsg("Error gen�rico no contemplado cerca de l�nea " + lex.getNewLineCounter()); }
break;
case 37:
<<<<<<< HEAD
//#line 87 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 102 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
case 37:
<<<<<<< HEAD
//#line 115 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 129 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 131 "gramatica.y"
>>>>>>> f75def1... comentario
=======
case 33:
//#line 162 "gramatica.y"
{ 
				msgStack.addMsg("Error genérico no contemplado cerca de línea " + lex.getNewLineCounter());
				hasError = true;
				yyval = new SyntacticTreeCommon("ERROR", null, null);
			}
break;
case 34:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 147 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 148 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 161 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
//#line 160 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 165 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 167 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 167 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 168 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 167 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 169 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
{
                    semanticStructStack.addMsg("L?nea " + stackOfLines.peek().intValue() + ": Sentencia IF ");
                    stackOfLines.pop();
					SyntacticTreeCtrl nodothen = new SyntacticTreeCtrl("THEN", (SyntacticTree)val_peek(1));
					yyval = new SyntacticTreeCommon("IF",(SyntacticTree)val_peek(3),nodothen);
                }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 35:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 88 "gramatica_2.y"
=======
case 38:
//#line 91 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 106 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
case 38:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 119 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 133 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 135 "gramatica.y"
>>>>>>> f75def1... comentario
=======
case 35:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 152 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 153 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 154 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 167 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
//#line 166 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 174 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 173 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 173 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 174 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 173 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 175 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
{
                    semanticStructStack.addMsg("L?nea " + stackOfLines.peek().intValue() + ": Sentencia IF ");
                    stackOfLines.pop();
					SyntacticTreeCtrl nodothen = new SyntacticTreeCtrl("THEN_ELSE", (SyntacticTree)val_peek(3));
					SyntacticTreeCtrl nodoelse = new SyntacticTreeCtrl("ELSE", (SyntacticTree)val_peek(1));
					SyntacticTree nodoCuerpo = new SyntacticTreeCommon("CUERPO",nodothen,nodoelse);
					yyval = new SyntacticTreeCommon("IF",(SyntacticTree)val_peek(5),nodoCuerpo);
                }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 38:
//#line 120 "gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 46:
<<<<<<< HEAD
//#line 116 "gramatica_2.y"
=======
case 39:
//#line 98 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 47:
//#line 112 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 134 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
case 41:
//#line 149 "gramatica.y"
=======
case 36:
//#line 187 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 37:
//#line 190 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 38:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 172 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 173 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 176 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 185 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
//#line 184 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 198 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 191 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 191 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 192 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 191 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 193 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 39:
//#line 196 "gramatica.y"
{ 
			SyntacticTree nodoCond = new SyntacticTreeCommon(val_peek(1).sval, (SyntacticTree)val_peek(2), (SyntacticTree)val_peek(0));
			SyntacticTree nodoCondCtrol = new SyntacticTreeCtrl("COND_IF",nodoCond);
			yyval = nodoCondCtrol; }
break;
case 40:
//#line 202 "gramatica.y"
{ val_peek(0).sval = "<"; yyval = val_peek(0); }
break;
case 41:
//#line 203 "gramatica.y"
{ val_peek(0).sval = ">"; yyval = val_peek(0); }
break;
case 42:
//#line 204 "gramatica.y"
{ val_peek(0).sval = "LET"; yyval = val_peek(0); }
break;
case 43:
//#line 205 "gramatica.y"
{ val_peek(0).sval = "GET"; yyval = val_peek(0); }
break;
case 44:
//#line 206 "gramatica.y"
{ val_peek(0).sval = "EQ"; yyval = val_peek(0); }
break;
case 45:
//#line 207 "gramatica.y"
{ val_peek(0).sval = "DIF"; yyval = val_peek(0); }
break;
case 46:
//#line 210 "gramatica.y"
{ 
		SyntacticTreeCommon nodo = null;
        semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Sentencia foreach");
        stackOfLines.pop();
		if(!(symbolTable.get(val_peek(3).sval).isDeclared()) || !(symbolTable.get(val_peek(1).sval).isDeclared())){
			if(!(symbolTable.get(val_peek(3).sval).isDeclared()))
				msgStack.addMsg("Error: Variable "+val_peek(3).sval +" no declarada.");
			else
				msgStack.addMsg("Error: Colección  "+val_peek(1).sval +" no declarada.");
			hasError = true;
			nodo = new SyntacticTreeCommon("ERROR", null, null);
		}else{
			if (!(symbolTable.get(val_peek(3).sval).getVariableType().equals(symbolTable.get(val_peek(1).sval).getVariableType()))) {
				msgStack.addMsg("Error: Tipos distintos en línea: " + lex.getNewLineCounter());
				hasError = true;
				nodo = new SyntacticTreeCommon("ERROR", null, null);
			} else {
				symbolTable.get(val_peek(3).sval).setHasPointer();
				SyntacticTree nodoAux1 = new SyntacticTreeLeaf("_" + val_peek(3).sval);
				SyntacticTree nodoMax1 = new SyntacticTreeLeaf("_" + val_peek(1).sval);
				SyntacticTree nodoInic = new SyntacticTreeMemory("::=",nodoAux1,nodoMax1);
				SyntacticTree nodoAux2 = new SyntacticTreeLeaf("_" + val_peek(3).sval);
				SyntacticTree nodoMax2 = new SyntacticTreeLeaf("_" + val_peek(1).sval);
				SyntacticTree nodoComp = new SyntacticTreeMemory("<<",nodoAux2,nodoMax2);
				SyntacticTree nodoCond = new SyntacticTreeCtrl("COND_FOREACH",nodoComp);
				SyntacticTree nodoBucle = new SyntacticTreeCommon("BUCLE", nodoInic,nodoCond);
				SyntacticTree nodoAux3 = new SyntacticTreeLeaf("_" + val_peek(3).sval);
				SyntacticTree nodoMax3 = new SyntacticTreeLeaf("_" + val_peek(1).sval);
				nodoMax3.setType(symbolTable.get(val_peek(1).sval).getVariableType());
				SyntacticTree nodoSuma = new SyntacticTreeMemory("+=", nodoAux3,nodoMax3);
				SyntacticTree nodoCuerpo = new SyntacticTreeCommon("CUERPO", (SyntacticTree)val_peek(0), nodoSuma);
				SyntacticTree nodoCuerpoAvance = new SyntacticTreeCtrl("CUERPOAVANCE", nodoCuerpo);
				nodo = new SyntacticTreeCommon("FOREACH", nodoBucle,nodoCuerpoAvance);
			}
		}
		yyval = nodo;
	}
break;
case 47:
//#line 249 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 48:
//#line 250 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 49:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 147 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 161 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 163 "gramatica.y"
>>>>>>> f75def1... comentario
=======
//#line 222 "gramatica.y"
=======
//#line 223 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 227 "gramatica.y"
>>>>>>> 67dbe83... comentario
{
			stackOfLines.push(lex.getNewLineCounter()); 
		}
break;
case 50:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 227 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 228 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 232 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 234 "gramatica (12).y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 50:
//#line 237 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
//#line 233 "gramatica (12).y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 50:
//#line 236 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
{ 
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Asignaci�n"); 
                stackOfLines.pop();
				if(((SyntacticTree) val_peek(0)).getTipo() == "LONG" && ((SyntacticTree) val_peek(2)).getTipo().equals(ElementoTS.INT))
=======
//#line 251 "gramatica (12).y"
=======
//#line 250 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 250 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 251 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 250 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 251 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 253 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 50:
//#line 256 "gramatica.y"
{
				SyntacticTree nodo = null;
				semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Asignación"); 
				stackOfLines.pop();
<<<<<<< HEAD
				if(((SyntacticTree) val_peek(0)).getTipo() == "LONG" && ((SyntacticTree) val_peek(2)).getTipo().equals(ElementoTS.INT)){
<<<<<<< HEAD
>>>>>>> 45299ea... visualización de árbol sintáctico
					msgStack.addMsg("ERROR: incompatibilidad de tipos");
=======
=======
				if(((SyntacticTree) val_peek(0)).getType() == "LONG" && ((SyntacticTree) val_peek(2)).getType().equals(ElementoTS.INT)){
>>>>>>> fde7cdb... varios
					msgStack.addMsg("Error: incompatibilidad de tipos en línea: " + lex.getNewLineCounter());
>>>>>>> 0fcca1b... varios
					hasError = true;
					nodo = new SyntacticTreeCommon("ERROR", null, null);
				}else{
					if(((SyntacticTree) val_peek(2)).getType() == "LONG" && ((SyntacticTree) val_peek(0)).getType().equals(ElementoTS.INT)){
						SyntacticTree nodoConv = new SyntacticTreeConver("itol",(SyntacticTree)val_peek(0));
						nodo = new SyntacticTreeCommon(":=",(SyntacticTree)val_peek(2),nodoConv);
					}else
						nodo = new SyntacticTreeCommon(":=",(SyntacticTree)val_peek(2),(SyntacticTree)val_peek(0));
					nodo.setType( ((SyntacticTree) val_peek(2)).getType());
				}
				yyval = nodo;
			}
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 49:
//#line 148 "gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 50:
<<<<<<< HEAD
//#line 129 "gramatica_2.y"
=======
case 48:
//#line 118 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 49:
//#line 121 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 151 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
=======
case 51:
//#line 276 "gramatica.y"
{ 
						SyntacticTree nodo = null;
						stackOfLines.push(lex.getNewLineCounter()); 
						if(!symbolTable.get(val_peek(0).sval).isDeclared()){
							msgStack.addMsg("Error: Variable no declarada en línea: " + lex.getNewLineCounter());
							hasError = true;
							nodo = new SyntacticTreeLeaf("ERROR");
						}else {
							nodo = new SyntacticTreeLeaf("_" + val_peek(0).sval);
							nodo.setType(symbolTable.get(val_peek(0).sval).getVariableType());
						}
						yyval = nodo;
					}
break;
>>>>>>> fcadff2... coment
case 52:
//#line 289 "gramatica.y"
{ 
				   		SyntacticTree nodo = null;
						stackOfLines.push(lex.getNewLineCounter());
						if(!symbolTable.get(val_peek(3).sval).isDeclared()) {
							msgStack.addMsg("Error: Variable no declarada en línea: " + lex.getNewLineCounter());
							hasError = true;
							nodo  = new SyntacticTreeLeaf("ERROR");
						}else{
							long pos = Long.valueOf(val_peek(1).sval);
							if((long)symbolTable.get(val_peek(3).sval).getCSize() <= pos){
								msgStack.addMsg("Error: Posición de colección superada en línea: " + lex.getNewLineCounter());
								hasError = true;
								nodo  = new SyntacticTreeLeaf("ERROR");
							}else{
								nodo = new SyntacticTreeLeaf("_" + val_peek(3).sval + "[" + val_peek(1).sval + "]");
								nodo.setType(symbolTable.get(val_peek(3).sval).getVariableType());
								((SyntacticTreeLeaf)nodo).setLeftSideAssignment();
							}
						}
						yyval = nodo;
					}
break;
case 53:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 164 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 178 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 180 "gramatica.y"
>>>>>>> f75def1... comentario
{ 
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Asignación"); 
                stackOfLines.pop();
            }
=======
//#line 258 "gramatica.y"
=======
//#line 271 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 272 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 276 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 279 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
{
					SintacticTree nodo;
					if(((SintacticTree) val_peek(2)).getTipo() != ((SintacticTree) val_peek(0)).getTipo())
							{
							if(((SintacticTree) val_peek(2)).getTipo() == "INT")
								nodo = new SintacticTreeCommon("+",new SintacticTreeConver("itol",(SintacticTree)val_peek(2)),(SintacticTree)val_peek(0));
							else
								nodo = new SintacticTreeCommon("+",(SintacticTree)val_peek(2),new SintacticTreeConver("itol",(SintacticTree)val_peek(0)));
							nodo.setTipo("LONG");
							}
						else
						{
							nodo = new SintacticTreeCommon("+",(SintacticTree)val_peek(2),(SintacticTree)val_peek(0));
							nodo.setTipo(((SintacticTree) val_peek(2)).getTipo());
							}
						yyval=nodo;
				}
<<<<<<< HEAD
			yyval=nodo;
	}
>>>>>>> fcadff2... coment
=======
>>>>>>> 4269743... generador de datos
=======
//#line 275 "gramatica (12).y"
{ 
=======
//#line 299 "gramatica (12).y"
=======
//#line 302 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 302 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 303 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 302 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 304 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 306 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 310 "gramatica.y"
>>>>>>> fde75a9... comentario
{
						SyntacticTree nodo = null;
>>>>>>> 45299ea... visualización de árbol sintáctico
						stackOfLines.push(lex.getNewLineCounter());
						if(!symbolTable.get(val_peek(3).sval).isDeclared() || !symbolTable.get(val_peek(1).sval).isDeclared()) {
							msgStack.addMsg("Error: Una o ambas variables no fueron declaradas en línea: " + lex.getNewLineCounter());
							hasError = true;
							nodo  = new SyntacticTreeLeaf("ERROR");
						}else {
							if (symbolTable.get(val_peek(1).sval).getIdentifierClass().equals(ElementoTS.VAR)) {
								nodo  = new SyntacticTreeLeaf("_" + val_peek(3).sval + "[" + "_" + val_peek(1).sval + "]");
								nodo.setType(symbolTable.get(val_peek(3).sval).getVariableType());
								((SyntacticTreeLeaf)nodo).setLeftSideAssignment();
								
							} else{
								msgStack.addMsg("Error: El subíndice es distinto a una variable o constante en línea: " + lex.getNewLineCounter());
								hasError = true;
								nodo  = new SyntacticTreeLeaf("ERROR");
							}
						}
						yyval = nodo;
					}
>>>>>>> cf97fd0... Arreglos en ventana
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 51:
//#line 157 "gramatica.y"
=======
case 54:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 170 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 184 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 186 "gramatica.y"
>>>>>>> f75def1... comentario
{ 
stackOfLines.push(lex.getNewLineCounter()); 
if(symbolTable.get(val_peek(0).sval).getDeclaracion() == false)
		        		msgStack.addMsg("ERROR: Variable no declarada.");
}
=======
//#line 262 "gramatica.y"
=======
//#line 289 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 290 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 294 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 297 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
//#line 290 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 318 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 323 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 323 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 324 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 323 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 327 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 329 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 333 "gramatica.y"
>>>>>>> fde75a9... comentario
{
					SyntacticTree nodo = null;
					if(((SyntacticTree) val_peek(2)).getType() != ((SyntacticTree) val_peek(0)).getType()) {
						if(((SyntacticTree) val_peek(2)).getType().equals(ElementoTS.INT))
							nodo = new SyntacticTreeCommon("+",new SyntacticTreeConver("itol",(SyntacticTree)val_peek(2)), (SyntacticTree)val_peek(0));
						else
							nodo = new SyntacticTreeCommon("+",(SyntacticTree)val_peek(2),new SyntacticTreeConver("itol", (SyntacticTree)val_peek(0)));
						nodo.setType(ElementoTS.LONG);
					} else {
						nodo = new SyntacticTreeCommon("+",(SyntacticTree)val_peek(2),(SyntacticTree)val_peek(0));
						nodo.setType(((SyntacticTree) val_peek(2)).getType());
					}
					yyval = nodo;
				}
<<<<<<< HEAD
			yyval=nodo;
	}
>>>>>>> fcadff2... coment
=======
>>>>>>> 4269743... generador de datos
break;
case 55:
//#line 347 "gramatica.y"
{
					SyntacticTree nodo = null;
					if(((SyntacticTree) val_peek(2)).getType() != ((SyntacticTree) val_peek(0)).getType()) {
						if(((SyntacticTree) val_peek(2)).getType().equals(ElementoTS.INT))
							nodo = new SyntacticTreeCommon("-",new SyntacticTreeConver("itol",(SyntacticTree)val_peek(2)),(SyntacticTree)val_peek(0));
						else
							nodo = new SyntacticTreeCommon("-",(SyntacticTree)val_peek(2),new SyntacticTreeConver("itol",(SyntacticTree)val_peek(0)));
						nodo.setType(ElementoTS.LONG);
					} else {
						nodo = new SyntacticTreeCommon("-",(SyntacticTree)val_peek(2),(SyntacticTree)val_peek(0));
						nodo.setType(((SyntacticTree) val_peek(2)).getType());
					}
					yyval = nodo;
				}
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 59:
<<<<<<< HEAD
//#line 149 "gramatica_2.y"
=======
case 50:
//#line 127 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 51:
//#line 128 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 58:
//#line 141 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 179 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
case 62:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 192 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 206 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 211 "gramatica.y"
>>>>>>> f75def1... comentario
=======
case 56:
//#line 361 "gramatica.y"
{ yyval =val_peek(0); }
break;
case 57:
//#line 364 "gramatica.y"
{
			SyntacticTree nodo = null;
			if(((SyntacticTree) val_peek(2)).getType() != ((SyntacticTree) val_peek(0)).getType()) {
				if(((SyntacticTree) val_peek(2)).getType().equals(ElementoTS.INT))
					nodo = new SyntacticTreeCommon("*",new SyntacticTreeConver("itol",(SyntacticTree)val_peek(2)),(SyntacticTree)val_peek(0));
				else
					nodo = new SyntacticTreeCommon("*",(SyntacticTree)val_peek(2),new SyntacticTreeConver("itol",(SyntacticTree)val_peek(0)));
			} else {
				nodo = new SyntacticTreeCommon("*",(SyntacticTree)val_peek(2),(SyntacticTree)val_peek(0));
			}
			nodo.setType(ElementoTS.LONG);
			yyval = nodo;
		}
break;
case 58:
//#line 377 "gramatica.y"
{
			SyntacticTree nodo = null;
			if(((SyntacticTree) val_peek(2)).getType() != ((SyntacticTree) val_peek(0)).getType()) {
				if(((SyntacticTree) val_peek(2)).getType().equals(ElementoTS.INT))
					nodo = new SyntacticTreeCommon("/", new SyntacticTreeConver("itol",(SyntacticTree)val_peek(2)),(SyntacticTree)val_peek(0));
				else
					nodo = new SyntacticTreeCommon("/", (SyntacticTree)val_peek(2),new SyntacticTreeConver("itol",(SyntacticTree)val_peek(0)));
				nodo.setType(ElementoTS.LONG);
			}else{
				nodo = new SyntacticTreeCommon("/",(SyntacticTree)val_peek(2),(SyntacticTree)val_peek(0));
				nodo.setType(((SyntacticTree)val_peek(2)).getType());
			}
			yyval = nodo;
		}
break;
case 59:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 278 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 347 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 348 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 352 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 355 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
//#line 349 "gramatica (12).y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 352 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 376 "gramatica (12).y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 379 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 381 "gramatica (12).y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 384 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 381 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 384 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 382 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 385 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 381 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 384 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 385 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 388 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 387 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 390 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 391 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 60:
//#line 394 "gramatica.y"
>>>>>>> fde75a9... comentario
{
				SyntacticTree nodo = null;
				if(!symbolTable.get(val_peek(0).sval).isDeclared()){
					msgStack.addMsg("Error: Variable no declarada en línea: " + lex.getNewLineCounter());
					hasError = true;
					nodo = new SyntacticTreeLeaf("_");
				}else{
					nodo = new SyntacticTreeLeaf("_" + val_peek(0).sval);
					nodo.setType(symbolTable.get(val_peek(0).sval).getVariableType());
				}
				yyval = nodo;
			}
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 60:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 157 "gramatica_2.y"
=======
case 59:
//#line 149 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 183 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
case 63:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 196 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 210 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 215 "gramatica.y"
>>>>>>> f75def1... comentario
=======
case 60:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 287 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 356 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 357 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 361 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 364 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
case 61:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 361 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 390 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 396 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 396 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 397 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 396 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 400 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 402 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 406 "gramatica.y"
>>>>>>> fde75a9... comentario
{
				String lexeme = val_peek(0).sval;
				ElementoTS tts = symbolTable.get(lexeme);
				if(lexeme == "32768"){
					tts.setVariableType(ElementoTS.LONG);
				}
				if (lexeme == "2147483648"){
					String lexnuevo = "2147483647";
					val_peek(0).sval = lexnuevo;
					symbolTable.get(lexeme).decreaseVariableRepetitions();
					if(symbolTable.containsKey(lexnuevo)){
						symbolTable.get(lexnuevo).increaseVariableRepetitions();
					}else {
						ElementoTS ttsnuevo = new ElementoTS( ElementoTS.CONST, tts.getValue(), tts.getVariableType());
						symbolTable.put(lexnuevo,ttsnuevo);
					}
				lexeme = lexnuevo;
					msgStack.addMsg("Warning: Constante positiva fuera de rango.");
				}
				SyntacticTree nodo = new SyntacticTreeLeaf(lexeme);
				nodo.setType(symbolTable.get(lexeme).getVariableType());
				yyval = nodo;
	        }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 62:
<<<<<<< HEAD
//#line 177 "gramatica_2.y"
=======
case 61:
<<<<<<< HEAD
//#line 169 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
case 61:
<<<<<<< HEAD
//#line 202 "gramatica.y"
=======
case 64:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 215 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 229 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 234 "gramatica.y"
>>>>>>> f75def1... comentario
=======
case 61:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 310 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 379 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 380 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 384 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 387 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
case 62:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 384 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 413 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 419 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 419 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 420 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 419 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 423 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 425 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 429 "gramatica.y"
>>>>>>> fde75a9... comentario
{
					SyntacticTree nodo = null;
					if(!symbolTable.get(val_peek(3).sval).isDeclared()){
						msgStack.addMsg("Error: Variable no declarada en línea: " + lex.getNewLineCounter());
						hasError = true;
						nodo = new SyntacticTreeLeaf("ERROR");
					}else{
						long pos = Integer.valueOf(val_peek(1).sval);
						if(symbolTable.get(val_peek(3).sval).getCSize() <= pos) {
							msgStack.addMsg("Error: Posicion de coleccion superada en línea: " + lex.getNewLineCounter());
							hasError = true;
							nodo = new SyntacticTreeLeaf("ERROR");
						}
						nodo = new SyntacticTreeLeaf("_" + val_peek(3).sval + "[" + val_peek(1).sval + "]");
						nodo.setType(symbolTable.get(val_peek(3).sval).getVariableType());
					}
					yyval = nodo;
				}
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 62:
//#line 206 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
case 65:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 219 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 233 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 242 "gramatica.y"
>>>>>>> f75def1... comentario
=======
case 62:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 322 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 391 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 392 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 396 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 399 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
case 63:
//#line 447 "gramatica.y"
{
					SyntacticTree nodo = null;
					if(!symbolTable.get(val_peek(3).sval).isDeclared() || !symbolTable.get(val_peek(1).sval).isDeclared()){
						msgStack.addMsg("Error: Una o ambas variables no fueron declaradas en línea: " + lex.getNewLineCounter());
						hasError = true;
						nodo = new SyntacticTreeLeaf("ERROR");
					}else{
						if (symbolTable.get(val_peek(1).sval).getIdentifierClass().equals(ElementoTS.VAR)) {
							nodo = new SyntacticTreeLeaf("_" + val_peek(3).sval + "[" + "_" + val_peek(1).sval + "]");
							nodo.setType(symbolTable.get(val_peek(3).sval).getVariableType());
						} else{
							msgStack.addMsg("Error: El subíndice es distinto a una variable o constante en línea: " + lex.getNewLineCounter());
							hasError = true;
							nodo = new SyntacticTreeLeaf("ERROR");
						}
					}
					yyval = nodo;
				}
break;
case 64:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 408 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 445 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 455 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 455 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 456 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 455 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 459 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 461 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 465 "gramatica.y"
>>>>>>> fde75a9... comentario
{
					String lexeme = val_peek(0).sval;
					ElementoTS tts = symbolTable.get(lexeme);
					String lexnuevo = "-" + lexeme;
					val_peek(0).sval = lexnuevo;
					if(!symbolTable.containsKey(lexnuevo)){
						ElementoTS ttsneg = new ElementoTS(ElementoTS.CONST, tts.getValue(), tts.getVariableType());
						symbolTable.put(lexnuevo,ttsneg);
					}else
						symbolTable.get(lexnuevo).increaseVariableRepetitions();
					if (tts.getVariableRepetitions() == 0)
						symbolTable.remove(lexeme);
					symbolTable.get(lexeme).decreaseVariableRepetitions();
					SyntacticTree nodo = new SyntacticTreeLeaf(lexnuevo);
					nodo.setType(symbolTable.get(lexnuevo).getVariableType());
					yyval = nodo;
	        }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 63:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 195 "gramatica_2.y"
=======
case 62:
//#line 186 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
//#line 224 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
case 66:
<<<<<<< HEAD
<<<<<<< HEAD
//#line 237 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 251 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 260 "gramatica.y"
>>>>>>> f75def1... comentario
=======
case 63:
<<<<<<< HEAD
//#line 343 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 412 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 413 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 417 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 420 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
case 65:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 429 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 464 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 474 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 474 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 475 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 474 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 478 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 480 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 484 "gramatica.y"
>>>>>>> fde75a9... comentario
{
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Sentencia print");
                stackOfLines.pop();
				yyval = new SyntacticTreeUnary("PRINT", new SyntacticTreeLeaf("_@cad" + symbolTable.get(val_peek(1).sval).getId()));
            }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 64:
<<<<<<< HEAD
//#line 230 "gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 65:
//#line 233 "gramatica.y"
{ msgStack.addMsg("Warning: Declaraci�n de bloque sin contenido"); }
break;
case 66:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 234 "gramatica.y"
{ msgStack.addMsg("Error: Comparaci�n incompleta"); }
break;
=======
>>>>>>> 733ee4b... Agregado size y TS
case 67:
//#line 266 "gramatica.y"
=======
case 64:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 350 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 419 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 420 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 424 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 427 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
case 66:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 436 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 471 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 481 "gramatica (12).y"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 481 "gramatica.y"
>>>>>>> 1ce0652... comentario
=======
//#line 482 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 481 "gramatica.y"
>>>>>>> d209296... comentario
=======
//#line 485 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 487 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 491 "gramatica.y"
>>>>>>> fde75a9... comentario
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 67:
//#line 494 "gramatica.y"
{ msgStack.addMsg("Warning: Declaración de bloque sin contenido"); }
break;
case 68:
//#line 495 "gramatica.y"
{ msgStack.addMsg("Error: Comparación incompleta en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 69:
//#line 496 "gramatica.y"
{ msgStack.addMsg("Error: Declaración de IF sin condición en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 70:
//#line 497 "gramatica.y"
{ msgStack.addMsg("Error: Declaracion de foreach sin índice y sin colección en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 71:
//#line 498 "gramatica.y"
{ msgStack.addMsg("Error: Declaracion de foreach sin colección en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 72:
//#line 499 "gramatica.y"
{ msgStack.addMsg("Error: Declaracion de foreach sin índice en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 73:
//#line 500 "gramatica.y"
{ msgStack.addMsg("Error: Nombre y valor de posicion de colección faltante en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 74:
//#line 501 "gramatica.y"
{ msgStack.addMsg("Error: Nombre de colección faltante en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 75:
//#line 502 "gramatica.y"
{ msgStack.addMsg("Error: Corchete de apertura faltante en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 76:
//#line 503 "gramatica.y"
{ msgStack.addMsg("Error: Corchete de cierre faltante en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 77:
//#line 504 "gramatica.y"
{ msgStack.addMsg("Error: Constante a la izquierda de una asignación en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 78:
//#line 505 "gramatica.y"
{ msgStack.addMsg("Error: Sentencia inválida en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
<<<<<<< HEAD
case 77:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 278 "gramatica.y"
{ msgStack.addMsg("Error: Corchete de cierre faltante"); }
break;
case 78:
//#line 279 "gramatica.y"
{ msgStack.addMsg("Error: Constante a la izquierda de una asignación"); }
break;
case 79:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 218 "gramatica_2.y"
=======
case 63:
//#line 192 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 64:
//#line 195 "gramatica_2.y"
{ msgStack.addMsg("Warning: Declaraci�n de bloque sin contenido"); }
break;
case 65:
//#line 196 "gramatica_2.y"
{ msgStack.addMsg("Error: Comparaci�n incompleta"); }
break;
case 66:
//#line 197 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaraci�n de IF sin condici�n"); }
break;
case 67:
//#line 198 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin �ndice y sin colecci�n"); }
break;
case 68:
//#line 199 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin colecci�n"); }
break;
case 69:
//#line 200 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin �ndice"); }
break;
case 70:
//#line 201 "gramatica_2.y"
{ msgStack.addMsg("Error: Nombre y valor de posicion de coleccion faltante"); }
break;
case 71:
//#line 202 "gramatica_2.y"
{ msgStack.addMsg("Error: Nombre de coleccion faltante"); }
break;
case 72:
//#line 203 "gramatica_2.y"
{ msgStack.addMsg("Error: Corchete de apertura faltante"); }
break;
case 73:
//#line 204 "gramatica_2.y"
{ msgStack.addMsg("Error: Corchete de cierre faltante"); }
break;
case 74:
//#line 205 "gramatica_2.y"
{ msgStack.addMsg("Error: Constante a la izquierda de una asignaci�n"); }
break;
case 75:
//#line 206 "gramatica_2.y"
{ msgStack.addMsg("Error: Sentencia inv�lida"); }
break;
case 76:
//#line 207 "gramatica_2.y"
{ msgStack.addMsg("Error: Constantes declaradas con tipo"); }
break;
case 77:
//#line 208 "gramatica_2.y"
{ msgStack.addMsg("Error: Falta de separador entre valores constantes"); }
break;
case 78:
//#line 209 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
{ msgStack.addMsg("Error: Sentencia de asignaci�n inv�lida"); }
break;
//#line 761 "Parser.java"
=======
//#line 247 "gramatica.y"
{ msgStack.addMsg("Error: Sentencia de asignaci�n inv�lida"); }
break;
//#line 790 "Parser.java"
>>>>>>> f713e33... comentario generico
=======
//#line 257 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Sentencia invᬩda"); }
=======
//#line 271 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
=======
//#line 280 "gramatica.y"
>>>>>>> f75def1... comentario
{ msgStack.addMsg("Error: Sentencia inválida"); }
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
break;
case 80:
//#line 281 "gramatica.y"
=======
//#line 365 "gramatica.y"
>>>>>>> fcadff2... coment
=======
//#line 434 "gramatica.y"
>>>>>>> 1d41d94... Up
=======
//#line 435 "gramatica.y"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 439 "gramatica.y"
>>>>>>> 67dbe83... comentario
=======
//#line 442 "gramatica (12).y"
>>>>>>> 4269743... generador de datos
=======
case 79:
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 451 "gramatica (12).y"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 486 "gramatica (12).y"
>>>>>>> 45299ea... visualización de árbol sintáctico
{ msgStack.addMsg("Error: Constantes declaradas con tipo"); }
=======
//#line 496 "gramatica (12).y"
=======
//#line 496 "gramatica.y"
<<<<<<< HEAD
>>>>>>> 1ce0652... comentario
=======
//#line 497 "gramatica.y"
>>>>>>> 67de6b7... _
=======
//#line 496 "gramatica.y"
>>>>>>> d209296... comentario
{ msgStack.addMsg("Error: Constantes declaradas con tipo"); hasError = true; }
>>>>>>> 1375c5c... arreglos varios
=======
=======
//#line 500 "gramatica.y"
>>>>>>> fde7cdb... varios
=======
//#line 502 "gramatica.y"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 506 "gramatica.y"
>>>>>>> fde75a9... comentario
{ msgStack.addMsg("Error: Constantes declaradas con tipo en línea: " + lex.getNewLineCounter()); hasError = true; }
>>>>>>> 0fcca1b... varios
break;
case 80:
//#line 507 "gramatica.y"
{ msgStack.addMsg("Error: Falta de separador entre valores constantes en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
case 81:
//#line 508 "gramatica.y"
{ msgStack.addMsg("Error: Sentencia de asignación inválida en línea: " + lex.getNewLineCounter()); hasError = true; }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//#line 820 "Parser.java"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 852 "Parser.java"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
=======
//#line 863 "Parser.java"
>>>>>>> f75def1... comentario
=======
//#line 1064 "Parser.java"
>>>>>>> fcadff2... coment
=======
//#line 1063 "Parser.java"
>>>>>>> 0507715... Update Parser.java
=======
//#line 1133 "Parser.java"
>>>>>>> 1d41d94... Up
=======
//#line 1134 "Parser.java"
>>>>>>> 1daf844... Update Parser.java
=======
//#line 1133 "Parser.java"
>>>>>>> 87ae9f5... comentario
=======
//#line 1137 "Parser.java"
>>>>>>> 67dbe83... comentario
=======
//#line 1142 "Parser.java"
>>>>>>> 4269743... generador de datos
=======
//#line 1164 "Parser.java"
>>>>>>> cf97fd0... Arreglos en ventana
=======
//#line 1203 "Parser.java"
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
//#line 1217 "Parser.java"
>>>>>>> 1375c5c... arreglos varios
=======
//#line 1218 "Parser.java"
>>>>>>> 67de6b7... _
=======
//#line 1217 "Parser.java"
>>>>>>> d209296... comentario
=======
//#line 1221 "Parser.java"
>>>>>>> fde7cdb... varios
=======
//#line 1223 "Parser.java"
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
//#line 1227 "Parser.java"
>>>>>>> fde75a9... comentario
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
