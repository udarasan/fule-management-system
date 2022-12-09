package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.FuelStationDTO;
import lk.esoft.fulemanagementsystem.service.FuelStationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class FuelStationServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private FuelStationService fuelStationService;

    @Test
    void requestFuel() {
        fuelStationService.requestFuel(1000,"udarasan");
        FuelStationDTO fuelStationDTOS = fuelStationService.getAllFuelStationDetailsByUsername("udarasan");
        assertEquals("REQUESTED",fuelStationDTOS.getStatus());
    }

    @Test
    void requestFuelStatusChange() {
        fuelStationService.requestFuelStatusChange("TEST","udarasan");
        FuelStationDTO fuelStationDTOS = fuelStationService.getAllFuelStationDetailsByUsername("udarasan");
        assertEquals("TEST",fuelStationDTOS.getStatus());
    }

    @Test
    void getAllFuelStationDetails() {
        List<FuelStationDTO> fuelStationDTOS=fuelStationService.getAllFuelStationDetails();
        assertNotNull(fuelStationDTOS);
    }
}