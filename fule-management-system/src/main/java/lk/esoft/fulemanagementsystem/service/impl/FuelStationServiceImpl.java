package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.repository.FuelStationRepository;
import lk.esoft.fulemanagementsystem.repository.UserRepository;
import lk.esoft.fulemanagementsystem.service.FuelStationService;
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



}
