package program;

import java.util.*;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String terminalTare() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String terminalZero() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String terminalMessage(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String terminalDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

}