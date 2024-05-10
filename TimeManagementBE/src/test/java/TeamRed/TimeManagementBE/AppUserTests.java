package TeamRed.TimeManagementBE;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import TeamRed.TimeManagementBE.domain.AppUser;

@DataJpaTest
public class AppUserTests {

  private AppUser appUser;

  @BeforeEach
  public void setUp() {
    appUser = new AppUser("Matti", "Mallikas", "testuser", "password");
    appUser.setId(1L);
  }

  @Test
  public void testGetId() {
    assertEquals(1L, appUser.getId());
  }

  @Test
  public void testGetFirst_name() {
    assertEquals("Matti", appUser.getFirst_name());
  }

  @Test
  public void testGetLast_name() {
    assertEquals("Mallikas", appUser.getLast_name());
  }

  @Test
  public void testGetUsername() {
    assertEquals("testuser", appUser.getUsername());
  }

  @Test
  public void testGetPassword_hash() {
    assertEquals("password", appUser.getPassword_hash());
  }

}
