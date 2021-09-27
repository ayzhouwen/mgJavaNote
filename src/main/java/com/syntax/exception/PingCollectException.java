package com.syntax.exception;

import lombok.Data;

@Data
public class PingCollectException extends RuntimeException{
    /**
     * rpc执行异常
     */
    public static final String RPC_ERRO = "RPC_ERRO";
    /**
     * 响应为空
     */
    public static final String RESP_NULL = "RESP_NULL";
    /**
     * 采集器节点URL为空
     */
    public static final String NODE_URL_NULL = "NODE_URL_NULL";
    private String message;
    private String code;

    public PingCollectException(String message, String code){
        this.message = message;
        this.code = code;
    };

    @Override
    public String toString() {
        return "PingCollectException 【message】：" + message + ", 【code】：" + code;
    }
}
