package com.mysite.prac.user;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.prac.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(UserRole.USER); //가입하면 유저
		this.userRepository.save(user);
		return user;
	}
	
	public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
        	
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
	
	public void modifyPassword(SiteUser modifyUser, String password) {
        modifyUser.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(modifyUser);
    }

    public boolean isSamePassword(SiteUser user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }
	
    public Optional<SiteUser> getByUserName(String username) {
    	Optional<SiteUser> user = this.userRepository.findByusername(username);
    	if(user.isPresent()) {
    		return user;
    	}else {
    		throw new com.mysite.prac.DataNotFoundException("SiteUser Not found");
    	}
    }
    
    
	
	

	
	

}
