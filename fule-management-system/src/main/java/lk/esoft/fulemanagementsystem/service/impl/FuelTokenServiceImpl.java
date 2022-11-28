package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.entity.FuelToken;
import lk.esoft.fulemanagementsystem.entity.Vehicle;
import lk.esoft.fulemanagementsystem.repository.FuelStationRepository;
import lk.esoft.fulemanagementsystem.repository.FuelTokenRepository;
import lk.esoft.fulemanagementsystem.repository.VehicleRepository;
import lk.esoft.fulemanagementsystem.service.FuelTokenService;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Udara San
 * @TimeStamp 11:12 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@Service
@Transactional
public class FuelTokenServiceImpl implements FuelTokenService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private FuelTokenRepository fuelTokenRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private FuelStationRepository fuelStationRepository;

    @Override
    public int generateToken(FuelTokenDTO fuelTokenDTO) {
        if (fuelTokenRepository.existsById(fuelTokenDTO.getTid())) {
            return VarList.Not_Found;
        } else {

            int availableBalance=vehicleRepository.getAvailableBalance(fuelTokenDTO.getVehicleRegNo());

            int newBalance=availableBalance-fuelTokenDTO.getRequestQuota();


            Vehicle vehicle=new Vehicle();
            vehicle.setVehicle_no(fuelTokenDTO.getVehicleRegNo());
            vehicle.setAvailable_quota(newBalance);
            vehicle.setUsername_Fk(fuelTokenDTO.getUsernameFk().getUsername());



            vehicleRepository.save(vehicle);
            fuelTokenRepository.save(modelMapper.map(fuelTokenDTO, FuelToken.class));
            return VarList.Created;
        }
    }

    @Override
    public int generateTokenInFirstTime(FuelTokenDTO fuelTokenDTO) {
        if (fuelTokenRepository.existsById(fuelTokenDTO.getTid())) {
            return VarList.Not_Found;
        } else {
            Vehicle vehicle=new Vehicle();
            vehicle.setVehicle_no(fuelTokenDTO.getVehicleRegNo());
            vehicle.setAvailable_quota(20);
            vehicle.setUsername_Fk(fuelTokenDTO.getUsernameFk().getUsername());

            vehicleRepository.save(vehicle);
            fuelTokenRepository.save(modelMapper.map(fuelTokenDTO, FuelToken.class));
            return VarList.Created;
        }
    }

    @Override
    public int getAvailableBalance(Integer vehicleRegNo) {

        return vehicleRepository.getAvailableBalance(vehicleRegNo);
    }

    @Override
    public boolean vehicleRegNoExists(Integer vehicleRegNo) {
        return fuelTokenRepository.existsByVehicleRegNo(vehicleRegNo);

    }

    @Override
    public int getAvailableBalanceInStation(Integer fid) {
        return fuelStationRepository.getAvailableBalance(fid);
    }

}
