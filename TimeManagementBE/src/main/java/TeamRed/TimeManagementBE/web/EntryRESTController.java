package TeamRed.TimeManagementBE.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import TeamRed.TimeManagementBE.domain.EntryRepository;
import TeamRed.TimeManagementBE.domain.Entry;
import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.service.AppUserDetailsService;
import jakarta.validation.Valid;
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
public class EntryRESTController {

	@Autowired
	private EntryRepository entryRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
    @Autowired
    private AppUserDetailsService userDetailsService;

	// Kaikkien käyttäjän omien työaikakirjausten haku
	@GetMapping("entries")
	public ResponseEntity<?> getEntries() {
		try {
			Iterable<Entry> entries = entryRepository.findByAppUser(userDetailsService.getAuthUser());
			if (((List<Entry>) entries).isEmpty()) {
				return new ResponseEntity<>("Työaikakirjauksia ei löytynyt", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(entries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Uuden työaikakirjauksen lisääminen
	@PostMapping("projects/{projectId}/entries")
	public ResponseEntity<?> addEntry(@Valid @RequestBody Entry entry, @PathVariable("projectId") Long projectId, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		try {
			Optional<Project> project = projectRepository.findById(projectId);
			if (!project.isEmpty() && userDetailsService.getUserRole(projectId) != null) { //pelkkä roolin tsekkauskin riittäisi
				entry.setProject(project.get());
				entry.setAppUser(userDetailsService.getAuthUser());
				Entry newEntry = entryRepository.save(entry);
				return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
			}
			return new ResponseEntity<>("Jokin meni pieleen", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Työaikakirjauksen muokkaus
	@PutMapping("projects/{projectId}/entries/{entryId}")
	public ResponseEntity<?> editEntry(@Valid @RequestBody Entry updatedEntry, @PathVariable("entryId") Long entryId, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		try {
			Optional<Entry> toBeEdited = entryRepository.findById(entryId);
			if (!toBeEdited.isEmpty() && toBeEdited.get().getAppUser().getId() == userDetailsService.getAuthIdentity()) {
				Entry entry = toBeEdited.get();
				entry.setComment(updatedEntry.getComment());
				entry.setEntry_date(updatedEntry.getEntry_date());
				entry.setStart_time(updatedEntry.getStart_time());
				entry.setEnd_time(updatedEntry.getEnd_time());
				//entry.setAppUser(userDetailsService.getAuthUser());
				entryRepository.save(entry);
				return new ResponseEntity<>(entry, HttpStatus.OK);
			}
			return new ResponseEntity<>("Jokin meni pieleen", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Työaikakirjauksen poisto
	@DeleteMapping("entries/{entryId}")
	public ResponseEntity<?> removeEntry(@PathVariable("entryId") Long entryId) {
		try {
			Optional<Entry> removableEntry = entryRepository.findById(entryId);
			if (!removableEntry.isEmpty() && removableEntry.get().getAppUser().getId() == userDetailsService.getAuthIdentity()) {
				entryRepository.delete(removableEntry.get());
				return new ResponseEntity<>("Työaikakirjaus poistettu onnistuneesti", HttpStatus.OK);
			}
			return new ResponseEntity<>("Jokin meni pieleen", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}