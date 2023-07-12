package com.mysite.prac.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserSecurity  implements UserDetails, OAuth2User{
	
	 private SiteUser user;
	private Map<String, Object> attributes;

	    public UserSecurity(SiteUser user, Map<String, Object> attributes) {
	        this.user = user;
	        this.attributes = attributes;
	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 Collection<GrantedAuthority> collections = new ArrayList<>();
	        collections.add(() -> {
	            return user.getRole().name();
	        });

	        return collections;
	    }

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
//계정만료여부(true: 만료x)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
//계정 잠김여부(true:잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		
		return null;
	}

}
