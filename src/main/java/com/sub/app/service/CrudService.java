package com.sub.app.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.sub.app.domain.Events;
import com.sub.app.domain.UserDetails;

public interface CrudService {

	ResponseEntity<String> erase();

	ResponseEntity<String> addEvents(Events events);

	ResponseEntity<List<Events>> getAllEvents();

	ResponseEntity<List<Events>> getEventsByActor(Integer actorId);

	ResponseEntity<String> updateActor(@Valid UserDetails userDetails);

	ResponseEntity<List<UserDetails>> getAllActors();

	ResponseEntity<Map<Integer, List<Events>>> getEventsByActor();

}
