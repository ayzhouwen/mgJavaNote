package com.designPattern.flyweight.demo1;

//员工财务报表
public class FinancialReportManager implements  IReportManager {
   protected  String tenantId=null; //租户id
    public  FinancialReportManager(String tenantId){
        this.tenantId=tenantId;
    }
    @Override
    public String createReport() {
        return "这是财务报表";
    }
}


