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






//#line 2 
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
    9,    9,    9,    3,   11,   11,   11,   11,   10,   13,
   12,   12,   12,   12,   12,   12,   14,   14,   21,   22,
   19,   20,   24,   24,   24,   24,   24,   24,   15,   26,
   26,   25,   16,   27,   27,   28,   28,   28,   29,   29,
   29,   23,   23,   23,   23,   17,   30,   18,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,
};
final static short yylen[] = {                            2,
    1,    2,    1,    1,    1,    2,    3,    3,    1,    2,
    1,    1,    1,    1,    3,    4,    6,    6,    8,    1,
    1,    3,    3,    1,    1,    2,    2,    1,    4,    1,
    2,    1,    2,    2,    2,    2,    6,    8,    1,    1,
    1,    3,    1,    1,    1,    1,    1,    1,    5,    1,
    1,    1,    3,    1,    4,    3,    3,    1,    3,    3,
    1,    1,    1,    4,    2,    4,    1,    2,    2,    5,
    3,    4,    4,    2,    3,    3,    4,    2,    2,    2,
    5,    2,
>>>>>>> 733ee4b... Agregado size y TS
};
final static short yydefred[] = {                         0,
    0,   30,   12,   13,    0,    1,    0,    4,    5,    0,
   11,   24,    0,   10,    2,    6,    0,    0,    0,    0,
    0,    0,   41,   52,   67,   45,   46,   47,   48,    0,
   43,   44,    0,   25,    0,   28,    0,    0,   32,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    7,    0,
    8,    0,   36,   79,    0,    0,   78,    0,   74,   80,
    0,   26,   27,   68,   31,   33,   34,   35,    0,    0,
   63,   82,    0,   69,    0,    0,    0,    0,   20,    0,
   21,    0,   15,    0,   76,    0,   75,   29,    0,    0,
    0,    0,   65,    0,    0,   71,   61,    0,    0,    0,
    0,    0,   16,    0,    0,   77,   55,    0,    0,    0,
    0,    0,   72,   73,    0,    0,    0,    0,   66,    0,
   22,   23,    0,    0,   81,   70,    0,    0,   42,   64,
   50,   51,   49,    0,    0,   59,   60,   18,    0,   17,
    0,   37,    0,    0,    0,   19,   38,
};
final static short yydgoto[] = {                          5,
    6,    7,    8,    9,   10,   18,   19,   33,   82,   34,
   35,   36,   37,   38,   39,   40,   41,   42,   43,   90,
  128,  145,   97,   44,   45,  133,   46,   98,   99,   47,
};
final static short yysindex[] = {                      -197,
   -1,    0,    0,    0,    0,    0, -197,    0,    0, -221,
    0,    0,   27,    0,    0,    0,  -24,  -16,   -4,   16,
  -78, -196,    0,    0,    0,    0,    0,    0,    0,  -77,
    0,    0, -214,    0,  -21,    0,    9,   22,    0,   25,
   32,   33,   45,  -45, -223, -183,   48,  -86,    0, -166,
    0, -164,    0,    0,    3, -159,    0,   10,    0,    0,
   46,    0,    0,    0,    0,    0,    0,    0,  -34,   13,
    0,    0, -154,    0, -158, -207,  -40, -163,    0,   50,
    0,  -30,    0,   19,    0,  -81,    0,    0, -141,   76,
  -43, -138,    0, -191, -141,    0,    0,   29,   14,   78,
 -137,  -80,    0,  -85,   31,    0,    0, -140,   27,  -40,
   34,   27,    0,    0,  -40,  -40,  -40,  -40,    0,   35,
    0,    0,   68,  -17,    0,    0,   27, -187,    0,    0,
    0,    0,    0,   14,   14,    0,    0,    0, -131,    0,
   27,    0,   39,   27, -132,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  134,    0,    0,    1,
    0,    0,    0,    0,    0,    0,   -2,    0,    0,    0,
 -134,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -39,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   16,  -14,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -185,    0,    0,    0,
    0,    0,    0,  -11,   -8,    0,    0,    0,    0,    0,
    0,    0,    0, -129,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  130,  131,    0,    0,    0,   47,   36,   18,
  -79,  -13,   26,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -20,   51,    0,    0,    0,    0,  -33,    0,
};
final static int YYTABLESIZE=300;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         73,
    9,   62,   62,   62,   73,   62,   89,   62,   81,   81,
   73,  107,   56,  102,  122,   59,   31,   12,   32,   62,
   62,   63,   62,   74,   12,   13,  102,   50,   58,  127,
   58,   57,   13,   57,   56,   75,   56,   17,   31,   52,
   32,   14,   49,   76,   58,   60,   11,   57,   91,    2,
   56,   95,   62,   11,   51,  117,   14,   14,    1,    2,
  118,  144,  103,    3,    4,    2,   48,  112,   31,   30,
   32,  116,   57,  115,   53,  140,  141,  142,   39,   39,
   65,  134,  135,   66,   69,   77,   31,   78,   32,  129,
   67,   68,   83,   96,   84,   85,  136,  137,  132,   30,
   86,   13,   87,   92,   88,   93,  108,  101,   94,  104,
  100,  113,  114,   63,   13,    2,  109,   30,  119,   13,
   13,  111,  120,  125,  126,  139,  130,  138,  143,  131,
   63,  146,  147,    3,   54,   40,   15,   16,    0,  124,
    0,  110,    0,    0,   62,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   62,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   79,   79,    0,    0,    0,  105,  121,
   54,   55,   58,    0,    0,    0,    0,  106,   80,  123,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
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
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  144,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
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
   -1,  261,  262,   -1,  256,  257,  258,  259,  260,  261,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  271,
  272,  273,  256,  257,   -1,  259,  260,  261,  262,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,  271,  272,  273,
