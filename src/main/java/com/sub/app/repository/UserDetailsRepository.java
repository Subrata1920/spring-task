package com.sub.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sub.app.domain.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

	UserDetails findByUserIdAndEnabledTrue(Integer actorId);

	UserDetails findByUserNameAndEnabledTrue(String userName);

}
