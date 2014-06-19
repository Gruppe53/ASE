package databaseAccess;

public class DBInfo {
	private String DBHost;
	private int DBPort;
	private String DBDatabase;
	private String DBUserName;
	private String DBPassword;
	final int whichserver = 2;

	public DBInfo() {
		 

		if (this.whichserver == 1){
			this.DBHost = "localhost";
			this.DBPort = 3306;
			this.DBDatabase = "gruppe55";
			this.DBUserName = "root";
			this.DBPassword = "";
		}
		if (this.whichserver == 2){
			this.DBHost = "72.13.93.206";
			this.DBPort = 3307;
			this.DBDatabase = "gruppe55";
			this.DBUserName = "gruppe55";
			this.DBPassword = "55gruppe";
		}
	}


	public String getDBHost() {
		return this.DBHost;
	}

	public int getDBPort(){
		return this.DBPort;
	}

	public String getDBDatabase() {
		return this.DBDatabase;
	}

	public String getDBUserName() {
		return this.DBUserName;
	}

	public String getDBPassword() {
		return this.DBPassword;
	}

}
