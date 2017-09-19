package syntax.dynamic.reflex;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.CtNewMethod;
//清单4. 用 Javassist 添加拦截器方法,先复制原方法
//https://www.ibm.com/developerworks/cn/java/j-dyn0916/
public class JassistTiming {
		public static void main(String[] args) {
				String cname="StringBuilderTest";
				try {
					CtClass clas=ClassPool.getDefault().get(cname);
					if (clas==null) {
						System.out.println("class"+cname+"not found");
					}else {
						String funName="buildString";
						addTiming(clas, funName);
						clas.writeFile();
						System.out.println("added timing to method "+cname+"."+funName);
					}
				} catch (Exception e) {
						e.printStackTrace();
				}
				
		}
		
		private static void addTiming(CtClass clas,String mname){
			try {
				CtMethod mold=clas.getDeclaredMethod(mname);
				String nname=mname+"$impl";
				mold.setName(nname);
				CtMethod mnew=CtNewMethod.copy(mold, mname, clas,null);
				String type=mold.getReturnType().getName();
				StringBuffer body=new StringBuffer();
				body.append("{\n long start=System.currentTimeMillis();\n");
				if (!"void".equals(type)) {
					body.append(type+"result =");
				}
				body.append(nname+"($$);\n");
				body.append("System.out.println(\"Call to method "+mname+"  took \"+\n (System.currentTimeMillis()-start)+"+
							"\"   ms. \");\n ");
				body.append("}");
				mnew.setBody(body.toString());
				clas.addMethod(mnew);
				System.out.println("Interceptor  method  body:");
				System.out.println(body.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}


