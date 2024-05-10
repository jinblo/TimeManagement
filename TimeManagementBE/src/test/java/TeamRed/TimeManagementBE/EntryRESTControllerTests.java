package TeamRed.TimeManagementBE;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.EntryRepository;
import TeamRed.TimeManagementBE.domain.Project;
import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.Role;
import TeamRed.TimeManagementBE.service.AppUserDetailsService;
import TeamRed.TimeManagementBE.web.EntryRESTController;

@SpringBootTest
public class EntryRESTControllerTests {

  @Autowired
  EntryRESTController entryRESTController;

  private MockMvc mockMvc;

  @Mock
  EntryRepository entryRepository;

  @Mock
  ProjectRepository projectRepository;

  @Mock
  AppUserDetailsService userDetailsService;

  @Test
  void contextLoads() throws Exception {
    assertNotNull(entryRESTController);
  }

  @BeforeEach
  public void setUp() throws Exception {
    EntryRESTController controller = new EntryRESTController(entryRepository, projectRepository, userDetailsService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void getEntries_ValidData_ShouldReturnOk() throws Exception {
    AppUser user = new AppUser();
    user.setId(1L);
    when(userDetailsService.getAuthUser()).thenReturn(user);
    mockMvc.perform(get("/entries"))
        .andExpect(status().isOk());
  }

  @Test
  public void getEntries_InvalidData_ShouldReturnInternalServerError() throws Exception {
    when(entryRepository.findByAppUser(null)).thenReturn(null);
    mockMvc.perform(get("/entries"))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void addEntry_ValidData_ShouldReturnCreated() throws Exception {
    AppUser user = new AppUser("etu", "suku", "AppUser1", "AppUser1");
    Project project = new Project("Project");
    project.setId(1L);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    when(userDetailsService.getUserRole(1L)).thenReturn(Role.OWNER);
    when(userDetailsService.getAuthUser()).thenReturn(user);
    mockMvc.perform(post("/projects/1/entries")
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            "{'comment':'Comment','entry_date':'2024-05-05','start_time':'08:00:00','end_time':'16:00:00'}"))
        .andExpect(status().isOk())
        .andExpect(content().string("Entry successfully added"));
  }

}
