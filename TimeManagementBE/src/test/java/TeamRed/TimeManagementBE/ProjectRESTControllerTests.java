package TeamRed.TimeManagementBE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.EntryRepository;
import TeamRed.TimeManagementBE.domain.Project;
import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.Role;
import TeamRed.TimeManagementBE.domain.UserProjectRoleRepository;
import TeamRed.TimeManagementBE.service.AppUserDetailsService;
import TeamRed.TimeManagementBE.web.ProjectRESTController;

@SpringBootTest
public class ProjectRESTControllerTests {

  @Autowired
  ProjectRESTController projectRESTController;

  private MockMvc mockMvc;

  @MockBean
  private ProjectRepository projectRepository;

  @MockBean
  private EntryRepository entryRepository;

  @MockBean
  private UserProjectRoleRepository roleRepository;

  @MockBean
  private AppUserDetailsService userDetailsService;

  @Test
  void contextLoads() throws Exception {
    assertNotNull(projectRESTController);
  }

  @BeforeEach
  public void setUp() throws Exception {
    ProjectRESTController controller = new ProjectRESTController(projectRepository, entryRepository, roleRepository,
        userDetailsService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void getProjects_ValidData_ShouldReturnOk() throws Exception {
    AppUser user = new AppUser();
    user.setId(1L);
    when(userDetailsService.getAuthUser()).thenReturn(user);
    mockMvc.perform(get("/projects"))
        .andExpect(status().isOk());
  }

  @Test
  public void getProjects_InvalidData_ShouldReturnInternalServerError() throws Exception {
    when(userDetailsService.getAuthUser()).thenReturn(null);
    mockMvc.perform(get("/projects"))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void getProjectById_ValidData_ShouldReturnOk() throws Exception {
    Project project = new Project("New project");
    project.setId(1L);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    when(userDetailsService.getUserRole(1L)).thenReturn(Role.OWNER);
    mockMvc.perform(get("/projects/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(toJson(project)));
  }

  @Test
  public void getProjectById_InvalidData_ShouldReturnNotFound() throws Exception {
    Project project = new Project("New project");
    project.setId(1L);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    when(userDetailsService.getUserRole(1L)).thenReturn(null);
    mockMvc.perform(get("/projects/1"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No results found"));
  }

  @Test
  public void getProjectById_InvalidId_ShouldReturnNotFound() throws Exception {
    when(projectRepository.findById(1L)).thenReturn(Optional.empty());
    mockMvc.perform(get("/projects/1"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No results found"));
  }

  @Test
  public void addProject_ValidData_ShouldReturnOk() throws Exception {
    Project project = new Project("New project");
    AppUser user = new AppUser();
    user.setId(1L);
    when(userDetailsService.getAuthUser()).thenReturn(user);
    mockMvc.perform(post("/projects")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(project)))
        .andExpect(status().isCreated())
        .andExpect(content().string("Project successfully added"));
  }

  @Test
  public void addProject_InvalidData_ShouldReturnUnprocessableEntity() throws Exception {
    Project invalidProject = new Project();
    mockMvc.perform(post("/projects")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(invalidProject)))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(content().string("Invalid data"));
  }

  @Test
  public void editProjectTitle_ValidData_ShouldReturnOk() throws Exception {
    Project existingProject = new Project("Old title");
    existingProject.setId(1L);
    Project updatedProject = new Project("New title");
    updatedProject.setId(1L);
    when(userDetailsService.getUserRole(1L)).thenReturn(Role.OWNER);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
    mockMvc.perform(put("/projects/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(updatedProject)))
        .andExpect(status().isOk())
        .andExpect(content().string("Project successfully updated"));
    assertEquals("New title", existingProject.getTitle());
  }

  @Test
  public void editProject_InvalidData_ShouldReturnNotFound() throws Exception {
    Project project = new Project("Title");
    project.setId(1L);
    when(userDetailsService.getUserRole(1L)).thenReturn(Role.USER);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    mockMvc.perform(put("/projects/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(project)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Updating failed"));
  }

  @Test
  public void removeProject_ValidData_ShouldReturnOk() throws Exception {
    when(userDetailsService.getUserRole(1L)).thenReturn(Role.OWNER);
    mockMvc.perform(delete("/projects/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Project successfully deleted"));
  }

  @Test
  public void removeProject_InvalidData_ShouldReturnOk() throws Exception {
    when(userDetailsService.getUserRole(1L)).thenReturn(Role.USER);
    mockMvc.perform(delete("/projects/1"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Deleting failed"));
  }

  @Test
  public void removeProject_NoRole_ShouldReturnInternalServerError() throws Exception {
    when(userDetailsService.getUserRole(1L)).thenReturn(null);
    mockMvc.perform(delete("/projects/1"))
        .andExpect(status().isInternalServerError());
  }

  public static String toJson(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
