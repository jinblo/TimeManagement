package TeamRed.TimeManagementBE.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import TeamRed.TimeManagementBE.domain.EntryRepository;
import TeamRed.TimeManagementBE.domain.Project;
import TeamRed.TimeManagementBE.domain.Entry;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin
@RestController
public class EntryRESTController {

    @Autowired
	private EntryRepository repository;

    //Kaikkien työaikakirjausten haku
	@GetMapping("entries")
	public ResponseEntity<?> getEntries() {
		try {
			Iterable<Entry> entries = repository.findAll();
			if (((List<Entry>) entries).isEmpty()) {
				return new ResponseEntity<>("Työaikakirjauksia ei löytynyt", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(entries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Työaikakirjauksen poisto
	@DeleteMapping("entries/{id}")
	public ResponseEntity<String> removeEntry(@PathVariable("id") Long id) {
		try {
			Optional<Entry> removableEntry = repository.findById(id);
			if (removableEntry.isEmpty()) {
				return new ResponseEntity<>("Työaikakirjausta ei löytynyt", HttpStatus.NOT_FOUND);
			}
			repository.delete(removableEntry.get());
			return new ResponseEntity<>("Työaikakirjaus poistettu onnistuneesti", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}