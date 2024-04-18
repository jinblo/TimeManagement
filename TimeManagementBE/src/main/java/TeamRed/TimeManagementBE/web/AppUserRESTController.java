package TeamRed.TimeManagementBE.web;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;
import TeamRed.TimeManagementBE.service.AppUserDetailsService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class AppUserRESTController {

	private final AppUserRepository appUserRepository;

	public AppUserRESTController(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}
	
    @Autowired
    private AppUserDetailsService userDetailsService;

	// Hae käyttäjä käyttäjänimen perusteella
	@GetMapping("/byusername/{username}")
	@JsonView(AppUser.BasicUserView.class)
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		try {
			AppUser user = appUserRepository.findByUsername(username);

			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No results found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Luo uusi käyttäjä
	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody AppUser newUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			newUser.setPassword_hash(encoder.encode(newUser.getPassword_hash()));
			appUserRepository.save(newUser);
			return new ResponseEntity<>("User successfully added", HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Päivitä käyttäjä ID:n perusteella
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody AppUser updatedUser,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			Optional<AppUser> user = appUserRepository.findById(id);
			if (!user.isEmpty() && id == userDetailsService.getAuthIdentity()) {
				AppUser userToBeUpdated = user.get();
				if (!updatedUser.getUsername().equals(user.get().getUsername())) {
					if (appUserRepository.existsByUsername(updatedUser.getUsername())) {
						throw new DataIntegrityViolationException("Username already exists");
					} else {
						userToBeUpdated.setUsername(updatedUser.getUsername());
					}
				}
				userToBeUpdated.setFirst_name(updatedUser.getFirst_name());
				userToBeUpdated.setLast_name(updatedUser.getLast_name());
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				userToBeUpdated.setPassword_hash(encoder.encode(updatedUser.getPassword_hash()));
				appUserRepository.save(userToBeUpdated);
				return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Updating failed", HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Poista käyttäjä ID:n perusteella
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		try {
			Optional<AppUser> user = appUserRepository.findById(id);
			if (!user.isEmpty() && user.get().getId() == userDetailsService.getAuthIdentity()) {
				appUserRepository.delete(user.get());
				return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Deleting failed", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}