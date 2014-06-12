package Users;

import java.util.ArrayList;

public interface IOperatorDAO {
	String getOperator(String opr) throws DALException;
	ArrayList<String> getOperatorList() throws DALException;
	boolean tryLogin(String oprNr, char[] password);
	String getInitials(String opr);
	boolean getActive();
	void userLogout();
}
