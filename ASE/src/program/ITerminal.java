package program;

public interface ITerminal {
	String terminalConnect (String host, String port);
	String terminalDisconnect();
	String terminalRead();
	String terminalTare();
	String terminalZero();
	String terminalOkWeight();
	String terminalOkProductBatch();
	String terminalDisplay();
	String getConnection();
}
