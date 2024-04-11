package TeamRed.TimeManagementBE;

import java.util.Collection;
//import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private long id;
	private String first_name;
	private String last_name;
	
	public CustomUserDetails (String username, String password_hash, Collection<? extends GrantedAuthority> authorities, long id, String first_name, String last_name) {
        this.username = username;
        this.password = password_hash;
        this.authorities = authorities;
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }
	
	@Override
	public String getUsername() {
		return username;
		}
	
	@Override
	public String getPassword() {
		return password;
		}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return Collections.emptyList();
		return authorities;
		}

	public long getId() {
		return id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}
	
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
	
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }

}
