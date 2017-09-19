package gc;
/*
 * 根据结果可以发现，三种生成的Class对象一样的。并且程序只打印一次“静态的参数初始化”。 
 
我们知道，静态的方法属性初始化，是在加载类的时候初始化。而非静态方法属性初始化，是new类实例对象的时候加载。
 
因此，这段程序说明，三种方式生成Class对象，其实只有一个Class对象。在生成Class对象的时候，首先判断内存中是否已经加载。
 
所以，生成Class对象的过程其实是如此的：
 
当我们编写一个新的Java类时,JVM就会帮我们编译成class对象,存放在同名的.class文件中。在运行时，当需要生成这个类的对象，JVM就会检查此类是否已经装载内存中。若是没有装载，则把.class文件装入到内存中。若是装载，则根据class文件生成实例对象。*/
public class TestClass {
	public static void main(String[] args) {

		try {
			// 测试.class
			@SuppressWarnings("rawtypes")
			Class testTypeClass = TestClassType.class;
			System.out.println("testTypeClass---" + testTypeClass);

			// 测试Class.forName()
			@SuppressWarnings("rawtypes")
			Class testTypeForName = Class.forName("gc.TestClassType");  //一定要加包名,否则找不到,System.out.println(System.getProperty("java.class.path"));用于输出默认类加载路径
			System.out.println("testTypeForName---" + testTypeForName);

			// 测试Object.getClass()
			TestClassType testTypeGetClass = new TestClassType();
			System.out.println("testTypeGetClass---"
					+ testTypeGetClass.getClass());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


 class  TestClassType {  
	  
    // 构造函数  
    public TestClassType() {  
        System.out.println("----构造函数---");  
    }  
  
    // 静态的参数初始化  
    static {  
        System.out.println("---静态的参数初始化---");  
    }  
  
    // 非静态的参数初始化  
    {  
        System.out.println("----非静态的参数初始化---");  
    }  
  
}  
