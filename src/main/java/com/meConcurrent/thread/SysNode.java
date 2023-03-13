package com.meConcurrent.thread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统节点信息
 */
@Data
@Slf4j
public class SysNode {
    String ip;
    Integer port;
    public String getMyUlr(){
        return this.ip +this.port;
    }
}
