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






//#line 2 "Copy_of_gramatica.y"
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
    3,    3,    3,   10,   10,   11,   11,   11,   11,   11,
   11,   12,   12,   17,   18,   20,   20,   20,   20,   20,
   20,   13,   21,   14,   22,   22,   23,   23,   23,   24,
   24,   24,   19,   19,   19,   19,   15,   25,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,
};
final static short yylen[] = {                            2,
    1,    2,    1,    1,    2,    3,    2,    2,    2,    1,
    1,    1,    1,    3,    4,    6,    1,    1,    3,    3,
    4,    5,    1,    1,    2,    2,    2,    2,    2,    2,
    2,    6,    8,    1,    3,    1,    1,    1,    1,    1,
    1,    5,    1,    3,    1,    4,    3,    3,    1,    3,
    3,    1,    1,    1,    4,    2,    4,    1,    2,    2,
    5,    3,    4,    4,    2,    3,    3,    4,    2,    2,
    2,    5,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   11,   12,   34,   43,   58,   38,
   39,   40,   41,    0,   36,   37,    0,    1,    0,    0,
    0,    0,    0,   23,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   59,    0,    0,
   24,   70,    0,    0,   69,    0,   65,    0,    0,    0,
    5,    0,    0,    0,   71,   26,   27,   28,   29,   30,
    0,    0,   54,   73,    0,   60,    0,    0,    0,    0,
   31,    0,   25,   67,    0,   66,    6,    0,    0,    0,
    0,    0,    0,    0,    0,   56,    0,    0,    0,   52,
    0,    0,    0,   21,    0,   68,   46,    0,   17,   18,
    0,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   57,   72,   22,
    0,   15,    0,   61,    0,   35,   55,    0,    0,    0,
    0,   50,   51,   19,   20,    0,    0,   32,    0,   16,
    0,   33,
};
final static short yydgoto[] = {                         17,
   18,   19,   20,   21,   22,   53,   54,   39,  101,   40,
   24,   25,   26,   27,   28,   29,   30,   83,   90,   31,
   32,   33,   91,   92,   34,
};
final static short yysindex[] = {                        39,
  -31,  -21,  -77, -229,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -71,    0,    0,    0,    0,   39, -200,
    7, -197, -196,    0,   10,   16,   18,   23,   25,   32,
  -45, -216, -198,   45,    0,   27, -170,    0, -196,    3,
    0,    0,   -4, -169,    0,   -3,    0, -200,   36,   57,
    0,    6,   52,   54,    0,    0,    0,    0,    0,    0,
  -34,    9,    0,    0, -158,    0, -164,   75,  -40, -167,
    0,   46,    0,    0,  -84,    0,    0,   21,  -79, -155,
 -151,   93,   68,  -43, -149,    0,  111,  130, -200,    0,
   13,   12,   73,    0,   22,    0,    0,   59,    0,    0,
  -26,    0,   29, -210,   93,  -40,   30,  130, -200,  -77,
  -78,  -67, -200,  -40,  -40,  -40,  -40,    0,    0,    0,
  -66,    0,  -79,    0, -220,    0,    0, -200,  -83,   12,
   12,    0,    0,    0,    0,  -19,   93,    0,    0,    0,
 -204,    0,
};
final static short yyrindex[] = {                         0,
    0,    0, -229,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  116,  122,
    0,    0, -135,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  125,    0,    0,
    0,  -17,   67,   69,    0,    0,    0,    0,    0,    0,
    0,  -39,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -229,   70,    0,
   74,  -11,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -229,   77,  -47,
    0,    0,   79,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   80,    0,  -10,
   -7,    0,    0,    0,    0,    0,    0,    0,  -46,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    5,  108,    0,    0,    0,   31,   11,   90,
   28,    0,    0,    0,    0,    0,    0,    0,   15,   60,
    0,    0,    0,  -35,    0,
};
final static int YYTABLESIZE=403;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         65,
   31,   53,   53,   53,   65,   53,   82,   53,   97,  139,
   65,   70,   66,   44,   74,  100,   15,  121,   16,   53,
   53,   47,   53,   48,  121,   47,   13,   35,  135,   41,
   23,   49,   48,   49,   48,   47,   50,   47,   15,   45,
   16,   13,   67,  137,  138,   66,   50,   49,   48,   23,
   68,   47,   50,  116,  124,  115,   50,  114,  117,    9,
  142,   52,   15,   55,   16,   51,  122,   73,   56,   14,
   69,   61,   89,  140,   57,   84,   58,   41,  130,  131,
   15,   59,   16,   60,   70,   71,  104,   38,   74,   76,
   75,  109,  113,   14,   77,   80,   79,   81,   15,   85,
   16,   86,   87,  102,   94,   73,   93,  103,  105,  125,
  107,   14,  128,  118,  119,    3,   15,  120,   16,  123,
  126,    4,  127,   10,    2,    7,   49,    8,   62,   14,
  132,  133,   44,  136,   15,   63,   16,   64,   42,   78,
    0,  141,    0,  106,    0,    0,    0,   14,    0,    0,
    0,    0,   15,    0,   16,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   14,    0,    0,    0,    0,
   15,    0,   16,    0,    0,   95,   95,    0,    0,    0,
   99,   42,   43,   14,   96,   96,    0,    0,   46,   15,
   45,   16,  129,  134,    0,    0,    0,    0,    0,    0,
    0,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   62,   63,    0,    0,    0,   62,   63,
  112,   45,   46,   64,   62,   63,   10,   11,   12,   13,
   53,   53,   53,   53,   36,   37,   38,    3,    4,    5,
    6,    7,    0,    0,    8,    0,    9,    0,   10,   11,
   12,   13,    0,    0,    0,    0,    0,   31,   36,   37,
   72,    3,    4,    5,    6,    7,    0,    0,    8,    0,
    9,    0,   10,   11,   12,   13,   36,   37,   98,    3,
    4,    5,    6,    7,    0,    0,    8,    0,    9,    0,
   10,   11,   12,   13,    1,    2,    0,    3,    4,    5,
    6,    7,    0,    0,    8,    0,    9,    0,   10,   11,
   12,   13,   36,   37,    0,    3,    4,    5,    6,    7,
    0,    0,    8,    0,    9,    0,   10,   11,   12,   13,
   36,    2,    0,   88,    4,    5,    6,    7,    0,    0,
    8,    0,    9,    0,   10,   11,   12,   13,   36,    2,
    0,    3,    4,    5,    6,    7,    0,    0,    8,    0,
    9,    0,   10,   11,   12,   13,   36,    2,    0,  108,
    4,    5,    6,    7,    0,    0,    8,    0,    9,    0,
   10,   11,   12,   13,    0,   36,    2,    0,  110,  111,
    5,    6,    7,    0,    0,    8,    0,    9,    0,   10,
   11,   12,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   41,   42,   43,   45,   45,   41,   47,   93,   93,
   45,   59,   59,   91,   93,   95,   60,   44,   62,   59,
   60,   93,   62,   19,   44,   93,   44,   59,   95,    2,
    0,   43,   43,   45,   45,   43,  257,   45,   60,  269,
   62,   59,  259,  264,  265,   31,  257,   59,   59,   19,
  267,   59,  257,   42,  265,   43,  257,   45,   47,   59,
  265,  259,   60,  260,   62,   59,   93,   40,   59,   91,
  269,   40,   68,   93,   59,   61,   59,   50,  114,  115,
   60,   59,   62,   59,   40,   59,   82,  258,   93,   93,
  260,   87,   88,   91,   59,   44,   91,   44,   60,   91,
   62,  260,  267,  259,   59,   78,  274,  259,   41,  105,
  260,   91,  108,   41,   93,    0,   60,   59,   62,   91,
  106,    0,   93,  259,    0,   59,   19,   59,   59,   91,
  116,  117,   59,  123,   60,   59,   62,   59,   59,   50,
   -1,  137,   -1,   84,   -1,   -1,   -1,   91,   -1,   -1,
   -1,   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   60,   -1,   62,   -1,   -1,  260,  260,   -1,   -1,   -1,
  260,  259,  260,   91,  269,  269,   -1,   -1,  260,   60,
  269,   62,  260,  260,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  259,  260,   -1,   -1,   -1,  259,  260,
   91,  269,  269,  269,  259,  260,  270,  271,  272,  273,
  270,  271,  272,  273,  256,  257,  258,  259,  260,  261,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  271,
  272,  273,   -1,   -1,   -1,   -1,   -1,  257,  256,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,  271,  272,  273,  256,  257,  258,  259,
  260,  261,  262,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,  271,  272,  273,  256,  257,   -1,  259,  260,  261,
  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,  271,
  272,  273,  256,  257,   -1,  259,  260,  261,  262,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,  271,  272,  273,
  256,  257,   -1,  259,  260,  261,  262,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,  271,  272,  273,  256,  257,
   -1,  259,  260,  261,  262,  263,   -1,   -1,  266,   -1,
  268,   -1,  270,  271,  272,  273,  256,  257,   -1,  259,
  260,  261,  262,  263,   -1,   -1,  266,   -1,  268,   -1,
  270,  271,  272,  273,   -1,  256,  257,   -1,  259,  260,
  261,  262,  263,   -1,   -1,  266,   -1,  268,   -1,  270,
  271,  272,  273,
};
}
final static short YYFINAL=17;
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
"bloque_decl : sent_decl ';'",
"bloque_decl : bloque_decl sent_decl ';'",
"sent_decl : comienzo_decl lista_vars",
"sent_decl : comienzo_decl lista_colecciones",
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
"bloque_ejec : BEGIN conj_sent_ejec END ';'",
"bloque_ejec : bloque_ejec BEGIN conj_sent_ejec END ';'",
"bloque_ejec : sent_ejec",
"conj_sent_ejec : sent_ejec",
"conj_sent_ejec : conj_sent_ejec sent_ejec",
"sent_ejec : sent_cond ';'",
"sent_ejec : sent_ctrl ';'",
"sent_ejec : sent_asig ';'",
"sent_ejec : sent_print ';'",
"sent_ejec : error_p ';'",
"sent_ejec : error ';'",
"sent_cond : comienzo_if '(' cond ')' bloque_ejec END_IF",
"sent_cond : comienzo_if '(' cond ')' bloque_ejec ELSE bloque_ejec END_IF",
"comienzo_if : IF",
"cond : factor comparador factor",
"comparador : '<'",
"comparador : '>'",
"comparador : LET",
"comparador : GET",
"comparador : EQ",
"comparador : DIF",
"sent_ctrl : comienzo_foreach ID IN ID bloque_ejec",
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
"error_p : BEGIN END",
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

