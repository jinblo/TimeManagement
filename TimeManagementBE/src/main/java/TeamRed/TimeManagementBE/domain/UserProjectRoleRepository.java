package TeamRed.TimeManagementBE.domain;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface UserProjectRoleRepository extends CrudRepository<UserProjectRole, Long> {

	UserProjectRole findById(ProjectRoleKey key);
	
	Set<UserProjectRole> findByAppUser_id(long user_id);

}
