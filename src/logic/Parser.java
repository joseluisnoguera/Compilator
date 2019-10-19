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






//#line 2 "gramatica_2.y"
package logic;
import java.util.Hashtable;
import logic.Lexicon;
import utils.ElementoTS;
import utils.ParserVal;
import utils.MsgStack;
import utils.Token;
import java.util.Stack;
//#line 26 "Parser.java"




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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
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
};
final static short yydefred[] = {                         0,
    0,   27,   11,   12,    0,    1,    0,    4,    5,    0,
   10,   21,    0,    9,    2,    6,    0,    0,    0,    0,
    0,    0,   38,   49,   64,   42,   43,   44,   45,    0,
   40,   41,    0,   22,    0,   25,    0,    0,   29,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    7,    0,
    8,    0,   33,   76,    0,    0,   75,    0,   71,   77,
    0,   23,   24,   65,   28,   30,   31,   32,    0,    0,
   60,   79,    0,   66,    0,    0,    0,    0,   17,   18,
    0,   14,    0,   73,    0,   72,   26,    0,    0,    0,
    0,   62,    0,    0,   68,   58,    0,    0,    0,    0,
   15,    0,    0,   74,   52,    0,    0,    0,    0,    0,
   69,   70,    0,    0,    0,    0,   63,   19,   20,    0,
   78,   67,    0,    0,   39,   61,   47,   48,   46,    0,
    0,   56,   57,   16,    0,   34,    0,    0,   35,
};
final static short yydgoto[] = {                          5,
    6,    7,    8,    9,   10,   18,   19,   33,   81,   34,
   35,   36,   37,   38,   39,   40,   41,   42,   43,   89,
  124,  138,   96,   44,   45,  129,   46,   97,   98,   47,
};
final static short yysindex[] = {                      -198,
  -12,    0,    0,    0,    0,    0, -198,    0,    0, -236,
    0,    0,   13,    0,    0,    0,  -19,  -11,  -10,    2,
  -81, -195,    0,    0,    0,    0,    0,    0,    0,  -80,
    0,    0, -184,    0,  -23,    0,   -5,   19,    0,   26,
   31,   33,   53,  -42, -215, -175,   55,  -84,    0, -163,
    0, -162,    0,    0,    5, -161,    0,    8,    0,    0,
   46,    0,    0,    0,    0,    0,    0,    0,  -36,   16,
    0,    0, -154,    0, -157, -203,  -38, -160,    0,    0,
  -27,    0,   20,    0,  -85,    0,    0, -144,   74,   17,
 -141,    0, -197, -144,    0,    0,   22,   -7,   79,  -83,
    0,  -84,   28,    0,    0, -143,   13,  -38,   30,   13,
    0,    0,  -38,  -38,  -38,  -38,    0,    0,    0,  -24,
    0,    0,   13, -194,    0,    0,    0,    0,    0,   -7,
   -7,    0,    0,    0,   13,    0,   13, -139,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  124,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   -8,    0,    0,    0,
 -142,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -41,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   69,  -17,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -183,    0,    0,    0,    0,    0,    0,  -16,
  -13,    0,    0,    0,    0,    0, -136,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  123,  125,    0,    0,    0,   38,   29,   15,
  -82,  -21,   24,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -28,   43,    0,    0,    0,    0,  -30,    0,
};
final static int YYTABLESIZE=290;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         59,
   59,   59,   73,   59,   88,   59,   73,  105,   73,   56,
   80,  119,   59,   63,   12,   74,  100,   59,   59,  100,
   59,   12,   17,   13,  123,   55,   54,   55,   54,   53,
   13,   53,   50,   52,  115,   13,   31,   11,   32,  116,
   90,   55,   54,   75,   11,   53,   14,   49,   51,   62,
   13,   76,  137,    2,   31,   94,   32,    1,    2,    2,
   53,  110,    3,    4,  114,  101,  113,   30,  134,  135,
  136,   48,   31,   57,   32,   60,   31,   65,   32,  125,
   36,   36,  130,  131,   66,   30,  132,  133,  128,   67,
   95,   68,   69,   77,   78,   82,   83,   84,   85,   13,
   86,   63,  106,   30,   87,   92,   91,  111,  112,   93,
  102,   13,    2,   99,  107,   63,   13,   13,  109,  117,
  121,  122,  126,    3,  127,  139,   51,   50,   37,   15,
  120,   16,  108,    0,    0,    0,    0,   62,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   62,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  103,   79,  118,   54,   55,   58,
    0,    0,    0,  104,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   70,   71,    0,    0,
   70,   71,   70,   71,    0,    0,   72,    0,   59,   59,
   59,   59,   20,    2,   61,   21,   22,    3,    4,   23,
    0,    0,   24,    0,   25,    0,   26,   27,   28,   29,
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
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
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
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  260,  260,  260,  259,  260,  260,
   -1,   -1,   -1,  269,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
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
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
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
"sent_decl : error ';'",
"comienzo_decl : tipo",
"tipo : INT",
"tipo : LONG",
"lista_vars : ID",
"lista_vars : lista_vars ',' ID",
"lista_colecciones : ID '[' lista_valores_inic ']'",
"lista_colecciones : lista_colecciones ',' ID '[' lista_valores_inic ']'",
"lista_valores_inic : CTE",
"lista_valores_inic : '_'",
"lista_valores_inic : lista_valores_inic ',' CTE",
"lista_valores_inic : lista_valores_inic ',' '_'",
<<<<<<< HEAD
"bloque_ejec : bloque_unico",
"conjunto_bloques : bloque_unico",
"conjunto_bloques : conjunto_bloques bloque_unico",
"conjunto_bloques : conjunto_bloques sent_ejec",
"conjunto_bloques : sent_ejec",
"bloque_unico : comienzo_bloque conjunto_bloques END ';'",
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
"sent_cond : comienzo_if '(' cond ')' bloque_then END_IF",
"sent_cond : comienzo_if '(' cond ')' bloque_then ELSE bloque_else END_IF",
"bloque_then : conjunto_bloques",
"bloque_else : conjunto_bloques",
=======
"sent_cond : comienzo_if '(' cond ')' bloque_unico END_IF",
"sent_cond : comienzo_if '(' cond ')' bloque_unico ELSE bloque_unico END_IF",
>>>>>>> 87f0bc1... cambios pre-entrega
"comienzo_if : IF",
"cond : factor comparador factor",
"comparador : '<'",
"comparador : '>'",
"comparador : LET",
"comparador : GET",
"comparador : EQ",
"comparador : DIF",
<<<<<<< HEAD
"sent_ctrl : comienzo_foreach ID IN ID bloque_foreach",
"bloque_foreach : bloque_unico",
"bloque_foreach : sent_ejec",
=======
"sent_ctrl : comienzo_foreach ID IN ID bloque_unico",
>>>>>>> 87f0bc1... cambios pre-entrega
"comienzo_foreach : FOREACH",
"sent_asig : inic_sent_asig ASSIGN expression",
"inic_sent_asig : ID",
"inic_sent_asig : ID '[' CTE ']'",
"expression : expression '+' term",
"expression : expression '-' term",
"expression : term",
"term : term '*' factor",
"term : term '/' factor",
"term : factor",
"factor : ID",
"factor : CTE",
"factor : ID '[' CTE ']'",
"factor : '-' CTE",
"sent_print : comienzo_print '(' CAD ')'",
"comienzo_print : PRINT",
"error_p : comienzo_bloque END",
"error_p : comparador factor",
"error_p : comienzo_if '(' ')' bloque_unico END_IF",
"error_p : comienzo_foreach IN bloque_unico",
"error_p : comienzo_foreach ID IN bloque_unico",
"error_p : comienzo_foreach IN ID bloque_unico",
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
//#line 222 "gramatica_2.y"
=======
//#line 213 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega

