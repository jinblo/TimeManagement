 package TeamRed.TimeManagementBE.web;

//import java.util.List;
//import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

//import com.fasterxml.jackson.annotation.JsonView;

//import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.UserProjectRole;
//import TeamRed.TimeManagementBE.domain.UserProjectRoleRepository;
//import jakarta.validation.Valid;
import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;
//import TeamRed.TimeManagementBE.domain.Project;

import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin
@RestController
@RequestMapping("/projects")
public class ProjectRESTController {

    //@Autowired
	//private ProjectRepository repository;
    
    @Autowired
    private AppUserRepository userRepository;
    
    //@Autowired
    //private UserProjectRoleRepository roleRepository;
    
    private AppUser getUserDetails() {    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username);
    }

    //Kaikkien tietyn käyttäjän projektien haku
	@GetMapping
	//@JsonView(Project.ProjectOverview.class)
	public ResponseEntity<?> getProjects() {
		try {
			//Iterable<Project> projects = repository.findByAppUser(getUserDetails());
			AppUser user = getUserDetails();

			Set<UserProjectRole> projects = user.getRoles();
			for (UserProjectRole role : projects) {
			    System.out.println(role);
			}
			if (((Set<UserProjectRole>) projects).isEmpty()) {
				return new ResponseEntity<>("Projekteja ei löytynyt", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(projects, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Palauttaa projektin haetulla id:llä, jos kyseessä käyttäjän oma projekti
	/*@GetMapping("/{projectId}")
	@JsonView(Project.DetailedProjectView.class)
	public ResponseEntity<?> getProjectById(@PathVariable("projectId") Long id) {
		try {
			Optional<Project> projectById = repository.findById(id);
			if (!projectById.isEmpty() && projectById.get().getAppUser().getId() == (getUserDetails().getId())) {
				return new ResponseEntity<>(projectById, HttpStatus.OK);
			}
			return new ResponseEntity<>("Annetulla id:llä ei löytynyt projektia", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
			
	//Uuden projektin lisääminen
	@PostMapping
	public ResponseEntity<Project> addProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		try {
			AppUser user = getUserDetails();
			Project newProject = new Project();
			newProject.setAppUser(user);
			newProject.setTitle(project.getTitle());
			repository.save(newProject);
			return new ResponseEntity<>(newProject, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Projektin muokkaus
	@PutMapping("/{projectId}")
	public ResponseEntity<?> editProject(@Valid @RequestBody Project updatedProject, @PathVariable("projectId") Long id, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		try {
			Optional<Project> toBeEdited = repository.findById(id);
			if (!toBeEdited.isEmpty() && toBeEdited.get().getAppUser().getId() == (getUserDetails().getId())) {
				Project project = toBeEdited.get();
				project.setTitle(updatedProject.getTitle());
				repository.save(project);
				return new ResponseEntity<>(project, HttpStatus.OK);
			}
			return new ResponseEntity<>("Annetulla id:llä ei löytynyt projektia", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Projektin poisto
	@DeleteMapping("/{projectId}")
	public ResponseEntity<String> removeProject(@PathVariable("projectId") Long id) {
		try {
			Optional<Project> removableProject = repository.findById(id);
			if (!removableProject.isEmpty() && removableProject.get().getAppUser().getId() == (getUserDetails().getId())) {
				repository.delete(removableProject.get());
				return new ResponseEntity<>("Projekti poistettu onnistuneesti", HttpStatus.OK);		
			}
			return new ResponseEntity<>("Annetulla id:llä ei löytynyt projektia", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
}