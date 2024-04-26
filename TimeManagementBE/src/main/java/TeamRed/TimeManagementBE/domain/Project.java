package TeamRed.TimeManagementBE.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import TeamRed.TimeManagementBE.domain.AppUser.BasicUserView;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Project {
	
	public interface ProjectOverview extends BasicUserView {};
	public interface DetailedProjectView extends ProjectOverview, EntryListView {};
	public interface EntryListView extends ProjectOverview {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(ProjectOverview.class)
	private long id;
	@Size(min=2, max=50)
	@NotBlank(message = "Pakollinen kenttä")
	@JsonView(ProjectOverview.class)
	private String title;
	
	@JsonIgnoreProperties(value = {"project"}, allowSetters = true)
	@JsonView(ProjectOverview.class)
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Set<UserProjectRole> roles = new HashSet<>();
	
	@JsonIgnoreProperties(value = {"project"}, allowSetters = true)
	@JsonView(DetailedProjectView.class)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="project")
	private List<Entry> entries;
	
	public Project() {}
	
	public Project(String title) {
		super();
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<UserProjectRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserProjectRole> roles) {
		this.roles = roles;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + "]";
	}
	
}
