package lk.esoft.fulemanagementsystem.service;

import lk.esoft.fulemanagementsystem.dto.FuelStationDTO;

import java.util.List;

public interface FuelStationService {

    int requestFuel(int quota, String username);

    int requestFuelStatusChange(String status, String username);

    List<FuelStationDTO> getAllFuelStationDetails();
   FuelStationDTO getAllFuelStationDetailsByUsername(String username);

}
