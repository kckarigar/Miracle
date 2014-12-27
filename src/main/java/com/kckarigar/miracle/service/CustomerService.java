/**
 *
 */
package com.kckarigar.miracle.service;

import java.util.List;

import com.kckarigar.miracle.entity.Customer;

/**
 * @author Kailash Karigar
 *
 */
public interface CustomerService {

	public Customer getCustomerByID(Integer id);

	public List<Customer> getCustomerList();

	public Boolean deleteCustomer(Integer id);

	public void saveCustomer(Customer customer);

	public Boolean editCustomer(Customer customer);

	public List<Customer> getCustomerByName(String name);
}
