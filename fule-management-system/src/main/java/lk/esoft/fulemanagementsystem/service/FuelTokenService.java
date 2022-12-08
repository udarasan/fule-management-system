package lk.esoft.fulemanagementsystem.service;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.dto.FuelTokenResponseDTO;

import java.util.HashMap;
import java.util.List;

/**
 * @author Udara San
 * @TimeStamp 11:12 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
public interface FuelTokenService {
    int generateToken(FuelTokenDTO fuelTokenDTO);

    int generateTokenInFirstTime(FuelTokenDTO fuelTokenDTO);

    int getAvailableBalance(Integer vehicleRegNo);

    boolean vehicleRegNoExists(Integer vehicleRegNo);


    int getAvailableBalanceInStation(Integer fid);

    boolean checkFuelRequestAvailability(int fid);

    List<FuelTokenDTO> getAllTokenByUsername(String username);


    FuelTokenResponseDTO getAllQRandDetails(String username);

    List<FuelTokenDTO> getAllTokenByFuelStationId(String username);

    int changePaymentStatus(int tid, String status);

    List<FuelTokenDTO> getAllTokens();
}
