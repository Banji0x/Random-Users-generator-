package com.generator.randomusersgenerator.services;

import com.github.javafaker.Faker;
import com.paging.paging.model.Address;
import com.paging.paging.model.User;
import com.paging.paging.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.IntStream;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner commandLineRunner(Faker detailsFaker, UserRepository userRepository) {
        return args -> {
            List<User> users = IntStream
                    .rangeClosed(1, 100)
                    .mapToObj(i ->
                            new User(
                                    detailsFaker.name().firstName(),
                                    detailsFaker.name().lastName(),
                                    detailsFaker.phoneNumber().cellPhone(),
                                    detailsFaker.internet().emailAddress(),
                                    new Address(
                                            detailsFaker.address().streetAddress(),
                                            detailsFaker.address().zipCode(),
                                            detailsFaker.address().city(),
                                            detailsFaker.address().state(),
                                            detailsFaker.address().country()
                                    )
                            )
                    )
                    .toList();
            userRepository.saveAll(users);
        };
    }

    @Bean
    Faker detailsFaker() {
        return new Faker();
    }
}
