package com.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//todo 优化：不能只用行来判断文本（语言一旦太长换行出错），应该用正在在一个大文本中去匹配
public class SqlUtil {
    /**
     * 新建菜单sql
     */
    private static String insertIntoMenuFile="D:\\菜单.sql";
//    private static String insertIntoMenuFile="D:\\workspace\\jeecg-boot\\src\\main\\java\\org\\jeecg\\modules\\dwmterminal\\loco\\vue3\\V20241016_1__menu_insert_LocoCheckConf.sql";
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
        List<String> idList=new ArrayList<String>();
        String content = FileUtil.readUtf8String(filePath);
        Pattern pattern = Pattern.compile("INSERT.*\\s*.*;");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group());
            String sql=matcher.group();
            Pattern idPattern = Pattern.compile("VALUES\\s*\\('([^']*)'");
            Matcher idMatcher = idPattern.matcher(sql);
            //尝试删掉insert语句中数据库
            Pattern dataBasePattern = Pattern.compile("INSERT.*INTO `\\S*\\.");
            Matcher dataBaseMatcher = dataBasePattern.matcher(sql);
            if (dataBaseMatcher.find()) {
                sql= StrUtil.replace(sql,dataBaseMatcher.group(),"INSERT INTO ");
            }
            insertIntoMenuSqlList.add(sql);
            while (idMatcher.find()) {
                idList.add(idMatcher.group(1));
            }
        }
        System.out.println(idList);
        return idList;
    }


    /**
     * 根据新插入的菜单id创建jeecgboot 管理员的角色权限
     */
    public static void  create_sys_role_permission(){
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
                 sqlStatements.add("\n");
                 sql="delete from sys_role_permission where id = '" + id+"';";
                 sqlStatements.add(sql);
                 sql = "INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `operate_date`, `operate_ip`) VALUES ('" + id + "', '" + adminRoleId + "', '" + permissionId + "', '" + DateUtil.now() + "', '127.0.0.1');";
                 sqlStatements.add(sql);
                 sqlStatements.add("\n");
            }
            System.out.println(sqlStatements);
            FileUtil.writeLines(sqlStatements, rolePermissionFile, "UTF-8");

        }
    }
}
