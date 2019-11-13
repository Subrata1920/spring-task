package com.sub.app.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sub.app.domain.Events;
import com.sub.app.domain.UserDetails;
import com.sub.app.repository.EventsRepository;
import com.sub.app.repository.UserDetailsRepository;
import com.sub.app.util.Constants;

@Service
public class CrudServiceImpl implements CrudService{
	
	@Autowired
	private EventsRepository eventsRepository;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Override
	public ResponseEntity<String> erase() {
		eventsRepository.deleteAll();
		return new ResponseEntity<String>(Constants.ERASED, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> addEvents(Events events) {
		Events ev = eventsRepository.findByEventIdAndUserId(events.getEventId(), events.getUserId());
		
		if(ev != null && ev.getEventId() == events.getEventId()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.EXISTS);
		}else {
			eventsRepository.save(events);
			return ResponseEntity.status(HttpStatus.CREATED).body(Constants.CREATED_MSG);
		}
	}

	@Override
	public ResponseEntity<List<Events>> getAllEvents() {
		return ResponseEntity.status(HttpStatus.OK).body(eventsRepository.findAllByOrderByEventIdAsc());
	}
	
	@Override
	public ResponseEntity<List<Events>> getEventsByActor(Integer actorId) {
		UserDetails user = userDetailsRepository.findByUserIdAndEnabledTrue(actorId);
		
		if(user != null) {
			List<Events> eventList = eventsRepository.findByUserId(user.getUserId());
			return ResponseEntity.status(HttpStatus.OK).body(eventList.stream().sorted(Comparator.comparingInt(Events::getEventId)).collect(Collectors.toList()));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity<String> updateActor(@Valid UserDetails userDetails) {
		UserDetails user = userDetailsRepository.findByUserNameAndEnabledTrue(userDetails.getUserName());
		
		if(user != null) {
			if(userDetails.getUserName().equals(user.getUserName()) && userDetails.isCredentialexpired()==user.isCredentialexpired()
					&& userDetails.getRole().equals(user.getRole()) && userDetails.isEnabled() == user.isEnabled() 
					&& userDetails.isExpired() == user.isExpired() && userDetails.getPassword().equals(user.getPassword())
					&& !userDetails.getPhoto().equals(user.getPhoto())) {
				/** 
				 * According to the requirement we can update the user details here.
				 */
				user.setPhoto(userDetails.getPhoto());
				return ResponseEntity.status(HttpStatus.OK).body(Constants.USER_UPDATE);
			}else if(userDetails.getUserName().equals(user.getUserName()) && userDetails.isCredentialexpired()==user.isCredentialexpired()
					&& userDetails.getRole().equals(user.getRole()) && userDetails.isEnabled() == user.isEnabled() 
					&& userDetails.isExpired() == user.isExpired() && userDetails.getPassword().equals(user.getPassword())
					&& userDetails.getPhoto().equals(user.getPhoto())){
				
				return ResponseEntity.status(HttpStatus.OK).body(Constants.NO_CHANGE);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.UNABLE_TO_UPDATE);
			}
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.USER_ID_NOT_FOUND);
		}
	}
	
	@Override
	public ResponseEntity<List<UserDetails>> getAllActors() {
		return ResponseEntity.status(HttpStatus.OK).body(userDetailsRepository.findAll());
	}
	
	@Override
	public ResponseEntity<Map<Integer, List<Events>>> getEventsByActor() {
		Map<Integer, List<Events>> events = eventsRepository.findAll()
				.stream().collect(Collectors.groupingBy(Events::getUserId));
		
		
		return ResponseEntity.status(HttpStatus.OK).body(events);
	}
}
