package ru.inno.task5.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class ProjectConfig {
    @Value("${custom.datasource.url}")
    private String datasourceUrl;
    @Value("${custom.datasource.username}")
    private String datasourceUserName;
    @Value("${custom.datasource.password}")
    private String datasourcePassword;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(datasourceUrl);
        dataSource.setUsername(datasourceUserName);
        dataSource.setPassword(datasourcePassword);
//        dataSource.setConnectionTimeout(1000);
        return dataSource;
    }
}
