package TeamRed.TimeManagementBE;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import TeamRed.TimeManagementBE.domain.Project;
import TeamRed.TimeManagementBE.domain.ProjectRepository;
import TeamRed.TimeManagementBE.domain.Entry;
import TeamRed.TimeManagementBE.domain.EntryRepository;

@SpringBootApplication
public class TimeManagementBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeManagementBeApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demoData(ProjectRepository projectRepo, EntryRepository entryRepo) {
		return (args) -> {
			Project testiprojekti = new Project("Testproject 1");
			projectRepo.save(testiprojekti);
			projectRepo.save(new Project("Testproject 2"));
			projectRepo.save(new Project("Testproject 3"));
			entryRepo.save(new Entry("Test title", "Test entry", LocalDate.parse("2022-02-02"), LocalTime.parse("10:05"), LocalTime.parse("15:15"), testiprojekti));
			entryRepo.save(new Entry("Test title 2", "Test entry 2", LocalDate.parse("2022-02-01"), LocalTime.parse("08:00"), LocalTime.parse("13:35"), testiprojekti));
		};
	}

}
