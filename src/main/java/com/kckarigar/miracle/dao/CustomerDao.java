/**
 *
 */
package com.kckarigar.miracle.dao;

import java.util.List;

import com.kckarigar.miracle.entity.Customer;

/**
 * @author Kailash Karigar
 *
 */
public interface CustomerDao {
	public Customer getCustomerById(Integer id);

	public List<Customer> getCustomerList();

	public Boolean deleteCustomer(Integer id);

	public void saveCustomer(Customer customer);

	public Boolean editCustomer(Customer customer);

	public List<Customer> getCustomerByName(String name);
}
