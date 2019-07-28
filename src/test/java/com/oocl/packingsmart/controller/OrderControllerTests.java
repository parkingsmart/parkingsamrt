package com.oocl.packingsmart.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ParkingSmartApplication.class)
@AutoConfigureMockMvc
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
        orderRepository.save(new Order("粤A12345",1,date.getTime(),date.getTime()));
    }

    @Test
    public void should_return_order_list_when_find_all_orders() throws Exception{
        // given

        //when
        String content = this.mockMvc.perform(get("/orders")).andExpect(status().isOk()).
                andReturn().getResponse().getContentAsString();
        //then
        JSONArray jsonArray_Order = new JSONObject(content).getJSONArray("pageOrders");
        Assertions.assertEquals("粤A12345", jsonArray_Order.getJSONObject(0).getString("carNumebr"));
    }
    @Test
    public void should_return_new_order_list_when_find_all_new_orders() throws Exception{
        // given

        //when
        String content = this.mockMvc.perform(get("/orders/newOrders")).andExpect(status().isOk()).
                andReturn().getResponse().getContentAsString();
        //then
        JSONArray jsonArray_Order = new JSONObject(content).getJSONArray("newOrders");
        Assertions.assertEquals(1, jsonArray_Order.length());
    }

}
