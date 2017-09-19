package com.meConcurrent.threadlocal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/30.
 */
public class ThreadLocalTest {
    public static void  test(){
        ThreadLocal<Map<String, Map<String, List>>> localChange = new ThreadLocal();
        //删除id列表
        List<A> jRemoveList = new ArrayList<A>();
        //修改完成后model列表
        List<A> jUpdateList = new ArrayList<A>();
        //新增的model列表
        List<A> jSaveList = new ArrayList<A>();
        Map<String, Map<String, List>> local = localChange.get();
        if (local == null) {
            local = new HashMap<>();
            Map<String, List> map = new HashedMap();
            map.put("jRemoveList", jRemoveList);
            map.put("jUpdateList", jUpdateList);
            map.put("jSaveList", jSaveList);
            local.put("goods", map);
            local.put("product", map);
            localChange.set(local);

        }


        A a = new A(3,"kaka");

        jRemoveList.add(a);
        A a1 = new A(5,"bibi");

        A a2 = new A(88,"xixi");
        a2.list.add(a);
        a2.list.add(a1);
        a2.list.add(a);
        a2.list.add(a1);
        jSaveList.add(a2);
        A root = new A(776,"root");
        jUpdateList.add(root);
        root.list.add(a);
        root.list.add(a1);
        root.list.add(a2);


        jRemoveList.add(a);


        local = localChange.get();

        System.out.println(local);
         JSONObject object= (JSONObject)JSON.toJSON(local);
        System.out.println(JSON.toJSONString(object));

        localChange.remove();
        localChange.set(null);

    }

    public static void main(String[] args) {
        test();
    }
}



class A {
    public  A(int id,String aname){
        this.id=id;
        this.Aname=aname;
    }
    protected int id;
    public String Aname;
    public  List<A> list=new ArrayList<>();
}

class B extends A {

    public B(int id, String aname) {
        super(id, aname);
    }
}