package com.oocl.packingsmart.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

//    @BeforeEach
//    void init() {
//        employeeRepository.save(new Employee("aaa", "1332435@163.com", "12334335625"));
//        employeeRepository.save(new Employee("bbb", "1244364@qq.com", "14445366745"));
//    }

    @Test
    public void should_return_created_when_success_add_order() throws Exception {
        // given
        Employee order = new Employee("ccc", "14253666@qq.com", "13455698877","1325457675");
        String json = new ObjectMapper().writeValueAsString(order);

        // when
        ResultActions result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        );

        // then
        result.andExpect(status().isCreated());
    }

}
