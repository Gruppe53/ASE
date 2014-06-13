package connect;

public interface ITerminalConnection {
	boolean terminalConnect(String host, String port);
	void terminalDisconnect();
	String getTerminalResponse(String output);
	String getHost();
	String getPort();
}
