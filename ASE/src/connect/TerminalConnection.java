package connect;

import java.io.*;
import java.net.*;

public class TerminalConnection implements ITerminalConnection {
	private String host;
	private int port;
	private Socket terminaleSocket;
	private PrintWriter terminalOutput;
	private BufferedReader terminalInput;
	
	public TerminalConnection() {
		this.host = "";
		this.port = 0;
	}
	
	@Override
	public boolean terminalConnect(String host, String port) {
		this.host = host;
		this.port = Integer.parseInt(port);
		
		try {
			if((this.terminaleSocket = new Socket(this.host, this.port)) != null) {
				this.terminalOutput = new PrintWriter(this.terminaleSocket.getOutputStream(), true);
				this.terminalInput = new BufferedReader(new InputStreamReader(this.terminaleSocket.getInputStream()));
				
				return true;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void terminalDisconnect() {
		if(this.terminaleSocket != null) {
			try {
				this.terminalOutput.flush();
				this.terminalOutput.close();
				this.terminalInput.close();
				this.terminaleSocket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getTerminalResponse(String output) {
	    try {
	    	this.terminalOutput.println(output);
	    	
	    	return this.terminalInput.readLine();
		}
	    catch(IOException e) {
			e.printStackTrace();
			
			return e.getMessage();
		}
	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public String getPort() {
		return Integer.toString(this.port);
	}
}
