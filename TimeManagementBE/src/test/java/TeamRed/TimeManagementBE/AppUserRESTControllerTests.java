package TeamRed.TimeManagementBE;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;
import TeamRed.TimeManagementBE.service.AppUserDetailsService;
import TeamRed.TimeManagementBE.service.JwtService;
import TeamRed.TimeManagementBE.web.AppUserRESTController;

@SpringBootTest
public class AppUserRESTControllerTests {

  @Autowired
  AppUserRESTController appUserRESTController;

  private MockMvc mockMvc;

  @Mock
  AppUserRepository appUserRepository;

  @Mock
  JwtService jwtService;

  @Mock
  AppUserDetailsService userDetailsService;

  @Test
  void contextLoads() throws Exception {
    assertNotNull(appUserRESTController);
  }

  @BeforeEach
  public void setUp() throws Exception {
    AppUserRESTController controller = new AppUserRESTController(appUserRepository, userDetailsService, jwtService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void createUser_ValidData_ShouldReturnCreated() throws Exception {
    AppUser user = new AppUser("etu", "suku", "AppUser1", "AppUser1");
    Mockito.when(appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(user);
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(toJson(user)))
        .andExpect(status().isCreated())
        .andExpect(content().string("User successfully added"));
  }

  @Test
  void createUser_DuplicateUsername_ShouldReturnConflict() throws Exception {
    AppUser duplicateUser = new AppUser("etu", "suku", "AppUser1", "AppUser1");
    Mockito.when(appUserRepository.save(Mockito.any(AppUser.class)))
        .thenThrow(new DataIntegrityViolationException("Username already exists"));
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(toJson(duplicateUser)))
        .andExpect(status().isConflict())
        .andExpect(content().string("Username already exists"));
  }

  @Test
  void createUser_InvalidData_ShouldReturnUnprocessableEntity() throws Exception {
    AppUser invalidUser = new AppUser();
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(toJson(invalidUser)))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(content().string("Invalid data"));
  }

  @Test
  void getUserByUsername_ExistingUser_ShouldReturnOk() throws Exception {
    AppUser existingUser = new AppUser("etu", "suku", "AppUser1", "AppUser1");
    Mockito.when(appUserRepository.findByUsername("AppUser1")).thenReturn(existingUser);
    mockMvc.perform(MockMvcRequestBuilders.get("/users/byusername/AppUser1")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{'id':0,'username':'AppUser1'}"));
  }

  @Test
  void getUserByUsername_NonExistingUser_ShouldReturnNotFound() throws Exception {
    Mockito.when(appUserRepository.findByUsername("nonexistent")).thenReturn(null);
    mockMvc.perform(MockMvcRequestBuilders.get("/users/byusername/nonexistent")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No results found"));
  }

  @Test
  @WithMockUser(username = "testuser", roles = "USER")
  void updateUser_ValidData_ShouldReturnOk() throws Exception {
    AppUser user = new AppUser("etu", "suku", "AppUser1", "AppUser1");
    user.setId(1L);
    Mockito.when(appUserRepository.findById(1L)).thenReturn(Optional.of(user));
    Mockito.when(appUserRepository.existsByUsername("AppUser1")).thenReturn(false);
    Mockito.when(userDetailsService.getAuthIdentity()).thenReturn(1l);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(toJson(user)))
        .andExpect(status().isOk())
        .andExpect(content().string("User successfully updated"));
  }

  @Test
  void updateUser_InvalidData_ShouldReturnUnprocessableEntity() throws Exception {
    AppUser invalidUser = new AppUser();
    mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(toJson(invalidUser)))
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateUser_Unauthorized_ShouldReturnNotFound() throws Exception {
    AppUser user = new AppUser("etu", "suku", "AppUser1", "AppUser1");
    user.setId(1L);
    Mockito.when(appUserRepository.findById(1L)).thenReturn(Optional.of(user));
    mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(toJson(user)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Updating failed"));
  }

  @Test
  void deleteUser_ValidId_ShouldReturnOk() throws Exception {
    AppUser user = new AppUser("etu", "suku", "AppUser1", "AppUser1");
    user.setId(1L);
    Mockito.when(appUserRepository.findById(1L)).thenReturn(Optional.of(user));
    Mockito.when(userDetailsService.getAuthIdentity()).thenReturn(1l);
    mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(toJson(user)))
        .andExpect(status().isOk())
        .andExpect(content().string("User successfully deleted"));
  }

  @Test
  void deleteUser_InvalidData_ShouldReturnNotFound() throws Exception {
    AppUser user = new AppUser();
    mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(toJson(user)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Deleting failed"));
  }

  public static String toJson(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
