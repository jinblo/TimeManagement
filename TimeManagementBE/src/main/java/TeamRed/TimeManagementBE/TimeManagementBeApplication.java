package TeamRed.TimeManagementBE;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import TeamRed.TimeManagementBE.domain.Project;
import TeamRed.TimeManagementBE.domain.ProjectRepository;
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
			AppUserRepository appUserRepo) {
		return (args) -> {
			//password: AppUser1
			AppUser testUser = new AppUser("first_name", "last_name", "email@email.com", "$2a$12$JoEvKPN77YLvGw/vqLpKeO4A.CW/1LbSweTpmwfUNfQCWC62DL/4q");
			appUserRepo.save(testUser);
			//password: AppUser2
			AppUser testUser2 = new AppUser("first_name", "last_name", "newuser@email.com", "$2a$12$faaHwhorn90N15gUoeXLxeqeP7Iv3Xn1Z9BnoPnTajKy.KEA2esm.");
			appUserRepo.save(testUser2);
			Project testiprojekti = new Project("Testproject 1");
			projectRepo.save(testiprojekti);
			projectRepo.save(new Project("Testproject 2"));
			projectRepo.save(new Project("Testproject 3"));
			entryRepo.save(new Entry("Test entry", LocalDate.parse("2022-02-02"),
					LocalTime.parse("10:05"), LocalTime.parse("15:15"), testiprojekti, testUser));
			entryRepo.save(new Entry("Test entry 2", LocalDate.parse("2022-02-01"),
					LocalTime.parse("08:00"), LocalTime.parse("13:35"), testiprojekti, testUser));
		};
	}

}
