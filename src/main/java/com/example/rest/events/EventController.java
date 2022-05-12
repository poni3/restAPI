package com.example.rest.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/api", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {
	
    
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    
	@PostMapping("/events")
	public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {
		
	    //모델 맵퍼
	    Event event = modelMapper.map(eventDto, Event.class);
	    
	    Event newEvent = this.eventRepository.save(event);
		URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
		return ResponseEntity.created(createUri).body(event);
	}
}
