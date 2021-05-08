package com.example.test;

import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReservationRESTfulTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ReservationsController controller;
	
	@Autowired
	ReservationRepository repository;
	
	private static List <Reservation> reservations;
	private static Reservation reservation;
	
	@BeforeClass
	 public static void initial() {
		// add some data
		reservations = new LinkedList<Reservation>();
		reservations.add(new Reservation(1, "Justin", "Trudeau", "justin.trudeau@gmail.com", 2, 
				Date.valueOf("2021-04-18"), Date.valueOf("2021-04-20"), 1));
		reservations.add(new Reservation(2, "Joe", "Biden", "joe.biden@gmail.com", 1,
				Date.valueOf("2021-04-21"), Date.valueOf("2021-04-21"), 1));
		reservations.add(new Reservation(3, "Manuel", "LopezObrador", "manuel.lopezobrador@gmail.com", 3,
				Date.valueOf("2021-04-22"), Date.valueOf("2021-04-24"), 1));
		reservations.add(new Reservation(4, "Francisco", "Sagasti", "francisco.sagasti@gmail.com", 3,
				Date.valueOf("2021-04-26"), Date.valueOf("2021-04-28"), 1));
		
		reservation = new Reservation(5, "Jorge", "Whittembury", "jorge.whittembury@gmail.com", 2);
		
	 }
	
	@Test
    public void testReservationsRoute() throws Exception {
        this.mockMvc.perform(get("/reservations").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }
	
	@Test
	public void findAllBookings() throws Exception {
		
		String apiUrl = "/bookings";
		Mockito.when(controller.findAll()).thenReturn(reservations);

		RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(rb).andReturn();
		
		String tr = result.getResponse().getContentAsString();
		System.out.println("ReservationRESTfulTest - findAllBookings - MvcResult: " + tr);

		String expected = "[{\"id\":1,\"firstName\":\"Justin\",\"lastName\":\"Trudeau\",\"email\":\"justin.trudeau@gmail.com\",\"numberGuests\":2,\"checkin\":\"2021-04-18\",\"checkout\":\"2021-04-20\",\"status\":1},{\"id\":2,\"firstName\":\"Joe\",\"lastName\":\"Biden\",\"email\":\"joe.biden@gmail.com\",\"numberGuests\":1,\"checkin\":\"2021-04-21\",\"checkout\":\"2021-04-21\",\"status\":1},{\"id\":3,\"firstName\":\"Manuel\",\"lastName\":\"LopezObrador\",\"email\":\"manuel.lopezobrador@gmail.com\",\"numberGuests\":3,\"checkin\":\"2021-04-22\",\"checkout\":\"2021-04-24\",\"status\":1},{\"id\":4,\"firstName\":\"Francisco\",\"lastName\":\"Sagasti\",\"email\":\"francisco.sagasti@gmail.com\",\"numberGuests\":3,\"checkin\":\"2021-04-26\",\"checkout\":\"2021-04-28\",\"status\":1}]";
		JSONAssert.assertEquals(expected, tr, true);

	}
	
	@Test
	public void findOneBooking() throws Exception {

		Mockito.when(controller.findOne(Mockito.anyLong())).thenReturn(reservation);
		
		String tr = mockMvc.perform( MockMvcRequestBuilders
			      .get("/bookings/{id}", 1L)
			      .accept(MediaType.APPLICATION_JSON))
			      .andReturn()
			      .getResponse()
			      .getContentAsString();
		System.out.println("ReservationRESTfulTest - findOneBooking - MvcResult: " + tr);
	}

	@Test
	public void updateBooking() throws Exception {
		
		Mockito.when(controller.updateBooking("Justin", "Trudeau", "justin.trudeau@gmail.com", "2", "2021-04-18", "2021-04-20", 1L)).thenReturn(reservation);
		System.out.println("ReservationRESTfulTest - saveBooking - firstName: " + reservation.getFirstName());
		
		mockMvc.perform( MockMvcRequestBuilders
			      .put("/bookings/{id}", 1L)
			      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			      .param("firstName", "Justin")
			      .param("lastName", "Trudeau")
			      .param("email", "justin.trudeau@gmail.com")
			      .param("numberGuests", "2")
			      .param("checkin", "2021-05-09")
			      .param("checkout", "2021-05-11")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());		
	}
	
	@Test
	public void deleteBooking() throws Exception {
		
		String success = "success";
		Mockito.when(controller.deleteOne(Mockito.anyLong())).thenReturn(success);
		
		String tr = mockMvc.perform( MockMvcRequestBuilders
			      .delete("/bookings/{id}", 1L)
			      .accept(MediaType.APPLICATION_JSON))
			      .andReturn()
			      .getResponse()
			      .getContentAsString();

		System.out.println("ReservationRESTfulTest - deleteBooking - MvcResult: " + tr);
		
		assertEquals("success", tr);

	}
	
	@Test
	public void reservationsSearch() throws Exception {
		
		Mockito.when(controller.bookingsSearch("",  "")).thenReturn(reservations);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/bookingsSearch")
			      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			      .param("from", "")
			      .param("to", "")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andDo(print());		
	}
	
 }
