package TeamRed.TimeManagementBE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;

@DataJpaTest
public class AppUserRepositoryTests {

  @Autowired
  private AppUserRepository appUserRepository;

  private AppUser appUser;

  @BeforeEach
  public void setUp() {
    appUser = new AppUser("first", "last", "testuser", "password");
    appUserRepository.save(appUser);
  }

  @Test
  public void testFindByUsername() {
    AppUser result = appUserRepository.findByUsername("testuser");
    assertNotNull(result);
    assertEquals("testuser", result.getUsername());
  }

  @Test
  public void testExistsByUsername() {
    boolean exists = appUserRepository.existsByUsername("testuser");
    assertTrue(exists);
  }
}
