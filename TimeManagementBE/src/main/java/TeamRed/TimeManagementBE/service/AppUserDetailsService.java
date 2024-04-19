package TeamRed.TimeManagementBE.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import TeamRed.TimeManagementBE.domain.AppUserRepository;
import TeamRed.TimeManagementBE.domain.ProjectRoleKey;
import TeamRed.TimeManagementBE.domain.Role;
import TeamRed.TimeManagementBE.domain.UserProjectRole;
import TeamRed.TimeManagementBE.domain.UserProjectRoleRepository;
import TeamRed.TimeManagementBE.CustomAuthenticationToken;
import TeamRed.TimeManagementBE.CustomUserDetails;
import TeamRed.TimeManagementBE.domain.AppUser;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserRepository userRepository;
	
    @Autowired
    private UserProjectRoleRepository roleRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String username) {
		AppUser user = userRepository.findByUsername(username);
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
	
	public CustomUserDetails loadUserById(long id) {
		Optional<AppUser> userToBeFound = userRepository.findById(id);
		if (userToBeFound.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		AppUser user = userToBeFound.get();
		return new CustomUserDetails(
				user.getUsername(),
				user.getPassword_hash(),
				Collections.emptyList(),
				user.getId(),
				user.getFirst_name(),
				user.getLast_name()
				);
	}
	
	public long getAuthIdentity() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomAuthenticationToken customToken = (CustomAuthenticationToken) auth;
		return customToken.getUser_id();
	}
	
	public AppUser getAuthUser() {
		Optional<AppUser> user = userRepository.findById(getAuthIdentity());
		return user.orElse(null);
		
	}
	
    public Role getUserRole(Long projectId) {
    	ProjectRoleKey key = new ProjectRoleKey(projectId, getAuthIdentity());
		UserProjectRole userProjectRole = roleRepository.findById(key);
		if (userProjectRole != null) {
			return userProjectRole.getRole();
		} else {
			return null;
		}
    }
}