>>>>>>> 733ee4b... Agregado size y TS
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

private Lexicon lex;
private Hashtable<String,ElementoTS> symbolTable;
private MsgStack msgStack;
private MsgStack semanticStructStack;
Stack<Integer> stackOfLines = new Stack<Integer>();
private int numLineaDecl;
private int countSize;

public Parser(Lexicon lex, Hashtable<String, ElementoTS> symbolTable, MsgStack msgStack, MsgStack semanticStructStack){
	this.lex = lex;
	this.symbolTable = symbolTable;
	this.msgStack = msgStack;
	this.semanticStructStack = semanticStructStack;
	this.countSize=0;
}

private int yylex(){
	Token token = lex.getNewToken();
	String lexeme = token.getLexeme();
	if (lexeme != null)
		yylval = new ParserVal(lexeme);
	return token.getTokenValue();
}

private void yyerror(String msg){
	msgStack.addMsg("L√≠nea "+ lex.getNewLineCounter() + ": " + msg);
}
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
//#line 31 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ semanticStructStack.addMsg("Linea "+ numLineaDecl +": Sentencia declarativa"); }
break;
case 8:
//#line 32 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ semanticStructStack.addMsg("Linea "+ numLineaDecl +": Sentencia declarativa"); }
break;
case 10:
//#line 34 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error gen√©rico en el bloque declarativo no contemplado cerca de l√≠nea " + lex.getNewLineCounter()); }
break;
case 11:
//#line 37 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ numLineaDecl = lex.getNewLineCounter(); }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 22:
//#line 62 "gramatica_2.y"
{ declaracion = false; }
=======
case 21:
//#line 59 "gramatica_2.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque principal de ejecuciÛn"); }
break;
case 22:
//#line 62 "gramatica_2.y"
{ declaracion = false; semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecuciÛn"); }
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
//#line 44 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 44 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{
		if(!symbolTable.get(val_peek(0).sval).getDeclaracion())
			symbolTable.get(val_peek(0).sval).setDeclaracion(true);
		else
			msgStack.addMsg("Error: variable"+ val_peek(0).sval+" re-declarada en el n√∫mero de l√≠nea " + lex.getNewLineCounter());
		}
break;
case 15:
//#line 50 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{
		if(!symbolTable.get(val_peek(2).sval).getDeclaracion())
			symbolTable.get(val_peek(2).sval).setDeclaracion(true);
		else
			msgStack.addMsg("Error: variable"+ val_peek(2).sval+" re-declarada en el n√∫mero de l√≠nea " + lex.getNewLineCounter());
		}
break;
case 16:
//#line 58 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{
		if(!symbolTable.get(val_peek(3).sval).getDeclaracion())
		{
			symbolTable.get(val_peek(3).sval).setDeclaracion(true);
			symbolTable.get(val_peek(3).sval).setCSize(String.valueOf(countSize)); /*setea tamanio de arreglo de val iniciales*/
			countSize=0;
		}
		else
			msgStack.addMsg("Error: coleccion"+ val_peek(3).sval+"re-declarada en el n√∫mero de l√≠nea " + lex.getNewLineCounter());;
		}
