package com.oocl.packingsmart.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.ParkingSmartApplication;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import com.oocl.parkingsmart.repository.ParkingLotRepository;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkingSmartApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("nactivetest")
@WithMockUser(username = "admin",roles = {"ADMIN"})
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void init(){
        employeeRepository.deleteAll();
        parkingLotRepository.deleteAll();
    }
    @Test
    public void should_return_created_sucessed_when_success_add_order() throws Exception {
        // given
        Employee order = new Employee();
        order.setName("ccc");
        order.setEmail("14253666@qq.com");
        order.setPhone("13455698877");
        String json = new ObjectMapper().writeValueAsString(order);
        // when
        ResultActions result = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        );
        // then
        result.andExpect(status().isCreated());
    }

    @Test
    public void should_return_all_orders() throws Exception {
        //given
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
        String result = mockMvc.perform(get("/employees")).andReturn().getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(result);
        // then
        Assertions.assertEquals(employee_1.getName(),jsonArray.getJSONObject(0).get("name"));
    }

    @Test
    public void should_update_user_career_when_success_put_a_user() throws Exception{
        Employee employee = new Employee();
        employee.setName("aaa");
        employee.setEmail("1332435@163.com");
        employee.setPhone("12334335625");
        employee.setPassword("11223344");
        employee.setOfficeId(0);
        Employee new_employee = employeeRepository.saveAndFlush(employee);

        new_employee.setOfficeId(1);

        String json = new ObjectMapper().writeValueAsString(new_employee);
        ResultActions result = this.mockMvc.perform(put("/employees/" + employee.getId()).
                contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8).
                content(json));

        assertEquals(1,employeeRepository.findById(employee.getId()).get().getOfficeId());
    }



    @Test
    public void should_return_manager_all_parkingLot_when_find_by_Manager_id() throws Exception {
        //given
        ParkingLot parkingLot1 = new ParkingLot("parkingLot1",10,1l);
        ParkingLot parkingLot2 = new ParkingLot("parkingLot2",12,1l);
        ParkingLot parkingLot3 = new ParkingLot("parkingLot3",13,1l);
        ParkingLot parkingLot4 = new ParkingLot("parkingLot4",10,2l);
        parkingLotRepository.saveAndFlush(parkingLot1);
        parkingLotRepository.saveAndFlush(parkingLot2);
        parkingLotRepository.saveAndFlush(parkingLot3);
        parkingLotRepository.saveAndFlush(parkingLot4);
        //when
        ResultActions result = mockMvc.perform(get("/employees/1/parking-lots"));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", Matchers.contains("parkingLot1", "parkingLot2", "parkingLot3")))
                .andExpect(jsonPath("$[*].manager", Matchers.contains(1,1,1)))
                .andExpect(jsonPath("$[*].size", Matchers.contains(10,12,13)));
    }

    @Test
    public void should_return_ok_when_update_success_parkingLot_manager_by_manager_id() throws Exception {
        //given
        ParkingLot parkingLot1 = new ParkingLot("parkingLot1",10,2l);
        ParkingLot parkingLot2 = new ParkingLot("parkingLot2",12,1l);
        ParkingLot parkingLot3 = new ParkingLot("parkingLot3",13,3l);
        ParkingLot parkingLot4 = new ParkingLot("parkingLot4",10,2l);
        ParkingLot parkingLot5 = new ParkingLot("parkingLot5",10,2l);
        parkingLotRepository.saveAll(Arrays.asList(parkingLot1,parkingLot2,parkingLot3,parkingLot4,parkingLot5));
        List<Long> ids = Arrays.asList(parkingLotRepository.findByName("parkingLot1").getId(),
                parkingLotRepository.findByName("parkingLot2").getId(),
                parkingLotRepository.findByName("parkingLot3").getId(),
                parkingLotRepository.findByName("parkingLot4").getId(),
                parkingLotRepository.findByName("parkingLot5").getId());
        //when
        mockMvc.perform(post("/employees/4/parking-lots/appointments")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(ids)))
        .andExpect(status().isOk());
        assertEquals(5,parkingLotRepository.findAllByManager(4l).size());
        assertEquals(4l,parkingLotRepository.findByName("parkingLot1").getManager().longValue());
        assertEquals(4l,parkingLotRepository.findByName("parkingLot2").getManager().longValue());
        assertEquals(4l,parkingLotRepository.findByName("parkingLot3").getManager().longValue());
        assertEquals(4l,parkingLotRepository.findByName("parkingLot4").getManager().longValue());
        assertEquals(4l,parkingLotRepository.findByName("parkingLot5").getManager().longValue());
    }

}
