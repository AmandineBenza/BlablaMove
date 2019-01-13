package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.integrity.dbcommunication.DatabaseConnectionChecker;
import com.xaamruda.bbm.users.dbaccess.service.UserService;
import com.xaamruda.bbm.users.identification.UserIdentificationChecker;

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
		"com.xaamruda.bbm.integrity",
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
		"com.xaamruda.bbm.integrity",
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
		"com.xaamruda.bbm.users.model",
})
//@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class);
		BBMLogger.infoln(">> Welcome to BlablaMove <<");
		DatabaseConnectionChecker.start();
		UserIdentificationChecker.start(context.getBean(UserService.class));
	}
	
	/*
	
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }
 
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
        bean.setDatabase(Database.H2);
        bean.setGenerateDdl(true);
        bean.setShowSql(true);
        return bean;
    }
 
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setPackagesToScan("com.xaamruda.bbm");
        return bean;
    }
 
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
	}	
	*/
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