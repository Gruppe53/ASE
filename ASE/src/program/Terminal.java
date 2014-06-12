package program;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Terminal implements ITerminal {
	private ITerminalConnection con;
	private int runtime = 0;
	
	public Terminal(ITerminalConnection con) {
		this.con = con;
	}

	@Override
	public String terminalConnect(String host, String port) {
		if(con.terminalConnect(host, port))
			return getConnection();
		else
			return "Could not connect";
	}

	@Override
	public String terminalDisconnect() {
	con.terminalDisconnect();
		
		return getConnection();
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
		
		if (runtime == 3){
			runtime = 0;
		}
		
		if (runtime == 0){
			runtime++;
			return terminalZero();
		}
		else {
			runtime++;
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
