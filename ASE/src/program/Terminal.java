package program;

import connect.ITerminalConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import databaseAccess.*;



public class Terminal implements ITerminal {
	
	private int okcount = 0;
	private ITerminalConnection con;
	
	public Terminal(ITerminalConnection con) {
		this.con = con;
	}

	@Override
	public String terminalConnect(String host, String port) {
		if(con.terminalConnect(host, port))
			return getConnection();
		else
			return "Could not connect";
	}

	@Override
	public String terminalDisconnect() {
	con.terminalDisconnect();
		
		return getConnection();
	}

	@Override
	public String terminalRead() {
		String str = con.getTerminalResponse("S");
		
		return "Current scale weight: " + getDigit(str) + " " + getUnit(str);
	}

	@Override
	public String terminalTare() {
		String str = con.getTerminalResponse("T");
		
		return "Scale tared, tara reads: " + getDigit(str) + " " + getUnit(str);
	}

	@Override
	public String terminalZero() {
		con.getTerminalResponse("Z");
		
		return "Scale has been zeroed.";
	}

	@Override
	public String terminalOkWeight() {
		
		if (okcount == 3){
			okcount = 0;
		}
		
		if (okcount == 0){
			okcount++;
			return terminalZero();
		}
		else {
			okcount++;
			return terminalTare();
		}
	
	}

	@Override
	public String terminalOkProductBatch(String productBatchNumber) {
		return getPrescription(productBatchNumber);
	}
	
	@Override
	public String getConnection() {
		return con.getHost() + ":" + con.getPort();
	}



	@Override
	public String terminalMessage(String msg) {
	con.getTerminalResponse("D " + msg);
		return "msg";
	}
	@Override
	public String terminalDisplay() {
		con.getTerminalResponse("DW");
		
		return "Weight is now displayed on scale";
	}
	private String getPrescription(String productBatchNumber) {
		ResultSet prescriptionNumber;
		String prescription = null;
		try {
			prescriptionNumber = Connector.doQuery("SELECT pre_id FROM productbatch WHERE pb_id = '" + productBatchNumber + "'");
			if(prescriptionNumber.next()) {
				prescription = prescriptionNumber.getString("pre_id");
			}
			else{
				return prescription = null;
			}
		} 
		catch (DALException e) {
			e.printStackTrace();			
		}
		catch (SQLException f) {
			f.printStackTrace();
		}
		return prescription;
	}
	
	private String getMaterialId(String productBatchNumber){
		String prescriptionId = getPrescription(productBatchNumber);
		ResultSet materialIdResultSet;
		String materialIdString = null;
		try{
			materialIdResultSet = Connector.doQuery("SELECT m_id FROM precomponent WHERE pre_id = '" + prescriptionId + "'");
			if(materialIdResultSet.next()){
				materialIdString = materialIdResultSet.getString("m_id");
			}
		}
		catch (DALException e) {
			e.printStackTrace();
		}
		catch (SQLException f) {
			f.printStackTrace();
		}
		return materialIdString;
	}
	
	private double getNetto(String productBatchNumber){
		String materialId = getMaterialId(productBatchNumber);
		String prescriptionId = getPrescription(productBatchNumber);
		ResultSet nettoResultSet;
		double nettoDouble = 0;
		try{
			nettoResultSet = Connector.doQuery("SELECT netto FROM precomponent WHERE pre_id = '" + prescriptionId + "' AND m_id = '" + materialId + "'");
			if(nettoResultSet.next()){
				nettoDouble = nettoResultSet.getDouble("netto");
			}
		}
		catch (DALException e) {
			e.printStackTrace();
		}
		catch (SQLException f) {
			f.printStackTrace();
		}
		return nettoDouble;
	}

	private double getTolerance(String productBatchNumber){
		String materialId = getMaterialId(productBatchNumber);
		String prescriptionId = getPrescription(productBatchNumber);
		ResultSet toleranceResultSet;
		double tolerancedouble = 0;
		try{
			toleranceResultSet = Connector.doQuery("SELECT tolerance FROM precomponent WHERE pre_id = '" + prescriptionId + "' AND m_id = '" + materialId + "'");
			if(toleranceResultSet.next()){
				tolerancedouble = toleranceResultSet.getDouble("tolerance");
			}
		}
		catch (DALException e) {
			e.printStackTrace();
		}
		catch (SQLException f) {
			f.printStackTrace();
		}
		return tolerancedouble;
	}

	//private String getMaterial(String +0);
	private String getDigit(String str) {
		String res = "";
		
		Pattern p = Pattern.compile("-?[\\d+.\\d+]");
		Matcher m = p.matcher(str);
		
		while(m.find()) {
			res += m.group();
		}
		
		return res;
	}
	
	private String getUnit(String str) {
		return str.substring(str.length() - 2).trim();
	}
}
