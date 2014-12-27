package com.kckarigar.miracle.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kckarigar.miracle.entity.Customer;
import com.kckarigar.miracle.service.CustomerService;

/**
 * @author Kailash Karigar
 *
 */

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	public CustomerController() {
		 
	}
	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Customer getCustomerByID(@PathVariable Integer id) {

		Customer customer = customerService.getCustomerByID(id);
		return customer;
	}

	@RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
	public List<Customer> getCustomerByName(@PathVariable String name) {

		List<Customer> customers = customerService.getCustomerByName(name);
		return customers;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Customer> getCustomerList() {
		List<Customer> customers = customerService.getCustomerList();
		return customers;
	}

	@RequestMapping(method = RequestMethod.POST)
	public List<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
		if (customer != null) {
			customerService.saveCustomer(customer);
		}

		return customerService.getCustomerList();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public List<Customer> deleteCustomer(@PathVariable Integer id) {

		customerService.deleteCustomer(id);
		return customerService.getCustomerList();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public List<Customer> editCustomer(@RequestBody Customer customer) {

		customerService.editCustomer(customer);
		return customerService.getCustomerList();
	}

}