private Lexicon lex;
private Hashtable<String,ElementoTS> symbolTable;
private MsgStack msgStack;
private MsgStack semanticStructStack;
private boolean declaracion = true;
Stack<Integer> stackOfLines = new Stack<Integer>();
private int numLineaDecl;

public Parser(Lexicon lex, Hashtable<String, ElementoTS> symbolTable, MsgStack msgStack, MsgStack semanticStructStack){
	this.lex = lex;
	this.symbolTable = symbolTable;
	this.msgStack = msgStack;
	this.semanticStructStack = semanticStructStack;
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
//#line 396 "Parser.java"
=======
//#line 410 "Parser.java"
>>>>>>> 87f0bc1... cambios pre-entrega
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
case 7:
//#line 31 "gramatica_2.y"
{ semanticStructStack.addMsg("Linea "+ numLineaDecl +": Sentencia declarativa"); }
break;
case 8:
//#line 32 "gramatica_2.y"
{ semanticStructStack.addMsg("Linea "+ numLineaDecl +": Sentencia declarativa"); }
break;
case 9:
//#line 33 "gramatica_2.y"
{ msgStack.addMsg("Error genérico en el bloque declarativo no contemplado cerca de línea " + lex.getNewLineCounter()); }
break;
case 10:
//#line 36 "gramatica_2.y"
{ numLineaDecl = lex.getNewLineCounter(); }
break;
<<<<<<< HEAD
case 22:
//#line 62 "gramatica_2.y"
{ declaracion = false; }
=======
case 21:
//#line 59 "gramatica_2.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque principal de ejecución"); }
break;
case 22:
//#line 62 "gramatica_2.y"
{ declaracion = false; semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecución"); }
>>>>>>> 87f0bc1... cambios pre-entrega
break;
case 23:
//#line 63 "gramatica_2.y"
{ declaracion = false; }
break;
<<<<<<< HEAD
case 26:
//#line 68 "gramatica_2.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecución"); }
break;
case 27:
//#line 71 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); /*Para debugging*/}
break;
case 33:
//#line 81 "gramatica_2.y"
{ msgStack.addMsg("Error genérico no contemplado cerca de línea " + lex.getNewLineCounter()); }
break;
case 34:
//#line 84 "gramatica_2.y"
=======
case 28:
//#line 72 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); /*Para debugging*/}
break;
case 36:
//#line 84 "gramatica_2.y"
{ msgStack.addMsg("Error genérico no contemplado cerca de línea " + lex.getNewLineCounter()); }
break;
case 37:
//#line 87 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
{
                    semanticStructStack.addMsg("Línea " + stackOfLines.peek().intValue() + ": Sentencia IF ");
                    stackOfLines.pop();
                }
break;
<<<<<<< HEAD
case 35:
//#line 88 "gramatica_2.y"
=======
case 38:
//#line 91 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
{
                    semanticStructStack.addMsg("Línea " + stackOfLines.peek().intValue() + ": Sentencia IF ");
                    stackOfLines.pop();
                }
break;
<<<<<<< HEAD
case 38:
//#line 102 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 46:
//#line 116 "gramatica_2.y"
=======
case 39:
//#line 98 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 47:
//#line 112 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
{ 
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Sentencia foreach");
                stackOfLines.pop();
            }
break;
<<<<<<< HEAD
case 49:
//#line 126 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 50:
//#line 129 "gramatica_2.y"
=======
case 48:
//#line 118 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 49:
//#line 121 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
{ 
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Asignación"); 
                stackOfLines.pop();
            }
break;
<<<<<<< HEAD
case 51:
//#line 135 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 52:
//#line 136 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 59:
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
{
		        if(declaracion){
				symbolTable.get(val_peek(0).sval).setDeclaracion();
			}else{
				if(symbolTable.get(val_peek(0).sval).getDeclaracion() == false)
			        	msgStack.addMsg("ERROR: Variable no declarada.");
		        }
	        }
break;
<<<<<<< HEAD
case 60:
//#line 157 "gramatica_2.y"
=======
case 59:
//#line 149 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
{
		        String lexeme = val_peek(0).sval;
		        ElementoTS tts = symbolTable.get(lexeme);
		        if(lexeme == "32768"){
		        	tts.setTipoAtributo("long");
		        }
		        if (lexeme == "2147483648"){
			        String lexnuevo = "2147483647";
			        val_peek(0).sval = lexnuevo;
			        symbolTable.get(lexeme).decreaseCounter();
			        if(symbolTable.containsKey(lexnuevo)){
			    	    symbolTable.get(lexnuevo).increaseCounter();
			        }else {
			    	    ElementoTS ttsnuevo = new ElementoTS(tts.getValue(),tts.getTipoAtributo());
			    	    symbolTable.put(lexnuevo,ttsnuevo);
		    	    }
			        msgStack.addMsg("Warning: Constante positiva fuera de rango.");
		        }
	        }
break;
<<<<<<< HEAD
case 62:
//#line 177 "gramatica_2.y"
=======
case 61:
//#line 169 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
{
		        String lexeme = val_peek(0).sval;
		        ElementoTS tts = symbolTable.get(lexeme);
		        String lexnuevo = "-" + lexeme;
		        val_peek(0).sval = lexnuevo;
		        if(!symbolTable.containsKey(lexnuevo)){
		    	    ElementoTS ttsneg = new ElementoTS(tts.getValue(),tts.getTipoAtributo());
		    	    symbolTable.put(lexnuevo,ttsneg);
		        }else{
		    	    symbolTable.get(lexnuevo).increaseCounter();
		        }
		        if (tts.getCantidad() == 0){
		    	    symbolTable.remove(lexeme);
		        }
				symbolTable.get(lexeme).decreaseCounter();
	        }
break;
<<<<<<< HEAD
case 63:
//#line 195 "gramatica_2.y"
=======
case 62:
//#line 186 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
{
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Sentencia print");
                stackOfLines.pop();
            }
break;
<<<<<<< HEAD
case 64:
//#line 201 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 65:
//#line 204 "gramatica_2.y"
{ msgStack.addMsg("Warning: Declaración de bloque sin contenido"); }
break;
case 66:
//#line 205 "gramatica_2.y"
{ msgStack.addMsg("Error: Comparación incompleta"); }
break;
case 67:
//#line 206 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaración de IF sin condición"); }
break;
case 68:
//#line 207 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin índice y sin colección"); }
break;
case 69:
//#line 208 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin colección"); }
break;
case 70:
//#line 209 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin índice"); }
break;
case 71:
//#line 210 "gramatica_2.y"
{ msgStack.addMsg("Error: Nombre y valor de posicion de coleccion faltante"); }
break;
case 72:
//#line 211 "gramatica_2.y"
{ msgStack.addMsg("Error: Nombre de coleccion faltante"); }
break;
case 73:
//#line 212 "gramatica_2.y"
{ msgStack.addMsg("Error: Corchete de apertura faltante"); }
break;
case 74:
//#line 213 "gramatica_2.y"
{ msgStack.addMsg("Error: Corchete de cierre faltante"); }
break;
case 75:
//#line 214 "gramatica_2.y"
{ msgStack.addMsg("Error: Constante a la izquierda de una asignación"); }
break;
case 76:
//#line 215 "gramatica_2.y"
{ msgStack.addMsg("Error: Sentencia inválida"); }
break;
case 77:
//#line 216 "gramatica_2.y"
{ msgStack.addMsg("Error: Constantes declaradas con tipo"); }
break;
case 78:
//#line 217 "gramatica_2.y"
{ msgStack.addMsg("Error: Falta de separador entre valores constantes"); }
break;
case 79:
//#line 218 "gramatica_2.y"
=======
case 63:
//#line 192 "gramatica_2.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 64:
//#line 195 "gramatica_2.y"
{ msgStack.addMsg("Warning: Declaración de bloque sin contenido"); }
break;
case 65:
//#line 196 "gramatica_2.y"
{ msgStack.addMsg("Error: Comparación incompleta"); }
break;
case 66:
//#line 197 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaración de IF sin condición"); }
break;
case 67:
//#line 198 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin índice y sin colección"); }
break;
case 68:
//#line 199 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin colección"); }
break;
case 69:
//#line 200 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin índice"); }
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
{ msgStack.addMsg("Error: Constante a la izquierda de una asignación"); }
break;
case 75:
//#line 206 "gramatica_2.y"
{ msgStack.addMsg("Error: Sentencia inválida"); }
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
{ msgStack.addMsg("Error: Sentencia de asignación inválida"); }
break;
//#line 761 "Parser.java"
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
