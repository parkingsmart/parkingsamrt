package com.oocl.packingsmart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.ParkingSmartApplication;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.repository.UserRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.repository.OrderRepository;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkingSmartApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("nactivetest")
public class UserControllerTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    public static final String LOGIN_URL = "/api/users";
    public User user;

    @Before
    public void init() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        //given
        user = new User("18356781234", "12345");
        userRepository.saveAndFlush(user);
    }

    @Test
    public void should_return_user_when_the_username_and_password_are_verified_to_be_correct() throws Exception {
        //when return user when the username and password are verified to be correct
        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL + "/login?username=" + user.getPhone() +
                "&password=" + user.getPassword()))
                .andExpect(status().isOk())
                .andReturn();
        //then
        JSONObject result = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals(user.getPhone(), result.getString("phone"));
    }

    @Test
    public void should_return_error_when_the_username_is_not_correct_and_password_is_correct() throws Exception {
        //When the email is not correct and password is correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL + "/login?username=1231321" +
                "&password=" + user.getPassword()))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }

    @Test
    public void should_return_error_when_the_username_is_correct_and_password_is_not_correct() throws Exception {
        //When the password is not correct and username is correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL + "/login?username=" + user.getPhone() +
                "&password=qwfsdsd"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }

    @Test
    public void should_return_error_when_the_username_and_password_is_not_correct() throws Exception {
        //When the username  and password is not correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL + "/login?username=121242342" +
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
        assertEquals("123456789", result.getString("phone"));
    }

    @Test
    public void should_return_user_order_list_when_find_user_orders() throws Exception {
        // given
        User user = new User("13726267000", "123");
        User savedUser = userRepository.save(user);
        Order order = new Order("粤A03566", 201907290737l, 201907290800l, "软件园", savedUser.getId());
        Order savedOrder = orderRepository.save(order);
        // when
        String result = mockMvc.perform(get("/api/users/" + savedUser.getId())).andReturn().getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(result);
        // then
        Assertions.assertEquals(order.getCarNumber(), jsonArray.getJSONObject(0).get("carNumber"));

    }

    @Test
    public void should_update_a_user_order_status_when_user_fetch_order() throws Exception {
        // given
        User user = new User("13726267000", "123");
        User savedUser = userRepository.save(user);
        Order order = new Order("粤A03566", 201907290737l, 201907290800l, "软件园", savedUser.getId());
        Order savedOrder = orderRepository.save(order);
        // when
        String json = new ObjectMapper().writeValueAsString(savedUser);
        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/api/users/"+savedUser.getId()+"?oderID="+savedOrder.getId())).andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(result);

        System.out.println(result);
        // then
        Assertions.assertEquals(3, jsonObject.get("status"));

    }

    @Test
    public void should_get_car_num_list_when_get_orders_by_id() throws Exception {
        // given
        User user = new User("13726267000", "123");
        User savedUser = userRepository.save(user);
        Order order1 = new Order("粤A03566", 201907290737l, 201907290800l, "软件园", savedUser.getId());
        Order order2 = new Order("粤B03566", 201907290732l, 201907290801l, "软件园", savedUser.getId());
        orderRepository.save(order1);
        orderRepository.save(order2);
        String msg = "carNums";
        // when
        String json = new ObjectMapper().writeValueAsString(savedUser);
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/"+savedUser.getId()).param("msg",msg)).andReturn().getResponse().getContentAsString();
        System.out.println(result);
        JSONArray jsonArray = new JSONArray(result);

        // then
        Assertions.assertEquals("粤B03566", jsonArray.get(0));
        Assertions.assertEquals(2, jsonArray.length());

    }
}

