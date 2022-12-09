package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.entity.FuelStation;
import lk.esoft.fulemanagementsystem.entity.FuelToken;
import lk.esoft.fulemanagementsystem.entity.Payment;
import lk.esoft.fulemanagementsystem.entity.User;
import lk.esoft.fulemanagementsystem.repository.FuelTokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class FuelTokenServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private FuelTokenServiceImpl fuelTokenService;

    @Autowired
    private FuelTokenRepository fuelTokenRepository;


    @Test
    void generateToken() {
        FuelTokenDTO fuelTokenDTO=new FuelTokenDTO();
        fuelTokenDTO.setTid(1);
        fuelTokenDTO.setFillingTimeAndDate(Date.valueOf("2022-12-05"));
        FuelStation fuelStation=new FuelStation();
        fuelStation.setFid(1);
        fuelTokenDTO.setFuelStationFk(fuelStation);
        Payment payment = new Payment();
        payment.setPid(1);
        fuelTokenDTO.setPidFk(payment);
        fuelTokenDTO.setRequestQuota(34);
        User user=new User();
        user.setUsername("udarasan");
        fuelTokenDTO.setUsernameFk(user);
        fuelTokenDTO.setVehicleRegNo(6129);
        fuelTokenDTO.setStatus("ACCEPTED");
        fuelTokenDTO.setTokenExpDate(Date.valueOf("2022-12-05"));
        int res=fuelTokenService.generateToken(fuelTokenDTO);
        assertEquals(res,404);
    }

    @Test
    void generateTokenInFirstTime() {

    }

    @Test
    void getAvailableBalance() {
    }

    @Test
    void vehicleRegNoExists() {
    }

    @Test
    void getAvailableBalanceInStation() {
    }

    @Test
    void checkFuelRequestAvailability() {
    }

    @Test
    void getAllTokenByUsername() {
    }

    @Test
    void getAllQRandDetails() {
    }

    @Test
    void getAllTokenByFuelStationId() {
    }

    @Test
    void changeTokenStatus() {
    }

    @Test
    void getAllTokens() {
    }

    @Test
    void sendMail() {
    }
}