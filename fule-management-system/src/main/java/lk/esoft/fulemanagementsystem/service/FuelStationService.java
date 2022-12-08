package lk.esoft.fulemanagementsystem.service;

public interface FuelStationService {

    int requestFuel(int quota, String username);

    int requestFuelStatusChange(String status, String username);
}
