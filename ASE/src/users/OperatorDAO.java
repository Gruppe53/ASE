package users;

import java.util.*;

public class OperatorDAO implements IOperatorDAO {
	private IOperatorDTO opr;
	private int active;

	public OperatorDAO(IOperatorDTO opr) {
		this.opr = opr;
	}

	@Override
	public String getOperator(int id) throws DALException {
		return opr.getName(id) +  ", ID: " + opr.getOprId(id);
	}


	@Override
	public String getInitials(int id) {
		String ini = "";
		
		for(String s : opr.getName(id).split(" "))
			ini += s.substring(0,1).toLowerCase();
		
		return ini;
	}

	@Override
	public boolean tryLogin(int id, char[] password) {
		if(Arrays.equals(opr.getPassword(id).toCharArray(), password)) {
			active = id;
			password = null;
			
			return true;
		}
			
		return false;
	}

	@Override
	public boolean getActive() {
		if(active != (Integer) null)
			return true;
		
		return false;
	}

	@Override
	public void userLogout() {
		active = (Integer) null;
	}
}