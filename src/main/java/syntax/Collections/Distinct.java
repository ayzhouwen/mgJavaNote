package syntax.Collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

//各种去重复的方法
public class Distinct {
   public  static   void f1(){
        List<String> listWithDup = new ArrayList<String>();
        listWithDup.add("2017"+"5"+"23");
        listWithDup.add("2017"+"5"+"23");
        listWithDup.add("2017"+"5"+"11");
        listWithDup.add("2017"+"5"+"11");

        List<String> listWithoutDup = new ArrayList<String>(new HashSet<String>(listWithDup));
        System.out.println("list with dup:"+ listWithDup);
        System.out.println("list without dup:"+ listWithoutDup);
       System.out.println("index:"+listWithoutDup.indexOf("2017"+"5"+"14"));
    }

    public static void main(String[] args) {
        Distinct.f1();
    }
}
