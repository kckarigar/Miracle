/**
 *
 */
package com.kckarigar.miracle.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.kckarigar.miracle.dao.CustomerDao;
import com.kckarigar.miracle.dao.CustomerDaoImpl;
import com.kckarigar.miracle.entity.Customer;
import com.kckarigar.miracle.entity.CustomerAddress;
import com.kckarigar.miracle.service.CustomerService;
import com.kckarigar.miracle.service.CustomerServiceImpl;

/**
 * @author Kailash Karigar
 *
 */
@Configuration
@ComponentScan("com.kckarigar.miracle")
@EnableWebMvc
@EnableTransactionManagement
public class AppConfig extends WebMvcConfigurerAdapter {

	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".jsp");		 
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations(
				"classpath:/assets/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		  
	} 

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		boolean isLocalDatabase = false;

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		if (isLocalDatabase) {
			dataSource.setUrl("jdbc:mysql://localhost:3306/kckarigardb");
			dataSource.setUsername("root");
			dataSource.setPassword("");
		} else {
			dataSource.setUrl("jdbc:mysql://127.11.61.2:3306/demo");
			dataSource.setUsername("admin");
			dataSource.setPassword("");
		}

		return dataSource;

	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");

		return properties;
	}

	@Autowired
	@Bean(name = "SessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(
				dataSource);
		sessionFactoryBuilder.addProperties(getHibernateProperties());
		sessionFactoryBuilder.addAnnotatedClass(Customer.class);
		sessionFactoryBuilder.addAnnotatedClass(CustomerAddress.class);

		return sessionFactoryBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);

		return transactionManager;
	}

	@Autowired
	@Bean(name = "customerDao")
	public CustomerDao getCustomerDao(SessionFactory sessionFactory) {

		return new CustomerDaoImpl(sessionFactory);

	}

	@Autowired
	@Bean(name = "customerService")
	public CustomerService getCustomerService(CustomerDao customerDao) {
		return new CustomerServiceImpl(customerDao);

	}

}
