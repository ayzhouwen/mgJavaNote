package syntax;

//各种默认值测试如数组,列表等
public class DefaultTest {

	static {
		System.out.println("静态方法快");
	}

	{
		System.out.println("普通方法快"); //每次new 都执行
	}
	public void test(){
	
		int size=10;
		double [] darr= new  double[size];
		Double [] Darr= new  Double[size];
		String [] DarrStr= new  String[size];
	}
	public static void main(String[] args) {
		DefaultTest dt=new DefaultTest();
		dt.test();

		DefaultTest dt2=new DefaultTest();

	}
}
