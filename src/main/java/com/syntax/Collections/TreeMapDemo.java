package com.syntax.Collections;

import com.meConcurrent.Queue.Student;
import lombok.ToString;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 摘自 java程序性能优化(葛一鸣) 96页
 *注意:treemap只能根据key进行排序,如果要根据value排序,需要将map转list
 */
public class TreeMapDemo {

   static class  Student implements Comparable<Student>{
        private String name;
        private int score;
        public Student(String name,int s){
            this.name=name;
            this.score=s;
        }
        @Override
        public int compareTo(Student o) {
            if (o.score<this.score) return 1;
            else if (o.score>this.score) return -1;
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder sb=new StringBuilder();
            sb.append("name: ");
            sb.append(name);
            sb.append(" ");
            sb.append("score:");
            sb.append(score);
            return sb.toString();
        }
    }

    static class StudentDetilInfo{
       Student s;
       public StudentDetilInfo(Student s){
           //建立相关学生的详细信息,这里忽略
           this.s=s;
       }

        @Override
        public String toString() {
            return s.name+"'s detail infomation";
        }
    }

    public static void main(String[] args) {
        TreeMap map=new TreeMap();
        Student s1=new Student("Billy",70);
        Student s2=new Student("David",85);
        Student s3=new Student("Kite",92);
        Student s4=new Student("Cissy",68);
        map.put(s1,new StudentDetilInfo(s1));
        map.put(s2,new StudentDetilInfo(s2));
        map.put(s3,new StudentDetilInfo(s3));
        map.put(s4,new StudentDetilInfo(s4));

        //筛选出成绩介于Ciisy和David之间的学生
        Map map1=map.subMap(s4,s2);
        for(Iterator iterator=map1.keySet().iterator();iterator.hasNext();){
            Student key= (Student) iterator.next();
            System.out.println(key+"->"+map1.get(key));
        }
        System.out.println("subMap end");

        //筛选出成绩低于Billy的学生
        map1=map.headMap(s1);
        for(Iterator iterator=map1.keySet().iterator();iterator.hasNext();){
            Student key= (Student) iterator.next();
            System.out.println(key+"->"+map1.get(key));
        }
        System.out.println("headMap end");

        //筛选出成绩高于Billy的学生
        map1=map.tailMap(s1);
        for(Iterator iterator=map1.keySet().iterator();iterator.hasNext();){
            Student key= (Student) iterator.next();
            System.out.println(key+"->"+map1.get(key));
        }
        System.out.println("headMap end");
    }
}
