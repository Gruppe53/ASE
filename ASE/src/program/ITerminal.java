package program;

public interface ITerminal {
	String terminalConnect (String host, String port);
	String terminalDisconnect();
	String terminalRead();
	String terminalTare();
	String terminalZero();
	String terminalOk();
	String terminalDisplay();
	String getConnection();
}
