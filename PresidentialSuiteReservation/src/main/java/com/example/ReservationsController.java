package com.example;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@Controller
public class ReservationsController {

	@Autowired
	ReservationRepository repository;

	@GetMapping(value="/reservation/{id}")
	public String reservation(@PathVariable Long id, Model model) {
		Reservation reservation = repository.findById(id).get();
		System.out.println("Debug - ReservationController - reservation - firstName " + reservation.getFirstName());
        model.addAttribute("reservation", reservation);
        return "reservation";
	}

    @GetMapping(value="/reservations")
	public String reservationsList(Model model) {
        model.addAttribute("reservations", repository.findAll());
        return "reservations";
	}
    
    @PostMapping(value="/reservations")
	public String reservationsAdd(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, 
			@RequestParam String numberGuests, @RequestParam String checkin, @RequestParam String checkout, Model model) {
        Reservation newReservation = new Reservation();
        Reservation reservation = new Reservation();
        reservation.setEmail(email);
        reservation.setFirstName(firstName);
        reservation.setLastName(lastName);
        reservation.setNumberGuests(Integer.parseInt(numberGuests));
        System.out.println("Debug - ReservationController - reservationAdd - firstName " + firstName + ", lastName " + lastName + 
        				", numberGuests " + numberGuests +  ", checkin " + checkin + ", checkout " + checkout);
        Date ci = Date.valueOf(checkin);
        reservation.setCheckin(ci);
        Date co = Date.valueOf(checkout);
        reservation.setCheckout(co);
        reservation.setStatus(1);
        // check if booked
    	Iterable<Reservation> reservationsIterable = repository.findAll();
        boolean booked = false;
        Iterator<Reservation> it = reservationsIterable.iterator();
        while (it.hasNext()) {
        	newReservation = it.next();
    		if ((ci.compareTo(newReservation.getCheckin()) >= 0) && (ci.compareTo(newReservation.getCheckout()) <= 0)) {
    			booked = true;
    		}        	
        }
        if (booked) {
            model.addAttribute("reservation", reservation);
            return "redirect:/reservations?errorMsgAdd=Booked on selected date";       	
        }
        
        System.out.println("Debug - ReservationController - reservationAdd - booked " + booked);

        repository.save(reservation);

        model.addAttribute("reservation", reservation);
        return "redirect:/reservations";
	}
	
    @GetMapping(value="/reservationUpdate/{id}")
    public String reservationUpdate(@PathVariable Long id, Model model) {
		Reservation reservation = repository.findById(id).get();
		System.out.println("Debug - ReservationController - reservationUpdate - firstName " + reservation.getFirstName());
        model.addAttribute("reservation", reservation);
        return "reservationUpdate";
    }
    
    @PostMapping(value="/reservationUpdate/{id}")
	public String reservationUpdate(@RequestParam String id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, 
			@RequestParam String numberGuests, @RequestParam String checkin, @RequestParam String checkout, Model model) {
        Reservation reservation = new Reservation();
        Long idUpdate = 0L;
        String errorMsg = "";
        boolean failed = false;
        try {
        	idUpdate = Long.valueOf(id);
            reservation = repository.findById(idUpdate).get();
        } catch (Exception e) {
        	System.out.println("ReservationController - reservationUpdate - Error " + e.getMessage());
        	errorMsg = e.getMessage();
        	failed = true;
        }
        if (reservation == null) {
        	System.out.println("ReservationController - reservationUpdate - Error: could not find any record with id " + id);
        	errorMsg = "could not find any record with id " + id;
        	failed = true;
        } 
        if (!failed) {
            reservation.setId(idUpdate);
            reservation.setEmail(email);
            reservation.setFirstName(firstName);
            reservation.setLastName(lastName);
            reservation.setNumberGuests(Integer.parseInt(numberGuests));
            System.out.println("Debug - ReservationController - reservationUpdate - numberGuests " + numberGuests +  ", checkin " + checkin + ", checkout " + checkout);
            Date ci = Date.valueOf(checkin);
            reservation.setCheckin(ci);
            Date co = Date.valueOf(checkout);
            reservation.setCheckout(co);
            reservation.setStatus(1);
            repository.save(reservation);      	
        }

        model.addAttribute("reservation", reservation);
        return "redirect:/reservations?errorMsgUpdate=" + errorMsg;
	}
    
