package com.example.rest.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventController {
	
	@PostMapping("/api/events")
	public ResponseEntity<?> createEvent() {
		
		URI createUri = linkTo(methodOn(EventController.class).createEvent()).slash("{id}").toUri();
		
		return ResponseEntity.created(createUri).build();
	}
}
