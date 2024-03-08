package TeamRed.TimeManagementBE.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Project {
	
	public interface ProjectOverview {};
	public interface DetailedProjectView extends ProjectOverview{};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(ProjectOverview.class)
	private long id;
	@JsonView(ProjectOverview.class)
	private String title;
	@ManyToOne
	@JoinColumn(name = "appUser_id")
	@JsonIgnore
	private AppUser appUser;
	//@JsonIgnoreProperties({"project"})
	@JsonView(DetailedProjectView.class)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="project")
	private List<Entry> entries;
	
	public Project() {}
	
	public Project(String title, AppUser appUser) {
		super();
		this.title = title;
		this.appUser = appUser;
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

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + "]";
	}
	
}
