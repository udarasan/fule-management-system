package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.MainFuelStockDTO;
import lk.esoft.fulemanagementsystem.entity.MainFuelStock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MainFuelStockServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private MainFuelStockServiceImpl mainFuelStockService;

    @Test
    void saveMainFuelStock() {
        MainFuelStockDTO mainFuelStock=new MainFuelStockDTO();
        mainFuelStock.setAvailable_limit(100);
        mainFuelStock.setMain_stock(4555);
        mainFuelStock.setMfs_id(1);
        mainFuelStock.setRequested_limit(10000);
        mainFuelStock.setStatus("AVAILABLE");

        mainFuelStockService.saveMainFuelStock(mainFuelStock);
        assertEquals(mainFuelStock, mainFuelStockService.getMainFuelStock());
    }
}