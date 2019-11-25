//package utils.syntacticTree;
//
//import java.util.Hashtable;
//import utils.ElementoTS;
//import utils.MsgStack;
//import utils.RegisterTable;
//
//public class SyntacticTreeCommon extends SyntacticTree {
//
//	public SyntacticTreeCommon(String lexeme, SyntacticTree nodoIzq, SyntacticTree nodoDer) {
//		super(lexeme);
//		super.setHijoIzq(nodoIzq);
//		super.setHijoDer(nodoDer);
//	}
//
//	public void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
//			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
//		comInterm.addMsg(blankPrefix + "Nodo: " + getElem());
//		String dataFromLeft = "";
//		String dataFromRight = "";
//		getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + "  ");
//		if (getHijoDer() != null)
//			getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + "  ");
//		dataFromLeft = getHijoIzq().getAlmacenamiento();
//		if (getHijoDer() != null)
//			dataFromRight = getHijoDer().getAlmacenamiento();
//
//		///// OBTENER OPERACIÓN /////
//		String operation = "";
//		switch(getElem()){
//		case "+": operation = "add";
//		case "-": operation = "sub";
//		case "*": operation = "imul";
//		case "/": {
//			operation = "idiv";
//			comAssembler.addMsg("cmp " + dataFromRight + ", " + 0);
//			comAssembler.addMsg("jz _DivisionPorCero"); //Salto a la subrutina de programa si el divisor es 0
//		}
//		case ":=": operation = "mov";
//		default: operation = "cmp"; //si los dos son hojas -- en cualquier otro caso
//		}
//
//		///// GENERAR CÓDIGO ADECUADO A CADA OPERACIÓN /////
//		if(getElem() == "+" || getElem() == "*" || getElem() == "-" || getElem() == ":=" || getElem() == "/") {
//			if(((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()) && (((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))) { ///// AMBOS SON CONST O VAR ///// S1
//				
//				if((operation == "imul")) { ///// OPERACIÓN MULTIPLICACIÓN /////
//					if(isInt(dataFromLeft, symbolTable)) { ///// ENTERO /////
//						String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
//						@SuppressWarnings("unused")
//						String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); 
//						comAssembler.addMsg("mov " + regAX + ", " + dataFromLeft);
//						comAssembler.addMsg("imul " + regAX + ", " + dataFromRight);
//						String reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler); // -----
//						comAssembler.addMsg("mov " + reg + ", DX:AX");
//						registros.freeReg(RegisterTable.AX);
//						registros.freeReg(RegisterTable.DX);
//						setAlmacenamiento(reg);
//					} else { ///// LONG /////
//						String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
//						@SuppressWarnings("unused")
//						String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Nuevamente se reserva para, si tiene algo, no pisarlo
//						comAssembler.addMsg("mov " + regEAX + ", " + dataFromLeft);
//						comAssembler.addMsg("imul " + regEAX + ", " + dataFromRight);
//						setAlmacenamiento(RegisterTable.NAME_EAX);
//						registros.freeReg(RegisterTable.EDX);
//						setAlmacenamiento(regEAX);
//					}
//					
//				} else { ///// NO ES MULTIPLICACIÓN /////
//					if(operation == "idiv")//ES DIVISION
//					{	
//						if(isInt(dataFromLeft, symbolTable)) {//ES ENTERO
//							//pide registros para contener al dividendo
//							String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
//							@SuppressWarnings("unused")
//							String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
//							comAssembler.addMsg("mov "+regAX+", " + dataFromLeft);//divendo en AX
//							comAssembler.addMsg("cwd");//extension de signo
//							String regCX= registros.getReg(RegisterTable.NAME_CX, getHijoDer(), comAssembler, symbolTable);
//							comAssembler.addMsg("mov " + regCX + ", " + dataFromRight);//guarda divisor
//							comAssembler.addMsg("idiv " + regCX);//DIVISION DX:AX / CX
//							registros.freeReg(RegisterTable.CX);//LIBERA 
//							registros.freeReg(RegisterTable.AX);//LIBERA COCIENTE
//							setAlmacenamiento(regDX);//DEVUELVE RESTO
//						}
//						else////ES LONG
//						{
//							String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
//							@SuppressWarnings("unused")
//							String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
//							comAssembler.addMsg("mov "+regEAX+", " + dataFromLeft);//divendo en EAX
//							comAssembler.addMsg("cwd");//extension de signo
//							String regECX= registros.getReg(RegisterTable.NAME_ECX, getHijoDer(), comAssembler, symbolTable);
//							comAssembler.addMsg("mov " + regECX + ", " + dataFromRight);//guarda divisor
//							comAssembler.addMsg("idiv " + regECX);//DIVISION EDX:EAX / ECX
//							registros.freeReg(RegisterTable.ECX);//LIBERA 
//							registros.freeReg(RegisterTable.EAX);//LIBERA COCIENTE
//							setAlmacenamiento(regEDX);//DEVUELVE RESTO
//						}
//					}
//					else//NO ES DIVISION
//					{
//						String reg;
//						if(isInt(dataFromLeft, symbolTable))///ES ENTERO
//							reg = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
//						else
//							reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler);
//						comAssembler.addMsg("mov " + reg + ", " + dataFromLeft);
//						comAssembler.addMsg(operation +" " + reg + ", " + dataFromRight);
//						setAlmacenamiento(reg);
//					}
//				}
//				
//			} else { ///// UNO DE LOS HIJOS NO ES CONST O VAR /////
//				
//				if((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) { ///// HIJO IZQ ES CONST O VAR ///// S4
//					
//					if(operation.equals("ADD") || operation.equals("imul")) { ///// OPERACIÓN CONMUTATIVA /////S4.A
//						
//						if (operation.equals("imul")) { ///// MULTIPLICACIÓN /////
//						
//							if(dataFromRight == RegisterTable.NAME_AX || dataFromRight == RegisterTable.NAME_BX || dataFromRight == RegisterTable.NAME_CX || dataFromRight == RegisterTable.NAME_DX) { ///// ES ENTERA /////
//								registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler,symbolTable);
//								registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler,symbolTable);
//								dataFromRight = getHijoDer().getAlmacenamiento();
//								comAssembler.addMsg("mov AX, " + dataFromRight);
//								comAssembler.addMsg("imul AX, " + dataFromLeft);
//								String reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
//								comAssembler.addMsg("mov " + reg + ", DX:AX");
//								registros.freeReg(RegisterTable.AX);
//								registros.freeReg(RegisterTable.DX);
//								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromRight));
//								setAlmacenamiento(reg);
//							} else { ///// ES LONG /////
//								registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler,symbolTable);
//								registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler,symbolTable);
//								dataFromRight = getHijoDer().getAlmacenamiento();
//								comAssembler.addMsg("mov EAX, " + dataFromRight);
//								comAssembler.addMsg("imul EAX, " + dataFromLeft);
//								setAlmacenamiento(RegisterTable.NAME_EAX);
//								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromRight));
//								registros.freeReg(RegisterTable.EDX);
//							}
//							
//						} else { ///// NO ES MULTIPLICACIÓN ///// ADD
//							
//							comAssembler.addMsg(operation + " " + dataFromRight + ", " + dataFromLeft);
//							setAlmacenamiento(dataFromRight);
//
//						}
//					} else { ///// OPERACIÓN NO CONMUTATIVA ///// DIV - S4.B
//						if(operation == "idiv")//ES DIVISION
//						{	
//							if(isInt(dataFromLeft, symbolTable)) {//ES ENTERO
//								//pide registros para contener al dividendo
//								String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
//								String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
//								comAssembler.addMsg("mov "+regAX+", " + dataFromLeft);//divendo en AX
//								comAssembler.addMsg("cwd");//extension de signo
//								String regCX= registros.getReg(RegisterTable.NAME_CX, getHijoDer(), comAssembler, symbolTable);
//								comAssembler.addMsg("mov " + regCX + ", " + dataFromRight);//guarda divisor
//								comAssembler.addMsg("idiv " + regCX);//DIVISION DX:AX / CX
//								registros.freeReg(RegisterTable.CX);//LIBERA 
//								registros.freeReg(RegisterTable.AX);//LIBERA COCIENTE
//								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromRight));
//								setAlmacenamiento(regDX);//DEVUELVE RESTO
//							}
//							else////ES LONG
//							{
//								String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
//								String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
//								comAssembler.addMsg("mov "+regEAX+", " + dataFromLeft);//divendo en EAX
//								comAssembler.addMsg("cwd");//extension de signo
//								String regECX= registros.getReg(RegisterTable.NAME_ECX, getHijoDer(), comAssembler, symbolTable);
//								comAssembler.addMsg("mov " + regECX + ", " + dataFromRight);//guarda divisor
//								comAssembler.addMsg("idiv " + regECX);//DIVISION EDX:EAX / ECX
//								registros.freeReg(RegisterTable.ECX);//LIBERA 
//								registros.freeReg(RegisterTable.EAX);//LIBERA COCIENTE
//								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromRight));
//								setAlmacenamiento(regEDX);//DEVUELVE RESTO
//							}
//						}
//						else//ES RESTA
//						{
//							String reg;
//							if(dataFromRight == RegisterTable.NAME_AX || dataFromRight == RegisterTable.NAME_BX || dataFromRight == RegisterTable.NAME_CX || dataFromRight == RegisterTable.NAME_DX)
//								reg = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
//							else
//								reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
//							dataFromRight = getHijoDer().getAlmacenamiento();
//							comAssembler.addMsg("mov " + reg + ", " + dataFromLeft);
//							comAssembler.addMsg(operation + " " + reg + ", " + dataFromRight);
//							setAlmacenamiento(reg);
//							registros.freeReg(registros.getRegPos(dataFromRight));
//						}
//					}
//					
//					
//				} else { ///// HIJO IZQ NO ES VAR O CONST
//					if (((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()) { ///// EL HIJO DER ES CONST O VAR /////
//						
//						if(operation == "imul") { ///// MULTIPLICACIÓN /////
//							if(dataFromLeft == RegisterTable.NAME_AX || dataFromLeft == RegisterTable.NAME_BX || dataFromLeft == RegisterTable.NAME_CX || dataFromLeft == RegisterTable.NAME_DX) { ///// ENTERO /////
//								registros.getReg(RegisterTable.NAME_AX,getHijoIzq(), comAssembler,symbolTable);
//								registros.getReg(RegisterTable.NAME_DX,getHijoIzq(), comAssembler,symbolTable);
//								dataFromLeft = getHijoIzq().getAlmacenamiento();
//								comAssembler.addMsg("mov AX, " + dataFromLeft);
//								comAssembler.addMsg("imul AX, " + dataFromRight);
//								String reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler); // -----
//								comAssembler.addMsg("mov " + reg + ", DX:AX"); 
//								registros.freeReg(RegisterTable.AX);
//								registros.freeReg(RegisterTable.DX);
//								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromLeft));
//								setAlmacenamiento(reg);
//							} else { ///// LONG /////
//								registros.getReg(RegisterTable.NAME_EAX,getHijoIzq(), comAssembler,symbolTable);
//								registros.getReg(RegisterTable.NAME_EDX,getHijoIzq(), comAssembler,symbolTable);
//								dataFromLeft = getHijoIzq().getAlmacenamiento();
//								comAssembler.addMsg("mov EAX, " + dataFromLeft);
//								comAssembler.addMsg("imul EAX, " + dataFromRight);
//								setAlmacenamiento(RegisterTable.NAME_EAX);
//								registros.freeReg(RegisterTable.EDX);
//								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromLeft));
//							}
//							
//						} else { ///// NO ES MULTIPLICACIÓN /////
//							if(operation == "idiv")//ES DIVISION
//							{	
//								if(isInt(dataFromLeft, symbolTable)) {//ES ENTERO
//									//pide registros para contener al dividendo
//									String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
//									String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
//									comAssembler.addMsg("mov "+regAX+", " + dataFromLeft);//divendo en AX
//									comAssembler.addMsg("cwd");//extension de signo
//									String regCX= registros.getReg(RegisterTable.NAME_CX, getHijoDer(), comAssembler, symbolTable);
//									comAssembler.addMsg("mov " + regCX + ", " + dataFromRight);//guarda divisor
//									comAssembler.addMsg("idiv " + regCX);//DIVISION DX:AX / CX
//									registros.freeReg(RegisterTable.CX);//LIBERA 
//									registros.freeReg(RegisterTable.AX);//LIBERA COCIENTE
//									if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//										registros.freeReg(registros.getRegPos(dataFromLeft));
//									setAlmacenamiento(regDX);//DEVUELVE RESTO
//								}
//								else////ES LONG
//								{
//									String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
//									String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
//									comAssembler.addMsg("mov "+regEAX+", " + dataFromLeft);//divendo en EAX
//									comAssembler.addMsg("cwd");//extension de signo
//									String regECX= registros.getReg(RegisterTable.NAME_ECX, getHijoDer(), comAssembler, symbolTable);
//									comAssembler.addMsg("mov " + regECX + ", " + dataFromRight);//guarda divisor
//									comAssembler.addMsg("idiv " + regECX);//DIVISION EDX:EAX / ECX
//									registros.freeReg(RegisterTable.ECX);//LIBERA 
//									registros.freeReg(RegisterTable.EAX);//LIBERA COCIENTE
//									if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//										registros.freeReg(registros.getRegPos(dataFromLeft));
//									setAlmacenamiento(regEDX);//DEVUELVE RESTO
//								}
//							}
//							else {//ES RESTA O SUMA
//								comAssembler.addMsg(operation+" "+dataFromLeft+", "+dataFromRight);
//								setAlmacenamiento(dataFromLeft);
//							}
//						}
//						
//			
//					} else { ///// NINGUNO DE SUS HIJOS ES CONST O VAR /////
//						
//						if(operation == "imul") {//ES MULTIPLICACION
//							if(dataFromLeft.length() == 2) {
//								registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler,symbolTable);
//								registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler,symbolTable);
//								dataFromLeft = getHijoIzq().getAlmacenamiento();
//								dataFromRight = getHijoDer().getAlmacenamiento();
//								comAssembler.addMsg("mov AX, " + dataFromLeft);
//								comAssembler.addMsg("imul AX, " + dataFromRight);
//								String reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler); // ----
//								comAssembler.addMsg("mov " + reg + ", DX:AX");
//								registros.freeReg(RegisterTable.AX);
//								registros.freeReg(RegisterTable.DX);
//								setAlmacenamiento(reg);
//								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromLeft));
//								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromRight));
//							}else {
//								registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler,symbolTable);
//								registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler,symbolTable);
//								dataFromLeft = getHijoIzq().getAlmacenamiento();
//								dataFromRight = getHijoDer().getAlmacenamiento();
//								comAssembler.addMsg("mov EAX, " + dataFromLeft);
//								comAssembler.addMsg("imul EAX, " + dataFromRight);
//								setAlmacenamiento(RegisterTable.NAME_EAX);
//								registros.freeReg(RegisterTable.EDX);
//								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromLeft));
//								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//									registros.freeReg(registros.getRegPos(dataFromRight));
//							}
//						} else {///// NO ES MULTIPLICACIÓN /////
//							if(operation == "idiv")//ES DIVISION
//							{	
//								if(isInt(dataFromLeft, symbolTable)) {//ES ENTERO
//									//pide registros para contener al dividendo
//									String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
//									String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
//									comAssembler.addMsg("mov "+regAX+", " + dataFromLeft);//divendo en AX
//									comAssembler.addMsg("cwd");//extension de signo
//									String regCX= registros.getReg(RegisterTable.NAME_CX, getHijoDer(), comAssembler, symbolTable);
//									comAssembler.addMsg("mov " + regCX + ", " + dataFromRight);//guarda divisor
//									comAssembler.addMsg("idiv " + regCX);//DIVISION DX:AX / CX
//									registros.freeReg(RegisterTable.CX);//LIBERA 
//									registros.freeReg(RegisterTable.AX);//LIBERA COCIENTE
//									if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
//										registros.freeReg(registros.getRegPos(dataFromLeft));
//									if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//										registros.freeReg(registros.getRegPos(dataFromRight));
//									setAlmacenamiento(regDX);//DEVUELVE RESTO
//								}
//								else////ES LONG
//								{
//									String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
//									String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
//									comAssembler.addMsg("mov "+regEAX+", " + dataFromLeft);//divendo en EAX
//									comAssembler.addMsg("cwd");//extension de signo
//									String regECX= registros.getReg(RegisterTable.NAME_ECX, getHijoDer(), comAssembler, symbolTable);
//									comAssembler.addMsg("mov " + regECX + ", " + dataFromRight);//guarda divisor
//									comAssembler.addMsg("idiv " + regECX);//DIVISION EDX:EAX / ECX
//									registros.freeReg(RegisterTable.ECX);//LIBERA 
//									registros.freeReg(RegisterTable.EAX);//LIBERA COCIENTE
//									if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
//										registros.freeReg(registros.getRegPos(dataFromLeft));
//									if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//										registros.freeReg(registros.getRegPos(dataFromRight));
//									setAlmacenamiento(regEDX);//DEVUELVE RESTO
//								}
//							}
//							else {//ES RESTA O SUMA
//								comAssembler.addMsg(operation + " " + dataFromLeft + ", " + dataFromRight);
//								setAlmacenamiento(dataFromLeft);
//								registros.freeReg(registros.getRegPos(dataFromRight)); //datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
//								}	
//						}	
//					}
//				}
//			}
//		}
//		else { ///// OPERACIÓN DE COMPARACIÓN /////
//			if((getElem() == "<") || (getElem() == ">") || (getElem() == "<=") || (getElem() == ">=") || (getElem() == "==") || (getElem() == "!=")) {
//				if ((int)(dataFromLeft.charAt(0)) >= 48 && (int)(dataFromLeft.charAt(0)) <= 57) {
//					String regAux;
//					if(isInt(dataFromLeft, symbolTable)) 
//						regAux = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
//					else
//						regAux = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
//					comAssembler.addMsg("mov " + regAux + ", " + dataFromLeft);
//					dataFromLeft = regAux;
//				}
//				dataFromRight = getHijoDer().getAlmacenamiento();
//				comAssembler.addMsg("cmp " + dataFromLeft +", " + dataFromRight); 
//				registros.freeReg(registros.getRegPos(dataFromLeft));
//				if(!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
//					registros.freeReg(registros.getRegPos(dataFromRight));
//				if(!((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))
//					registros.freeReg(registros.getRegPos(dataFromLeft));
//				setAlmacenamiento(getElem()); //_S, If, Cuerpo, Comparadores, 
//			} 
//			/* else {//ASIGNACION
//				String regAux=dataFromLeft;
//				comAssembler.addMsg("mov " + dataFromLeft + ", " + dataFromRight);
//				if(((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))//Si es hoja la parte izquierda, toma un registro para almacenar el valor de dataFromLeft 
//				{
//					if(isInt(dataFromLeft, symbolTable))
//						regAux = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
//					else
//						regAux = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
//					comAssembler.addMsg("mov " + regAux + ", " + dataFromLeft);//mueve la cte o variable a un nuevo registro
//				}
//				if(!((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))
//				{
//					registros.freeReg(registros.getRegPos(dataFromRight));
//				}
//				setAlmacenamiento(regAux);
//			}*/
//		}
//	}
//
//	private boolean isInt(String data, Hashtable<String, ElementoTS> symbolTable) {
//		boolean result = (data == RegisterTable.NAME_AX) || (data == RegisterTable.NAME_BX) || (data == RegisterTable.NAME_CX) || (data == RegisterTable.NAME_DX);
//		if (symbolTable.get(data) != null)
//			result = result || (symbolTable.get(data).getTipoAtributo().equals(ElementoTS.INT));
//		if (symbolTable.get(data.substring(1)) != null)
//			result = result || (symbolTable.get(data.substring(1)).getTipoAtributo().equals(ElementoTS.INT));
//		return result;
//	}
//}

