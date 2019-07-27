package com.oocl.packingsmart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.ParkingSmartApplication;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingSmartApplication.class)
@AutoConfigureMockMvc
public class LoginControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void contextLoads() {
    }

    @Before
    public void init(){
        employeeRepository.deleteAll();
    }

    @Test
    public void should_return_when_() throws Exception {
        //given
        Employee employee = new Employee();
        employee.setEmail("12312313@qq.com");
        employee.setName("lisi");
        employee.setPhone("18342536211");
        employee.setPassword("sdfwcnwuw");
        employeeRepository.saveAndFlush(employee);
        //when
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees?username="+employee.getEmail()+
                "&password="+employee.getPassword()))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees?username="+employee.getPhone()+
                "&password="+employee.getPassword()))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees?username="+employee.getId()+
                "&password="+employee.getPassword()))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees?username=1231321"+
                "&password="+employee.getPassword()))
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));


    }
}
