package TeamRed.TimeManagementBE.web;
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
	private final AppUserRepository repository;

	@Autowired
	public AppUserDetailsService(AppUserRepository appuserRepository) {
		this.repository = appuserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException

	{   
		AppUser curruser = repository.findByEmail(username);
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword_hash(), 
				AuthorityUtils.createAuthorityList(curruser.getEmail()));
		return user;
	} 
	

}