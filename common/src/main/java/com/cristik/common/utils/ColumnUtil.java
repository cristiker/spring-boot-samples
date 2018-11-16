package com.cristik.common.utils;

/**
 * @Package: com.cristik.common.utils.ColumnUtil
 * @ClassName: ColumnUtil.java
 * @Description:
 * @Author: cristik
 * @CreateDate: 2016/7/13 14:46
 * @Version: v1.0
 */
public class ColumnUtil {

    static char[] a_z = "abcdefghijklmnopqrstwvuxyz".toCharArray();
    static char[] A_Z = "abcdefghijklmnopqrstwvuxyz".toUpperCase().toCharArray();



    public static String columnToProperty2(String column) {
        if (column == null || column.equals("")) {
            return "";
        }
        StringBuilder sb = new StringBuilder(column.length());
        int i = 0;
        int length = column.length();
        for (int j = 0; j < length; j++) {
            if (column.charAt(j) == '_') {
                while (column.charAt(++j) == '_') {
                }
                i = j;
                char c = column.charAt(i);
                if (sb.length() == 0) {
                } else {
                    for (int k = 0; k < a_z.length; k++) {
                        if (a_z[k] == c) {
                            c = A_Z[k];
                            break;
                        }
                    }
                }
                sb.append(c);
            } else {
                sb.append(column.charAt(j));
            }
        }

        return sb.toString();
    }

    public static String columnToProperty(String column) {
        StringBuilder result = new StringBuilder();
        if (column == null || column.isEmpty()) {
            return "";
        } else if (!column.contains("_")) {
            return column.substring(0, 1).toLowerCase() + column.substring(1);
        } else {
            String[] columns = column.split("_");
            for (String columnSplit : columns) {
                if (columnSplit.isEmpty()) {
                    continue;
                }
                if (result.length() == 0) {
                    result.append(columnSplit.toLowerCase());
                } else {
                    result.append(columnSplit.substring(0, 1).toUpperCase()).append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }

    }

    public static String propertyToColumn(String property){
        if (property == null || property.isEmpty()){
            return "";
        }
        StringBuilder column = new StringBuilder();
        column.append(property.substring(0,1).toLowerCase());
        for (int i = 1; i < property.length(); i++) {
            String s = property.substring(i, i + 1);
            // 在小写字母前添加下划线
            if(!Character.isDigit(s.charAt(0)) && s.equals(s.toUpperCase())){
                column.append("_");
            }
            // 其他字符直接转成小写
            column.append(s.toLowerCase());
        }

        return column.toString();
    }

    public static String columnToPropertyWithFisrtUpper(String column){
        String property = columnToProperty(column);
        return property.substring(0,1).toUpperCase()+property.substring(1);
    }



    public static void main(String[] args) {
        System.out.println(columnToProperty("oauth_login"));
        System.out.println(columnToProperty2("oauth_login"));
        System.out.println(propertyToColumn("oauthLogin"));
        System.out.println(columnToPropertyWithFisrtUpper("oauth_login"));
    }

}
