package com.kckarigar.miracle.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kckarigar.miracle.entity.Customer;
import com.kckarigar.miracle.entity.CustomerAddress;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

 
public class TestUtility {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	 public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        return mapper.writeValueAsBytes(object);
	    }
	
	public static List<Customer> createMockCustomerList(Integer noOfCustomers) {
		List<Customer> list = new ArrayList<Customer>();
		for (Integer i = 1; i <= noOfCustomers; i++) {
			list.add(createMockCustomer(i));
		}

		return list;
	}

	public static Customer createMockCustomer(Integer i) {
		Customer customer = new Customer();
		customer.setId(i);
		customer.setName("Name" + i);
		customer.setEmail("Email" + i);
		customer.setTelephone("Phone" + i);

		CustomerAddress address = new CustomerAddress();
		address.setAddress_id(i);
		address.setStreet("Street" + i);
		address.setCity("City" + i);
		address.setState("State" + i);
		address.setZip("Zip" + i);

		customer.setAddress(address);

		return customer;
	}
	
	public static Customer createMockCustomerKK() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Kailash Karigar");
		customer.setEmail("kckarigar@gmail.com");
		customer.setTelephone("604.726.6619");

		CustomerAddress address = new CustomerAddress();
		address.setAddress_id(1);
		address.setStreet("14855 100th Ave");
		address.setCity("Surrey");
		address.setState("BC");
		address.setZip("V3R 2W1");
		customer.setAddress(address);
		return customer;

	}

}