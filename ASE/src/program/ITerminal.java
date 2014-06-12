package program;

public interface ITerminal {
	String terminalConnect (String host, String port);
	String terminalDisconnect();
	String terminalRead();
	String terminalTare();
	String terminalZero();
	String terminalMessage(String msg);
	String terminalDisplay();
	String getConnection();
}
