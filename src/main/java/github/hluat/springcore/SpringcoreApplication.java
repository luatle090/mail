package github.hluat.springcore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import github.hluat.springcore.services.FileResource;
import github.hluat.springcore.services.MailTemplate;


@SpringBootApplication
@ConfigurationPropertiesScan("github.hluat.springcore.config")
public class SpringcoreApplication implements CommandLineRunner{
	@Autowired
	private MailTemplate mailTemplate;

	@Autowired
	private FileResource fileResource;

	public static void main(String[] args) {
		SpringApplication.run(SpringcoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileResource.loadResource(null);
		mailTemplate.processSendMail("static/teacher-day/input.xlsx", "src/main/resources/static/teacher-day/output.xlsx");		
	}
}
