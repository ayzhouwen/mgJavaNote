package syntax.dynamic.reflex;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;




//https://www.ibm.com/developerworks/cn/java/j-dyn0603/
//动态性之反射
public class TwoString {
		private int num;
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		private String m_s1,m_s2;
		public  TwoString(){};
		public TwoString(String s1,String s2){
			m_s1=s1;
			m_s2=s2;
			System.out.println("构造函数:m_s1="+m_s1+",m_s2="+m_s2);
		}
		
		public void testConstructor(){

			//通过反射来实现类的实例化指定特定的构造函数
			Class[] types=new Class[]{String.class,String.class};
			try {
				Constructor cons=TwoString.class.getConstructor(types);
				Object [] parg=new Object[]{"a","b"};
				TwoString ts=(TwoString) cons.newInstance(parg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		public static void main(String[] args) {
		TwoString ts=new TwoString();
			ts.incrementField("num", ts);
			ts.incrementProperty("num", ts);
			 
			Integer [] intArr=new Integer[] {1,2,3,4};
			ts.growArray(intArr, 2);
			
			
			
	           
		}
		
		//通过反射改变指定字段的状态值
		public int incrementField(String name ,Object obj) {
			try {
				
				Field field=obj.getClass().getDeclaredField(name);
				int value=field.getInt(obj)+1;
				field.setInt(obj, value);
				return value;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
		
		//通过反射调用类的方法
		
		public  int incrementProperty(String name,Object obj){
			try {
				String prop=Character.toUpperCase(name.charAt(0))+name.substring(1);
				String mname="get"+prop;
				Class[] types=new Class[]{};
				Method method=obj.getClass().getMethod(mname, types);
				Object result=method.invoke(obj, new Object[0]);
				int value=((Integer)result).intValue()+1;
				mname="set"+prop;
				types=new Class[]{int.class};
				method=obj.getClass().getMethod(mname, types);
				method.invoke(obj, new Object[] {new Integer(value)});
				return value;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			return -1;
		}
		
		//通过反射来扩展一个数组
public		 Object growArray(Object array,int size){
	Object grown=null;
	try {
		
		Class type=array.getClass().getComponentType();
		 grown=Array.newInstance(type,size);
		System.arraycopy(array, 0, grown,0,Math.min(Array.getLength(array), size));
	} catch (Exception e) {
		e.printStackTrace();
	}

	return grown;
}



 
 
}
