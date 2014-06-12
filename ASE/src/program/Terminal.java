package program;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Terminal implements ITerminal {
	private ITerminalConnection con;
	
	public Terminal(ITerminalConnection con) {
		this.con = con;
	}

	@Override
	public String terminalConnect(String host, String port) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String terminalDisconnect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String terminalRead() {
		String str = con.getTerminalResponse("S");
		
		return "Current scale weight: " + getDigit(str) + " " + getUnit(str);
	}

	@Override
	public String terminalTare() {
		String str = con.getTerminalResponse("T");
		
		return "Scale tared, tara reads: " + getDigit(str) + " " + getUnit(str);
	}

	@Override
	public String terminalZero() {
		con.getTerminalResponse("Z");
		
		return "Scale has been zeroed.";
	}

	@Override
	public String terminalOk() {
		con.getTerminalResponse("Ok");
		
		int i = 0;
		
		if (i==0){
			i++;
			return terminalZero();
			
		}
		else{
			return terminalTare();
		}
	
	
	}


	@Override
	public String getConnection() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String terminalDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getDigit(String str) {
		String res = "";
		
		Pattern p = Pattern.compile("-?[\\d+.\\d+]");
		Matcher m = p.matcher(str);
		
		while(m.find()) {
			res += m.group();
		}
		
		return res;
	}
	
	private String getUnit(String str) {
		return str.substring(str.length() - 2).trim();
	}
}
