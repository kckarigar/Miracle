/**
 *
 */
package com.kckarigar.miracle.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kckarigar.miracle.entity.Customer;
import com.kckarigar.miracle.exception.CustomerNotFoundException;

/**
 * @author Karigar
 *
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {
	@Autowired
	private SessionFactory sessionFactory;

	public CustomerDaoImpl() {

	}

	public CustomerDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kckarigar.miracle.dao.CustomerDao#getCustomerById(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Customer getCustomerById(Integer id) {
		String hql = "from Customer where id=" + id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Customer> listCustomer = query.list();

		if (listCustomer.isEmpty()) {
			throw new CustomerNotFoundException("Customer not found with id: "
					+ id);
		} else {
			return listCustomer.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Customer> getCustomerList() {
		String hql = "from Customer";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Customer> listCustomer = query.list();
		if (listCustomer.isEmpty()) {
			throw new CustomerNotFoundException("Customer list not found");
		} else {
			return listCustomer;
		}

	}

	@Transactional
	public Boolean deleteCustomer(Integer id) {
		Boolean isDeleted = false;
		Customer customer = (Customer) sessionFactory.getCurrentSession().get(
				Customer.class, id);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer to be deleted not found");
		} else {
			sessionFactory.getCurrentSession().delete(customer);
			isDeleted = true;
		}
		return isDeleted;
	}

	@Transactional
	public void saveCustomer(Customer customer) {
		 
		if (customer == null) {
			throw new CustomerNotFoundException("Customer to be saved not found");
		} else {
			sessionFactory.getCurrentSession().save(customer);
			 
		}
		 
	}

	@Transactional
	public Boolean editCustomer(Customer customer) {
		Boolean isEdited = false;
		if (customer == null) {
			throw new CustomerNotFoundException("Customer to be edited not found");
		} else {
			sessionFactory.getCurrentSession().update(customer);
			isEdited = true;
		}	
        return isEdited;
	}
	
	@Transactional
	public List<Customer> getCustomerByName(String name) {
		
		String hql = "from Customer where name = '" + name +"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Customer> listCustomer = query.list();
		if (listCustomer.isEmpty()) {
			throw new CustomerNotFoundException("Customer(s) with name "+name+" not found");
		} else {
			return listCustomer;
		}
	}

}
