package com.sub.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sub.app.domain.Events;

public interface EventsRepository extends JpaRepository<Events, Integer>{

	Events findByEventIdAndUserId(Integer evnetId, Integer userId);

	List<Events> findAllByOrderByEventIdAsc();

	List<Events> findByUserId(Integer userId);

}
