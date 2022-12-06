package com.syntax.other.testbyte;

import lombok.Data;


/**
 * 请求体中不同的数据类型转json字符串后,字符串字节大小测试,这里id是long,数字类型全是基本类
 *
 */
@Data
public class StationCollectReq {
    /**
     *
     * @param assetId
     * @param collectItem
     * @param reqId
     * @param cycleId
     */
    public StationCollectReq(long assetId, int collectItem, long reqId,
                             long cycleId, long offsetId, int limit){
        this.assetId=assetId;
        this.collectItem=collectItem;
        this.reqId =reqId;
        this.cycleId=cycleId;
        this.offsetId=offsetId;
        this.limit=limit;
    };
    //资产id
    private long assetId;
    //采集项目 对应 redis推送文档
    private int collectItem;
    //请求id
    private long reqId;
    //分配周期id
    private long  cycleId;
    //偏移id,跳过该id来取数据
    private long offsetId;
    //从偏移id往后取多少条数据
    private int limit;

    public static void main(String[] args) {

    }


}
