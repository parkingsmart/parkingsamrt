package com.oocl.packingsmart.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.ParkingSmartApplication;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ParkingSmartApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("nactivetest")
public class OrderControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrderRepository orderRepository;

    List<Order> orders = new ArrayList<>();

    @BeforeEach
    public void initOrderList(){
        orderRepository.deleteAll();
        orders.clear();
        Date date = new Date();
        orderRepository.save(new Order("粤A12345", date.getTime(), date.getTime(), "南方软件园", 1l));
    }

    @Test
    public void should_return_order_list_when_find_all_orders() throws Exception{
        // given

        //when
        String content = this.mockMvc.perform(get("/api/orders")).andExpect(status().isOk()).
                andReturn().getResponse().getContentAsString();
        //then
        JSONArray jsonArray_Order = new JSONObject(content).getJSONArray("orders");
        Assertions.assertEquals("粤A12345", jsonArray_Order.getJSONObject(0).getString("carNumber"));
    }
    @Test
    public void should_return_new_order_list_when_find_all_new_orders() throws Exception{
        // given

        //when
        String content = this.mockMvc.perform(get("/api/orders")).andExpect(status().isOk()).
                andReturn().getResponse().getContentAsString();
        //then
        JSONArray jsonArray_Order = new JSONObject(content).getJSONArray("orders");
        Assertions.assertEquals(1, jsonArray_Order.length());
    }

    @Test
    public void should_return_created_when_receive_an_order() throws Exception{
        // given
        Date date = new Date();
        Order order = new Order("粤CAB996", date.getTime(), date.getTime(), "南方软件园", 1l);
        // when
        String json = new ObjectMapper().writeValueAsString(order);
        // then
        String content = this.mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)).andExpect(status().isCreated()).
                andReturn().getResponse().getContentAsString();
    }

    @Test
    public void should_return_created_when_user_create_an_order() throws Exception{
        // given
        Date date = new Date();
        Order order = new Order();

        order.setUserId(1L);
        order.setAppointAddress("南方软件园");
        order.setCarNumber("粤CB1323423");
        order.setAppointTime(date.getTime());
        order.setCreateAt(date.getTime());
        order.setStatus(0);
        // when
        String json = new ObjectMapper().writeValueAsString(order);
        // then
        this.mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)).andExpect(status().isCreated());

    }
    @Test
    public void should_return_exception_when_car_is_not_finish() throws Exception{
        // given

        Order order = new Order();
        order.setUserId(1L);
        order.setAppointAddress("南方软件园");
        order.setCarNumber("粤A12345");
        order.setAppointTime(new Date().getTime());
        order.setCreateAt(new Date().getTime());
        order.setStatus(2);
        // when
        String json = new ObjectMapper().writeValueAsString(order);
        // then
        this.mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)).andExpect(status().isConflict()).
                andExpect(MockMvcResultMatchers.content().string("[Conflict]: 该车辆订单正在进行中！"));

    }
    @Test
    public void should_return_exception_when__other_user_use_your_car() throws Exception{
        // given

        Order order = new Order();
        order.setUserId(2L);
        order.setAppointAddress("南方软件园");
        order.setCarNumber("粤A12345");
        order.setAppointTime(new Date().getTime());
        order.setCreateAt(new Date().getTime());
        order.setStatus(0);
        // when
        String json = new ObjectMapper().writeValueAsString(order);
        // then
        this.mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)).andExpect(status().isConflict()).
                andExpect(MockMvcResultMatchers.content().string("[Conflict]: 车牌号已被别人使用！！"));

    }




}
