package com.iqianjin.appperformance.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

public class DataSourceConfigureBuilder {

    public static DataSource buildDataSource(String url, String userName, String password, int initialSize, int maxActive) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(1);

        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);

        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);

        return dataSource;
    }

    public static SqlSessionFactory buildSqlSessionFactory(DataSource dataSource, String mapperPath) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factory.setConfiguration(configuration);

        Resource[] mappers = new PathMatchingResourcePatternResolver().getResources(mapperPath);
        factory.setMapperLocations(mappers);

        return factory.getObject();
    }
}
