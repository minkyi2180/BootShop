package com.mysite.prac.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.mysite.prac.user.SiteUser;

public class PrincipalDetails implements UserDetails, OAuth2User{
	 private SiteUser user;
	 private Map<String, Object> attributes;

	 public PrincipalDetails(SiteUser user, Map<String, Object> attributes) {
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

	    // get Password 메서드
	    @Override
	    public String getPassword() {
	        return user.getPassword();
	    }

	    // get Username 메서드 (생성한 User은 loginId 사용)
	    @Override
	    public String getUsername() {
	        return user.getUsername();
	    }

	    // 계정이 만료 되었는지 (true: 만료X)
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    // 계정이 잠겼는지 (true: 잠기지 않음)
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    // 비밀번호가 만료되었는지 (true: 만료X)
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    // 계정이 활성화(사용가능)인지 (true: 활성화)
	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

		@Override
		public Map<String, Object> getAttributes() {
			// TODO Auto-generated method stub
			return attributes;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
}
