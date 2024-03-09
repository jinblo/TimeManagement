package TeamRed.TimeManagementBE.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import TeamRed.TimeManagementBE.domain.AppUserRepository;
import TeamRed.TimeManagementBE.domain.AppUser;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AppUserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		AppUser curruser = repository.findByEmail(email);
		if (curruser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		UserDetails user = new org.springframework.security.core.userdetails.User(email, curruser.getPassword_hash(),
				AuthorityUtils.createAuthorityList(curruser.getEmail()));
		return user;
	}
	
}