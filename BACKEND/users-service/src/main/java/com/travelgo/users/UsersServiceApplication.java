package com.travelgo.users;

import com.travelgo.users.model.UserEntity;
import com.travelgo.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                UserEntity u = new UserEntity();
                u.setUsername("demo");
                u.setEmail("demo@example.com");
                u.setFirstName("Demo");
                u.setLastName("User");
                u.setImage(null);
                repository.save(u);
            }
        };
    }
}
