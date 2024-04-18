package TeamRed.TimeManagementBE.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long>{
	
	Iterable<Entry> findByAppUser(AppUser appUser);
	
	List<Entry> findByProjectAndAppUser(Project project, AppUser appUser);
}
