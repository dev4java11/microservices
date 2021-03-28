package pe.com.dev4java11.ms2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import pe.com.dev4java11.ms2.dto.Person;

@SpringBootApplication
public class Ms2Application {

	public static void main(String[] args) {
		SpringApplication.run(Ms2Application.class, args);
	}

	@RestController
	@RequestMapping("/api")
	public class Api {
		
		private List<Person> persons;
		
		private Timer tGetPersons;
		
		private Timer tGetPersonById;
		
		public Api(MeterRegistry registry) {
			this.persons = new ArrayList<>();
			this.initializePersons();
			registry.gaugeCollectionSize("population", Tags.of("size", "persons"), persons);
			this.tGetPersons = registry.timer("get_persons", "http_method", "GET");
			this.tGetPersonById = registry.timer("get_person_by_id", "http_method", "GET");
		}
		
		private void initializePersons() {
			Person p1 = new Person("1", "Bob");
			Person p2 = new Person("2", "Mary");
			Person p3 = new Person("3", "Jhon");
			Person p4 = new Person("4", "Gaby");
			Person p5 = new Person("5", "Henry");
			this.persons.add(p1);
			this.persons.add(p2);
			this.persons.add(p3);
			this.persons.add(p4);
			this.persons.add(p5);
		}
		
		@GetMapping("/persons")
		public ResponseEntity<?> get() {
			return tGetPersons.record(() ->
				ResponseEntity.of(Optional.ofNullable(persons)));
		}
		
		@GetMapping("/persons/{id}")
		public ResponseEntity<?> get(@PathVariable String id) {
			return tGetPersonById.record(() ->
				ResponseEntity.of(persons
					.stream()
					.filter(f -> f.getId().equals(id))
					.findFirst()));	
		}
	}
}
