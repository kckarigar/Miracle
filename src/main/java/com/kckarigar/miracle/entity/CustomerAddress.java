/**
 * 
 */
package com.kckarigar.miracle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * @author Kailash Karigar
 *
 */
@Entity
@Table(name = "CUSTOMERADDRESS")
public class CustomerAddress {
	
	@Id
	@Column(name = "ADDRESS_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)		
	private Integer address_id;
	 
	 

	@Column(name = "STREET")
	@Length(max = 100)
	private String street;
	
	@Column(name = "CITY")
	@Length(max = 50)
	private String city;
	
	@Column(name = "STATE")
	@Length(max = 25)
	private String state;
	
	@Length(max = 10)
	@Column(name = "ZIP")
	private String zip;

	public Integer getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	

}
