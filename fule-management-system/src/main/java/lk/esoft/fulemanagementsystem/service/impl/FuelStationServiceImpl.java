package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.FuelStationDTO;
import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.entity.FuelStation;
import lk.esoft.fulemanagementsystem.entity.FuelToken;
import lk.esoft.fulemanagementsystem.repository.FuelStationRepository;
import lk.esoft.fulemanagementsystem.repository.UserRepository;
import lk.esoft.fulemanagementsystem.service.FuelStationService;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FuelStationServiceImpl implements FuelStationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public List<FuelStationDTO> getAllFuelStationDetails() {
        List<FuelStation> fuelStations = fuelStationRepository.findAll();

        return modelMapper.map(fuelStations, new TypeToken<ArrayList<FuelStationDTO>>() {
        }.getType());
    }
}
