package com.example.rest.events;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
	
	public void validae(EventDto eventDto, Errors errors) {
		
		if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
			errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong");
			errors.rejectValue("MaxPrice", "wrongValue", "Max is wrong");
		}
		
		LocalDateTime end = eventDto.getEndEventDateTime();
		if(end.isBefore(eventDto.getBeginEventDateTime())||
				end.isBefore(eventDto.getCloseEnrollmentDateTime())||
				end.isBefore(eventDto.getBeginEnrollmentDateTime())) {
			errors.rejectValue("EndEventDateTime", "wrongValue", "EndEventDateTime is wrong");
		}
		
		//TODO beginEventDateTime
		//TODO getCloseEnrollmentDateTime
		
	}
	
}
