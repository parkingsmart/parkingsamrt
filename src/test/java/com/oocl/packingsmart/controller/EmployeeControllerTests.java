package com.oocl.packingsmart.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.ParkingSmartApplication;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkingSmartApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("nactivetest")
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void should_return_created_sucessed_when_success_add_order() throws Exception {
        // given
        employeeRepository.deleteAll();
        Employee order = new Employee();
        order.setName("ccc");
        order.setEmail("14253666@qq.com");
        order.setPhone("13455698877");
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
    public void should_return_all_users_when_get_users() throws Exception {

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
        Assertions.assertEquals(employee_1.getName(),jsonArray.getJSONObject(0).get("name"));
    }

    @Test
    public void should_update_user_career_isActive_when_success_put_a_user() throws Exception{

        employeeRepository.deleteAll();
        Employee employee = new Employee();
        employee.setName("aaa");
        employee.setEmail("1332435@163.com");
        employee.setPhone("12334335625");
        employee.setPassword("11223344");
        employee.setOfficeId(0);
        Employee new_employee = employeeRepository.saveAndFlush(employee);

        new_employee.setOfficeId(1);

        String json = new ObjectMapper().writeValueAsString(new_employee);
        ResultActions result = this.mockMvc.perform(put("/users/" + employee.getId()).
                contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8).
                content(json));

        assertEquals(1,employeeRepository.findById(employee.getId()).get().getOfficeId());
    }




}
