
public class Test2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		String str = "12'3456--abcd";
		
		System.out.println(str.replaceAll("'", ""));
		System.out.println(str.replaceAll("'", "").replaceAll("-", ""));
	}
}
