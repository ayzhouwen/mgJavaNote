package com.io.file.stream;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

//维护数据校验和。校验和是用于维护数据完整性的一项技术
//参考
//http://www.aiuxian.com/article/p-1259052.html
public class CheckedInputStreamTest {

    public void runCheckedInputStream() throws IOException{
        String path=CheckedInputStreamTest.class.getResource("/IOTest.zip").getPath();
        FileInputStream in =new FileInputStream(path);
        CheckedInputStream checked=new CheckedInputStream(in,new Adler32());
        byte[] b=new byte[4096];
        while((checked.read(b)) !=-1){

        }

        in.close();
        checked.close();
        System.out.println("chexksum: "+checked.getChecksum().getValue());
    }

    public static void main(String[] args) throws IOException {
        CheckedInputStreamTest cis=new CheckedInputStreamTest();
        cis.runCheckedInputStream();
    }
}
