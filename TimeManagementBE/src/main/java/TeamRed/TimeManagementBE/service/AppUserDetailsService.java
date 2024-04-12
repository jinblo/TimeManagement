package TeamRed.TimeManagementBE.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import TeamRed.TimeManagementBE.domain.AppUserRepository;
import TeamRed.TimeManagementBE.CustomAuthenticationToken;
import TeamRed.TimeManagementBE.CustomUserDetails;
import TeamRed.TimeManagementBE.domain.AppUser;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		AppUser user = repository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(
				user.getUsername(),
				user.getPassword_hash(),
				Collections.emptyList(),
				user.getId(),
				user.getFirst_name(),
				user.getLast_name()
				);
	}
	
	public long getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomAuthenticationToken customToken = (CustomAuthenticationToken) auth;
		return customToken.getUser_id();
		
	}
}

