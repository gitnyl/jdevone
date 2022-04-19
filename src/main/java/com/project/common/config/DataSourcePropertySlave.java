package com.project.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ConfigurationProperties("spring.datasource.slave.hikari")
public class DataSourcePropertySlave {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
