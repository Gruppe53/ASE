package program;

public interface ITerminal {
	String terminalConnect (String host, String port);
	String terminalDisconnect();
	String terminalRead();
	String terminalTare();
	String terminalZero();
	String terminalMessage(String msg);
	String terminalOkWeight();
	String terminalOkGetPrescription(String productBatchNumber);
	String terminalOkGetMaterialId(String productBatchNumber);
	String terminalOkGetMaterialName(String productBatchNumber);
	String terminalDisplay();
	String getConnection();
	double getCurrentWeight();
	boolean tolerableWeight(String productBatchNumber);
}