package utils.syntacticTree;

import java.util.Hashtable;
import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SyntacticTreeCommon extends SyntacticTree {

	public SyntacticTreeCommon(String lexeme, SyntacticTree nodoIzq, SyntacticTree nodoDer) {
		super(lexeme);
		super.setHijoIzq(nodoIzq);
		super.setHijoDer(nodoDer);
	}

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
<<<<<<< HEAD
			Hashtable<String, ElementoTS> symbolTable) {
		String datoIzq = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable);
		String datoDer = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm,symbolTable);
=======
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
		String datoIzq = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, deep+1);
		String datoDer = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm,symbolTable, deep+1);
>>>>>>> 7eacf2b... _

		Pattern patron = Pattern.compile("_*");
		Matcher matchIzq = patron.matcher(datoIzq);
		Matcher matchDer = patron.matcher(datoDer);
=======
	public void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
=======
	public void recorreArbol(RegisterTable registers, MsgStack assemblerCode, MsgStack comInterm,
>>>>>>> 0fcca1b... varios
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
<<<<<<< HEAD
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
		super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + " ");
		super.getHijoDer().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + " ");

		String dato1 = getHijoIzq().getAlmacenamiento();
		String dato2 = getHijoIzq().getAlmacenamiento();
>>>>>>> 154a393... comentario

		String op="";

		switch(super.getElem())
		{
		case "+":
			op="ADD";
		case "-":
			op="SUB";
		case "*":
			op="IMUL";
		case "/":
		{
<<<<<<< HEAD
			op="DIV";
<<<<<<< HEAD:src/utils/sintacticTree/SintacticTreeCommon.java

			contEtiquetas++;
=======
=======
			op="IDIV";
<<<<<<< HEAD
>>>>>>> 9acbfaf... comentario
			comAssembler.addMsg("CMP " + datoDer + ", " + 0);
			comAssembler.addMsg("JZ _DivisionPorCero");//Salto al error del programa si el divisor es 0

			/*contEtiquetas++;

					comAssembler.addMsg("CMP " + datoDer + ", " + 0);
					comAssembler.addMsg("JZ _label" + contEtiquetas);//Salto al error del programa si el divisor es 0
				/*	comAssembler.addMsg("_label" + contEtiquetas + ":");
					comAssembler.addMsg("invoke StdOut, addr _Errorlabel" + contEtiquetas);
>>>>>>> cf97fd0... Arreglos en ventana:src/utils/syntacticTree/SyntacticTreeCommon.java

			comAssembler.addMsg("ADD datoDer, "+0);
			comAssembler.addMsg("JZ _label"+contEtiquetas);//Salto al error del programa si el divisor es 0
			comAssembler.addMsg("_label"+contEtiquetas+":");
			comAssembler.addMsg("invoke StdOut, addr _Errorlabel"+contEtiquetas);

			ElementoTS tupla = new ElementoTS("_Errorlabel"+contEtiquetas, "", "Error: division por cero");
			symbolTable.put("_Errorlabel"+contEtiquetas, tupla);

			contEtiquetas++;

			comAssembler.addMsg("JMP _label"+contEtiquetas);//salto al final del programa
			comAssembler.addMsg("_label"+contEtiquetas+":");

			comAssembler.addMsg("invoke ExitProcess, "+0);
=======
			comAssembler.addMsg("CMP " + dato2 + ", " + 0);
			comAssembler.addMsg("JZ _DivisionPorCero");//Salto al error del programa si el divisor es 0
>>>>>>> 154a393... comentario
		}
		case ":=":
		{
			op="MOV";
		}
		default://si los dos son hojas -- en cualquier otro caso
		{
			op="CMP";
		}
		}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD:src/utils/sintacticTree/SintacticTreeCommon.java
		if( matchIzq.matches() && matchDer.matches()) 

		{

			//si los dos son hojas
			int reg=registros.getRegFree();//obtener algun registro libre
			boolean state=true;
			registros.setRegTable(reg, state);
			comAssembler.addMsg("MOV R"+reg+", "+datoIzq);				//devuelvo codigo assembler correspondiente
			comAssembler.addMsg(op+" R"+reg+", "+datoDer);
=======
		if(esHoja(datoIzq) && esHoja(datoDer)){//si los dos son hojas
//			int reg=registros.getRegFree();//obtener algun registro libre
//			boolean state=true;
//			registros.setRegTable(reg, state);
//			comAssembler.addMsg("MOV R"+reg+", "+datoIzq);				//devuelvo codigo assembler correspondiente
//			comAssembler.addMsg(op+" R"+reg+", "+datoDer);
>>>>>>> cf97fd0... Arreglos en ventana:src/utils/syntacticTree/SyntacticTreeCommon.java
			//devuelve lo que se devuelve por pantalla
			//comInterm.addMsg();
//			return "R"+reg;
		}
		else
			if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()=="-" || super.getElem()==":=" || super.getElem()=="/")			


				if(matchIzq.matches())//si el izquierdo es hoja;
					if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()==":=")//es operacion conmutativa
=======
		if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()=="-" || super.getElem()==":=" || super.getElem()=="/") {
			if(esHoja(dato1) && esHoja(dato2)){//si los dos son hojas
				if((op == "IMUL")) {//si es operacion de multiplicacion
					String reg;
					if(symbolTable.get(dato1.substring(0)).getTipoAtributo() == "int") {
						String regAX = registros.getReg("AX", this, comAssembler);
						String regDX = registros.getReg("DX", this, comAssembler);
						dato1 = getHijoIzq().getAlmacenamiento();
						dato2 = getHijoDer().getAlmacenamiento();
						comAssembler.addMsg("MOV "+ regAX + ", " + dato1);				//devuelvo codigo assembler correspondiente
						comAssembler.addMsg("IMUL "+regAX+", "+dato2);
						reg = registros.getRegFreeLong(this);
						comAssembler.addMsg("MOV " + reg + ", DX:AX");
						registros.setRegTable("AX",false);
						registros.setRegTable("DX",false);
						setAlmacenamiento(reg);
					}else {
						String regEAX = registros.getReg("EAX",this, comAssembler);
						dato1 = getHijoIzq().getAlmacenamiento();
						dato2 = getHijoDer().getAlmacenamiento();
						comAssembler.addMsg("MOV EAX, "+dato1);				//devuelvo codigo assembler correspondiente
						comAssembler.addMsg("IMUL EAX, "+dato2);
						//						comAssembler.addMsg(/*comando para reducir a EAX el contenido de EDX:EAX*/); //Línea comentada para poder compilar
						setAlmacenamiento("EAX");
					}
				}else { //si no es operacion de multiplicacion
					String reg;
					if(symbolTable.get(dato1.substring(0)).getTipoAtributo() == "int")
						reg = registros.getRegFreeInt(this);//obtener algun registro int libre
=======
		if(getElem() == "+" || getElem() == "*" || getElem()=="-" || getElem()==":=" || getElem()=="/") {
			if(esHoja(dato1) && esHoja(dato2)) {//si los dos son hojas
				if((op == "IMUL")) {//si es operacion de multiplicacion
					String reg;
					if(symbolTable.get(dato1.substring(1)).getTipoAtributo().equals(ElementoTS.INT)) {
=======
		comInterm.addMsg(blankPrefix + "Nodo: " + getElem());
		System.out.println("entro a common " + getElem());
		getHijoIzq().recorreArbol(registers, assemblerCode, comInterm, symbolTable, blankPrefix + getBlankSpace());
		if (getHijoDer() != null)
			getHijoDer().recorreArbol(registers, assemblerCode, comInterm, symbolTable, blankPrefix + getBlankSpace());
		System.out.println("vuelve a common " + getElem());
		String op = getElem();
		if(op == "+") suma(registers,assemblerCode,symbolTable);
		else if(op == "-") resta(registers,assemblerCode,symbolTable);
		else if(op == "*") multiplicacion(registers,assemblerCode,symbolTable);
		else if(op == "/") division(registers,assemblerCode,symbolTable);
		else if(op == ":=") asignacion(registers, assemblerCode, symbolTable);
		else if((op == "<") || (op == ">") || (op == "LET") || (op == "GET") || (op == "EQ") || (op == "DIF"))
			comparacion(registers,assemblerCode,symbolTable);
	}

	private void asignacion(RegisterTable registers, MsgStack assemblerCode, Hashtable<String, ElementoTS> symbolTable) {
		String dataFromLeft = getHijoIzq().getAlmacenamiento();
		String dataFromRight = getHijoDer().getAlmacenamiento();
		if ((((SyntacticTreeLeaf)getHijoIzq()).isCollectionPointer() && getHijoDer().isVariable()) 
				|| (getHijoIzq().isVariable() && getHijoDer().isVariable())) {  //// Se mueve el lado derecho a registro ////
			String tempReg;
			if (isInt(dataFromRight, symbolTable))
				tempReg = registers.getRegFreeInt(getHijoDer(), symbolTable, assemblerCode);
			else
				tempReg = registers.getRegFreeLong(getHijoDer(), symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + tempReg + ", " + dataFromRight);
			dataFromRight = tempReg;
		}
		if (((SyntacticTreeLeaf)getHijoIzq()).isCollectionPointer()) //// Se realiza la asignación ////
			if (getHijoIzq().getType().equals(ElementoTS.INT))
				assemblerCode.addMsg("mov word ptr [" + dataFromLeft + "], " + dataFromRight);
			else
				assemblerCode.addMsg("mov dword ptr [" + dataFromLeft + "], " + dataFromRight);
		else
			assemblerCode.addMsg("mov " + dataFromLeft + ", " + dataFromRight);
		if(!(getHijoDer().isVariableOrConst()))
			registers.freeReg(registers.getRegPos(dataFromRight));
		if(!(getHijoIzq().isVariableOrConst()))
			registers.freeReg(registers.getRegPos(dataFromLeft));
	}

	private void comparacion(RegisterTable registers, MsgStack assemblerCode, Hashtable<String, ElementoTS> symbolTable) {
		String dataFromLeft = getHijoIzq().getAlmacenamiento();
		String dataFromRight;
		if (getHijoIzq().isVariableOrConst()) { //// Izq constante o variable ////
			String regAux;
			if(isInt(dataFromLeft, symbolTable))
				regAux = registers.getRegFreeInt(getHijoIzq(), symbolTable, assemblerCode);
			else
				regAux = registers.getRegFreeLong(getHijoIzq(), symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + regAux + ", " + dataFromLeft);
			dataFromLeft = regAux;
		}
		dataFromRight = getHijoDer().getAlmacenamiento();
		assemblerCode.addMsg("cmp " + dataFromLeft +", " + dataFromRight);
		registers.freeReg(registers.getRegPos(dataFromLeft));
		if(!(getHijoDer().isVariableOrConst()))
			registers.freeReg(registers.getRegPos(dataFromRight));
		setAlmacenamiento(getElem());
	}

	private void suma(RegisterTable registers, MsgStack assemblerCode, Hashtable<String, ElementoTS> symbolTable) {
		String reg;
		String dataFromLeft = getHijoIzq().getAlmacenamiento();
		String dataFromRight =  getHijoDer().getAlmacenamiento();
		if(getHijoIzq().isVariableOrConst() && getHijoDer().isVariableOrConst()) {
			if(isInt(dataFromLeft, symbolTable))///ES ENTERO
				reg = registers.getRegFreeInt(getHijoIzq(), symbolTable, assemblerCode);
			else
				reg = registers.getRegFreeLong(getHijoIzq(), symbolTable, assemblerCode);
			dataFromRight = getHijoDer().getAlmacenamiento();
			assemblerCode.addMsg("mov " + reg + ", " + dataFromLeft);
			assemblerCode.addMsg("add " + reg + ", " + dataFromRight);
		} else {
			if(getHijoIzq().isVariableOrConst()) { // HIJO DER ES REG
				assemblerCode.addMsg("add " + dataFromRight + ", " + dataFromLeft);
				reg = dataFromRight;
				if (!getHijoIzq().isVariableOrConst())
					registers.freeReg(registers.getRegPos(dataFromLeft));
			} else { // HIJO IZQ ES REG (Puede que el derecho también sea registro)
				assemblerCode.addMsg("add " + dataFromLeft + ", " + dataFromRight);
				reg = dataFromLeft;
				if (!getHijoDer().isVariableOrConst())
					registers.freeReg(registers.getRegPos(dataFromRight));
			}
		}
		setAlmacenamiento(reg);
	}

<<<<<<< HEAD
<<<<<<< HEAD
		///// GENERAR CÓDIGO ADECUADO A CADA OPERACIÓN /////
<<<<<<< HEAD
		if(getElem() == "+" || getElem() == "*" || getElem() == "-" || getElem() == ":=" || getElem() == "/") {
<<<<<<< HEAD
<<<<<<< HEAD
			if((getHijoIzq() != null) && ((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) && (getHijoDer() != null) && ((((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))) {//si los dos son hojas
				if((operation == "IMUL")) {//si es operacion de multiplicacion TODO: Si el elemento de la izquierda es const ¿no se rompe?
					if(symbolTable.get(dataFromLeft.substring(1)).getTipoAtributo().equals(ElementoTS.INT)) {
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
=======
			if(((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()) && (((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))) { ///// AMBOS SON CONST O VAR /////
<<<<<<< HEAD
				///// OPERACIÓN MILTIPLICACIÓN /////
				if((operation == "IMUL")) {
					if(isInt(dataFromLeft, symbolTable)) {
>>>>>>> bca257b... resueltos problemas en common
						String regAX = registros.getReg(RegisterTable.NAME_AX, this, comAssembler);
						@SuppressWarnings("unused")
						String regDX = registros.getReg(RegisterTable.NAME_DX, this, comAssembler); // Se reserva para, si tiene algo, no pisarlo
=======
						String regAX = registros.getReg(RegisterTable.NAME_AX, this, comAssembler,symbolTable);
						@SuppressWarnings("unused")
						String regDX = registros.getReg(RegisterTable.NAME_DX, this, comAssembler,symbolTable); //Se reserva para, si tiene algo, no pisarlo
						dataFromLeft = getHijoIzq().getAlmacenamiento();
						dataFromRight = getHijoDer().getAlmacenamiento();
>>>>>>> 050f179... Update -
=======
				if((operation == "IMUL")) { ///// OPERACIÓN MULTIPLICACIÓN /////
					if(isInt(dataFromLeft, symbolTable)) { ///// ENTERO /////
						String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
						@SuppressWarnings("unused")
						String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
>>>>>>> 88b2c34... _
=======
=======
		if(getElem() == "+" || getElem() == "*" || getElem() == "-"  || getElem() == "/") {
>>>>>>> a030643... Up common
			if(((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()) && (((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))) { ///// AMBOS SON CONST O VAR ///// S1
				
				if((operation == "IMUL")) { ///// OPERACIÓN MULTIPLICACIÓN /////
					if(isInt(dataFromLeft, symbolTable)) { ///// ENTERO /////
						String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
						@SuppressWarnings("unused")
						String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); 
>>>>>>> b423eb5... Up SyntacticTreeCommon
						comAssembler.addMsg("MOV " + regAX + ", " + dataFromLeft);
						comAssembler.addMsg("IMUL " + regAX + ", " + dataFromRight);
						String reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler); // -----
						comAssembler.addMsg("MOV " + reg + ", DX:AX");
						registros.freeReg(RegisterTable.AX);
						registros.freeReg(RegisterTable.DX);
						setAlmacenamiento(reg);
<<<<<<< HEAD
<<<<<<< HEAD
					} else {
						String regEAX = registros.getReg(RegisterTable.NAME_EAX,this, comAssembler);
						@SuppressWarnings("unused")
						String regEDX = registros.getReg(RegisterTable.NAME_EDX,this, comAssembler); // Nuevamente se reserva para, si tiene algo, no pisarlo
=======
					}else {
						String regEAX = registros.getReg(RegisterTable.NAME_EAX,this, comAssembler,symbolTable);
						@SuppressWarnings("unused")
						String regEDX = registros.getReg(RegisterTable.NAME_EDX,this, comAssembler,symbolTable); //Se reserva para, si tiene algo, no pisarlo
						dataFromLeft = getHijoIzq().getAlmacenamiento();
						dataFromRight = getHijoDer().getAlmacenamiento();
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
					} else { ///// LONG /////
						String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
						@SuppressWarnings("unused")
						String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Nuevamente se reserva para, si tiene algo, no pisarlo
>>>>>>> 88b2c34... _
						comAssembler.addMsg("MOV " + regEAX + ", " + dataFromLeft);
						comAssembler.addMsg("IMUL " + regEAX + ", " + dataFromRight);
						setAlmacenamiento(RegisterTable.NAME_EAX);
						registros.freeReg(RegisterTable.EDX);
						setAlmacenamiento(regEAX);
					}
					
				} else { ///// NO ES MULTIPLICACIÓN /////
<<<<<<< HEAD
					String reg;
<<<<<<< HEAD
					if(isInt(dataFromLeft, symbolTable))
<<<<<<< HEAD
						reg = registros.getRegFreeInt(this);
>>>>>>> 1375c5c... arreglos varios
					else
<<<<<<< HEAD
						reg = registros.getRegFreeLong(this);//obtener algun registro long libre

					comAssembler.addMsg("MOV "+reg+", "+dato1);				//devuelvo codigo assembler correspondiente
					comAssembler.addMsg(op+" "+reg+", "+dato2);
=======
						reg = registros.getRegFreeLong(this);
=======
					if(symbolTable.get(dataFromLeft.substring(1)).getTipoAtributo().equals(ElementoTS.INT))
						reg = registros.getRegFreeInt(this,symbolTable,comAssembler);
					else
						reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
						reg = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
					else
						reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler);
>>>>>>> 88b2c34... _
					comAssembler.addMsg("MOV " + reg + ", " + dataFromLeft);
					comAssembler.addMsg(operation +" " + reg + ", " + dataFromRight);
>>>>>>> a091e6e... arreglos por punteros null
					setAlmacenamiento(reg);
				}
<<<<<<< HEAD
<<<<<<< HEAD
			}
			else
<<<<<<< HEAD
				if(esHoja(dato1))//si el izquierdo es hoja;
					if(op == "ADD" || op == "MOV")//es operacion conmutativa
>>>>>>> 9acbfaf... comentario
					{
						comAssembler.addMsg(op+" "+dato2+", "+dato1);//operacion sobre el mismo registro
						setAlmacenamiento(dato2);
					}
					else
					{if (op == "IMUL") {
						if(dato2.length() == 2) {
							registros.getReg("AX",this, comAssembler);
							registros.getReg("DX",this, comAssembler);
							dato1 = getHijoIzq().getAlmacenamiento();
							dato2 = getHijoDer().getAlmacenamiento();
							comAssembler.addMsg("MOV AX, " + dato2);
							comAssembler.addMsg("IMUL AX, " + dato1);
							String reg = registros.getRegFreeLong(this);
							comAssembler.addMsg("MOV " + reg + ", DX:AX");
							registros.setRegTable("AX",false);
							registros.setRegTable("DX",false);
							setAlmacenamiento(reg);
						}else {
							registros.getReg("EAX",this, comAssembler);
							registros.getReg("EDX",this, comAssembler);
							dato1 = getHijoIzq().getAlmacenamiento();
							dato2 = getHijoDer().getAlmacenamiento();
							comAssembler.addMsg("MOV EAX, " + dato2);
							comAssembler.addMsg("IMUL EAX, " + dato1);
							setAlmacenamiento("EAX");
							registros.setRegTable("EDX",false);
						}
					}else {
						String reg;
						if(dato2.length() == 2)
							reg = registros.getRegFreeInt(this);
						else {
							reg = registros.getRegFreeLong(this);
							comAssembler.addMsg("MOV " + reg + ", " + dato1);
							comAssembler.addMsg(op + " " + reg + ", " + dato2);
							setAlmacenamiento(reg);
							registros.setRegTable(dato2,false);
						}
					}	
					}
				else
				{
<<<<<<< HEAD
					if(matchDer.matches())
					{	//si el derecho es hoja;
						comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
						//comInterm.addMsg();
					}
=======
					if(esHoja(dato2))	//si el derecho es hoja;
						if(op == "IMUL") {
							if(dato1.length() == 2) {
								registros.getReg("AX",this, comAssembler);
								registros.getReg("DX",this, comAssembler);
								dato1 = getHijoIzq().getAlmacenamiento();
								dato2 = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dato1);
								comAssembler.addMsg("IMUL AX, " + dato2);
=======
				if((getHijoIzq() != null) && ((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))
					if(operation.equals("ADD") || operation.equals("MOV")) { //es operacion conmutativa
						comAssembler.addMsg(operation + " " + dataFromRight + ", " + dataFromLeft); //operacion sobre el mismo registro
=======
			} else
				if((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) ///// HIJO IZQ ES CONST O VAR /////
=======
			} else { ///// UNO DE LOS HIJOS NO ES CONST O VAR /////
				if((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) { ///// HIJO IZQ ES CONST O VAR /////
>>>>>>> 88b2c34... _
					if(operation.equals("ADD") || operation.equals("MOV")) { ///// OPERACIÓN CONMUTATIVA /////
						comAssembler.addMsg(operation + " " + dataFromRight + ", " + dataFromLeft);
>>>>>>> bca257b... resueltos problemas en common
						setAlmacenamiento(dataFromRight);
					} else { ///// OPERACIÓN NO CONMUTATIVA /////
<<<<<<< HEAD
						if (operation.equals("IMUL")) {
							if(dataFromRight.length() == 2) {
<<<<<<< HEAD
								registros.getReg(RegisterTable.NAME_AX,this, comAssembler);
								registros.getReg(RegisterTable.NAME_DX,this, comAssembler);
=======
								registros.getReg(RegisterTable.NAME_AX,this, comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_DX,this, comAssembler,symbolTable);
								dataFromLeft = getHijoIzq().getAlmacenamiento();
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
=======
					if(operation == "IDIV")//ES DIVISION
					{	
						if(isInt(dataFromLeft, symbolTable)) {//ES ENTERO
							//pide registros para contener al dividendo
							String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
							@SuppressWarnings("unused")
							String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
							comAssembler.addMsg("MOV "+regAX+", " + dataFromLeft);//divendo en AX
							comAssembler.addMsg("CWD");//extension de signo
							String regCX= registros.getReg(RegisterTable.NAME_CX, getHijoDer(), comAssembler, symbolTable);
							comAssembler.addMsg("MOV " + regCX + ", " + dataFromRight);//guarda divisor
							comAssembler.addMsg("IDIV " + regCX);//DIVISION DX:AX / CX
							registros.freeReg(RegisterTable.CX);//LIBERA 
							registros.freeReg(RegisterTable.AX);//LIBERA COCIENTE
							setAlmacenamiento(regDX);//DEVUELVE RESTO
						}
						else////ES LONG
						{
							String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
							@SuppressWarnings("unused")
							String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
							comAssembler.addMsg("MOV "+regEAX+", " + dataFromLeft);//divendo en EAX
							comAssembler.addMsg("CWD");//extension de signo
							String regECX= registros.getReg(RegisterTable.NAME_ECX, getHijoDer(), comAssembler, symbolTable);
							comAssembler.addMsg("MOV " + regECX + ", " + dataFromRight);//guarda divisor
							comAssembler.addMsg("IDIV " + regECX);//DIVISION EDX:EAX / ECX
							registros.freeReg(RegisterTable.ECX);//LIBERA 
							registros.freeReg(RegisterTable.EAX);//LIBERA COCIENTE
							setAlmacenamiento(regEDX);//DEVUELVE RESTO
						}
					}
					else//NO ES DIVISION
					{
						String reg;
						if(isInt(dataFromLeft, symbolTable))///ES ENTERO
							reg = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
						else
							reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler);
						comAssembler.addMsg("MOV " + reg + ", " + dataFromLeft);
						comAssembler.addMsg(operation +" " + reg + ", " + dataFromRight);
						setAlmacenamiento(reg);
					}
				}
				
			} else { ///// UNO DE LOS HIJOS NO ES CONST O VAR /////
				
				if((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) { ///// HIJO IZQ ES CONST O VAR ///// S4
					
					if(operation.equals("ADD") || operation.equals("IMUL")) { ///// OPERACIÓN CONMUTATIVA /////S4.A
						
>>>>>>> b423eb5... Up SyntacticTreeCommon
						if (operation.equals("IMUL")) { ///// MULTIPLICACIÓN /////
						
							if(dataFromRight == RegisterTable.NAME_AX || dataFromRight == RegisterTable.NAME_BX || dataFromRight == RegisterTable.NAME_CX || dataFromRight == RegisterTable.NAME_DX) { ///// ES ENTERA /////
								registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler,symbolTable);
>>>>>>> 88b2c34... _
								dataFromRight = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dataFromRight);
								comAssembler.addMsg("IMUL AX, " + dataFromLeft);
								String reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
								comAssembler.addMsg("MOV " + reg + ", DX:AX");
								registros.freeReg(RegisterTable.AX);
								registros.freeReg(RegisterTable.DX);
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
								setAlmacenamiento(reg);
<<<<<<< HEAD
<<<<<<< HEAD
							} else {
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler);
=======
							}else {
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler,symbolTable);
								dataFromLeft = getHijoIzq().getAlmacenamiento();
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
							} else { ///// ES LONG /////
								registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler,symbolTable);
>>>>>>> 88b2c34... _
								dataFromRight = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dataFromRight);
								comAssembler.addMsg("IMUL EAX, " + dataFromLeft);
								setAlmacenamiento(RegisterTable.NAME_EAX);
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
								registros.freeReg(RegisterTable.EDX);
							}
							
						} else { ///// NO ES MULTIPLICACIÓN ///// ADD
							
							comAssembler.addMsg(operation + " " + dataFromRight + ", " + dataFromLeft);
							setAlmacenamiento(dataFromRight);

						}
					} else { ///// OPERACIÓN NO CONMUTATIVA ///// DIV - S4.B
						if(operation == "IDIV")//ES DIVISION
						{	
							if(isInt(dataFromLeft, symbolTable)) {//ES ENTERO
								//pide registros para contener al dividendo
								String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
								String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
								comAssembler.addMsg("MOV "+regAX+", " + dataFromLeft);//divendo en AX
								comAssembler.addMsg("CWD");//extension de signo
								String regCX= registros.getReg(RegisterTable.NAME_CX, getHijoDer(), comAssembler, symbolTable);
								comAssembler.addMsg("MOV " + regCX + ", " + dataFromRight);//guarda divisor
								comAssembler.addMsg("IDIV " + regCX);//DIVISION DX:AX / CX
								registros.freeReg(RegisterTable.CX);//LIBERA 
								registros.freeReg(RegisterTable.AX);//LIBERA COCIENTE
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
								setAlmacenamiento(regDX);//DEVUELVE RESTO
							}
							else////ES LONG
							{
								String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
								String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
								comAssembler.addMsg("MOV "+regEAX+", " + dataFromLeft);//divendo en EAX
								comAssembler.addMsg("CWD");//extension de signo
								String regECX= registros.getReg(RegisterTable.NAME_ECX, getHijoDer(), comAssembler, symbolTable);
								comAssembler.addMsg("MOV " + regECX + ", " + dataFromRight);//guarda divisor
								comAssembler.addMsg("IDIV " + regECX);//DIVISION EDX:EAX / ECX
								registros.freeReg(RegisterTable.ECX);//LIBERA 
								registros.freeReg(RegisterTable.EAX);//LIBERA COCIENTE
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
								setAlmacenamiento(regEDX);//DEVUELVE RESTO
							}
						}
						else//ES RESTA
						{
							String reg;
<<<<<<< HEAD
<<<<<<< HEAD
							if(!(dataFromRight == RegisterTable.NAME_AX || dataFromRight == RegisterTable.NAME_BX || dataFromRight == RegisterTable.NAME_CX || dataFromRight == RegisterTable.NAME_DX))
								reg = registros.getRegFreeInt(this);
							else
								reg = registros.getRegFreeLong(this);
=======
							if(dataFromRight == RegisterTable.NAME_AX || dataFromRight == RegisterTable.NAME_BX || dataFromRight == RegisterTable.NAME_CX || dataFromRight == RegisterTable.NAME_DX)
								reg = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
							else
								reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
>>>>>>> 88b2c34... _
							dataFromRight = getHijoDer().getAlmacenamiento();
							comAssembler.addMsg("MOV " + reg + ", " + dataFromLeft);
							comAssembler.addMsg(operation + " " + reg + ", " + dataFromRight);
							setAlmacenamiento(reg);
							registros.freeReg(registros.getRegPos(dataFromRight));
						}
=======
							if(dataFromRight.length() == 2)
								reg = registros.getRegFreeInt(this,symbolTable,comAssembler);
							else {
								reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
								comAssembler.addMsg("MOV " + reg + ", " + dataFromLeft);
								comAssembler.addMsg(operation + " " + reg + ", " + dataFromRight);
								setAlmacenamiento(reg);
								registros.freeReg(registros.getRegPos(dataFromRight));
							}
						}	
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
					}
					
					
				} else { ///// HIJO IZQ NO ES VAR O CONST
					if (((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()) { ///// EL HIJO DER ES CONST O VAR /////
						
						if(operation == "IMUL") { ///// MULTIPLICACIÓN /////
							if(dataFromLeft == RegisterTable.NAME_AX || dataFromLeft == RegisterTable.NAME_BX || dataFromLeft == RegisterTable.NAME_CX || dataFromLeft == RegisterTable.NAME_DX) { ///// ENTERO /////
								registros.getReg(RegisterTable.NAME_AX,getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_DX,getHijoIzq(), comAssembler,symbolTable);
								dataFromLeft = getHijoIzq().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dataFromLeft);
								comAssembler.addMsg("IMUL AX, " + dataFromRight);
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
								String reg = registros.getRegFreeLong(this);
=======
								String reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
								comAssembler.addMsg("MOV " + reg + ", DX:AX");
<<<<<<< HEAD
								registros.setRegTable("AX",false);
								registros.setRegTable("DX",false);
=======
=======
								String reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler); // -----
								comAssembler.addMsg("MOV " + reg + ", DX:AX"); 
>>>>>>> 88b2c34... _
								registros.freeReg(RegisterTable.AX);
								registros.freeReg(RegisterTable.DX);
								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromLeft));
>>>>>>> bca257b... resueltos problemas en common
								setAlmacenamiento(reg);
<<<<<<< HEAD
							}else {
<<<<<<< HEAD
<<<<<<< HEAD
								registros.getReg("EAX",this, comAssembler);
								registros.getReg("EDX",this, comAssembler);
								dato1 = getHijoIzq().getAlmacenamiento();
								dato2 = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dato1);
								comAssembler.addMsg("IMUL EAX, " + dato2);
								setAlmacenamiento("EAX");
								registros.setRegTable("EDX",false);
=======
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler);
=======
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler,symbolTable);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
							} else { ///// LONG /////
								registros.getReg(RegisterTable.NAME_EAX,getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX,getHijoIzq(), comAssembler,symbolTable);
>>>>>>> 88b2c34... _
								dataFromLeft = getHijoIzq().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dataFromLeft);
								comAssembler.addMsg("IMUL EAX, " + dataFromRight);
								setAlmacenamiento(RegisterTable.NAME_EAX);
								registros.freeReg(RegisterTable.EDX);
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
=======
								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromLeft));
>>>>>>> bca257b... resueltos problemas en common
							}
							
						} else { ///// NO ES MULTIPLICACIÓN /////
							if(operation == "IDIV")//ES DIVISION
							{	
								if(isInt(dataFromLeft, symbolTable)) {//ES ENTERO
									//pide registros para contener al dividendo
									String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
									String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
									comAssembler.addMsg("MOV "+regAX+", " + dataFromLeft);//divendo en AX
									comAssembler.addMsg("CWD");//extension de signo
									String regCX= registros.getReg(RegisterTable.NAME_CX, getHijoDer(), comAssembler, symbolTable);
									comAssembler.addMsg("MOV " + regCX + ", " + dataFromRight);//guarda divisor
									comAssembler.addMsg("IDIV " + regCX);//DIVISION DX:AX / CX
									registros.freeReg(RegisterTable.CX);//LIBERA 
									registros.freeReg(RegisterTable.AX);//LIBERA COCIENTE
									if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
										registros.freeReg(registros.getRegPos(dataFromLeft));
									setAlmacenamiento(regDX);//DEVUELVE RESTO
								}
								else////ES LONG
								{
									String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
									String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
									comAssembler.addMsg("MOV "+regEAX+", " + dataFromLeft);//divendo en EAX
									comAssembler.addMsg("CWD");//extension de signo
									String regECX= registros.getReg(RegisterTable.NAME_ECX, getHijoDer(), comAssembler, symbolTable);
									comAssembler.addMsg("MOV " + regECX + ", " + dataFromRight);//guarda divisor
									comAssembler.addMsg("IDIV " + regECX);//DIVISION EDX:EAX / ECX
									registros.freeReg(RegisterTable.ECX);//LIBERA 
									registros.freeReg(RegisterTable.EAX);//LIBERA COCIENTE
									if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
										registros.freeReg(registros.getRegPos(dataFromLeft));
									setAlmacenamiento(regEDX);//DEVUELVE RESTO
								}
							}
							else {//ES RESTA O SUMA
								comAssembler.addMsg(operation+" "+dataFromLeft+", "+dataFromRight);
								setAlmacenamiento(dataFromLeft);
							}
						}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 154a393... comentario
					else//ninguno es hoja
					{
<<<<<<< HEAD
						if(op == "IMUL") {
							if(dato1.length() == 2) {
								registros.getReg("AX",this, comAssembler);
								registros.getReg("DX",this, comAssembler);
								dato1 = getHijoIzq().getAlmacenamiento();
								dato2 = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dato1);
								comAssembler.addMsg("IMUL AX, " + dato2);
=======
=======
					else { ///// NINGUNO DE SUS HIJOS ES CONST O VAR /////
>>>>>>> bca257b... resueltos problemas en common
=======
					} else { ///// NINGUNO DE SUS HIJOS ES CONST O VAR /////
>>>>>>> 88b2c34... _
						if(operation == "IMUL") {
=======
						
			
					} else { ///// NINGUNO DE SUS HIJOS ES CONST O VAR /////
						
						if(operation == "IMUL") {//ES MULTIPLICACION
>>>>>>> b423eb5... Up SyntacticTreeCommon
							if(dataFromLeft.length() == 2) {
								registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler,symbolTable);
								dataFromLeft = getHijoIzq().getAlmacenamiento();
								dataFromRight = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dataFromLeft);
								comAssembler.addMsg("IMUL AX, " + dataFromRight);
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
								String reg = registros.getRegFreeLong(this);
=======
								String reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
								String reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler); // ----
>>>>>>> 88b2c34... _
								comAssembler.addMsg("MOV " + reg + ", DX:AX");
								registros.setRegTable("AX",false);
								registros.setRegTable("DX",false);
								setAlmacenamiento(reg);
								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromLeft));
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
							}else {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
								registros.getReg("EAX",this, comAssembler);
								registros.getReg("EDX",this, comAssembler);
								dato1 = getHijoIzq().getAlmacenamiento();
								dato2 = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dato1);
								comAssembler.addMsg("IMUL EAX, " + dato2);
								setAlmacenamiento("EAX");
								registros.setRegTable("EDX",false);
							}
						} else {
							comAssembler.addMsg(op+" "+dato1+", "+dato2);
							setAlmacenamiento(dato1);
							registros.setRegTable(dato2, false);//datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
=======
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler);
=======
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler,symbolTable);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
								registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler,symbolTable);
>>>>>>> 88b2c34... _
								dataFromLeft = getHijoIzq().getAlmacenamiento();
								dataFromRight = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dataFromLeft);
								comAssembler.addMsg("IMUL EAX, " + dataFromRight);
								setAlmacenamiento(RegisterTable.NAME_EAX);
								registros.freeReg(RegisterTable.EDX);
								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromLeft));
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
							}
<<<<<<< HEAD
						} else {
							comAssembler.addMsg(operation + " " + dataFromLeft + ", " + dataFromRight);
							setAlmacenamiento(dataFromLeft);
							registros.freeReg(registros.getRegPos(dataFromRight)); //datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
>>>>>>> a091e6e... arreglos por punteros null
						}
=======
						} else {///// NO ES MULTIPLICACIÓN /////
							if(operation == "IDIV")//ES DIVISION
							{	
								if(isInt(dataFromLeft, symbolTable)) {//ES ENTERO
									//pide registros para contener al dividendo
									String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
									String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
									comAssembler.addMsg("MOV "+regAX+", " + dataFromLeft);//divendo en AX
									comAssembler.addMsg("CWD");//extension de signo
									String regCX= registros.getReg(RegisterTable.NAME_CX, getHijoDer(), comAssembler, symbolTable);
									comAssembler.addMsg("MOV " + regCX + ", " + dataFromRight);//guarda divisor
									comAssembler.addMsg("IDIV " + regCX);//DIVISION DX:AX / CX
									registros.freeReg(RegisterTable.CX);//LIBERA 
									registros.freeReg(RegisterTable.AX);//LIBERA COCIENTE
									if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
										registros.freeReg(registros.getRegPos(dataFromLeft));
									if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
										registros.freeReg(registros.getRegPos(dataFromRight));
									setAlmacenamiento(regDX);//DEVUELVE RESTO
								}
								else////ES LONG
								{
									String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
									String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
									comAssembler.addMsg("MOV "+regEAX+", " + dataFromLeft);//divendo en EAX
									comAssembler.addMsg("CWD");//extension de signo
									String regECX= registros.getReg(RegisterTable.NAME_ECX, getHijoDer(), comAssembler, symbolTable);
									comAssembler.addMsg("MOV " + regECX + ", " + dataFromRight);//guarda divisor
									comAssembler.addMsg("IDIV " + regECX);//DIVISION EDX:EAX / ECX
									registros.freeReg(RegisterTable.ECX);//LIBERA 
									registros.freeReg(RegisterTable.EAX);//LIBERA COCIENTE
									if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
										registros.freeReg(registros.getRegPos(dataFromLeft));
									if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
										registros.freeReg(registros.getRegPos(dataFromRight));
									setAlmacenamiento(regEDX);//DEVUELVE RESTO
								}
							}
							else {//ES RESTA O SUMA
								comAssembler.addMsg(operation + " " + dataFromLeft + ", " + dataFromRight);
								setAlmacenamiento(dataFromLeft);
								registros.freeReg(registros.getRegPos(dataFromRight)); //datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
								}	
						}	
>>>>>>> b423eb5... Up SyntacticTreeCommon
					}
				}
<<<<<<< HEAD
<<<<<<< HEAD
		}
		else 
		{
<<<<<<< HEAD
<<<<<<< HEAD
			if((super.getElem() == "<")||(super.getElem() == ">")||(super.getElem() == "<=")||(super.getElem() == ">=")||(super.getElem() == "==")||(super.getElem() == "!="))
				comAssembler.addMsg("CMP " + datoIzq +", " + datoDer);

<<<<<<< HEAD
			else 
			{
				if(matchDer.matches())
				{
					comAssembler.addMsg("");
				}

				return "";//_S, IF, Cuerpo, comparadores, 



			}
		return datoDer;
	}
