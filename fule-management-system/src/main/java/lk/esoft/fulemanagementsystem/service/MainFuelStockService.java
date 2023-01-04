package lk.esoft.fulemanagementsystem.service;

import lk.esoft.fulemanagementsystem.dto.FuelStationDTO;
import lk.esoft.fulemanagementsystem.dto.MainFuelStockDTO;

import java.util.List;

public interface MainFuelStockService {
    List<MainFuelStockDTO> getMainStockDetails();
}
