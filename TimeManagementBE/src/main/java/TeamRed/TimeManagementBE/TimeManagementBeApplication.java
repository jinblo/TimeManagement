package TeamRed.TimeManagementBE;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import TeamRed.TimeManagementBE.domain.Project;
import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.Role;
import TeamRed.TimeManagementBE.domain.UserProjectRole;
import TeamRed.TimeManagementBE.domain.UserProjectRoleRepository;
import TeamRed.TimeManagementBE.domain.AppUser;
import TeamRed.TimeManagementBE.domain.AppUserRepository;
import TeamRed.TimeManagementBE.domain.Entry;
import TeamRed.TimeManagementBE.domain.EntryRepository;

@SpringBootApplication
public class TimeManagementBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeManagementBeApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(ProjectRepository projectRepo, EntryRepository entryRepo,
			AppUserRepository appUserRepo, UserProjectRoleRepository roleRepo) {
		return (args) -> {
			//Lisätään kaksi käyttäjää:
			//password: AppUser1
			AppUser testUser = new AppUser("first_name", "last_name", "email@email.com", "$2a$12$JoEvKPN77YLvGw/vqLpKeO4A.CW/1LbSweTpmwfUNfQCWC62DL/4q");
			appUserRepo.save(testUser);
			//password: AppUser2
			AppUser testUser2 = new AppUser("first_name", "last_name", "newuser@email.com", "$2a$12$faaHwhorn90N15gUoeXLxeqeP7Iv3Xn1Z9BnoPnTajKy.KEA2esm.");
			appUserRepo.save(testUser2);
			//Lisätään kolme projektia: toiselle käyttäjälle Testproject 1 ja Testproject 3, toiselle käyttäjälle Testproject 2
			Project testiprojekti = new Project("Testproject 1");
			projectRepo.save(testiprojekti);
			
			UserProjectRole role = new UserProjectRole();
			role.setRole(Role.OWNER);
			role.setAppUser(appUserRepo.findByEmail("email@email.com"));
			role.setProject(testiprojekti);
			roleRepo.save(role);

			testiprojekti.getRoles().add(role);
			testUser.getRoles().add(role);
			
			
			
			
			//projectRepo.save(new Project("Testproject 2", testUser2));
			//projectRepo.save(new Project("Testproject 3", testUser));
			//Lisätään Testproject 1:een kaksi työaikakirjausta
			entryRepo.save(new Entry("Test entry", LocalDate.parse("2022-02-02"),
					LocalTime.parse("10:05"), LocalTime.parse("15:15"), testiprojekti));
			entryRepo.save(new Entry("Test entry 2", LocalDate.parse("2022-02-01"),
					LocalTime.parse("08:00"), LocalTime.parse("13:35"), testiprojekti));
		};
	}

}
