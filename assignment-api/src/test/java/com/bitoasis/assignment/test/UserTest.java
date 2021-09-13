package com.bitoasis.assignment.test;

import com.bitoasis.assignment.AssignmentApplication;
import com.bitoasis.assignment.configuration.SecurityConfiguration;
import com.bitoasis.assignment.configuration.UserConfiguration;
import com.bitoasis.assignment.jpa.UserJpaRepository;
import com.bitoasis.assignment.request.UserRegistrationRequest;
import com.bitoasis.assignment.util.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AssignmentApplication.class, UserConfiguration.class, SecurityConfiguration.class})
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserJpaRepository userJpaRepository;

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
    }

    @Test
    public void whenRegisterUser_AndRequestIsNull_ThenShouldReturnError() throws Exception {
        String request = jacksonUtil.toJson(null);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Technical error,Please check with admin")));
    }

    @Test
    public void whenRegisterUser_AndNameIsNull_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setName(null);
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in name : Invalid name")));
    }

    @Test
    public void whenRegisterUser_AndNameIsEmpty_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setName("");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in name : Invalid name")));
    }

    @Test
    public void whenRegisterUser_AndNameIsSpace_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setName("   ");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in name : Invalid name")));
    }

    @Test
    public void whenRegisterUser_AndPasswordIsNull_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setPassword(null);
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in password : Invalid password")));
    }

    @Test
    public void whenRegisterUser_AndPasswordIsEmpty_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setPassword("");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in password : Password should be from 8 to 12 digits")));
    }

    @Test
    public void whenRegisterUser_AndPasswordIsSpace_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setPassword("   ");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in password : Password should be from 8 to 12 digits")));
    }

    @Test
    public void whenRegisterUser_AndPasswordLessThan8Digits_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setPassword("1234567");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in password : Password should be from 8 to 12 digits")));
    }

    @Test
    public void whenRegisterUser_AndPasswordMoreThan12Digits_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setPassword("1234567891234");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in password : Password should be from 8 to 12 digits")));
    }

    @Test
    public void whenRegisterUser_AndEmailIsNull_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setEmail(null);
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in email : Invalid email")));
    }

    @Test
    public void whenRegisterUser_AndEmailIsEmpty_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setEmail("");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in email : Invalid email")));
    }

    @Test
    public void whenRegisterUser_AndEmailIsSpace_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setEmail("   ");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in email : Invalid email")));
    }

    @Test
    public void whenRegisterUser_AndEmailWithInvalidFormat_ThenShouldReturnError() throws Exception {
        userRegistrationRequest.setEmail("infobitoasis.net");
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(-1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Error in email : Invalid email")));
    }

    @Test
    public void whenRegisterUser_AndRequestIsValid_ThenShouldReturnObject() throws Exception {
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(userRegistrationRequest.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(userRegistrationRequest.getEmail())));
    }

    @Test
    public void whenRegisterUser_AndEmailAlreadyRegistered_ThenShouldReturnError() throws Exception {
        String request = jacksonUtil.toJson(userRegistrationRequest);

        ResultActions action = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(userRegistrationRequest.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(userRegistrationRequest.getEmail())));

        ResultActions action1 = mvc.perform(post("/v1/register")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        action1
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", is("Email already registered " + userRegistrationRequest.getEmail())));
    }
}
