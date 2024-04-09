package TeamRed.TimeManagementBE.web;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
=======
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
>>>>>>> d47cc6507a0b58fb967ce4ed300085f59faca064
import org.springframework.web.bind.annotation.*;
import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;
<<<<<<< HEAD
import TeamRed.TimeManagementBE.domain.Project;
=======
import jakarta.validation.Valid;
>>>>>>> d47cc6507a0b58fb967ce4ed300085f59faca064

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
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Päivitä käyttäjä ID:n perusteella
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody AppUser updatedUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
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

<<<<<<< HEAD
    // Päivitä käyttäjä ID:n perusteella
    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser updatedUser) {
        if (appUserRepository.existsById(id)) {
            updatedUser.setId(id);
            AppUser savedUser = appUserRepository.save(updatedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Poista käyttäjä ID:n perusteella
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (appUserRepository.existsById(id)) {
            appUserRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Päivitä projektin muokkausreitti rajoittamaan pääsyä käyttäjärooleihin
    // perustuen
    @PutMapping("/{id}/projects/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id,
            @PathVariable Long projectId,
            @RequestBody Project updatedProject,
            @AuthenticationPrincipal UserDetails userDetails) {
        AppUser user = appUserRepository.findByEmail(userDetails.getUsername());
        if (user == null || user.getId() != id) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Tarkista, onko käyttäjällä oikea rooli projektin muokkaamiseen
        if (!userHasEditPermission(user)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Logiikka projektin päivittämiseen...
        // Esimerkkilogiikka
        updatedProject.setId(projectId);
        Project savedProject = updatedProject; // Tallenna päivitetty projekti tähän
        return new ResponseEntity<>(savedProject, HttpStatus.OK);
    }

    // Päivitä projektin poistoreitti rajoittamaan pääsyä käyttäjärooleihin
    // perustuen
    @DeleteMapping("/{id}/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id,
            @PathVariable Long projectId,
            @AuthenticationPrincipal UserDetails userDetails) {
        AppUser user = appUserRepository.findByEmail(userDetails.getUsername());
        if (user == null || user.getId() != id) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Tarkista, onko käyttäjällä oikea rooli projektin poistamiseen
        if (!userHasEditPermission(user)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Logiikka projektin poistamiseen...
        // Esimerkkilogiikka
        // Poista projekti projectId:n perusteella
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Metodi tarkistamaan, onko käyttäjällä oikeus muokata/poistaa projektia
    private boolean userHasEditPermission(AppUser user) {
        // Toteuta logiikka käyttäjän roolin tarkistamiseen
        // Esimerkiksi, tarkista onko käyttäjällä rooli "USER" tai "VIEWER"
        return !user.getRole().equals("USER") && !user.getRole().equals("VIEWER");
    }
=======
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
>>>>>>> d47cc6507a0b58fb967ce4ed300085f59faca064
}
