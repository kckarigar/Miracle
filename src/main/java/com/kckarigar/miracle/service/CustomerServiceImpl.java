package com.kckarigar.miracle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kckarigar.miracle.dao.CustomerDao;
import com.kckarigar.miracle.entity.Customer;

/**
 * @author Kailash Karigar
 *
 */

@Component
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;

	public CustomerServiceImpl() {

	}

	public CustomerServiceImpl(CustomerDao customerDao) {

		this.customerDao = customerDao;
	}

	public Customer getCustomerByID(Integer id) {

		return customerDao.getCustomerById(id);
	}

	public List<Customer> getCustomerList() {

		return customerDao.getCustomerList();
	}

	public Boolean deleteCustomer(Integer id) {

		return customerDao.deleteCustomer(id);

	}

	public void saveCustomer(Customer customer) {
       
		  customerDao.saveCustomer(customer);

	}

	public Boolean editCustomer(Customer customer) {

		return customerDao.editCustomer(customer);

	}

	public List<Customer> getCustomerByName(String name) {
		
		return customerDao.getCustomerByName(name);
	}

}
