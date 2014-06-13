package databaseAccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Connector {

	public static Connection connectToDatabase(String url, String username, String password)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException
			{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		return (Connection) DriverManager.getConnection(url, username, password);
			}

	private static Connection conn;
	private static Statement stm;

	public Connector(String server, int port, String database, String username, String password)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
			{
		conn = connectToDatabase("jdbc:mysql://" + server + ":" + port + "/" + database, username, password);
		stm = conn.createStatement();
			}

	public Connector() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		this(MySQLserverId.server, MySQLserverId.port, MySQLserverId.database, MySQLserverId.username, MySQLserverId.password);
	}


	public static ResultSet doQuery(String str) throws DALException {
		try { return stm.executeQuery(str); }
		catch (SQLException e) { throw new DALException(e);}
	}
	
	public static int doUpdate(String str) throws DALException {
		try { return stm.executeUpdate(str); }
		catch (SQLException e) { throw new DALException(e); }
	}
}