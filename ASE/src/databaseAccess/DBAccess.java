package databaseAccess;

import java.sql.*;

public class DBAccess {
	private Connection connect					= null;
	private Statement statement					= null;
	private ResultSet resultSet					= null;
	
	private String DBHost;
	private int DBPort;
	private String DBDatabase;
	private String DBUserName;
	private String DBPassword;
	
	public DBAccess(String DBHost, int DBPort, String DBDatabase, String DBUserName, String DBPassword) {
		this.DBHost = DBHost;
		this.DBPort = DBPort;
		this.DBDatabase = DBDatabase;
		this.DBUserName = DBUserName;
		this.DBPassword = DBPassword;
	}
	
	public ResultSet doSqlQuery(String query) throws Exception, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			connect = DriverManager.getConnection("jdbc:mysql://" + DBHost + ":" + DBPort + "/" + DBDatabase, DBUserName, DBPassword);
			
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);

			return resultSet;
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public int doSqlUpdate(String query) throws Exception, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			connect = DriverManager.getConnection("jdbc:mysql://" + DBHost + ":" + DBPort + "/" + DBDatabase, DBUserName, DBPassword);
			
			statement = connect.createStatement();

			return statement.executeUpdate(query);
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public void closeSql() throws SQLException {
		this.resultSet.close();
		this.statement.close();
		this.connect.close();
	}
}