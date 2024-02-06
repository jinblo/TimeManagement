package TeamRed.TimeManagementBE.domain;

import java.sql.Time;
import java.util.Date;

public class Entry {
	
	private long entry_id;
	private String entry_title;
	private String entry;
	private Date entry_date;
	private Time start_time;
	private Time end_time;
	
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
	public Date getEntry_date() {
		return entry_date;
	}
	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}
	public Time getStart_time() {
		return start_time;
	}
	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}
	public Time getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}
	
	

}
