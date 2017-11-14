package com.aio.channel.Tcpserver;
// 代码来源  http://codepub.cn/2016/02/26/Asynchronous-non-blocking-message-communication-framework-based-on-Java-NIO2/

import java.nio.ByteBuffer;

/**
 * <p>
 * Created with IntelliJ IDEA. 16/2/24 16:57
 * </p>
 * <p>
 * ClassName:ServerOnFuture
 * </p>
 * <p>
 * Description:基于Future的NIO2服务端实现，此时的服务端还无法实现多客户端并发，如果有多个客户端并发连接该服务端的话，
 * 客户端会出现阻塞，待前一个客户端处理完毕，服务端才会接受下一个客户端的连接并处理
 * </P>
 *
 * @author Wang Xu
 * @version V1.0.0
 * @since V1.0.0
 * WebSite: http://codepub.cn
 * Licence: Apache v2 License
 */
public class ServerOnFuture {
    static  final  int DEFAULT_PORT=7777;
    static  final String IP="127.0.0.1";
    static ByteBuffer buffer=ByteBuffer.allocateDirect(1024);

    public static void main(String[] args) {
    //    Path
    }

}
