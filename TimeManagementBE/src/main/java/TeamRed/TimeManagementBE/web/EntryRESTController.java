import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

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
}