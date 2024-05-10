package TeamRed.TimeManagementBE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;

@DataJpaTest
public class AppUserRepositoryTests {

  @Mock
  private AppUserRepository appUserRepository;

  private AppUser appUser;

  @BeforeEach
  public void setUp() {
    appUser = new AppUser();
    appUser.setUsername("testuser");
  }

  @Test
  public void testFindByUsername() {
    when(appUserRepository.findByUsername("testuser")).thenReturn(appUser);

    AppUser result = appUserRepository.findByUsername("testuser");

    assertNotNull(result);
    assertEquals("testuser", result.getUsername());
  }

  @Test
  public void testExistsByUsername() {
    when(appUserRepository.existsByUsername("testuser")).thenReturn(true);

    boolean exists = appUserRepository.existsByUsername("testuser");

    assertTrue(exists);
  }
}
