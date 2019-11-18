package utils;


import java.util.ArrayList;

public class RegisterTable {

	private ArrayList< Boolean > regState; // False=libre | True=ocupado

	public RegisterTable(int size) {
		this.regState=new ArrayList< Boolean >(size);
		boolean state=false;
		for(int i=0; i<this.regState.size();i++)  
		{
			this.regState.set(i, state);
		}
	}

	public String getRegFreeInt() {//Solo devuelve los registros BX, CX y DX
		String reg = null;
		for(int i=0; i<this.regState.size();i++){
			if(!(this.regState.get(i))) {
				switch(i){
					case 0: reg="AX";
					case 1: reg="BX";
					case 2: reg="CX";
					case 3: reg="DX";
				}
				this.regState.set(i, true);
				break;
			}
		}

		return reg;
	}

	public String getRegFreeLong() {//Solo devuelve los registros EBX, ECX y EDX
		String reg = null;
		for(int i=0; i<this.regState.size();i++)  
		{
			if(!(this.regState.get(i))) {
				switch(i)
				{
				case 0: reg="EAX";
				case 1: reg="EBX";
				case 2: reg="ECX";
				case 3: reg="EDX";
				}
				this.regState.set(i, true);

				break;}
		}

		return reg;
	}

	public void setRegTable(String reg, boolean state) {
		int index=0;
		if(reg == "EBX" || reg=="BX")
			index=1;
		else
			if(reg == "ECX" || reg=="CX")
				index=2;
			else
				index=3;
		this.regState.set(index, state);
	}

	public String getRegAX() {
		this.regState.set(0,true);
		return "AX";
	}
	
	public String getRegEAX() {
		this.regState.set(0,true);
		return "EAX";
	}

}

