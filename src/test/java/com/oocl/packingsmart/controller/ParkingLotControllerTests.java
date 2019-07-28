package com.oocl.packingsmart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.parkingsmart.ParkingSmartApplication;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import com.oocl.parkingsmart.repository.ParkingLotRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkingSmartApplication.class})
@AutoConfigureMockMvc
public class ParkingLotControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Before
    public void deleteAll(){
        parkingLotRepository.deleteAll();
    }
    @org.junit.jupiter.api.Test
    public void should_return_parking_lot_list_when_find_all_parkinglots() throws Exception{
        // given

        //when

        //then



    }
    @Test
    public void should_add_an_parking_lot_when_success_add_parking_lot() throws Exception {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("ParkingLotA");
        parkingLot.setSize(10);
        parkingLot.setActive(true);
        parkingLot.setParkedNum(0);
        String json = new ObjectMapper().writeValueAsString(parkingLot);

        // when
        ResultActions result = mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        );
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        // then
        result.andExpect(status().isCreated());
        assertEquals(1,parkingLots.size());


    }

}