//#line 196 "Copy_of_gramatica.y"

private Lexicon lex;
private Hashtable<String,ElementoTS> symbolTable;
private MsgStack msgStack;
private MsgStack semanticStructStack;
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
//#line 416 "Parser.java"
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
//#line 30 "Copy_of_gramatica.y"
{ semanticStructStack.addMsg("Linea "+ numLineaDecl +": Sentencia declarativa"); }
break;
case 8:
//#line 31 "Copy_of_gramatica.y"
{ semanticStructStack.addMsg("Linea "+ numLineaDecl +": Sentencia declarativa"); }
break;
case 9:
//#line 32 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error genérico en el bloque declarativo no contemplado cerca de línea " + lex.getNewLineCounter()); }
break;
case 10:
//#line 35 "Copy_of_gramatica.y"
{ numLineaDecl = lex.getNewLineCounter(); }
break;
case 31:
//#line 70 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error genérico no contemplado cerca de línea " + lex.getNewLineCounter()); }
break;
case 32:
//#line 73 "Copy_of_gramatica.y"
{
                    semanticStructStack.addMsg("Línea " + stackOfLines.peek().intValue() + ": Sentencia IF ");
                    stackOfLines.pop();
                }
break;
case 33:
//#line 77 "Copy_of_gramatica.y"
{
                    semanticStructStack.addMsg("Línea " + stackOfLines.peek().intValue() + ": Sentencia IF ");
                    stackOfLines.pop();
                }
