package users;

import java.util.*;

public class OperatorDTO implements IOperatorDTO {
	ArrayList<Operator> oprs = new ArrayList<Operator>();
	
	public class Operator {
		int id;
		String name;
		String opr;
		String password;
		
		public Operator(int id, String name, String opr, String password) {
			super();
			this.id = id;
			this.name = name;
			this.opr = opr;
			this.password = password;
		}
		
		public Operator(String name, String opr, String password) {
			super();
			this.id = oprs.get(oprs.size() - 1).id + 1;
			this.name = name;
			this.password = password;
		}
	}
	public String getName(int id) {
		for (int i = 0; i < oprs.size(); i++)
			if(oprs.get(i).opr.equals(id))
				return oprs.get(i).name;
		return null;
	}
	

	public int getOprId(int id) {
		for (int i = 0; i < oprs.size(); i++)
			if(oprs.get(i).opr.equals(id))
				return oprs.get(i).id;
		return -1;
	}
	
	public String getPassword(int id) {
		for (int i = 0; i < oprs.size(); i++)
			if(oprs.get(i).opr.equals(id))
				return oprs.get(i).password;
		return null;
	}
	
}
