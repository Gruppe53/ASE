package program;

import java.io.*;
import java.net.*;

public class TerminalConnection implements ITerminalConnection {
	private String host;
	private int port;
	private Socket terminalSocket;
	private PrintWriter terminalOutput;
	private BufferedReader terminalInput;

	public TerminalConnection() {
		this.host = "";
		this.port = 0;
	}

	@Override
	public boolean terminalConnect(String host, String port) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void terminalDisconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTerminalResponse(String output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPort() {
		// TODO Auto-generated method stub
		return null;
	}
}
