package syntax;

//测试null关键字,.真是不测不知道,一侧真的吓一跳,以后千万不能眼高手低
//a=new Object(); d=c=b=a; a=null;
//总结:把a对象设为null,只是删除a对象与堆内存的关联,如果b,c,d有好几个对象同时与堆内存发生引用,那么a=null不会影响到b,c,d
//所以当开发缓存或集合时,需要即时的释放内存
public class testNull {
	public static void main(String[] args) {
		Stdu s1=	   new Stdu(); s1.id=1; s1.name="haha";
		Stdu s2=	   new Stdu(); s1.id=2; s1.name="haha2";
		Stdu s3=	   new Stdu(); s1.id=2; s1.name="haha3";
		Object list[]={s1,s2,s3};
		Stdu o=(Stdu) list[0];
		//Stdu o1=(Stdu) list[0];.
		Stdu o1=o;
	  o1.id=999;
	  Stdu o2=	(Stdu) list[0];
	  System.out.println(o2==o1);
		o1=null;
		list[0]=null;
		System.out.println(o);
	}
}


class Stdu{
	public int id;
	public String  name;
}
