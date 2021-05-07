package com.example;

public class ReservationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	ReservationNotFoundException(Long id) {
	    super("Could not find reservation " + id);
	  }

}
