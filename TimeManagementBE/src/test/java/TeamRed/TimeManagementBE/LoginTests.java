package TeamRed.TimeManagementBE;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import TeamRed.TimeManagementBE.domain.AccountCredentialsDTO;
import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;
import TeamRed.TimeManagementBE.service.AppUserDetailsService;
import TeamRed.TimeManagementBE.service.JwtService;
import TeamRed.TimeManagementBE.web.LoginRESTController;

@SpringBootTest
public class LoginTests {

  @Autowired
  LoginRESTController loginRESTController;

  private MockMvc mockMvc;

  @Mock
  AppUserRepository userRepository;

  @Mock
  JwtService jwtService;

  @Mock
  AuthenticationManager authManager;

  @Mock
  AppUserDetailsService userDetailsService;

  @Mock
  Authentication authentication;

  @Test
  void contextLoads() throws Exception {
    assertNotNull(loginRESTController);
  }

  @BeforeEach
  public void setUp() throws Exception {
    LoginRESTController controller = new LoginRESTController(jwtService, authManager, userRepository);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void getToken_ValidData_ShouldReturnOk() throws Exception {
    AppUser user = new AppUser("Ensimmäinen", "Käyttäjä", "AppUser1",
        "$2a$12$JoEvKPN77YLvGw/vqLpKeO4A.CW/1LbSweTpmwfUNfQCWC62DL/4q");
    AccountCredentialsDTO creds = new AccountCredentialsDTO("AppUser1", "AppUser1");
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("AppUser1", "AppUser1");
    when(authManager.authenticate(token)).thenReturn(token);
    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(creds)))
        .andExpect(status().isOk());
  }

  public static String toJson(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
