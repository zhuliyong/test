package com.vicmob.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author keith
 * @version 1.0
 * @date 2018/11/28 9:25
 */
@Configuration
@MapperScan(basePackages = {"com.vicmob.mapper"}, sqlSessionTemplateRef  = "xyhSqlSessionTemplate")
public class WarmthDataSourceConfig {
    @Bean(name = "xyhDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource xyhDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "xyhSqlSessionFactory")
    @Primary
    public SqlSessionFactory xyhSqlSessionFactory(@Qualifier("xyhDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "xyhTransactionManager")
    @Primary
    public DataSourceTransactionManager xyhTransactionManager(@Qualifier("xyhDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "xyhSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate xyhSqlSessionTemplate(@Qualifier("xyhSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
