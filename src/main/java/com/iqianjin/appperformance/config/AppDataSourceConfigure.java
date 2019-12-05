package com.iqianjin.appperformance.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "com.iqianjin.appperformance.db.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class AppDataSourceConfigure {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.initialSize:2}")
    private int initialSize;

    @Value("${spring.datasource.maxActive:10}")
    private int maxActive;

    @Bean
    public DataSource dataSource() {
        return DataSourceConfigureBuilder.buildDataSource(this.url, this.username, this.password, this.initialSize, this.maxActive);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        return DataSourceConfigureBuilder.buildSqlSessionFactory(dataSource, "classpath*:/mapper/*Mapper.xml");
    }
}
