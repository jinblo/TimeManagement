package TeamRed.TimeManagementBE.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.Project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin
@RestController
public class ProjectRESTController {

    @Autowired
	private ProjectRepository repository;

    //Kaikkien projektien haku
	@GetMapping("projects")
	public ResponseEntity<?> getProjects() {
		try {
			Iterable<Project> projects = repository.findAll();
			if (((List<Project>) projects).isEmpty()) {
				return new ResponseEntity<>("Projekteja ei löytynyt", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(projects, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Projektin poisto
	@DeleteMapping("projects/{id}")
	public ResponseEntity<String> removeProject(@PathVariable("id") Long id) {
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