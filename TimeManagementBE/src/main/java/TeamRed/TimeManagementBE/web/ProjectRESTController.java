 package TeamRed.TimeManagementBE.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.ProjectRoleKey;
import TeamRed.TimeManagementBE.domain.Role;
import TeamRed.TimeManagementBE.domain.UserProjectRole;
import TeamRed.TimeManagementBE.domain.UserProjectRoleRepository;
import TeamRed.TimeManagementBE.service.AppUserDetailsService;
import jakarta.validation.Valid;
import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.Entry;
import TeamRed.TimeManagementBE.domain.EntryRepository;
import TeamRed.TimeManagementBE.domain.Project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin
@RestController
@RequestMapping("/projects")
public class ProjectRESTController {

    @Autowired
	private ProjectRepository projectRepository;
    
    @Autowired
    private EntryRepository entryRepository;
    
    @Autowired
    private UserProjectRoleRepository roleRepository;
    
    @Autowired
    private AppUserDetailsService userDetailsService;

    //Kaikkien tietyn käyttäjän projektien haku
	@GetMapping
	@JsonView(Project.ProjectOverview.class)
	public ResponseEntity<?> getProjects() {
		try {
			AppUser user = userDetailsService.getAuthUser();
			Set<UserProjectRole> projects = user.getRoles();
			if (((Set<UserProjectRole>) projects).isEmpty()) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
			}
			return new ResponseEntity<>(projects, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Palauttaa projektin haetulla id:llä, jos kyseessä käyttäjän oma projekti
	@GetMapping("/{projectId}")
	@JsonView(Project.DetailedProjectView.class)
	public ResponseEntity<?> getProjectById(@PathVariable("projectId") Long projectId) {
		try {
			Optional<Project> project = projectRepository.findById(projectId);
			Role role = userDetailsService.getUserRole(projectId);
			if (!project.isEmpty() && role != null) {
				if (role.equals(Role.USER)) {
					List<Entry> entries = entryRepository.findByProjectAndAppUser(project.get(), userDetailsService.getAuthUser());
					project.get().setEntries(entries);
				}
				return new ResponseEntity<>(project, HttpStatus.OK);
			}
			return new ResponseEntity<>("No results found", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
			
	//Uuden projektin lisääminen
	@PostMapping
	public ResponseEntity<?> addProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		try {
			Project newProject = new Project(project.getTitle());
			projectRepository.save(newProject);
			AppUser user = userDetailsService.getAuthUser();
			UserProjectRole role = new UserProjectRole();
			role.setRole(Role.OWNER);
			role.setAppUser(user);
			role.setProject(newProject);
			roleRepository.save(role);		
			return new ResponseEntity<>("Project successfully added", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Projektin muokkaus
	@PutMapping("/{projectId}")
	public ResponseEntity<?> editProject(@Valid @RequestBody Project updatedProject, @PathVariable("projectId") Long projectId, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		try {
			Optional<Project> toBeEdited = projectRepository.findById(projectId);
			if (!toBeEdited.isEmpty() && userDetailsService.getUserRole(projectId).equals(Role.OWNER)) {
				Project project = toBeEdited.get();
				project.setTitle(updatedProject.getTitle());
				projectRepository.save(project);
				Set<UserProjectRole> roles = updatedProject.getRoles();
				for (UserProjectRole role : roles) {
					AppUser user = role.getAppUser();
					ProjectRoleKey key = new ProjectRoleKey(projectId, user.getId());
					UserProjectRole userProjectRole = roleRepository.findById(key);
					if (userProjectRole == null) {
						UserProjectRole newProjectRole = new UserProjectRole();
						newProjectRole.setRole(role.getRole());
						newProjectRole.setAppUser(user);
						newProjectRole.setProject(project);
						roleRepository.save(newProjectRole);
					} else if (role.getRole() == null) {
						roleRepository.delete(userProjectRole);
					} else {
						userProjectRole.setRole(role.getRole());
						roleRepository.save(userProjectRole);
					}
				}
				return new ResponseEntity<>("Project successfully updated", HttpStatus.OK);
			}
			return new ResponseEntity<>("Updating failed", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Projektin poisto
	@DeleteMapping("/{projectId}")
	public ResponseEntity<String> removeProject(@PathVariable("projectId") Long projectId) {
		try {
			if (userDetailsService.getUserRole(projectId).equals(Role.OWNER)) {
				projectRepository.deleteById(projectId);
				return new ResponseEntity<>("Project successfully deleted", HttpStatus.OK);
			}
			return new ResponseEntity<>("Deleting failed", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}