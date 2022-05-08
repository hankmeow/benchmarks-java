package com.hankmew.benchmarks.middleware.mysql.config;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties("biz.datasource")
public class HikariConfiguration {
    @Getter
    Map<String, HikariConfig> hikari = new HashMap<>();
}
