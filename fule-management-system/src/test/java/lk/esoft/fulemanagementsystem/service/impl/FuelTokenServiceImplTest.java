package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.dto.FuelTokenResponseDTO;
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
import java.util.List;

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
        FuelTokenDTO fuelTokenDTO = new FuelTokenDTO();
        fuelTokenDTO.setTid(1);
        fuelTokenDTO.setFillingTimeAndDate(Date.valueOf("2022-12-05"));
        FuelStation fuelStation = new FuelStation();
        fuelStation.setFid(1);
        fuelTokenDTO.setFuelStationFk(fuelStation);
        Payment payment = new Payment();
        payment.setPid(1);
        fuelTokenDTO.setPidFk(payment);
        fuelTokenDTO.setRequestQuota(34);
        User user = new User();
        user.setUsername("udarasan");
        fuelTokenDTO.setUsernameFk(user);
        fuelTokenDTO.setVehicleRegNo(6129);
        fuelTokenDTO.setStatus("ACCEPTED");
        fuelTokenDTO.setTokenExpDate(Date.valueOf("2022-12-05"));
        int res = fuelTokenService.generateToken(fuelTokenDTO);
        assertEquals(res, 404);
    }

    @Test
    void generateTokenInFirstTime() {
        FuelTokenDTO fuelTokenDTO = new FuelTokenDTO();
        fuelTokenDTO.setTid(1);
        fuelTokenDTO.setFillingTimeAndDate(Date.valueOf("2022-12-05"));
        FuelStation fuelStation = new FuelStation();
        fuelStation.setFid(1);
        fuelTokenDTO.setFuelStationFk(fuelStation);
        Payment payment = new Payment();
        payment.setPid(1);
        fuelTokenDTO.setPidFk(payment);
        fuelTokenDTO.setRequestQuota(34);
        User user = new User();
        user.setUsername("udarasan");
        fuelTokenDTO.setUsernameFk(user);
        fuelTokenDTO.setVehicleRegNo(6129);
        fuelTokenDTO.setStatus("ACCEPTED");
        fuelTokenDTO.setTokenExpDate(Date.valueOf("2022-12-05"));
        int res = fuelTokenService.generateToken(fuelTokenDTO);
        assertEquals(res, 404);
    }

    @Test
    void getAvailableBalance() {
        assertNotNull(fuelTokenService.getAvailableBalance(6129));
    }

    @Test
    void vehicleRegNoExists() {
        assertNotNull(fuelTokenService.vehicleRegNoExists(6129));
    }

    @Test
    void getAvailableBalanceInStation() {
        assertNotNull(fuelTokenService.getAvailableBalanceInStation(1));
    }

    @Test
    void checkFuelRequestAvailability() {
        assertNotNull(fuelTokenService.checkFuelRequestAvailability(1));
    }

    @Test
    void getAllTokenByUsername() {
        assertNotNull(fuelTokenService.getAllTokenByUsername("udarasan"));
    }

    @Test
    void getAllQRandDetails() {
        assertNotNull(fuelTokenService.getAllQRandDetails("udarasan"));
    }

    @Test
    void getAllTokenByFuelStationId() {
        assertNotNull(fuelTokenService.getAllTokenByFuelStationId("udarasan"));
    }

    @Test
    void changeTokenStatus() {
        fuelTokenService.changeTokenStatus(1, "Requested");
        FuelToken fuelToken = fuelTokenRepository.getQRGeneratingToken("udarasan");
        assertEquals("Requested", fuelToken.getStatus());
    }

    @Test
    void getAllTokens() {
        List<FuelTokenDTO>list=fuelTokenService.getAllTokens();
        assertNotNull(list);
    }

    @Test
    void sendMail() {
    }
}