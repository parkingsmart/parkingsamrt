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
    public void should_return_created_sucessed_when_add_a_users() throws Exception {
        // given
        employeeRepository.deleteAll();
        Employee employee = new Employee();
        employee.setName("ccc");
        employee.setEmail("14253666@qq.com");
        employee.setPhone("13455698877");
        String json = new ObjectMapper().writeValueAsString(employee);

        // when
        ResultActions result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        );
        // then
        result.andExpect(status().isCreated());
    }

    @Test
    public void should_return_users_list_when_get_all_users() throws Exception {

        employeeRepository.deleteAll();
        Employee employee_1 = new Employee();
        Employee employee_2 = new Employee();
        employee_1.setName("aaa");
        employee_1.setEmail("1332435@163.com");
        employee_1.setPhone("12334335625");
        employee_1.setPassword("11223344");
        employee_2.setName("bbb");
        employee_2.setEmail("1333335@163.com");
        employee_2.setPhone("12355335725");
        employee_2.setPassword("12223344");
        employeeRepository.saveAndFlush(employee_1);
        employeeRepository.saveAndFlush(employee_2);
        // when
        String result = mockMvc.perform(get("/users")).andReturn().getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(result);
        // then
        Assertions.assertEquals(employee_1.getName(),jsonArray.getJSONObject(1).get("name"));
    }

}
