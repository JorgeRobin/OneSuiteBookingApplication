package com.example;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    ReservationRepository reservationRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {

		List<Reservation> reservations = new LinkedList<Reservation>();
		reservations.add(new Reservation("Justin", "Trudeau", "justin.trudeau@gmail.com", 2, 
				Date.valueOf("2021-04-18"), Date.valueOf("2021-04-20"), 1));
		reservations.add(new Reservation("Joe", "Biden", "joe.biden@gmail.com", 1,
				Date.valueOf("2021-04-21"), Date.valueOf("2021-04-21"), 1));
		reservations.add(new Reservation("Manuel", "LopezObrador", "manuel.lopezobrador@gmail.com", 3,
				Date.valueOf("2021-04-22"), Date.valueOf("2021-04-24"), 1));
		reservations.add(new Reservation("Francisco", "Sagasti", "francisco.sagasti@gmail.com", 3,
				Date.valueOf("2021-04-26"), Date.valueOf("2021-04-28"), 1));
		reservationRepository.saveAll(reservations);
	}

}
