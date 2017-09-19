package syntax.Collections;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created by Administrator on 2017/8/24.
 */
public class LIST {

    //集合测试
    public   static  void  collection(){
        List<String> aList = new ArrayList<String>();
        aList.add("aaa");
        aList.add("aaa");
        aList.add("bbb");
        aList.add("ccc");
        List<String> bList = new ArrayList<String>();
        bList.add("aaa");
        bList.add("ddd");
        bList.add("eee");
        bList.add("aaa");
        bList.add("aaa");
        bList.add("aaa");
        // 并集
        Collection<String> unionList = CollectionUtils.union(aList, bList);
        // 交集
        Collection<String> intersectionList = CollectionUtils.intersection(aList, bList);
        // 是否存在交集
        boolean isContained = CollectionUtils.containsAny(aList, bList);
        // 交集的补集
        Collection<String> disjunctionList = CollectionUtils.disjunction(aList, bList);
        // 集合相减
        Collection<String> subtractList = CollectionUtils.subtract(aList, bList);

        // 排序
        Collections.sort((List<String>) unionList);
        Collections.sort((List<String>) intersectionList);
        Collections.sort((List<String>) disjunctionList);
        Collections.sort((List<String>) subtractList);

        // 测试
        System.out.println("A: " + ArrayUtils.toString(aList.toArray()));
        System.out.println("B: " + ArrayUtils.toString(bList.toArray()));
        System.out.println("A has one of B? : " + isContained);
        System.out.println("Union(A, B): "
                + ArrayUtils.toString(unionList.toArray()));
        System.out.println("Intersection(A, B): "
                + ArrayUtils.toString(intersectionList.toArray()));
        System.out.println("Disjunction(A, B): "
                + ArrayUtils.toString(disjunctionList.toArray()));
        System.out.println("Subtract(A, B): "
                + ArrayUtils.toString(subtractList.toArray()));
    }
    //测试随机数包含
    public  static void contain(){
        List<Double> list=new ArrayList<>();
        list.add(0.14);
        list.add(0.8);
        list.add(0.5);
        list.add(0.9);
        list.add(0.3);
        list.sort(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return  o1.compareTo(o2);
            }
        });

        System.out.println(list);

        double nextDouble = Math.random();
        System.out.println(list.indexOf(nextDouble));

    }

    //抽象抽象类转换
    public  static  void  convert(){
        Man man=new  Man();
        man.sleep();

        Woman woman=new  Woman();
        woman.sleep();
        abstractPeople ap=woman;
        ap.sleep();

        Animal animal=woman;

        List<Woman> womanList=new ArrayList<>();
        womanList.add(woman);
        abstractConvert(womanList);


    }

    ////	 java泛型的通配符的上边界和下边界 限定通配符的上边界：
//	 说明他能存放A或者是A的子类。 限定通配符的下边界：
    //List<? extends A> _List1=new ArrayList<B>();

    //	 说明它能存放的是B或者B的超类.
//	 注意： 限定通配符总是包括自己。
  //  List<? super B> _List2=new ArrayList<A>();
    private  static void abstractConvert(List <? extends  Animal>list){  //如果没有? extends则无法转换
       for (Object o :list){
           Animal animal =(Animal)o;
           System.out.println(animal);
       }
    }





    public static void main(String[] args) {
        collection();
    }



}

abstract class  Animal{

}

abstract class   abstractPeople extends  Animal{
    public  abstractPeople(){
        System.out.println("abstractPeople");
    }

    public  abstract  void  sleep();

}

class Man extends abstractPeople{

    public  Man(){
        System.out.println("man");
    }
    @Override
    public void sleep() {
        System.out.println("man:sleep 20");
    }
}


class Woman extends abstractPeople{

    public  Woman(){
        System.out.println("Woman");
    }
    @Override
    public void sleep() {
        System.out.println("Woman:sleep 10");
    }
}