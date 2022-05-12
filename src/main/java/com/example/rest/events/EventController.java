package com.example.rest.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/api", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
	
	@PostMapping("/events")
	public ResponseEntity<?> createEvent(@RequestBody Event event) {
		
		URI createUri = linkTo(EventController.class).slash("{id}").toUri();
		event.setId(10);
		return ResponseEntity.created(createUri).body(event);
	}
}
