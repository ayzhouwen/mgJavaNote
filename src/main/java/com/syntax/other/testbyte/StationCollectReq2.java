package com.syntax.other.testbyte;

import lombok.Data;


/**
 * 请求体中不同的数据类型转json字符串后,字符串字节大小测试,这里id是字符串,数字类型全是包装类
 *
 */
@Data
public class StationCollectReq2 {
    /**
     *
     * @param assetId
     * @param collectItem
     * @param reqId
     * @param cycleId
     */
    public StationCollectReq2(String assetId, Integer collectItem, Long reqId,
                              Long cycleId, String offsetId, Integer limit){
        this.assetId=assetId;
        this.collectItem=collectItem;
        this.reqId =reqId;
        this.cycleId=cycleId;
        this.offsetId=offsetId;
        this.limit=limit;
    };
    //资产id
    private String assetId;
    //采集项目 对应 redis推送文档
    private Integer collectItem;
    //请求id
    private Long reqId;
    //分配周期id
    private Long  cycleId;
    //偏移id,跳过该id来取数据
    private String offsetId;
    //从偏移id往后取多少条数据
    private Integer limit;

    public static void main(String[] args) {

    }


}
