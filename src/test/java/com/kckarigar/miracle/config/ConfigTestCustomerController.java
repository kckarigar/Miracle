package com.kckarigar.miracle.config;

import org.mockito.Mockito;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.kckarigar.miracle.dao.CustomerDao;
import com.kckarigar.miracle.service.CustomerService;

@Configuration
@ComponentScan(basePackages = { "com.kckarigar.miracle" })
public class ConfigTestCustomerController {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public CustomerService mockCustomerService() {
		return Mockito.mock(CustomerService.class);
	}
	 
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public CustomerDao mockCustomerDao() {
		return Mockito.mock(CustomerDao.class);
	}
	 

}
