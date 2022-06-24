package com.airjnc.common.config;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

import static org.apache.ibatis.session.AutoMappingUnknownColumnBehavior.FAILING;

@org.springframework.context.annotation.Configuration
@MapperScan(basePackages = {"com.airjnc.*.dao"})
public class MybatisConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        Resource[] res = new PathMatchingResourcePatternResolver()
            .getResources("classpath:mybatis/mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(res);
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.airjnc.*.domain");
        sqlSessionFactoryBean.setConfiguration(setMybatisConfiguration());
        return sqlSessionFactoryBean.getObject();
    }


    @Bean
    public Configuration setMybatisConfiguration() {

        Configuration configuration = new Configuration();
        configuration.setAutoMappingUnknownColumnBehavior(FAILING);
        configuration.setMapUnderscoreToCamelCase(true);

        return configuration;
    }
}
