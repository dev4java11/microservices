package pe.com.dev4java11.ms1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Ms1Application {

	public static void main(String[] args) {
		SpringApplication.run(Ms1Application.class, args);
	}

	@RestController
	@RequestMapping("/api")
	public class ApiController {

		@GetMapping("/hello/{name}")
		public String get(@PathVariable String name) {
			log.info("Hello  {}.", name);
			return "Hello " + name + ".";
		}
	}
}
