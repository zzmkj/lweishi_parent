package com.lweishi.repair.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

@Configuration
public class MyDataSourceConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
		
	/*@Autowired
	private DataSource dataSource;*/
	
	/*@Bean
	@Lazy
	//@Primary
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		//return new LazyConnectionDataSourceProxy(dbcpDataSource());
		log.info("MyDataSourceConfig:@Lazy dataSource");
		String jdbcUrl = "jdbc:mariadb://kxarticledb:3306/kxarticle?useUnicode=true&amp;characterEncoding=utf-8&autoReconnect=true&zeroDateTimeBehavior=round";
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		
		return dataSource;
	}*/

    @Bean
    @Lazy
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(DataSourceProperties properties) {
        log.info("MyDataSourceConfig:@Lazy dataSource");
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
    }

}