=======
			return super.getElem();//_S, IF, Cuerpo, comparadores, 
		}
		return datoDer;
=======
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
		String datoIzq;
		String datoDer;
		if (getHijoIzq() != null)
			datoIzq = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + " ");
		if (getHijoDer() != null)
			datoDer = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + " ");

//		String op="";
//
//		switch(super.getElem()){
//		case "+":
//			op="ADD";
//		case "-":
//			op="SUB";
//		case "*":
//			op="IMUL";
//		case "/":
//		{
//			op="IDIV";
//			comAssembler.addMsg("CMP " + datoDer + ", " + 0);
//			comAssembler.addMsg("JZ _DivisionPorCero");//Salto al error del programa si el divisor es 0
//
//			/*contEtiquetas++;
//
//					comAssembler.addMsg("CMP " + datoDer + ", " + 0);
//					comAssembler.addMsg("JZ _label" + contEtiquetas);//Salto al error del programa si el divisor es 0
//				/*	comAssembler.addMsg("_label" + contEtiquetas + ":");
//					comAssembler.addMsg("invoke StdOut, addr _Errorlabel" + contEtiquetas);
//
//					ElementoTS tupla = new ElementoTS("_Errorlabel"+contEtiquetas, "", "Error: division por cero");
//					symbolTable.put("_Errorlabel"+contEtiquetas, tupla);
//
//					contEtiquetas++;
//
//					comAssembler.addMsg("JMP _label"+contEtiquetas);//salto al final del programa
//					comAssembler.addMsg("_label"+contEtiquetas+":");
//
//					comAssembler.addMsg("invoke ExitProcess, "+0);*/
//		}
//		case ":=":
//		{
//			op="MOV";
//		}
//		default://si los dos son hojas -- en cualquier otro caso
//		{
//			op="CMP";
//		}
//		}
//		if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()=="-" || super.getElem()==":=" || super.getElem()=="/") {
//			if(esHoja(datoIzq) && esHoja(datoDer)){//si los dos son hojas
//				if((op == "IMUL")) {//si es operacion de multiplicacion
//					String reg;
//					if(symbolTable.get(datoIzq.substring(0)).getTipoAtributo() == "int") {
//						comAssembler.addMsg("MOV AX, "+datoIzq);				//devuelvo codigo assembler correspondiente
//						comAssembler.addMsg("IMUL AX, "+datoDer);
//						reg = registros.getRegFreeLong();
//						comAssembler.addMsg("MOV " + reg + ", DX:AX");
//						return reg;
//					}else {
//						comAssembler.addMsg("MOV EAX, "+datoIzq);				//devuelvo codigo assembler correspondiente
//						comAssembler.addMsg("IMUL EAX, "+datoDer);
//						reg = registros.getRegFreeLong();
////						comAssembler.addMsg(/*comando para reducir a EAX el contenido de EDX:EAX*/); //Línea comentada para poder compilar
//						comAssembler.addMsg("MOV " + reg + ", EAX");
//						return reg;
//					}
//				}else { //si no es operacion de multiplicacion
//					String reg;
//					if(symbolTable.get(datoIzq.substring(0)).getTipoAtributo() == "int")
//						reg = registros.getRegFreeInt();//obtener algun registro int libre
//					else
//						reg = registros.getRegFreeLong();//obtener algun registro long libre
//
//					comAssembler.addMsg("MOV "+reg+", "+datoIzq);				//devuelvo codigo assembler correspondiente
//					comAssembler.addMsg(op+" "+reg+", "+datoDer);
//					return reg;
//				}
//			}
//			else
//				if(esHoja(datoIzq))//si el izquierdo es hoja;
//					if(op == "ADD" || op == "MOV")//es operacion conmutativa
//					{
//						comAssembler.addMsg(op+" "+datoDer+", "+datoIzq);//operacion sobre el mismo registro
//						return datoDer;
//					}
//					else
//					{if (op == "IMUL") {
//
//					}else {
//
//					}	
//					//						int reg=registros.getRegFree();//obtener algun registro libre
//					//						boolean state=true;
//					//						registros.setRegTable(reg, state);
//					//						comAssembler.addMsg("MOV R"+reg+", "+datoDer);							//devuelvo codigo assembler correspondiente
//					//						comAssembler.addMsg(op+" "+reg+", "+datoIzq);
//					//comInterm.addMsg();
//					//registros.setRegTable(datoIzq, false); datoIzq es un String de la forma R1 , hay que transformarlo a numero no mas
//					//						return "R"+reg;
//					}
//				else
//				{
//					if(esHoja(datoDer))
//					{	//si el derecho es hoja;
//						comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
//						//comInterm.addMsg();
//					}
//					else//ninguno es hoja
//					{
//						comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
//						//registros.setRegTable(datoDer, false);datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
//						//comInterm.addMsg();
//					}
//					return datoIzq;
//				}
//		}
//		else 
//		{
//			if((super.getElem() == "<")||(super.getElem() == ">")||(super.getElem() == "<=")||(super.getElem() == ">=")||(super.getElem() == "==")||(super.getElem() == "!="))
//				comAssembler.addMsg("CMP " + datoIzq +", " + datoDer);
//
//			return super.getElem();//_S, IF, Cuerpo, comparadores, 
//		}
//		return datoDer;
		return "";
