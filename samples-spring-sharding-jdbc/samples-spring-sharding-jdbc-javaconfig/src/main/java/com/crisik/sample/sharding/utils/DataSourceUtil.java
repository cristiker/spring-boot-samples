package com.crisik.sample.sharding.utils;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;


public class DataSourceUtil {

    private static final String HOST = "localhost";

    private static final int PORT = 3306;

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "Admin@123";

    public static DataSource createDataSource(final String dataSourceName) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariDataSource.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8"
                , HOST, PORT, dataSourceName));
        hikariDataSource.setUsername(USER_NAME);
        hikariDataSource.setPassword(PASSWORD);
        return hikariDataSource;
    }
}
