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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkingSmartApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("nactivetest")
public class ParkingLotControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Before
    public void deleteAll(){
        parkingLotRepository.deleteAll();
    }
    @Test
    public void should_return_parking_lot_list_when_find_all_parking_lots() throws Exception{

        // given
        ParkingLot parkingLotA = new ParkingLot();
        parkingLotA.setName("ParkingLotA");
        parkingLotA.setSize(10);
        parkingLotA.setActive(true);
        parkingLotA.setParkedNum(0);
        parkingLotRepository.saveAndFlush(parkingLotA);
        // when
        String result = mockMvc.perform(get("/api/parking-lots")).andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(result);
        // then
        Assertions.assertEquals(parkingLotA.getName(), ((JSONArray)jsonObject.get("AllParkingLot")).getJSONObject(0).get("name"));

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
        ResultActions result = mockMvc.perform(post("/api/parking-lots")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        );
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        // then
        result.andExpect(status().isCreated());
        assertEquals(1,parkingLots.size());


    }
    @Test
    public void should_update_a_parking_lot_size_when_success_put_a_parking_lot_info() throws Exception {
        //Given
        ParkingLot parkingLotA = new ParkingLot();
        parkingLotA.setName("ParkingLotA");
        parkingLotA.setSize(10);
        parkingLotA.setActive(true);
        parkingLotA.setParkedNum(0);
        ParkingLot savedParkingLot = parkingLotRepository.saveAndFlush(parkingLotA);
        // when
        savedParkingLot.setSize(20);
        String json = new ObjectMapper().writeValueAsString(savedParkingLot);
        ResultActions result = this.mockMvc.perform(put("/api/parking-lots/" + savedParkingLot.getId()).
                contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8).
                content(json));
        int size = parkingLotRepository.findById(savedParkingLot.getId()).get().getSize();
        // then
        result.andExpect(status().isOk());
        assertEquals(20,size);
    }
    @Test
    public void should_update_a_parking_lot_isActive_when_success_put_a_parking_lot_info() throws Exception {
        //Given
        ParkingLot parkingLotA = new ParkingLot();
        parkingLotA.setName("ParkingLotA");
        parkingLotA.setSize(10);
        parkingLotA.setActive(true);
        parkingLotA.setParkedNum(0);
        ParkingLot savedParkingLot = parkingLotRepository.saveAndFlush(parkingLotA);
        // when
        savedParkingLot.setActive(false);
        String json = new ObjectMapper().writeValueAsString(savedParkingLot);
        ResultActions result = this.mockMvc.perform(put("/api/parking-lots/" + savedParkingLot.getId()).
                contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8).
                content(json));
        boolean isActive = parkingLotRepository.findById(savedParkingLot.getId()).get().isActive();
        // then
        result.andExpect(status().isOk());
        assertEquals(false,isActive);
    }

}
