package syntax.dynamic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

//java动态编译并执行演示
//http://blog.csdn.net/haitaofeiyang/article/details/44264231
public class TestCompil {
	public static void main(String[] args)  {
		//通过io流创建一个临时文件,然后动态编译
		String strjava=" public class TestJave{ public static void main(String[] args){System.out.println(\"nihao\");}}";
		String path="D:/workspace/images";
		String fname="TestJave.java";
		try {
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"/"+fname)));
			bw.write(strjava);
			bw.flush();
			bw.close();
			//动态编译
			JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
			int flag=compiler.run(null, null, null, path+"/"+fname);
			System.out.println(flag==0?"编译成功":"编译失败");
		//	两种动态执行编译方法
		//	1.通过Runtime.getRuntime();启动新的线程进行
			Runtime run=Runtime.getRuntime();
			Process process=run.exec("java -cp  "+fname+"     "+ fname);
			InputStream is =process.getInputStream();
			BufferedReader bis=new BufferedReader(new InputStreamReader(is));
			String info=null;
			while (null !=(info=bis.readLine())) {
			System.out.println(info);
			}
			//2.通过反射动态执行
		
				URL[] urls=new URL[]{new URL("file:/"+path+"/")};
				URLClassLoader loader=new URLClassLoader(urls);
				//通过反射调用此类
				Class clazz=loader.loadClass("TestJave");
				Method m=clazz.getMethod("main",String[].class);
				//m.invoke(null,new String[]{"aa","bb"});
				//由于可变参数是jdk5.0之后才有,上面代码会编译成m.invoke(null,"aa","bb");会发生参数不匹配的问题
				//由此必须加上Object 强转
				m.invoke(null,(Object)new String []{});
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
