package com.mysite.prac.user;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Pattern;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	SiteUser findAllByusername(String username);
	Optional<SiteUser> findByusername(String username);
	Optional<SiteUser> findById(Long id);
	SiteUser findByUsername(String username);
	
	
	
}
