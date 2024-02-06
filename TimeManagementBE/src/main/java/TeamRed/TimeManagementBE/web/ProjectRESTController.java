package TeamRed.TimeManagementBE.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.Project;

import org.springframework.web.bind.annotation.GetMapping;


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
}