>>>>>>> 45299ea... visualizaciÃ³n de Ã¡rbol sintÃ¡ctico
=======
			if((super.getElem() == "<")||(super.getElem() == ">")||(super.getElem() == "<=")||(super.getElem() == ">=")||(super.getElem() == "==")||(super.getElem() == "!=")) {
				if(esHoja(dato1)) {
					if(symbolTable.get(dato1.substring(1)).getTipoAtributo() == "int") 
						dato1 = registros.getRegFreeInt(this);
=======
			if((getElem() == "<")||(getElem() == ">")||(getElem() == "<=")||(getElem() == ">=")||(getElem() == "==")||(getElem() == "!=")) {
				if((getHijoIzq() != null) && (((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) {
					if(symbolTable.get(dataFromLeft.substring(1)).getTipoAtributo().equals(ElementoTS.INT)) 
<<<<<<< HEAD
						dataFromLeft = registros.getRegFreeInt(this);
>>>>>>> a091e6e... arreglos por punteros null
=======
=======
			}
<<<<<<< HEAD
>>>>>>> 88b2c34... _
		} else { ///// OPERACIÓN DE COMPARACIÓN /////
=======
		}
		else { ///// OPERACIÓN DE COMPARACIÓN /////
>>>>>>> b423eb5... Up SyntacticTreeCommon
			if((getElem() == "<") || (getElem() == ">") || (getElem() == "<=") || (getElem() == ">=") || (getElem() == "==") || (getElem() == "!=")) {
				if ((int)(dataFromLeft.charAt(0)) >= 48 && (int)(dataFromLeft.charAt(0)) <= 57) {
					String regAux;
					if(isInt(dataFromLeft, symbolTable)) 
<<<<<<< HEAD
						regAux = registros.getRegFreeInt(this);
>>>>>>> bca257b... resueltos problemas en common
					else
						regAux = registros.getRegFreeLong(this);
=======
						regAux = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
					else
						regAux = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
>>>>>>> 88b2c34... _
					comAssembler.addMsg("MOV " + regAux + ", " + dataFromLeft);
					dataFromLeft = regAux;
				}
<<<<<<< HEAD
<<<<<<< HEAD
				comAssembler.addMsg("CMP " + dato1 +", " + dato2);
				registros.setRegTable(dato1,false);
				if(!esHoja(dato2)) {
					registros.setRegTable(dato2,false);
=======
						dataFromLeft = registros.getRegFreeInt(this,symbolTable,comAssembler);
					else
						dataFromLeft = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
				}
				setAlmacenamiento(super.getElem());//_S, IF, Cuerpo, comparadores, 
=======
=======
				dataFromRight = getHijoDer().getAlmacenamiento();
>>>>>>> bca257b... resueltos problemas en common
				comAssembler.addMsg("CMP " + dataFromLeft +", " + dataFromRight); 
				registros.freeReg(registros.getRegPos(dataFromLeft));
				if(!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
					registros.freeReg(registros.getRegPos(dataFromRight));
<<<<<<< HEAD
				setAlmacenamiento(getElem());//_S, IF, Cuerpo, comparadores, 
>>>>>>> a091e6e... arreglos por punteros null
=======
				if(!((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))
					registros.freeReg(registros.getRegPos(dataFromLeft));
<<<<<<< HEAD
				setAlmacenamiento(getElem()); //_S, IF, Cuerpo, comparadores, 
>>>>>>> bca257b... resueltos problemas en common
=======
				setAlmacenamiento(getElem()); //_S, If, Cuerpo, Comparadores, 
<<<<<<< HEAD
>>>>>>> 6bb5a8f... _
			}
			else//ASIGNACION
			{
=======
			} else {//ASIGNACION
>>>>>>> 2514a93... hay que pedirle mas mas a la vida
				String regAux=dataFromLeft;
				comAssembler.addMsg("MOV " + dataFromLeft + ", " + dataFromRight);
				if(((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))//Si es hoja la parte izquierda, toma un registro para almacenar el valor de dataFromLeft 
				{
					if(isInt(dataFromLeft, symbolTable))
						regAux = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
					else
						regAux = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
					comAssembler.addMsg("MOV " + regAux + ", " + dataFromLeft);//mueve la cte o variable a un nuevo registro
				}
				if(!((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))
				{
					registros.freeReg(registros.getRegPos(dataFromRight));
				}
				setAlmacenamiento(regAux);
			}
=======
	private void resta(RegisterTable registros, MsgStack comAssembler, Hashtable<String, ElementoTS> symbolTable) {
		String reg;
=======
	private void resta(RegisterTable registers, MsgStack assemblerCode, Hashtable<String, ElementoTS> symbolTable) {
		String regResult;
>>>>>>> 0fcca1b... varios
		String dataFromLeft = getHijoIzq().getAlmacenamiento();
		if (getHijoIzq().isVariableOrConst()) {
			if(isInt(dataFromLeft, symbolTable)) ///ES ENTERO
				regResult = registers.getRegFreeInt(this, symbolTable, assemblerCode);
			else
				regResult = registers.getRegFreeLong(this,symbolTable,assemblerCode);
			assemblerCode.addMsg("mov " + regResult + ", " + dataFromLeft);
		} else { regResult = dataFromLeft; }
		String dataFromRight = getHijoDer().getAlmacenamiento();
		assemblerCode.addMsg("sub " + regResult + ", " + dataFromRight);
		setAlmacenamiento(regResult);
		if (!getHijoDer().isVariableOrConst())
			registers.freeReg(registers.getRegPos(dataFromRight));
	}

	private void multiplicacion(RegisterTable registers, MsgStack assemblerCode, Hashtable<String, ElementoTS> symbolTable) {
		String dataFromLeft = getHijoIzq().getAlmacenamiento();
		String dataFromRight = getHijoDer().getAlmacenamiento();
		if(isInt(dataFromLeft, symbolTable)) {//ENTERO
			String regAX = registers.getReg(RegisterTable.NAME_AX, getHijoIzq(), symbolTable, assemblerCode);
			@SuppressWarnings("unused")
			String regDX = registers.getReg(RegisterTable.NAME_DX, getHijoIzq(), symbolTable, assemblerCode); // Se reserva para que, si tiene algo, no sea pisado
			dataFromLeft = getHijoIzq().getAlmacenamiento();
			dataFromRight = getHijoDer().getAlmacenamiento();
			assemblerCode.addMsg("mov " + regAX + ", " + dataFromLeft);
			if (getHijoDer().isConstant()) {
				String regAux = registers.getRegFreeInt(getHijoDer(), symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + regAux + ", " + dataFromRight);
				dataFromRight = regAux;
				getHijoDer().setAlmacenamiento(regAux);
			}
			assemblerCode.addMsg("imul " + dataFromRight);
			assemblerCode.addMsg("shl "  + regDX + ", 16");
			assemblerCode.addMsg("mov " + regDX + ", " + regAX);
			registers.freeReg(RegisterTable.AX);
			if (!getHijoDer().isVariableOrConst())
				registers.freeReg(registers.getRegPos(dataFromRight));
			regDX = registers.extendTo32bits(registers.getRegPos(RegisterTable.NAME_DX));			
			setAlmacenamiento(regDX);
		} else {//LONG
			String regEAX = registers.getReg(RegisterTable.NAME_EAX, getHijoIzq(), symbolTable, assemblerCode);
			@SuppressWarnings("unused")
			String regEDX = registers.getReg(RegisterTable.NAME_EDX, getHijoIzq(), symbolTable, assemblerCode); // Nuevamente se reserva para, si tiene algo, no pisarlo
			dataFromLeft = getHijoIzq().getAlmacenamiento();
			dataFromRight = getHijoDer().getAlmacenamiento();
			assemblerCode.addMsg("mov " + regEAX + ", " + dataFromLeft);
			if (getHijoDer().isConstant()) {
				String regAux = registers.getRegFreeLong(getHijoDer(), symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + regAux + ", " + dataFromRight);
				dataFromRight = regAux;
				getHijoDer().setAlmacenamiento(regAux);
			}
			assemblerCode.addMsg("imul " + dataFromRight);
			registers.freeReg(RegisterTable.EDX);
			if (!getHijoDer().isVariableOrConst())
				registers.freeReg(registers.getRegPos(dataFromRight));
			setAlmacenamiento(regEAX);
		}
		if (!getHijoIzq().isVariableOrConst()) {
			registers.freeReg(registers.getRegPos(dataFromLeft));
		}
		if (!getHijoDer().isVariableOrConst()) {
			registers.freeReg(registers.getRegPos(dataFromRight));
		}
	}

	private void division(RegisterTable registers, MsgStack assemblerCode,Hashtable<String, ElementoTS> symbolTable) {
		String dataFromLeft = getHijoIzq().getAlmacenamiento();
		String dataFromRight = getHijoDer().getAlmacenamiento();
		String regAuxControlZero = "";
		if ((int)(dataFromRight.charAt(0)) >= 48 && (int)(dataFromRight.charAt(0)) <= 57) { // Con constantes a izquierda se rompe la comparación
			if (symbolTable.get(dataFromRight).getVariableType().equals(ElementoTS.INT))
				regAuxControlZero = registers.getRegFreeInt(getHijoDer(), symbolTable, assemblerCode);
			else
				regAuxControlZero = registers.getRegFreeLong(getHijoDer(), symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + regAuxControlZero + ", " + dataFromRight);
			assemblerCode.addMsg("cmp " + regAuxControlZero + ", " + 0);
		} else
			assemblerCode.addMsg("cmp " + dataFromRight + ", " + 0);
		assemblerCode.addMsg("jz _msgDivisionPorCero"); //Salto a la subrutina de programa si el divisor es 0
		if (regAuxControlZero != "")
			registers.freeReg(registers.getRegPos(regAuxControlZero));

		if(isInt(dataFromLeft, symbolTable)) { //// ES ENTERO
			//pide registros para contener al dividendo
			String regAX = registers.getReg(RegisterTable.NAME_AX, getHijoIzq(), symbolTable, assemblerCode);
			@SuppressWarnings("unused")
			String regDX = registers.getReg(RegisterTable.NAME_DX, getHijoIzq(), symbolTable, assemblerCode);
			dataFromLeft = getHijoIzq().getAlmacenamiento();
			assemblerCode.addMsg("mov " + regAX + ", " + dataFromLeft); //divendo en AX
			assemblerCode.addMsg("cwd"); //extensión de signo para 16 bits
			String divider;
			if (getHijoDer().isVariableOrConst()) {
				divider = registers.getRegFreeInt(getHijoDer(), symbolTable, assemblerCode);
				dataFromRight = getHijoDer().getAlmacenamiento();
				assemblerCode.addMsg("mov " + divider + ", " + dataFromRight);//guarda divisor
			} else
				divider = getHijoDer().getAlmacenamiento();
			assemblerCode.addMsg("idiv " + divider);//DIVISION DX:AX / CX
			registers.freeReg(registers.getRegPos(divider));//LIBERA
			registers.freeReg(RegisterTable.DX);//LIBERA COCIENTE
			setAlmacenamiento(regAX);//DEVUELVE RESTO
		} else { //// ES LONG
			String regEAX = registers.getReg(RegisterTable.NAME_EAX, getHijoIzq(), symbolTable, assemblerCode);
			@SuppressWarnings("unused")
			String regEDX = registers.getReg(RegisterTable.NAME_EDX, getHijoIzq(), symbolTable, assemblerCode);
			dataFromLeft = getHijoIzq().getAlmacenamiento();
			assemblerCode.addMsg("mov " + regEAX + ", " + dataFromLeft); //divendo en EAX
			assemblerCode.addMsg("cdq"); //extension de signo para 32 bits
			String divider;
			if (getHijoDer().isVariableOrConst()) {
				divider = registers.getRegFreeLong(getHijoDer(), symbolTable, assemblerCode);
				dataFromRight = getHijoDer().getAlmacenamiento();
				assemblerCode.addMsg("mov " + divider + ", " + dataFromRight);//guarda divisor
			} else
				divider = getHijoDer().getAlmacenamiento();
			assemblerCode.addMsg("idiv " + divider);//DIVISION EDX:EAX / ECX
			registers.freeReg(registers.getRegPos(divider));
			registers.freeReg(RegisterTable.EDX);//libera resto
			setAlmacenamiento(regEAX);// devuelve cociente
		}
		if (!getHijoIzq().isVariableOrConst()) {
			registers.freeReg(registers.getRegPos(dataFromLeft));
		}
		if (!getHijoDer().isVariableOrConst()) {
<<<<<<< HEAD
			registros.freeReg(registros.getRegPos(dataFromRight));
>>>>>>> 51f241d... arreglos varios
=======
			registers.freeReg(registers.getRegPos(dataFromRight));
>>>>>>> 0fcca1b... varios
		}
>>>>>>> 154a393... comentario
	}

<<<<<<< HEAD
<<<<<<< HEAD
	public boolean esHoja(String dato) {
<<<<<<< HEAD
		return ((dato.charAt(0) == '_') || ((dato.charAt(0) <= '9')&&(dato.charAt(0) >= '0')));
=======
		return ((dato.charAt(0) == '_') || ((dato.charAt(0) <= '9') && (dato.charAt(0) >= '0'))); //TODO: ??????????
>>>>>>> 1375c5c... arreglos varios
	}
>>>>>>> 9acbfaf... comentario
=======
//	public boolean esHoja(String dato) {
//		return ((dato.charAt(0) == '_') || ((dato.charAt(0) <= '9') && (dato.charAt(0) >= '0')));
//	}
>>>>>>> a091e6e... arreglos por punteros null
=======
	private boolean isInt(String data, Hashtable<String, ElementoTS> symbolTable) {
		boolean result = (data == RegisterTable.NAME_AX) || (data == RegisterTable.NAME_BX) || (data == RegisterTable.NAME_CX) || (data == RegisterTable.NAME_DX);
		if (symbolTable.get(data) != null)
			result = result || (symbolTable.get(data).getVariableType().equals(ElementoTS.INT));
		if (data.charAt(0) == '_')
			result = result || (symbolTable.get(data.substring(1)).getVariableType().equals(ElementoTS.INT));
		return result;
	}
<<<<<<< HEAD
>>>>>>> bca257b... resueltos problemas en common
}
=======
}
>>>>>>> 51f241d... arreglos varios
