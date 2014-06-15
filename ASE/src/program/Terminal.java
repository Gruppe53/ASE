package program;

import connect.ITerminalConnection;

import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import databaseAccess.*;



public class Terminal implements ITerminal {
	
	private int okcount = 0;
	private ITerminalConnection con;
	private databaseAccess.Connector connector;
	
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
	public String terminalOkWeight() {
		
		if (okcount == 3){
			okcount = 0;
		}
		
		if (okcount == 0){
			okcount++;
			return terminalZero();
		}
		else {
			okcount++;
			return terminalTare();
		}
	
	}

	@Override
	public String terminalOkProductBatch() {
		return getReciept();
	}

	@Override
	public String getConnection() {
		return con.getHost() + ":" + con.getPort();
	}



	@Override
	public String terminalMessage(String msg) {
	con.getTerminalResponse("D " + msg);
		return "msg";
	}
	@Override
	public String terminalDisplay() {
		con.getTerminalResponse("DW");
		
		return "Weight is now displayed on scale";
	}
	private String getReciept() {
		ResultSet recieptNumber;
		String reciept;
		try {
			recieptNumber = connector.doQuery("SELECT");
			while(recieptNumber.next()) {
				reciept = recieptNumber.getString("SELECT");
			}
		} catch (DALException e) {
			e.printStackTrace();
		}
		return reciept;
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
