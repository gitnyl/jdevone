package com.project.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.project.jdevone.jdev"})
public class DataSourceConfig {
    /**
     * RoutingDataSource로 선언되어있는 DataSource를 LazyConnectionDataSourceProxy에 등록시켜,
     * lazy loading을 통해 연결할때마다 ReplicationRoutingDataSource를 통해 master/slave datasource를 분기시킴
     */

    @Bean("RoutingDataSource")
    public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", masterDataSource);
        dataSourceMap.put("slave", slaveDataSource);

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }

    @Bean("routingLazyDataSource")
    public DataSource routingLazyDataSource(@Qualifier("RoutingDataSource") DataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("routingLazyDataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource); return transactionManager;
    }

    @Bean(name = "masterDataSource")
    public HikariDataSource createDatasourceMaster(DataSourcePropertyMaster dataSourceMaster) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dataSourceMaster.getUrl());
        hikariDataSource.setUsername(dataSourceMaster.getUsername());
        hikariDataSource.setPassword(dataSourceMaster.getPassword());
        return hikariDataSource;
    }
    @Bean(name = "slaveDataSource")
    public HikariDataSource createDatasourceSlave(DataSourcePropertySlave dataSourceSlave) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dataSourceSlave.getUrl());
        hikariDataSource.setUsername(dataSourceSlave.getUsername());
        hikariDataSource.setPassword(dataSourceSlave.getPassword());
        return hikariDataSource;
    }

}
