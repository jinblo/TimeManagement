package TeamRed.TimeManagementBE;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.Entry;
import TeamRed.TimeManagementBE.domain.Project;
import TeamRed.TimeManagementBE.domain.ProjectRoleKey;
import TeamRed.TimeManagementBE.domain.Role;
import TeamRed.TimeManagementBE.domain.UserProjectRole;

public class ProjectTests {

  @Test
  void constructorTest() throws Exception {
    String title = "Project";
    Project project = new Project(title);
    assertThat(project.getTitle()).isEqualTo(title);
  }

  @Test
  void emptyConstructorTest() throws Exception {
    String title = "Project";
    Project project = new Project();
    project.setTitle(title);
    assertThat(project.getTitle()).isEqualTo(title);
  }

  @Test
  void setRolesTest() throws Exception {
    Project project = new Project("Project");
    project.setId(1L);
    Set<UserProjectRole> roles = new HashSet<>();
    AppUser user = new AppUser();
    user.setId(1L);
    ProjectRoleKey key = new ProjectRoleKey(1L, 1L);
    UserProjectRole role = new UserProjectRole(key, project, user, Role.OWNER);
    roles.add(role);
    project.setRoles(roles);
    assertThat(project.getRoles()).isEqualTo(roles);
  }

  @Test
  void setEntriesTest() throws Exception {
    Project project = new Project("Project");
    project.setId(1L);
    AppUser user = new AppUser();
    user.setId(1L);
    Entry entry = new Entry("Comment", LocalDate.parse("2022-05-05"), LocalTime.parse("10:05"),
        LocalTime.parse("15:15"), project, user);
    List<Entry> entries = Arrays.asList(entry);
    project.setEntries(entries);
    assertThat(project.getEntries()).isEqualTo(entries);
  }

}