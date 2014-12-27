/**
 * 
 */
package com.kckarigar.miracle.controller;



import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.*;//.anyInt;
import static org.mockito.Matchers.*;//.anyObject;
import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.kckarigar.miracle.config.ConfigTestCustomerController;
import com.kckarigar.miracle.controller.CustomerController;
import com.kckarigar.miracle.entity.Customer;
import com.kckarigar.miracle.entity.NewCustomerAddress;
import com.kckarigar.miracle.entity.NewCustomerToSave;
import com.kckarigar.miracle.service.CustomerService;
import com.kckarigar.miracle.util.TestUtility;

/**
 * Unit tests the CustomerController
 * 
 * @author Kailash Karigar
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConfigTestCustomerController.class })
@WebAppConfiguration
public class TestCustomerController {

	private MockMvc mockMvc;
	
	//@Autowired
    //protected WebApplicationContext webApplicationContext;

	@Autowired
	private CustomerService mockCustomerService;

	@Before
	public void setUp() {
		Mockito.reset(mockCustomerService);
		mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(mockCustomerService)).build();
		// mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	@Ignore
	public void testgetCustomerByID() throws Exception {		 
		Customer customer = TestUtility.createMockCustomerKK();
		when(mockCustomerService.getCustomerByID(anyInt()))
				.thenReturn(customer);

		mockMvc.perform(
				get("/customers/{id}", 2).accept(
						TestUtility.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(TestUtility.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name").value("Kailash Karigar"))				 
				.andExpect(jsonPath("$.telephone", is("604.726.6619")))	
				.andExpect(jsonPath("$.email", is("kckarigar@gmail.com")))	
				 
				.andExpect(jsonPath("$.address.street", is("14855 100th Ave")))
				.andExpect(jsonPath("$.address.city", is("Surrey")))
				.andExpect(jsonPath("$.address.state", is("BC")))
				.andExpect(jsonPath("$.address.zip", is("V3R 2W1")))
				.andReturn();
		verify(mockCustomerService, times(1)).getCustomerByID(2);
        verifyNoMoreInteractions(mockCustomerService);

	}
	
	@Test	
	@Ignore
	public void testGetCustomerList() throws Exception {
		Integer size = 5; 		 
		List<Customer> customers = TestUtility.createMockCustomerList(size); 		
		when(mockCustomerService.getCustomerList())
				.thenReturn(customers);
		
		mockMvc.perform(get("/customers").accept(TestUtility.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())		
				//.andDo(print()) 
				.andExpect(content().contentType(TestUtility.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(size)))
				.andExpect(jsonPath("$[1].name").value("Name2"))
				 
				.andExpect(jsonPath("$[0].id").value(1)) 
				.andExpect(jsonPath("$[0].name").value(customers.get(0).getName())) 
				.andExpect(jsonPath("$[0].telephone").value(customers.get(0).getTelephone())) 
				.andExpect(jsonPath("$[0].email").value(customers.get(0).getEmail())) 
				
				.andExpect(jsonPath("$[0].address.street").value(customers.get(0).getAddress().getStreet()))  	
				.andExpect(jsonPath("$[0].address.city").value(customers.get(0).getAddress().getCity()))  	
				.andExpect(jsonPath("$[0].address.state").value(customers.get(0).getAddress().getState())) 
				.andExpect(jsonPath("$[0].address.zip").value(customers.get(0).getAddress().getZip()))  	
				 
				.andExpect(jsonPath("$[4].id").value(5)) 
				.andExpect(jsonPath("$[4].name").value(customers.get(4).getName())) 
				.andExpect(jsonPath("$[4].telephone").value(customers.get(4).getTelephone())) 
				.andExpect(jsonPath("$[4].email").value(customers.get(4).getEmail())) 
				
				.andExpect(jsonPath("$[4].address.street").value(customers.get(4).getAddress().getStreet()))  	
				.andExpect(jsonPath("$[4].address.city").value(customers.get(4).getAddress().getCity()))  	
				.andExpect(jsonPath("$[4].address.state").value(customers.get(4).getAddress().getState())) 
				.andExpect(jsonPath("$[4].address.zip").value(customers.get(4).getAddress().getZip()))   		
			 	.andReturn();  
			verify(mockCustomerService, times(1)).getCustomerList();
	        verifyNoMoreInteractions(mockCustomerService);
        
	}

	
 
	@Test
	public void testSaveCustomer() throws Exception {
		Customer customer = TestUtility.createMockCustomer(3);
		final Integer NEW_ID = customer.getId();
		customer.setId(null);
		
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Customer p = (Customer) args[0];
				p.setId(NEW_ID); // emulate the successful save populating the id
	            return "called with arguments: " + args;
			}
		}).when(mockCustomerService).saveCustomer((Customer) anyObject());	 
		
		NewCustomerToSave   newCustomer  =  new NewCustomerToSave();
		newCustomer.setName(customer.getName());
		newCustomer.setEmail(customer.getEmail());
		newCustomer.setTelephone(customer.getTelephone());
		newCustomer.setAddress(new NewCustomerAddress());
		newCustomer.getAddress().setStreet(customer.getAddress().getStreet());	
		newCustomer.getAddress().setCity(customer.getAddress().getCity());	
		newCustomer.getAddress().setState(customer.getAddress().getState());	
		newCustomer.getAddress().setZip(customer.getAddress().getZip());	
		
		mockMvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertObjectToJsonBytes(newCustomer))
				//.accept(TestUtility.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().isOk())				 
				//.andExpect(content().string(NEW_ID.toString()))
				.andReturn();
        //.andExpect(jsonPath("$.id").value(3))
        /*
		.andExpect(jsonPath("$.name").value(customer.getName())) 
		.andExpect(jsonPath("$.telephone").value(customer.getTelephone())) 
		.andExpect(jsonPath("$.email").value(customer.getEmail())) 
		 
		.andExpect(jsonPath("$.address.street").value(customer.getAddress().getStreet()))  	
		.andExpect(jsonPath("$.address.city").value(customer.getAddress().getCity()))  	
		.andExpect(jsonPath("$.address.state").value(customer.getAddress().getState())) 
		.andExpect(jsonPath("$.address.zip").value(customer.getAddress().getZip())) 
		*/ 
       
	     
		//verify(mockCustomerService, times(1)).saveCustomer(customer);
        //verifyNoMoreInteractions(mockCustomerService);
			 	
	}

	@Test
	public void testDeleteCustomer() throws Exception {

	}

	@Test
	public void testEditCustomer() throws Exception {

	}

}
