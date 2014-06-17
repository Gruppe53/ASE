package program;


public interface ITerminal {
	String terminalConnect (String host, String port);
	String terminalDisconnect();
	String terminalRead();
	String terminalTare();
	String terminalZero();
	String terminalMessage(String msg);
	String terminalOkWeight(String productBatchNumber) throws Exception;
	String terminalOkGetPrescription(String productBatchNumber) throws Exception;
	String terminalOkGetMaterialId(String productBatchNumber) throws Exception;
	String terminalOkGetMaterialName(String productBatchNumber) throws Exception;
	String terminalDisplay();
	String getConnection();
	double getCurrentWeight();
	boolean tolerableWeight(String productBatchNumber) throws Exception;
}
