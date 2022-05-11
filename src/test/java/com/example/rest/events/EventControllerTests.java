package com.example.rest.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper; //객체를 json으로 변환. 스프링부트사용할때 등록되어져있음
	
	@Test
	public void createEvent() throws Exception {
		
		Event event = Event.builder()
				      .name("Spring")
				      .description("REST API Hello World!!")
				      .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,21))
				      .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14,21))
				      .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
				      .beginEventDateTime(LocalDateTime.of(2018, 11,26,14,21))
				      .basePrice(100)
				      .maxPrice(200)
				      .limitOfEnrollment(100)
				      .location("m2b 공유오피스").id(10)
				      .build();
		
		mockMvc.perform(post("/api/events/")
				   .contentType(MediaType.APPLICATION_JSON_UTF8)
				   .accept(MediaTypes.HAL_JSON)
				   .content(objectMapper.writeValueAsString(event)))
			    .andDo(print())
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("id").exists());
		         
	}
	
}
