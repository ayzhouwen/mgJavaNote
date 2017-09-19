package syntax.dynamic.reflex;

public class StringBuilderTest {
	   String result="";
		private String buildString(int length){
			long start = System.currentTimeMillis();
				for(int i=0;i<length;i++){
					result+=(char)(i%26+'a');
				}
				 System.out.println("Call to buildString took " +
				            (System.currentTimeMillis()-start) + " ms.");
				return  result;
		}
	public static void main(String[] args) {
		StringBuilderTest inst=new StringBuilderTest();
		String [] teststr={"1","45","789","1234","12345","123456"};
		for(int i=0;i<teststr.length;i++){
			String result=inst.buildString(Integer.parseInt(teststr[i]));
			System.out.println("Constructed string of length " +
	                result.length());
		}
	}
}
