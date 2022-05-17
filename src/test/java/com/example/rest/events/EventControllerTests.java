package com.example.rest.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper; //객체를 json으로 변환. 스프링부트사용할때 등록되어져있음
	
	//@MockBean
	//EventRepository eventRepository;
	
	@Test
	public void createEvent() throws Exception {
		
		EventDto event = EventDto.builder()
				      .name("Spring")
				      .description("REST API Hello World!!")
				      .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,21))
				      .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14,21))
				      .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
				      .endEventDateTime(LocalDateTime.of(2018, 11,26,14,21))
				      .basePrice(100)
				      .maxPrice(200)
				      .limitOfEnrollment(100)
				      .location("m2b 공유오피스")
				      .build();
		//Mockito.when(eventRepository.save(event)).thenReturn(event); //save가 호출됬을때 event 리턴
		
		mockMvc.perform(post("/api/events")
				   .contentType(MediaType.APPLICATION_JSON_UTF8)
				   .accept(MediaTypes.HAL_JSON)
				   .content(objectMapper.writeValueAsString(event)))
			    .andDo(print())
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("id").exists())
		        .andExpect(header().exists(HttpHeaders.LOCATION))
		        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
		        .andExpect(jsonPath("id").value(Matchers.not("100")))
		        .andExpect(jsonPath("free").value(Matchers.not(true)))
		        .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
		        
		         
	}
	
	@Test
	public void createEvent_BadRequest() throws Exception {
		
		Event event = Event.builder()
		              .id(100)
				      .name("Spring")
				      .description("REST API Hello World!!")
				      .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,21))
				      .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14,21))
				      .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
				      .endEventDateTime(LocalDateTime.of(2018, 11,26,14,21))
				      .basePrice(100)
				      .maxPrice(200)
				      .limitOfEnrollment(100)
				      .location("m2b 공유오피스")
				      .free(true)
				      .offline(true)
				      .build();
		//Mockito.when(eventRepository.save(event)).thenReturn(event); //save가 호출됬을때 event 리턴
		
		mockMvc.perform(post("/api/events")
				   .contentType(MediaType.APPLICATION_JSON_UTF8)
				   .accept(MediaTypes.HAL_JSON)
				   .content(objectMapper.writeValueAsString(event)))
			    .andDo(print())
		        .andExpect(status().isBadRequest());
		        
		        
		         
	}
	
	
	@Test
	public void createEvent_Bad_Request_Empty_Input() throws JsonProcessingException, Exception {
		
		EventDto eventDto = EventDto.builder().build();
		
		this.mockMvc.perform(post("/api/events")
				    .contentType(MediaType.APPLICATION_JSON_UTF8)
				    .content(this.objectMapper.writeValueAsString(eventDto)))
		            .andExpect(status().isBadRequest());
		
		
		
	}
	
	
	@Test
	public void createEvent_Bad_Request_Wrong_Input() throws JsonProcessingException, Exception {
		
		EventDto eventDto = EventDto.builder()
								  .name("Spring")
							      .description("REST API Hello World!!")
							      .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 26, 14,21))
							      .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14,21))
							      .beginEventDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
							      .endEventDateTime(LocalDateTime.of(2018, 11,23,14,21))
							      .basePrice(10000)
							      .maxPrice(200)
							      .limitOfEnrollment(100)
							      .location("m2b 공유오피스")
							      .build();
		
		this.mockMvc.perform(post("/api/events")
				    .contentType(MediaType.APPLICATION_JSON_UTF8)
				    .content(objectMapper.writeValueAsString(eventDto)))
		            .andExpect(status().isBadRequest());
		
		
		
	}
	
}
