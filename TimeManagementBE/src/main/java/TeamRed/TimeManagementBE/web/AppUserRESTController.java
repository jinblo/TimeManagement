package TeamRed.TimeManagementBE.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;

@RestController
@RequestMapping("/users")
public class AppUserRESTController {

	private final AppUserRepository appUserRepository;

	public AppUserRESTController(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	// Hae käyttäjä ID:n perusteella
	@GetMapping("/{id}")
	public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
		try {
			AppUser user = appUserRepository.findById(id).orElse(null);

			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Hae käyttäjä käyttäjänimen perusteella
	@GetMapping("/byusername/{username}")
	public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) {
		try {
			AppUser user = appUserRepository.findByUsername(username);

			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Luo uusi käyttäjä
	@PostMapping
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser newUser) {
		try {
			AppUser savedUser = appUserRepository.save(newUser);

			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Päivitä käyttäjä ID:n perusteella
	@PutMapping("/{id}")
	public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser updatedUser) {
		try {
			if (appUserRepository.existsById(id)) {
				updatedUser.setId(id);
				AppUser savedUser = appUserRepository.save(updatedUser);
				return new ResponseEntity<>(savedUser, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Poista käyttäjä ID:n perusteella
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		try {
			if (appUserRepository.existsById(id)) {
				appUserRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
