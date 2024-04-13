package TeamRed.TimeManagementBE.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;
import jakarta.validation.Valid;

@CrossOrigin
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
	public ResponseEntity<?> createUser(@Valid @RequestBody AppUser newUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			newUser.setPassword_hash(encoder.encode(newUser.getPassword_hash()));
			AppUser savedUser = appUserRepository.save(newUser);

			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("Something weird happened", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Päivitä käyttäjä ID:n perusteella
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody AppUser updatedUser,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		// Tarkista, onko käyttäjällä oikeus päivittää projektin tietoja
		if (!isAllowedToUpdateProjectInformation()) {
			// Jos ei ole lupaa päivittää tietoja, palauta virhekoodi 403
			return new ResponseEntity<>("Sinulla ei ole lupaa päivittää projektin tietoja", HttpStatus.FORBIDDEN);
		}

		try {
			// Päivitä käyttäjälogiikka
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// Jos tapahtuu virhe, palauta virhekoodi 500
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Poista käyttäjä ID:n perusteella
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		// Tarkista, onko käyttäjällä oikeus poistaa projektin tietoja
		if (!isAllowedToDeleteProjectInformation()) {
			// Jos ei ole lupaa poistaa tietoja, palauta virhekoodi 403
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		try {
			// Poista käyttäjälogiikka
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// Jos tapahtuu virhe, palauta virhekoodi 500
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Apumetodi tarkistaaksesi, onko käyttäjällä oikeus päivittää projektin tietoja
	private boolean isAllowedToUpdateProjectInformation() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Vaihda "admin" rooliin, jolla on lupa päivittää projektin tietoja
		return authentication != null && authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));
	}

	// Apumetodi tarkistaaksesi, onko käyttäjällä oikeus poistaa projektin tietoja
	private boolean isAllowedToDeleteProjectInformation() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Vaihda "admin" rooliin, jolla on lupa poistaa projektin tietoja
		return authentication != null && authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));
	}
}
