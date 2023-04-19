package com.emsi.patientsmvc;

import com.emsi.patientsmvc.entities.Patient;
import com.emsi.patientsmvc.repositories.patientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);


    }
   @Bean
    CommandLineRunner commandLineRunner(patientRepository patientRepository){
       return args ->{
           patientRepository.save (new Patient(null, "Hassan",  new Date (), false,  112));
           patientRepository.save (new Patient(null, "Mohamed",  new Date (), false,  127));
           patientRepository.save (new Patient(null, "Yassmine",  new Date (), false,  102));
           patientRepository.save (new Patient(null, "Hanae",  new Date(), false,  137));


           patientRepository.findAll().forEach(p->{
               System.out.println(p.getNom());
           });


       };
    }
@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
