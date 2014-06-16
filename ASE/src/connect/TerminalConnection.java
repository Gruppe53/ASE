package connect;

import java.io.*;
import java.net.*;
import program.*;

public class TerminalConnection implements ITerminalConnection {
	private String host;
	private int port;
	private Socket scaleSocket;
	private PrintWriter scaleOutput;
	private BufferedReader scaleInput;
	
	public TerminalConnection() {
		this.host = "";
		this.port = 0;
	}
	
	@Override
	public boolean terminalConnect(String host, String port) {
		this.host = host;
		this.port = Integer.parseInt(port);
		
		try {
			if((this.scaleSocket = new Socket(this.host, this.port)) != null) {
				this.scaleOutput = new PrintWriter(this.scaleSocket.getOutputStream(), true);
				this.scaleInput = new BufferedReader(new InputStreamReader(this.scaleSocket.getInputStream()));
				
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
		if(this.scaleSocket != null) {
			try {
				this.scaleOutput.flush();
				this.scaleOutput.close();
				this.scaleInput.close();
				this.scaleSocket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getTerminalResponse(String output) {
	    try {
	    	this.scaleOutput.println(output);
	    	
	    	return this.scaleInput.readLine();
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
