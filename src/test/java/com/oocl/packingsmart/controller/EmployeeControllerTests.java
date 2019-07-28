package com.oocl.packingsmart.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.ParkingSmartApplication;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkingSmartApplication.class})
@AutoConfigureMockMvc
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void should_return_created_when_success_add_order() throws Exception {
        // given
        employeeRepository.deleteAll();
        Employee order = new Employee("ccc", "14253666@qq.com", "13455698877");
        String json = new ObjectMapper().writeValueAsString(order);

        // when
        ResultActions result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        );
        // then
        result.andExpect(status().isCreated());
    }

    @Test
    public void should_return_all_orders() throws Exception {

        employeeRepository.deleteAll();
        Employee employee_1 = new Employee("aaa", "1332435@163.com", "12334335625","11223344");
        Employee employee_2 = new Employee("bbb", "1333335@163.com", "12355335725","12223344");
        employeeRepository.saveAndFlush(employee_1);
        employeeRepository.saveAndFlush(employee_2);
        // when
        String result = mockMvc.perform(get("/users")).andReturn().getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(result);
        // then
        Assertions.assertEquals(employee_1.getName(),jsonArray.getJSONObject(0).get("name"));
    }


}
