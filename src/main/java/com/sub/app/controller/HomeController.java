package com.sub.app.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sub.app.domain.Events;
import com.sub.app.domain.UserDetails;
import com.sub.app.service.CrudService;

@RestController
public class HomeController {
	
	@Autowired
	public CrudService crudService;
	
	@RequestMapping(value= {"/","/home"})
	public String home() {
		return "home";
	}
	
	@RequestMapping(value= "/erase")
	public ResponseEntity<String> erase() {
		return crudService.erase();
	}
	
	@PostMapping(value= "/events")
	public ResponseEntity<String> addEvents(@RequestBody @Valid Events events) {
		return crudService.addEvents(events);
	}
	
	@GetMapping(value= "/getAllEvents")
	public ResponseEntity<List<Events>> getAllEvents() {
		return crudService.getAllEvents();
	}
	
	@GetMapping(value= "/events/actor/{actorId}")
	public ResponseEntity<List<Events>> getEventsByActor(@PathVariable("actorId") Integer actorId) {
		return crudService.getEventsByActor(actorId);
	}
	
	@PutMapping(value= "/actors")
	public ResponseEntity<String> updateActor(@RequestBody @Valid UserDetails userDetails) {
		return crudService.updateActor(userDetails);
	}
	
	@GetMapping(value= "/getAllActors")
	public ResponseEntity<List<UserDetails>> getAllActors() {
		return crudService.getAllActors();
	}
	
	@GetMapping(value= "/getEventsByActor")
	public ResponseEntity<Map<Integer, List<Events>>> getEventsByActor() {
		return crudService.getEventsByActor();
	}
}
