package com.bitoasis.assignment.test;

import com.bitoasis.assignment.AssignmentApplication;
import com.bitoasis.assignment.configuration.CoinConfiguration;
import com.bitoasis.assignment.configuration.SecurityConfiguration;
import com.bitoasis.assignment.configuration.UserConfiguration;
import com.bitoasis.assignment.enums.CachingKey;
import com.bitoasis.assignment.jpa.UserJpaRepository;
import com.bitoasis.assignment.request.UserRegistrationRequest;
import com.bitoasis.assignment.util.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AssignmentApplication.class, CoinConfiguration.class, SecurityConfiguration.class, UserConfiguration.class})
@AutoConfigureMockMvc
public class TickerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.host}")
    private String host;

    private UserRegistrationRequest userRegistrationRequest;

    private TestUtil jacksonUtil = new TestUtil();

    @Before
    public void setUp() throws Exception {
        userRegistrationRequest = UserRegistrationRequest.builder()
                .name("BitOasis")
                .email("info@bitoasis.net")
                .password("123456789")
                .build();
    }

    @After
    public void tearDown() throws Exception {
        userJpaRepository.deleteAll();
        redisTemplate.delete("BAT");
    }

    @Test
    public void whenCallFindTicker_AndUserNotAuthenticated_ThenShouldReturnUnauthorized() throws Exception {
        ResultActions action = mvc.perform(get("/v1/ticker/BAT")
                .contentType(MediaType.APPLICATION_JSON));
        action
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenCallFindTicker_AndTokenInvalidAuthenticated_ThenShouldReturnUnauthorized() throws Exception {
        ResultActions action = mvc.perform(get("/v1/ticker/BAT").header("Authorization", "ascaf")
                .contentType(MediaType.APPLICATION_JSON));
        action
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenCallFindTicker_AndUserAuthenticated_ButEmailNotFound_ThenShouldReturnError() throws Exception {
        ResultActions action1 = mvc.perform(get("/v1/ticker/BATSS").header("Authorization", "Basic " + TestUtil.decode("email@email.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON));
        action1
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void whenCallFindTicker_AndUserAuthenticated_ButTokenInvalid_ThenShouldReturnError() throws Exception {
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(userRegistrationRequest.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(userRegistrationRequest.getEmail())));

        ResultActions action1 = mvc.perform(get("/v1/ticker/BATSS").header("Authorization", "Basic " + TestUtil.decode(userRegistrationRequest.getEmail(), userRegistrationRequest.getPassword())+"a")
                .contentType(MediaType.APPLICATION_JSON));
        action1
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenCallFindTicker_AndUserAuthenticated_ButCoinCodeNotRound_ThenShouldReturnError() throws Exception {
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(userRegistrationRequest.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(userRegistrationRequest.getEmail())));

        ResultActions action1 = mvc.perform(get("/v1/ticker/BATSS").header("Authorization", "Basic " + TestUtil.decode(userRegistrationRequest.getEmail(), userRegistrationRequest.getPassword()))
                .contentType(MediaType.APPLICATION_JSON));
        action1
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCallFindTicker_AndUserAuthenticated_ThenShouldReturnSuccess() throws Exception {
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(userRegistrationRequest.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(userRegistrationRequest.getEmail())));

        ResultActions action1 = mvc.perform(get("/v1/ticker/BAT").header("Authorization", "Basic " + TestUtil.decode(userRegistrationRequest.getEmail(), userRegistrationRequest.getPassword()))
                .contentType(MediaType.APPLICATION_JSON));
        action1
                .andDo(print())
                .andExpect(status().isOk());
    }
}
