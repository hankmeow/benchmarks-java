package com.hankmew.benchmarks.middleware.mysql.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@MapperScan(
        basePackages = {"com.hankmew.benchmarks.middleware.mysql.mapper"},
        sqlSessionFactoryRef = "dynamicSqlSessionFactory"
)
@Slf4j
@Configuration
public class DynamicDataSourceConfiguration {
    @Resource
    private HikariConfiguration hikariConfiguration;
    @Resource
    private MybatisProperties mybatisProperties;
    @Resource
    private ResourceLoader resourceLoader = new DefaultResourceLoader();


    public Map<Object, Object> dynamicDataSourceMap() {
        HashMap<Object, Object> map = new HashMap<>();
        for (DynamicDataSourceEnum value : DynamicDataSourceEnum.values()) {
            HikariConfig hikariConfig = hikariConfiguration.getHikari().get(value.name().toLowerCase());
            if (Objects.isNull(hikariConfig)) {
                throw new RuntimeException("hikariConfiguration database not exist, db name is " + value.name().toLowerCase());
            }
            hikariConfig.setPoolName(value.name().toLowerCase()+"-DB");
            map.put(value, new HikariDataSource(hikariConfig));
        }
        return map;
    }

    @Lazy
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> map = dynamicDataSourceMap();
        dataSource.setTargetDataSources(map);
        //默认值
        dataSource.setDefaultTargetDataSource(map.get(DynamicDataSourceEnum.SHARD0));
        return dataSource;
    }

    @Lazy
    @Bean("dynamicSqlSessionFactory")
    public SqlSessionFactory dynamicSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        return sqlSessionFactory(dataSource);
    }

    @Lazy
    @Bean("dynamicPlatformTransactionManager")
    public PlatformTransactionManager dynamicPlatformTransactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Lazy
    @Bean("dynamicTransactionTemplate")
    public TransactionTemplate dynamicTransactionTemplate(@Qualifier("dynamicPlatformTransactionManager") PlatformTransactionManager platformTransactionManager) throws Exception {
        return new TransactionTemplate(platformTransactionManager);
    }

    /**
     * see MybatisAutoConfiguration#sqlSessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     */
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        //这里可以加拦截器
        if (StringUtils.hasText(this.mybatisProperties.getConfigLocation())) {
            factory.setConfigLocation(this.resourceLoader.getResource(this.mybatisProperties.getConfigLocation()));
        }
        factory.setConfiguration(mybatisProperties.getConfiguration());
        if (StringUtils.hasLength(this.mybatisProperties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(this.mybatisProperties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(this.mybatisProperties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(this.mybatisProperties.getTypeHandlersPackage());
        }
        if (ObjectUtils.isNotEmpty(this.mybatisProperties.resolveMapperLocations())) {
            factory.setMapperLocations(this.mybatisProperties.resolveMapperLocations());
        }
        return factory.getObject();
    }
}
