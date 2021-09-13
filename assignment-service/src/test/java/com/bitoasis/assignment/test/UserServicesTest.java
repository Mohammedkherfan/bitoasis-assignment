package com.bitoasis.assignment.test;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.UserBo;
import com.bitoasis.assignment.repository.CachingRepository;
import com.bitoasis.assignment.repository.CachingRepositoryTest;
import com.bitoasis.assignment.repository.UserRepository;
import com.bitoasis.assignment.repository.UserRepositoryTest;
import com.bitoasis.assignment.service.CachingCoinsService;
import com.bitoasis.assignment.service.CheckEmailExistenceService;
import com.bitoasis.assignment.service.FindUserByEmailService;
import com.bitoasis.assignment.service.UserRegistrationService;
import com.bitoasis.assignment.service.impl.CachingCoinsServiceImpl;
import com.bitoasis.assignment.service.impl.CheckEmailExistenceServiceImpl;
import com.bitoasis.assignment.service.impl.FindUserByEmailServiceImpl;
import com.bitoasis.assignment.service.impl.UserRegistrationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class UserServicesTest {

    private UserRepository userRepository;
    private CachingRepository cachingRepository;

    private UserRegistrationService userRegistrationService;
    private FindUserByEmailService findUserByEmailService;
    private CheckEmailExistenceService checkEmailExistenceService;
    private CachingCoinsService cachingCoinsService;

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryTest();
        cachingRepository = new CachingRepositoryTest();

        userRegistrationService = new UserRegistrationServiceImpl(userRepository);
        findUserByEmailService = new FindUserByEmailServiceImpl(userRepository);
        checkEmailExistenceService = new CheckEmailExistenceServiceImpl(userRepository);
        cachingCoinsService = new CachingCoinsServiceImpl(cachingRepository);
    }

    @Test
    public void registerUser_ThenShouldReturnObject() {
        UserBo userBo = userRegistrationService.register(UserBo.builder()
                .name("BitOasis")
                .email("info@bitoasis.net")
                .password("123456789")
                .build());

        Assert.assertNotNull(userBo);
    }

    @Test
    public void findUser_AndUserNotExist_ThenShouldReturnEmpty() {
        Optional<UserBo> userBo = findUserByEmailService.findByEmail("test@test.com");
        Assert.assertTrue(!userBo.isPresent());
    }

    @Test
    public void findUser_AndUserExist_ThenShouldReturnObject() {
        UserBo userBo = userRegistrationService.register(UserBo.builder()
                .name("BitOasis")
                .email("info@bitoasis.net")
                .password("123456789")
                .build());
        Optional<UserBo> user = findUserByEmailService.findByEmail(userBo.getEmail());
        Assert.assertTrue(user.isPresent());
    }

    @Test
    public void CheckUserExistences_AndUserNotExist_ThenShouldReturnFalse() {
        Boolean isExist = checkEmailExistenceService.existsByEmail("test@test.com");
        Assert.assertFalse(isExist);
    }

    @Test
    public void CheckUserExistences_AndUserExist_ThenShouldReturnTrue() {
        UserBo userBo = userRegistrationService.register(UserBo.builder()
                .name("BitOasis")
                .email("info@bitoasis.net")
                .password("123456789")
                .build());
        Boolean isExist = checkEmailExistenceService.existsByEmail(userBo.getEmail());
        Assert.assertTrue(isExist);
    }

    @Test
    public void whenCachingCoins_AndCoinsListIsEmpty_ThenShouldReturnSuccess() {
        cachingCoinsService.cachingCoin(new ArrayList<>());
    }

    @Test
    public void whenCachingCoins_AndCoinsListIsNotEmpty_ThenShouldReturnSuccess() {
        cachingCoinsService.cachingCoin(Arrays.asList(CoinBo.builder().name("Test").code("TST").build()));
    }
}
