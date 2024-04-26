package TeamRed.TimeManagementBE.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class AppUser {
	
	public interface BasicUserView {}
	public interface UserDetailsView extends BasicUserView {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(BasicUserView.class)
    private long id;
    @NotBlank
    @Size(max = 20)
    @JsonView(UserDetailsView.class)
    private String first_name;
    @NotBlank
    @Size(max = 30)
    @JsonView(UserDetailsView.class)
    private String last_name;
    @NotNull
    @Size(max = 20)
    @Column(unique = true)
    @JsonView(BasicUserView.class)
    private String username;
    @NotNull
    @Size(min = 8, max = 100)
    private String password_hash;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
    private Set<UserProjectRole> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "appUser")
    private List<Entry> entries;

    public AppUser() {
    }

    public AppUser(String first_name, String last_name, String username, String password_hash) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password_hash = password_hash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public Set<UserProjectRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserProjectRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AppUser [id=" + id + ", username=" + username + "]";
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

}