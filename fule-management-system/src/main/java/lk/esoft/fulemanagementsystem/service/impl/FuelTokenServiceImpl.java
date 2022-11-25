package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.entity.FuelToken;
import lk.esoft.fulemanagementsystem.repository.FuelTokenRepository;
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

    @Override
    public int generateToken(FuelTokenDTO fuelTokenDTO) {
        if (fuelTokenRepository.existsById(fuelTokenDTO.getTid())) {
            return VarList.Not_Found;
        } else {
            fuelTokenRepository.save(modelMapper.map(fuelTokenDTO, FuelToken.class));
            return VarList.Created;
        }
    }
}
