package ma.enset.Patient_Mvc;

import ma.enset.Patient_Mvc.entities.Patient;
import ma.enset.Patient_Mvc.repository.PatientRepository;
import ma.enset.Patient_Mvc.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientMvcApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(PatientRepository patientRepository){
		return args -> {
			patientRepository.save(new Patient(null,"user1",new Date(),false,300));
			patientRepository.save(new Patient(null,"user2",new Date(),false,400));
			patientRepository.save(new Patient(null,"user3",new Date(),false,500));
		};
	}
	@Bean  // maitenant spring secrité sait que notre app utilise Bcrypte comme un alogo de cryptage
	PasswordEncoder passwordEncodera(){
		return new BCryptPasswordEncoder();
	}
	//@Bean // Bean signifier que pour chaque démarage il faut exucute ce code
	CommandLineRunner commandLineRunnerJdbcUsers(JdbcUserDetailsManager jdbcUserDetailsManager){
		PasswordEncoder passwordEncoder=passwordEncodera();
		return args -> {
			UserDetails u1=jdbcUserDetailsManager.loadUserByUsername("User1");
			if(u1==null) jdbcUserDetailsManager.createUser(
					User.withUsername("User1").password(passwordEncoder.encode("1234")).roles("USER").build()
			);

			UserDetails u2=jdbcUserDetailsManager.loadUserByUsername("User2");
			if(u2==null) jdbcUserDetailsManager.createUser(
					User.withUsername("User2").password(passwordEncoder.encode("1234")).roles("USER").build()
			);

			UserDetails u3=jdbcUserDetailsManager.loadUserByUsername("admin");
			if(u3==null)
				jdbcUserDetailsManager.createUser(
					User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
			);// au lieu de mot cle "roles" on peut utliser "Authority" mais il faut changer "hasroles" vers "hasAutority"
		};
	}
	//@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
		return args -> {
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			accountService.addNewUser("user1","1234","user1@gmail.com","1234");
			accountService.addNewUser("user2","1234","user1@gmail.com","1234");
			accountService.addNewUser("admin","1234","user1@gmail.com","1234");
			accountService.AddRoleToUser("user1","USER");
			accountService.AddRoleToUser("user2","USER");
			accountService.AddRoleToUser("admin","USER");
			accountService.AddRoleToUser("admin","ADMIN");

		};
	}
}
