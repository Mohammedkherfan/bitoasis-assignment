package com.bitoasis.assignment.configuration;

import com.bitoasis.assignment.manager.UserManager;
import com.bitoasis.assignment.manager.impl.UserManagerImpl;
import com.bitoasis.assignment.repository.UserRepository;
import com.bitoasis.assignment.repository.impl.UserRepositoryImpl;
import com.bitoasis.assignment.service.CheckEmailExistenceService;
import com.bitoasis.assignment.service.FindUserByEmailService;
import com.bitoasis.assignment.service.UserRegistrationService;
import com.bitoasis.assignment.service.impl.CheckEmailExistenceServiceImpl;
import com.bitoasis.assignment.service.impl.FindUserByEmailServiceImpl;
import com.bitoasis.assignment.service.impl.UserRegistrationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public UserRegistrationService userRegistrationService(UserRepository userRepository) {
        return new UserRegistrationServiceImpl(userRepository);
    }

    @Bean
    public CheckEmailExistenceService checkEmailExistenceService(UserRepository userRepository) {
        return new CheckEmailExistenceServiceImpl(userRepository);
    }

    @Bean
    public FindUserByEmailService findUserByEmailService(UserRepository userRepository) {
        return new FindUserByEmailServiceImpl(userRepository);
    }

    @Bean
    public UserManager userManager(UserRegistrationService userRegistrationService,
                                   CheckEmailExistenceService checkEmailExistenceService) {
        return new UserManagerImpl(userRegistrationService, checkEmailExistenceService);
    }
}
