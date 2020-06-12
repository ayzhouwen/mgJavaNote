package com.syntax;

import java.util.StringTokenizer;

public class StringTokenizerTest {
    public static void main(String[] args) {
        StringTokenizer exprsTok = new StringTokenizer("www.baidu.com.bbc", ".",
                true);
        while(exprsTok.hasMoreElements()){
            System.out.println("Token:" + exprsTok.nextToken());
        }


    }
}