break;
case 34:
//#line 84 "Copy_of_gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 42:
//#line 98 "Copy_of_gramatica.y"
{ 
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Sentencia foreach");
                stackOfLines.pop();
            }
break;
case 43:
//#line 104 "Copy_of_gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 44:
//#line 107 "Copy_of_gramatica.y"
{ 
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() + ": Asignación"); 
                stackOfLines.pop();
            }
break;
case 45:
//#line 113 "Copy_of_gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 46:
//#line 114 "Copy_of_gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 53:
//#line 127 "Copy_of_gramatica.y"
{
		        if(!symbolTable.containsKey(val_peek(0).sval)){
			        msgStack.addMsg("ERROR: Variable no declarada.");
		        }
	        }
break;
case 54:
//#line 132 "Copy_of_gramatica.y"
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
case 56:
//#line 152 "Copy_of_gramatica.y"
{
		        String lexeme = val_peek(0).sval;
		        ElementoTS tts = symbolTable.get(lexeme);
		        String lexnuevo = "-" + lexeme;
		        val_peek(0).sval = lexnuevo;
		        if(!symbolTable.containsKey(lexnuevo)){
		    	    ElementoTS ttsneg = new ElementoTS(tts.getValue(),tts.getTipoAtributo());
		    	    symbolTable.put(lexnuevo,ttsneg);
		        }else{
		    	    symbolTable.get(lexnuevo).decreaseCounter();
		        }
		        if (tts.getCantidad() == 0){
		    	    symbolTable.remove(lexeme);
		        }
	        }
