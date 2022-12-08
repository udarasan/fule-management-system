package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.repository.FuelStationRepository;
import lk.esoft.fulemanagementsystem.repository.UserRepository;
import lk.esoft.fulemanagementsystem.service.FuelStationService;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FuelStationServiceImpl implements FuelStationService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private FuelStationRepository fuelStationRepository;


    @Override
    public int requestFuel(int quota, String username) {
        //get fuel station pK
        int fid = fuelStationRepository.getFidByUserName(username);

        if (fuelStationRepository.existsById(fid)) {
            fuelStationRepository.requestFuel(fid,quota);
            return VarList.Accepted;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public int requestFuelStatusChange(String status, String username) {

        int fid = fuelStationRepository.getFidByUserName(username);

        if (fuelStationRepository.existsById(fid)) {
            fuelStationRepository.requestFuelStatusChange(status,fid);
            return VarList.Accepted;
        } else {
            return VarList.Not_Found;
        }
    }
}
