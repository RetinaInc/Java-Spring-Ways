package com.rabin.secdemo.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:database-properties.properties"})
@ComponentScan(basePackages="com.rabin.secdemo")
public class PersistanceConfig {
	@Autowired
	private Environment env;
	private Logger logger = Logger.getLogger(getClass().getName());
	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(env.getProperty("jdbc.driverClassName"));
			dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
			dataSource.setUser(env.getProperty("jdbc.user"));
			dataSource.setPassword(env.getProperty("jdbc.pass"));
			//only for pool size
			/*
			 * dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty(
			 * "jdbc.pool.initialPoolSize")));
			 * dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty(
			 * "jdbc.pool.minPoolSize")));
			 * dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty(
			 * "jdbc.pool.maxPoolSize")));
			 * dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty(
			 * "jdbc.pool.maxIdleTime")));
			 */
			//replaced by helper method


			
			dataSource.setInitialPoolSize(getIntProperty("jdbc.pool.initialPoolSize"));
			dataSource.setInitialPoolSize(getIntProperty("jdbc.pool.minPoolSize"));
			dataSource.setInitialPoolSize(getIntProperty("jdbc.pool.maxPoolSize"));
			dataSource.setInitialPoolSize(getIntProperty("jdbc.pool.maxIdleTime"));
			logger.info(">>>===>>> jdbc.driverClassName=" +env.getProperty("jdbc.driverClassName"));
			logger.info(">>>===>>> jdbc.url=" +env.getProperty("jdbc.url"));
			logger.info(">>>===>>> jdbc.user=" +env.getProperty("jdbc.user"));
			//logger.info(">>>===>>> jdbc.password=" +env.getProperty("jdbc.password"));
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//throw new RuntimeException(e);
		}
		
		return dataSource;
		
		
	}
	//need helper method for reading env property n convert to int
	private int getIntProperty(String propName) {
		String propVal = env.getProperty(propName);
		int intPropVal = Integer.parseInt(propVal);
		return intPropVal;
	}
	/*
	@Bean 
	LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] {"com.rabin.crmdemo.model"});
		sessionFactory.setHibernateProperties(dbHibernateConfig());
		return sessionFactory;
		
	}
	
	Properties dbHibernateConfig() {
		Properties toReturn = new Properties();
		toReturn.setProperty("hibernate.hdm2ddl.auto", env.getProperty("hibernate.hdm2ddl.auto"));
		toReturn.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		toReturn.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return toReturn;
		
	}
	
	//Transactional Manager 
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionfactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionfactory);
		return transactionManager;
	}
	*/
	
	
	// Transaction Manager non hibernate way
			/*
			 @Autowired
			 @Bean(name = "transactionManager")
			 public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
			      DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
			      if (dataSource != null) {
			    	  System.out.println("Datasource value here" +dataSource);
			      }
			      
			      return transactionManager;
			 }
			 */
}
