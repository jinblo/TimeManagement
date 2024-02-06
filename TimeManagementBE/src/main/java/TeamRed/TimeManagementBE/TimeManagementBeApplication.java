package TeamRed.TimeManagementBE;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import TeamRed.TimeManagementBE.domain.Project;
import TeamRed.TimeManagementBE.domain.ProjectRepository;

@SpringBootApplication
public class TimeManagementBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeManagementBeApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demoData(ProjectRepository projectRepo) {
		return (args) -> {
			projectRepo.save(new Project("Testproject 1"));
			projectRepo.save(new Project("Testproject 2"));
			projectRepo.save(new Project("Testproject 3"));
		};
	}

}