break;
case 57:
//#line 169 "Copy_of_gramatica.y"
{
                semanticStructStack.addMsg("Linea "+ stackOfLines.peek().intValue() +": Sentencia print");
                stackOfLines.pop();
            }
break;
case 58:
//#line 175 "Copy_of_gramatica.y"
{ stackOfLines.push(lex.getNewLineCounter()); }
break;
case 59:
//#line 178 "Copy_of_gramatica.y"
{ msgStack.addMsg("Warning: Declaración de bloque sin contenido"); }
break;
case 60:
//#line 179 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Comparación incompleta"); }
break;
case 61:
//#line 180 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Declaración de IF sin condición"); }
break;
case 62:
//#line 181 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin índice y sin colección"); }
break;
case 63:
//#line 182 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin colección"); }
break;
case 64:
//#line 183 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Declaracion de FOREACH sin índice"); }
break;
case 65:
//#line 184 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Nombre y valor de posicion de coleccion faltante"); }
break;
case 66:
//#line 185 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Nombre de coleccion faltante"); }
break;
case 67:
//#line 186 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Corchete de apertura faltante"); }
break;
case 68:
//#line 187 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Corchete de cierre faltante"); }
break;
case 69:
//#line 188 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Constante a la izquierda de una asignación"); }
break;
case 70:
//#line 189 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Sentencia inválida"); }
break;
case 71:
//#line 190 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Constantes declaradas con tipo"); }
break;
case 72:
//#line 191 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Falta de separador entre valores constantes"); }
break;
case 73:
//#line 192 "Copy_of_gramatica.y"
{ msgStack.addMsg("Error: Sentencia de asignación inválida"); }
break;
//#line 748 "Parser.java"
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



//## Constructors ###############################################
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

