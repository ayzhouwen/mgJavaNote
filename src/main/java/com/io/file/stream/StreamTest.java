package com.io.file.stream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class StreamTest {
    public static void main(String[] args) {
        try {
            byte [] buf=new byte[1024];
            InputStream inStream=new FileInputStream("");
            try {
                inStream.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
