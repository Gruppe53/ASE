package users;

public interface IOperatorDAO {
	String getOperator(int id) throws DALException;
	boolean tryLogin(int id, char[] password);
	String getInitials(int id);
	boolean getActive();
	void userLogout();
}
