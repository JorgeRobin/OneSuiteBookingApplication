package com.example.test;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReservationsController.class)
public class ReservationRESTfulTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReservationRepository repository;
	
	private static List <Reservation> reservations;
	
	@BeforeClass
	 public static void setupTestData() {
		reservations = new LinkedList<Reservation>();
		reservations.add(new Reservation(1, "Justin", "Trudeau", "justin.trudeau@gmail.com", 2, 
				Date.valueOf("2021-04-18"), Date.valueOf("2021-04-20"), 1));
		reservations.add(new Reservation(2, "Joe", "Biden", "joe.biden@gmail.com", 1,
				Date.valueOf("2021-04-21"), Date.valueOf("2021-04-21"), 1));
		reservations.add(new Reservation(3, "Manuel", "LopezObrador", "manuel.lopezobrador@gmail.com", 3,
				Date.valueOf("2021-04-22"), Date.valueOf("2021-04-24"), 1));
		reservations.add(new Reservation(4, "Francisco", "Sagasti", "francisco.sagasti@gmail.com", 3,
				Date.valueOf("2021-04-26"), Date.valueOf("2021-04-28"), 1));

	 }

	@Test
	public void findAllBookings() throws Exception {
		
		String apiUrl = "/bookings";
		Mockito.when(repository.findAll()).thenReturn(reservations);

		RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(rb).andReturn();
		System.out.println("ReservationRESTfulTest - findBookingById - MvcResult: " + result.getResponse());
		String tr = result.getResponse().getContentAsString();

		String expected = "[{\"id\":1,\"firstName\":\"Justin\",\"lastName\":\"Trudeau\",\"email\":\"justin.trudeau@gmail.com\",\"numberGuests\":2,\"checkin\":\"2021-04-18\",\"checkout\":\"2021-04-20\",\"status\":1},{\"id\":2,\"firstName\":\"Joe\",\"lastName\":\"Biden\",\"email\":\"joe.biden@gmail.com\",\"numberGuests\":1,\"checkin\":\"2021-04-21\",\"checkout\":\"2021-04-21\",\"status\":1},{\"id\":3,\"firstName\":\"Manuel\",\"lastName\":\"LopezObrador\",\"email\":\"manuel.lopezobrador@gmail.com\",\"numberGuests\":3,\"checkin\":\"2021-04-22\",\"checkout\":\"2021-04-24\",\"status\":1},{\"id\":4,\"firstName\":\"Francisco\",\"lastName\":\"Sagasti\",\"email\":\"francisco.sagasti@gmail.com\",\"numberGuests\":3,\"checkin\":\"2021-04-26\",\"checkout\":\"2021-04-28\",\"status\":1}]";
		JSONAssert.assertEquals(expected, tr, true);

	}
	
	@Test
	public void findBooking() throws Exception {
		
		String apiUrl = "/bookings/1/";
		Reservation reservation = new Reservation(1, "Justin", "Trudeau", "justin.trudeau@gmail.com", 2, 
				Date.valueOf("2021-04-18"), Date.valueOf("2021-04-20"), 1);
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(reservation));

		RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(rb).andReturn();
		System.out.println("ReservationRESTfulTest - findBookingById - MvcResult: " + result.getResponse());
		String tr = result.getResponse().getContentAsString();

		String expected = "{\"id\":1,\"firstName\":\"Justin\",\"lastName\":\"Trudeau\",\"email\":\"justin.trudeau@gmail.com\",\"numberGuests\":2,\"checkin\":\"2021-04-18\",\"checkout\":\"2021-04-20\",\"status\":1}";
		JSONAssert.assertEquals(expected, tr, true);

	}

	@Test
	public void saveBooking() throws Exception {
		
		Reservation reservation = new Reservation("Justin", "Trudeau", "justin.trudeau@gmail.com", 2, 
				Date.valueOf("2021-04-18"), Date.valueOf("2021-04-20"), 1);
		Mockito.when(repository.save(any(Reservation.class))).thenReturn(reservation);
		System.out.println("ReservationRESTfulTest - saveBooking - firstName: " + reservation.getFirstName());
		
		assertEquals("Justin", reservation.getFirstName());

	}
	
 }
