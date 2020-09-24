package ibtwlb.ibtwlb;

import java.beans.BeanProperty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ibtwlb.ibtwlb.repositories.*;
import ibtwlb.ibtwlb.models.*;
import ibtwlb.ibtwlb.UserService.*;

@SpringBootApplication
public class LibraryApplication {

	private static final Logger log = LoggerFactory.getLogger(LibraryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository, ArtifactsRepository artifactsRepository,
			LoanRepository loanRepository, UserService userService) {
		return (args) -> {		

			userService.save(new User("Mr. Librarian", "librarian", "dylan@gmail.com", "password", "password", "This is an admin account.", "Kildare", "ADMIN"));
			userService.save(new User("Mr. Administrator", "admin", "admin@gmail.com", "password", "password", "This is another admin account.", "Kildare", "ADMIN"));
			userService.save(new User("Brian Leahy", "user", "user@gmail.com", "password", "password", "This is the default user", "Kildare", "USER"));
			userService.save(new User("Hugh McKeeney", "user1", "user1@gmail.com", "password", "password", "This is the default user", "Kildare", "USER"));
			userService.save(new User("Sean Stewart", "user2", "user2@gmail.com", "password", "password", "This is the default user", "Kildare", "USER"));
			userService.save(new User("Liam Phelan", "user3", "user3@gmail.com", "password", "password", "This is the default user", "Kildare", "USER"));
			userService.save(new User("Braddy Yeoh", "user4", "user4@gmail.com", "password", "password", "This is the default user", "Kildare", "USER"));
			
			artifactsRepository.save(new Artifact("To Kill a Mockingbird", "Harper Lee", "Racism bad, children good", "Book" ));
			artifactsRepository.save(new Artifact("Harry Potter", "JK Rowling", "Baldness bad, lightning scar good", "Book" ));
			artifactsRepository.save(new Artifact("1984", "George Orwell", "Soviets bad, freedom good", "Book" ));
			artifactsRepository.save(new Artifact("Top Gear", "Jeremy Clarkson", "Walking bad, cars good", "Magazine" ));
			artifactsRepository.save(new Artifact("Animal Farm", "George Orwell", "Communism bad, freedom good", "Book" ));
			artifactsRepository.save(new Artifact("50 Shades Of Grey", "E.L James", "Storyline bad, hormones good", "Book" ));
			artifactsRepository.save(new Artifact("The 4 Hour Work Week", "Tim Ferris", "9-5 bad, freedom good", "Book" ));
			artifactsRepository.save(new Artifact("Losing My Virginity", "Richard Branson", "Dyslexia bad, billionaire good", "Book" ));
			artifactsRepository.save(new Artifact("Shoe Dog", "Phil Knight", "Adidas bad, Nike good", "Book" ));
			
			DateFormat df = new SimpleDateFormat("dd/MM/yy");
			Date startDate = new Date();
			Date endDate = startDate;

			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, 7);
			endDate = c.getTime();

			loanRepository.save(new Loan(2L, 1, df.format(startDate), df.format(endDate), artifactsRepository));
			loanRepository.save(new Loan(3L, 3, df.format(startDate), df.format(endDate), artifactsRepository));
			
			
			c.add(Calendar.DATE, -20);
			startDate = c.getTime();
			c.add(Calendar.DATE, +10);
			endDate = c.getTime();			
			loanRepository.save(new Loan(2L, 2, df.format(startDate), df.format(endDate), artifactsRepository));
			loanRepository.save(new Loan(3L, 4, df.format(startDate), df.format(endDate), artifactsRepository));
			Artifact a = artifactsRepository.findById(2);
			a.setOnLoan(false);
			a = artifactsRepository.findById(4);
			a.setOnLoan(false);
			artifactsRepository.save(a);

			log.info("===========================");
			for (User user : userRepository.findAll()) {
				log.info(user.getUsername());
				log.info(user.getPassword());
				
			}

			for (Loan loan : loanRepository.findAll()) {
				log.info("User ID of loans in repo");
				log.info(loan.getUserId().toString());
			}
			log.info("");
		};
	}
}