    @GetMapping(value="/reservationDelete/{id}")
    public String reservationDelete(@PathVariable Long id, Model model) {
		Reservation reservation = repository.findById(id).get();
		System.out.println("Debug - ReservationController - reservationDelete - firstName " + reservation.getFirstName());
        model.addAttribute("reservation", reservation);
        return "reservationDelete";
    }

    
    @PostMapping(value="/reservationDelete/{id}")
	public String reservationDelete(@RequestParam String id, Model model) {
        Reservation reservation = null;
        Long idDelete = 0L; 
        String errorMsg = "";
        boolean failed = false;
        try {
        	idDelete = Long.valueOf(id);
            reservation = repository.findById(idDelete).get();
        } catch (Exception e) {
        	System.out.println("ReservationController - reservationDelete - Error " + e.getMessage());
        	errorMsg = e.getMessage();
        	failed = true;
        }
        if (reservation == null) {
        	System.out.println("ReservationController - reservationDelete - Error: could not find any record with id " + id);
        	errorMsg = "could not find any record with id " + id;
        	failed = true;
        }
        if (!failed) {
        	repository.deleteById(idDelete);
        }

        return "redirect:/reservations?errorMsgUpdate=" + errorMsg;
	}
    
    @GetMapping(value="/")
    public String index() {
        return "index";
    }
    
    @GetMapping(value="/reservationsSearch")
    public String reservationsSearch() {
        return "reservationsSearch";
    }
    
    @PostMapping(value="/reservationsSearch")
    public String reservationsSearchDates(@RequestParam String from, @RequestParam String to, Model model) {
    	Iterable<Reservation> reservationsIterable = repository.findAll();
    	List<Reservation> reservations = new ArrayList<Reservation>();
    	Calendar c = Calendar.getInstance();
    	long now = c.getTime().getTime();
    	c.add(Calendar.DATE, 30);
    	long now30days = c.getTime().getTime();
    	java.sql.Date fromDate = new java.sql.Date(now);
    	java.sql.Date toDate = new java.sql.Date(now30days);
    	if (from != null && !from.isEmpty()) {
     		fromDate = Date.valueOf(from);		
    	}
    	if (to != null && !from.isEmpty()) {
    		toDate = Date.valueOf(to);		
    	}
        System.out.println("Debug - ReservationController - reservationsSearchDates - fromDate " + 
        			fromDate +  ", toDate " + toDate);
        Iterator<Reservation> it = reservationsIterable.iterator();
        Reservation reservation = new Reservation();
        while (it.hasNext()) {
        	reservation = it.next();
        	Date checkin = reservation.getCheckin();
    		if ((checkin.compareTo(fromDate) >= 0) && (checkin.compareTo(toDate) <= 0)) {
    			reservations.add(reservation);
    		}
        }
    	model.addAttribute("reservations", reservations);
        return "reservations";
    }
    
    // RESTful API methods
    @GetMapping("/bookingsCount")
    @ResponseBody
    public Long count() {
        return repository.count();
    }
    
    @GetMapping("/bookings")
    @ResponseBody
    public List<Reservation> findAll() {
    	Iterable<Reservation> reservationsIterable = repository.findAll();
    	List<Reservation> reservationsList = new ArrayList<Reservation>();
    	reservationsIterable.forEach(reservationsList::add);
        return reservationsList;
    }
    
    @GetMapping("/bookings/{id}")
    @ResponseBody
    public Reservation findBooking(@PathVariable Long id) {
    	
    	Reservation reservation;
    	try {
    		reservation = repository.findById(id).get();
    	} catch (Exception e) {
    		throw new ReservationNotFoundException(id);
    	}

    	return reservation;
    }

    @PutMapping("/bookings/{id}")
    public Reservation updateBooking(String firstName, String lastName, String email,
    		String numberGuests, String checkin, String checkout, @PathVariable Long id) {
      Reservation newReservation = new Reservation();
      newReservation.setFirstName(firstName);
      newReservation.setLastName(firstName);
      newReservation.setEmail(email);
      newReservation.setNumberGuests(Integer.parseInt(numberGuests));
      System.out.println("Debug - ReservationController - updateBooking - numberGuests " + numberGuests +  ", checkin " + checkin + ", checkout " + checkout);
      Date ci = Date.valueOf(checkin);
      newReservation.setCheckin(ci);
      Date co = Date.valueOf(checkout);
      newReservation.setCheckout(co);
      newReservation.setStatus(1);
      return repository.findById(id)
        .map(reservation -> {
        	reservation.setFirstName(newReservation.getFirstName());
        	reservation.setLastName(newReservation.getFirstName());
        	reservation.setEmail(newReservation.getEmail());
        	reservation.setNumberGuests(newReservation.getNumberGuests());
        	reservation.setCheckin(newReservation.getCheckin());
        	reservation.setCheckout(newReservation.getCheckout());
        	reservation.setStatus(newReservation.getStatus());
          return repository.save(reservation);
        })
        .orElseGet(() -> {
          newReservation.setId(id);
          return repository.save(newReservation);
        });
    }

    @DeleteMapping("/bookings/{id}")
    public void deleteReservation(@PathVariable Long id) {
      repository.deleteById(id);
    }
    
}
