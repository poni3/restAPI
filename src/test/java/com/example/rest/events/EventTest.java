package com.example.rest.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class EventTest {
	
	@Test
	public void builder() {
		Event event = Event.builder()
				           .name("Inflearn Spring REST APO")
				           .description("REST API development with Spring")
				           .build();
		
		assertThat(event).isNotNull();
	}
	
	@Test
	public void javaBean() {
		Event event = new Event();
		String name = "Event";
		event.setName(name);
		String descrption = "Spring";
		event.setDescription(descrption);
		
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(descrption);
	}
}
