package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.integrity.dbcommunication.DatabaseConnectionChecker;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * BlablaMove entry point. 
 */

@EnableJpaRepositories(basePackages = {
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.users"})
@SpringBootApplication(scanBasePackages = { 
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.billing",
		"com.xaamruda.bbm.commons",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
		"com.xaamruda.bbm.users.model",
		"com.xaamruda.bbm.integrity.errorHandler"
})
@ComponentScan(basePackages= {
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.billing",
		"com.xaamruda.bbm.commons",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
		"com.xaamruda.bbm.users.model",
		"com.xaamruda.bbm.integrity.errorHandler"
})
@EntityScan(basePackages= {
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.billing",
		"com.xaamruda.bbm.commons",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
		"com.xaamruda.bbm.users.model",
		"com.xaamruda.bbm.integrity.errorHandler"
})
//@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
		BBMLogger.infoln(">> Welcome to BlablaMove <<");
		DatabaseConnectionChecker.start();
	}	
	
//	@Bean
//	public EntityManager entityManager() {
//	    return entityManagerFactory().getObject().createEntityManager();
//	}
	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//	    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//	    em.setDataSource(dataSource());
//	    em.setPackagesToScan("package.where.your.entites.like.CustSys.are.stored");
//	    return em;
//	}
//	@Bean
//	public DataSource dataSource() {
//	    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//	    dataSourceBuilder.url("jdbc:mysql://dbnouveaux/test");
//	    dataSourceBuilder.username("root");
//	    dataSourceBuilder.password("mdp");
//	    dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//	    return dataSourceBuilder.build();   
//	}
//// 
////    @Bean
////    public JpaVendorAdapter jpaVendorAdapter() {
////        HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
////        bean.setDatabase(Database.MYSQL);
////        bean.setGenerateDdl(true);
////        bean.setShowSql(true);
////        return bean;
////    }
//// 
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
//            JpaVendorAdapter jpaVendorAdapter) {
//        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setJpaVendorAdapter(jpaVendorAdapter);
//        bean.setPackagesToScan("com.xaamruda.bbm");
//        return bean;
//    }
// 
//    @Bean
//    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }
}