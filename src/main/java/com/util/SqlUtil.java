package com.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtil {
    /**
     * 新建菜单sql
     */
    private static String insertIntoMenuFile="D:\\菜单.sql";
    /**
     * 插入菜单列表
     */
    private static List<String> insertIntoMenuSqlList=new ArrayList<>();
    private static String rolePermissionFile="D:\\rolePermission.sql";
    /**
     * 管理员角色id
     */
    private static String adminRoleId="f6817f48af4fb3af11b9e8bf182f618b";
    public static void main(String[] args) {

        create_sys_role_permission();
    }

    //读取插入语句的主键id
    public static List<String> getIdList(){
        String filePath = insertIntoMenuFile; // 替换为实际的文件路径
        List<String> ioList=new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Pattern pattern = Pattern.compile("VALUES\\s+\\('([^']+)'.*");
            while ((line = br.readLine())!= null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    ioList.add(matcher.group(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ioList);
        return ioList;
    }

    public static List<String> getInsertIntoMenuSqlList(){


        try (BufferedReader br = new BufferedReader(new FileReader(insertIntoMenuFile))) {
            String line;
            String regex = "INSERT INTO `sys_permission`";
            Pattern pattern = Pattern.compile(regex);

            while ((line = br.readLine())!= null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    insertIntoMenuSqlList.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       return  insertIntoMenuSqlList;
    }

    /**
     * 根据新插入的菜单id创建jeecgboot 管理员的角色权限
     */
    public static void  create_sys_role_permission(){
        //读取sql文
        getInsertIntoMenuSqlList();
        List<String> idList= getIdList();
        if (CollUtil.isNotEmpty(idList)){
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            List<String> sqlStatements = new ArrayList<>();
            for (String permissionId : idList) {
                //新插入菜单sql
                String menuSql="";
                for (int i = 0; i <insertIntoMenuSqlList.size() ; i++) {
                    if (insertIntoMenuSqlList.get(i).contains(permissionId)){
                        menuSql=insertIntoMenuSqlList.get(i);
                        break;
                    }
                }
                long id = snowflake.nextId();
                //先删除后新增
                 String sql = "delete from sys_permission where id = '" + permissionId+"';";
                 sqlStatements.add(sql);
                 sqlStatements.add(menuSql);

                 sql="delete from sys_role_permission where id = '" + id+"';";
                 sqlStatements.add(sql);
                 sql = "INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `operate_date`, `operate_ip`) VALUES ('" + id + "', '" + adminRoleId + "', '" + permissionId + "', '" + DateUtil.now() + "', '127.0.0.1');";
                 sqlStatements.add(sql);
            }
            System.out.println(sqlStatements);
            FileUtil.writeLines(sqlStatements, rolePermissionFile, "UTF-8");

        }
    }
}
