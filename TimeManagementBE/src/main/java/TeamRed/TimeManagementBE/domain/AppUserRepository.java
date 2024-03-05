
package TeamRed.TimeManagementBE.domain;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
	
	AppUser findByEmail(String email);

}
