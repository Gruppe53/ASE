package program;

public interface ITerminal {
	String terminalConnect (String host, String port);
	String terminalDisconnect();
	String terminalRead();
	String terminalTare();
	String terminalZero();
	String terminalOk();
	String terminalOk2();
	String terminalDisplay();
	String getConnection();
}
