package TeamRed.TimeManagementBE.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Entry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long entry_id;

	private String entry_title;
	private String entry;
	private LocalDate entry_date;
	private LocalTime start_time;
	private LocalTime end_time;

	@ManyToOne
	@JoinColumn(name = "project_id")
	@JsonIgnoreProperties({ "entries" })
	private Project project;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties({ "userEntries" })
	private User user;

	public Entry() {
		super();
	}

	public Entry(String entry_title, String entry, LocalDate entry_date, LocalTime start_time, LocalTime end_time,
			Project project) {
		super();
		this.entry_title = entry_title;
		this.entry = entry;
		this.entry_date = entry_date;
		this.start_time = start_time;
		this.end_time = end_time;
		this.project = project;
	}

	public long getEntry_id() {
		return entry_id;
	}

	public void setEntry_id(long entry_id) {
		this.entry_id = entry_id;
	}

	public String getEntry_title() {
		return entry_title;
	}

	public void setEntry_title(String entry_title) {
		this.entry_title = entry_title;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public LocalDate getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(LocalDate entry_date) {
		this.entry_date = entry_date;
	}

	public LocalTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}

	public LocalTime getEnd_time() {
		return end_time;
	}

	public void setEnd_time(LocalTime end_time) {
		this.end_time = end_time;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
