package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * BlablaMove entry point. 
 */

/*@EnableMongoRepositories(basePackages = {
	"com.xaamruda.bbm.offers.dbaccess.repository",
	"com.xaamruda.bbm.users.dbaccess.repository"
}) */
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
		"com.xaamruda.bbm.users.model"
})
@ComponentScan(basePackages= { "com.xaamruda.bbm.app",
"com.xaamruda.bbm.billing",
"com.xaamruda.bbm.commons",
"com.xaamruda.bbm.communication",
"com.xaamruda.bbm.controller",
"com.xaamruda.bbm.offers",
"com.xaamruda.bbm.roads",
"com.xaamruda.bbm.users",
"com.xaamruda.bbm.users.model"})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
		BBMLogger.infoln(">> Welcome to BlablaMove <<");
	}	

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