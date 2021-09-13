package com.bitoasis.assignment.test;

import com.bitoasis.assignment.exception.UserException;
import com.bitoasis.assignment.manager.UserManager;
import com.bitoasis.assignment.manager.impl.UserManagerImpl;
import com.bitoasis.assignment.repository.UserRepository;
import com.bitoasis.assignment.repository.UserRepositoryTest;
import com.bitoasis.assignment.request.UserRegistrationRequest;
import com.bitoasis.assignment.response.UserRegistrationResponse;
import com.bitoasis.assignment.service.CheckEmailExistenceService;
import com.bitoasis.assignment.service.UserRegistrationService;
import com.bitoasis.assignment.service.impl.CheckEmailExistenceServiceImpl;
import com.bitoasis.assignment.service.impl.UserRegistrationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserManagerTest {

    private UserRepository userRepository;

    private UserRegistrationService userRegistrationService;
    private CheckEmailExistenceService checkEmailExistenceService;

    private UserManager userManager;

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryTest();

        userRegistrationService = new UserRegistrationServiceImpl(userRepository);
        checkEmailExistenceService = new CheckEmailExistenceServiceImpl(userRepository);

        userManager = new UserManagerImpl(userRegistrationService, checkEmailExistenceService);
    }

    @Test
    public void whenRegisterUser_AndUserNotExistInSystem_ThenShouldReturnObject() {
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .name("BitOasis")
                .email("info@bitoasis.net")
                .password("123456879")
                .build();
        UserRegistrationResponse response = userManager.register(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(request.getEmail(), response.getEmail());
        Assert.assertEquals(request.getName(), response.getName());
    }

    @Test(expected = UserException.class)
    public void whenRegisterUser_AndUserAlreadyExistInSystem_ThenShouldReturnObjectException() {
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .name("BitOasis")
                .email("info@bitoasis.net")
                .password("123456879")
                .build();
        UserRegistrationResponse response = userManager.register(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(request.getEmail(), response.getEmail());
        Assert.assertEquals(request.getName(), response.getName());

        userManager.register(request);
    }
}
