package com.example;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private int numberGuests = 1;
	private Date checkin = null;
	private Date checkout = null;
	private int status = 1;
	
	public Reservation() {
		super();
	}


	public Reservation(String firstName, String lastName, String email, 
			int numberGuests, Date checkin, Date checkout, int status) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.numberGuests = numberGuests;
		this.checkin = checkin;
		this.checkout = checkout;
		this.status = status;
	}
	
	public Reservation(long id, String firstName, String lastName, String email, 
			int numberGuests, Date checkin, Date checkout, int status) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.numberGuests = numberGuests;
		this.checkin = checkin;
		this.checkout = checkout;
		this.status = status;
	}
	
	public Reservation(long id, String firstName, String lastName, String email, 
			int numberGuests) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.numberGuests = numberGuests;
	}
		
	public long getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public int getNumberGuests() {
		return numberGuests;
	}
	public Date getCheckin() {
		return checkin;
	}
	public Date getCheckout() {
		return checkout;
	}
	public int getStatus() {
		return status;
	}
		
	public void setId(long id) {
		this.id = id;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setNumberGuests(int numberGuests) {
		this.numberGuests = numberGuests;
	}
	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}
	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
