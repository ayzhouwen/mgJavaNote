package com.designPattern.flyweight.demo1;

//员工个人信息报表
public class EmployeeReportManager implements  IReportManager {
    protected String tenantId=null;
    public EmployeeReportManager(String tenantId){
        this.tenantId=tenantId;
    }
    @Override
    public String createReport() {
        return "这是员工报表";
    }
}
