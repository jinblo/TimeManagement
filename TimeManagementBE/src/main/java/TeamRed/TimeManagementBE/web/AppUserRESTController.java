package TeamRed.TimeManagementBE.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;
import TeamRed.TimeManagementBE.domain.Project;

@RestController
@RequestMapping("/api/users")
public class AppUserRESTController {

    private final AppUserRepository appUserRepository;

    public AppUserRESTController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // Hae käyttäjä ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        AppUser user = appUserRepository.findById(id).orElse(null);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Hae käyttäjä sähköpostiosoitteen perusteella
    @GetMapping
    public ResponseEntity<AppUser> getUserByEmail(@RequestParam String email) {
        AppUser user = appUserRepository.findByEmail(email);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Luo uusi käyttäjä
    @PostMapping
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser newUser) {
        AppUser savedUser = appUserRepository.save(newUser);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

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
}
