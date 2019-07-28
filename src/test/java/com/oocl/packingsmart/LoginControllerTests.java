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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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

    public Employee employee;

    @Test
    public void contextLoads() {
    }

    @Before
    public void init(){
        //given
        employeeRepository.deleteAll();
        employee = new Employee();
        employee.setEmail("12312313@qq.com");
        employee.setName("lisi");
        employee.setPhone("18342536211");
        employee.setPassword("sdfwcnwuw");
        employeeRepository.saveAndFlush(employee);
    }

    @Test
    public void should_return_true_when_the_email_and_password_are_verified_to_be_correct() throws Exception {
        //When the email and password are verified to be correct, the login is successful.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username="+employee.getEmail()+
                "&password="+employee.getPassword()))
                .andExpect(status().isOk());
    }
    @Test
    public void should_return_true_when_the_phone_and_password_are_verified_to_be_correct() throws Exception {
        //When the email and password are verified to be correct, the login is successful.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username="+employee.getPhone()+
                "&password="+employee.getPassword()))
                .andExpect(status().isOk());
    }
    @Test
    public void should_return_true_when_the_id_and_password_are_verified_to_be_correct() throws Exception {
        //When the email and password are verified to be correct, the login is successful.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username="+employee.getId()+
                "&password="+employee.getPassword()))
                .andExpect(status().isOk());
    }
    @Test
    public void should_return_error_when_the_email_is_not_correct_and_password_is_correct() throws Exception {
        //When the email is not correct and password is correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username=1231321"+
                "&password="+employee.getPassword()))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }
    @Test
    public void should_return_error_when_the_id_is_not_correct_and_password_is_correct() throws Exception {
        //When the id is not correct and password is correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username=111"+
                "&password="+employee.getPassword()))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }
    @Test
    public void should_return_error_when_the_phone_is_not_correct_and_password_is_correct() throws Exception {
        //When the phone is not correct and password is correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username=18342536212"+
                "&password="+employee.getPassword()))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }
    @Test
    public void should_return_error_when_the_email_and_password_is_not_correct() throws Exception {
        //When the email and password is not correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username=542543234@qq.com"+
                "&password=sdvsv2123"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }
    @Test
    public void should_return_error_when_the_id_and_password_is_not_correct() throws Exception {
        //When the id and password is not correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username=1241"+
                "&password=125dvbdfb"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }
    @Test
    public void should_return_error_when_the_phone_and_password_is_not_correct() throws Exception {
        //When the phone and password is not correct, the login is failed.
        //then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users?username=18342536213"+
                "&password=r2g4berbe"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("wrong user name or password"));
    }

}