break;
case 17:
//#line 68 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{
		if(!symbolTable.get(val_peek(5).sval).getDeclaracion())
		{
			symbolTable.get(val_peek(5).sval).setDeclaracion(true);
			symbolTable.get(val_peek(5).sval).setCSize(String.valueOf(countSize));/*setea tamanio de arreglo de val iniciales*/
			countSize=0;
		}
		else
			msgStack.addMsg("Error: coleccion"+ val_peek(5).sval+" re-declarada en el n√∫mero de l√≠nea " + lex.getNewLineCounter());
		}
break;
case 18:
//#line 78 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{
		if(!symbolTable.get(val_peek(5).sval).getDeclaracion())
		{
			symbolTable.get(val_peek(5).sval).setDeclaracion(true);
			symbolTable.get(val_peek(5).sval).setCSize(val_peek(1).sval);
		}
		else
			msgStack.addMsg("Error: coleccion"+ val_peek(5).sval+"re-declarada en el n√∫mero de l√≠nea " + lex.getNewLineCounter());;
		}
>>>>>>> f713e33... comentario generico
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 26:
//#line 88 "gramatica.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecuciÛn"); }
=======
case 19:
//#line 87 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{
		if(!symbolTable.get(val_peek(7).sval).getDeclaracion())
		{
			symbolTable.get(val_peek(7).sval).setDeclaracion(true);
			symbolTable.get(val_peek(7).sval).setCSize(val_peek(1).sval);
		}
		else
			msgStack.addMsg("Error: coleccion"+ val_peek(7).sval+" re-declarada en el n√∫mero de l√≠nea " + lex.getNewLineCounter());
		}
break;
case 20:
//#line 98 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{countSize++;}
break;
case 21:
//#line 99 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{countSize++;}
break;
case 22:
//#line 100 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{countSize++;}
break;
case 23:
//#line 101 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{countSize++;}
break;
case 29:
<<<<<<< HEAD
//#line 101 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecuciÛÆ¢©"); }
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 115 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Bloque de ejecuci√≥n"); }
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
break;
case 30:
//#line 118 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); /*Para debugging*/}
break;
case 36:
//#line 126 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error gen√©rico no contemplado cerca de l√≠nea " + lex.getNewLineCounter()); }
break;
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
{ msgStack.addMsg("Error genÈrico no contemplado cerca de lÌnea " + lex.getNewLineCounter()); }
break;
case 37:
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
{
                    semanticStructStack.addMsg("L√≠nea " + stackOfLines.peek().intValue() + ": Sentencia IF ");
                    stackOfLines.pop();
                }
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 35:
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
//#line 119 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 133 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{
                    semanticStructStack.addMsg("L√≠nea " + stackOfLines.peek().intValue() + ": Sentencia IF ");
                    stackOfLines.pop();
                }
break;
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
//#line 147 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 49:
<<<<<<< HEAD
//#line 147 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 161 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{ 
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Sentencia foreach");
                stackOfLines.pop();
		if(symbolTable.get(val_peek(3).sval).getDeclaracion() == false)
		        	msgStack.addMsg("ERROR: Variable no declarada.");
		if(symbolTable.get(val_peek(1).sval).getDeclaracion() == false)
		        	msgStack.addMsg("ERROR: Colecci√≥n no declarada.");
            }
break;
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
case 52:
//#line 175 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 53:
<<<<<<< HEAD
//#line 164 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 178 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{ 
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Asignaci√≥n"); 
                stackOfLines.pop();
            }
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 51:
//#line 157 "gramatica.y"
=======
case 54:
<<<<<<< HEAD
//#line 170 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 184 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{ 
stackOfLines.push(lex.getNewLineCounter()); 
if(symbolTable.get(val_peek(0).sval).getDeclaracion() == false)
		        		msgStack.addMsg("ERROR: Variable no declarada.");
}
break;
case 55:
//#line 189 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ 
stackOfLines.push(lex.getNewLineCounter());
if(symbolTable.get(val_peek(3).sval).getDeclaracion() == false)
		        		msgStack.addMsg("ERROR: Variable no declarada.");
}
break;
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
//#line 192 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 206 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{
		if(symbolTable.get(val_peek(0).sval).getDeclaracion() == false)
		        	msgStack.addMsg("ERROR: Variable no declarada.");
		}
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 60:
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
//#line 196 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 210 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
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
			    	    ElementoTS ttsnuevo = new ElementoTS(tts.getTipoAtributo(),tts.getValue(),"");
			    	    symbolTable.put(lexnuevo,ttsnuevo);
		    	    }
			        msgStack.addMsg("Warning: Constante positiva fuera de rango.");
		        }
	        }
