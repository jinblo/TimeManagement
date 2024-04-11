package TeamRed.TimeManagementBE.service;
//import java.util.Collection;
import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import TeamRed.TimeManagementBE.domain.AppUserRepository;
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
		/*Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("id", currentUser.getId());
		userDetails.put("first_name", currentUser.getFirst_name());
		userDetails.put("last_name", currentUser.getLast_name());
		UserDetails user = new org.springframework.security.core.userdetails.User(
				username,
				currentUser.getPassword_hash(),
				Collections.emptyList(),
				userDetails);
		return user;*/
		//Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
		return new CustomUserDetails(user.getUsername(), user.getPassword_hash(), Collections.emptyList(), user.getId(), user.getFirst_name(), user.getLast_name());
	}
}

