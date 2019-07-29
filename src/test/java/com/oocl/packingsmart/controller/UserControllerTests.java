package com.oocl.packingsmart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.ParkingSmartApplication;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.repository.UserRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkingSmartApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("nactivetest")
public class UserControllerTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    public static final String LOGIN_URL = "/api/users";
    public User user;

    @Before
    public void init(){
        userRepository.deleteAll();
        //given
        user = new User("18356781234","12345");
        userRepository.saveAndFlush(user);
    }

    @Test
    public void should_return_user_when_the_username_and_password_are_verified_to_be_correct() throws Exception {
        //when return user when the username and password are verified to be correct
        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL + "/login?username=" + user.getPhone()+
                "&password=" + user.getPassword()))
                .andExpect(status().isOk())
                .andReturn();
        //then
        JSONObject result = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("", result.getString("password"));
        assertEquals(user.getPhone(), result.getString("phone"));
    }

    @Test
    public void should_return_error_when_the_username_is_not_correct_and_password_is_correct() throws Exception {
        //When the email is not correct and password is correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL+"/login?username=1231321" +
                "&password=" + user.getPassword()))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }

    @Test
    public void should_return_error_when_the_username_is_correct_and_password_is_not_correct() throws Exception {
        //When the password is not correct and username is correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL+"/login?username="+user.getPhone() +
                "&password=qwfsdsd"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }

    @Test
    public void should_return_error_when_the_username_and_password_is_not_correct() throws Exception {
        //When the username  and password is not correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL+"/login?username=121242342" +
                "&password=qwfsdsd"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }

    @Test
    public void should_return_user_when_the_username_and_password_is_registered() throws Exception {
        //When the email is not correct and password is correct, the login is failed.
        //then
        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL + "/registered?username=123456789" +
                "&password=12345"))
                .andExpect(status().isOk())
                .andReturn();
        //then
        JSONObject result = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("12345", result.getString("password"));
        assertEquals("123456789", result.getString("phone"));
    }
}
