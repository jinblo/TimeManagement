package TeamRed.TimeManagementBE.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.Project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@RestController
public class ProjectRESTController {

    @Autowired
	private ProjectRepository repository;
    
    private String getUserDetails() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String username = auth.getName();
    	return username;
    }

    //Kaikkien tietyn käyttäjän projektien haku
	@GetMapping("projects")
	@JsonView(Project.ProjectOverview.class)
	public ResponseEntity<?> getProjects() {
		try {
			Iterable<Project> projects = repository.findByAppUser_Email(getUserDetails());
			if (((List<Project>) projects).isEmpty()) {
				return new ResponseEntity<>("Projekteja ei löytynyt", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(projects, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Palauttaa projektin haetulla id:llä, jos kyseessä käyttäjän oma projekti
		@GetMapping("/projects/{projectId}")
		@JsonView(Project.DetailedProjectView.class)
		public ResponseEntity<?> getProjectById(@PathVariable("projectId") Long id) {
			try {
				Optional<Project> projectById = repository.findById(id);
				if (!projectById.isEmpty() && projectById.get().getAppUser().getEmail().equals(getUserDetails())) {
					return new ResponseEntity<>(projectById, HttpStatus.OK);
				}
				return new ResponseEntity<>("Annetulla id:llä ei löytynyt projektia", HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
			
	//Uuden projektin lisääminen
	@PostMapping("projects")
	public ResponseEntity<Project> addProject(@RequestBody Project project) {
		try {
			Project newProject = repository.save(project);
			return new ResponseEntity<>(newProject, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Projektin muokkaus
	@PutMapping("projects/{projectId}")
	public ResponseEntity<?> editProject(@RequestBody Project updatedProject, @PathVariable("projectId") Long id) {
		try {
			Optional<Project> toBeEdited = repository.findById(id);
			if (toBeEdited.isEmpty()) {
				return new ResponseEntity<>("Projektia ei löytynyt", HttpStatus.NOT_FOUND);
			}
			Project project = toBeEdited.get();
			project.setTitle(updatedProject.getTitle());
			repository.save(project);
			return new ResponseEntity<>(project, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Projektin poisto
	@DeleteMapping("projects/{projectId}")
	public ResponseEntity<String> removeProject(@PathVariable("projectId") Long id) {
		try {
			Optional<Project> removableProject = repository.findById(id);
			if (removableProject.isEmpty()) {
				return new ResponseEntity<>("Projektia ei löytynyt", HttpStatus.NOT_FOUND);
			}
			repository.delete(removableProject.get());
			return new ResponseEntity<>("Projekti poistettu onnistuneesti", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}