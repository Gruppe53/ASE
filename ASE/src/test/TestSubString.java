package test;

public class TestSubString {
	public static void main(String[] args) {
		String test = "[30400004] Natrium (100.0 g)";
		
		String idStr = test.substring(1,9);
		int id = Integer.parseInt(test.substring(1,9));
		
		System.out.println(idStr);
		System.out.println(id);
	}
}
