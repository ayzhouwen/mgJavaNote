package com.nio.TcpServer.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class BuffUtil {
    public static String getString(ByteBuffer buffer) throws CharacterCodingException {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;

            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
             charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
            //charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();


    }
}