break;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
case 62:
//#line 177 "gramatica_2.y"
=======
case 61:
//#line 169 "gramatica_2.y"
>>>>>>> 87f0bc1... cambios pre-entrega
=======
case 61:
//#line 202 "gramatica.y"
=======
case 64:
<<<<<<< HEAD
//#line 215 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 229 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{
		if(symbolTable.get(val_peek(3).sval).getDeclaracion() == false)
		        	msgStack.addMsg("ERROR: Variable no declarada.");
		}
break;
<<<<<<< HEAD
case 62:
//#line 206 "gramatica.y"
>>>>>>> f713e33... comentario generico
=======
case 65:
<<<<<<< HEAD
//#line 219 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 233 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{
		        String lexeme = val_peek(0).sval;
		        ElementoTS tts = symbolTable.get(lexeme);
		        String lexnuevo = "-" + lexeme;
		        val_peek(0).sval = lexnuevo;
		        if(!symbolTable.containsKey(lexnuevo)){
		    	    ElementoTS ttsneg = new ElementoTS(tts.getTipoAtributo(),tts.getValue(),"");
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
<<<<<<< HEAD
case 63:
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
//#line 237 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 251 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
{
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Sentencia print");
                stackOfLines.pop();
            }
break;
<<<<<<< HEAD
<<<<<<< HEAD
case 64:
//#line 230 "gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 65:
//#line 233 "gramatica.y"
{ msgStack.addMsg("Warning: DeclaraciÛn de bloque sin contenido"); }
break;
case 66:
//#line 234 "gramatica.y"
{ msgStack.addMsg("Error: ComparaciÛn incompleta"); }
break;
=======
>>>>>>> 733ee4b... Agregado size y TS
case 67:
//#line 257 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 68:
//#line 260 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Warning: Declaraci√≥n de bloque sin contenido"); }
break;
case 69:
//#line 261 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Comparaci√≥n incompleta"); }
break;
case 70:
//#line 262 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Declaraci√≥n de IF sin condici√≥n"); }
break;
case 71:
//#line 263 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin √≠ndice y sin colecci√≥n"); }
break;
case 72:
//#line 264 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin colecci√≥n"); }
break;
case 73:
//#line 265 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin √≠ndice"); }
break;
case 74:
//#line 266 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Nombre y valor de posicion de coleccion faltante"); }
break;
case 75:
//#line 267 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Nombre de coleccion faltante"); }
break;
case 76:
//#line 268 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Corchete de apertura faltante"); }
break;
case 77:
//#line 269 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Corchete de cierre faltante"); }
break;
case 78:
//#line 270 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Constante a la izquierda de una asignaci√≥n"); }
break;
case 79:
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
{ msgStack.addMsg("Warning: DeclaraciÛn de bloque sin contenido"); }
break;
case 65:
//#line 196 "gramatica_2.y"
{ msgStack.addMsg("Error: ComparaciÛn incompleta"); }
break;
case 66:
//#line 197 "gramatica_2.y"
{ msgStack.addMsg("Error: DeclaraciÛn de IF sin condiciÛn"); }
break;
case 67:
//#line 198 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin Ìndice y sin colecciÛn"); }
break;
case 68:
//#line 199 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin colecciÛn"); }
break;
case 69:
//#line 200 "gramatica_2.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin Ìndice"); }
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
{ msgStack.addMsg("Error: Constante a la izquierda de una asignaciÛn"); }
break;
case 75:
//#line 206 "gramatica_2.y"
{ msgStack.addMsg("Error: Sentencia inv·lida"); }
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
{ msgStack.addMsg("Error: Sentencia de asignaciÛn inv·lida"); }
break;
//#line 761 "Parser.java"
=======
//#line 247 "gramatica.y"
{ msgStack.addMsg("Error: Sentencia de asignaciÛn inv·lida"); }
break;
//#line 790 "Parser.java"
>>>>>>> f713e33... comentario generico
=======
//#line 257 "C:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Sentencia inv·¨©da"); }
=======
//#line 271 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Sentencia inv√°lida"); }
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
break;
case 80:
//#line 272 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Constantes declaradas con tipo"); }
break;
case 81:
//#line 273 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Falta de separador entre valores constantes"); }
break;
case 82:
//#line 274 "c:\Users\Carolina\Documents\GitHub\compilator\gramatica.y"
{ msgStack.addMsg("Error: Sentencia de asignaci√≥n inv√°lida"); }
break;
<<<<<<< HEAD
//#line 820 "Parser.java"
>>>>>>> 733ee4b... Agregado size y TS
=======
//#line 852 "Parser.java"
>>>>>>> 3ddb440... Update Parser / ElementoTS :+1:
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
