package lk.esoft.fulemanagementsystem.service.impl;

import lk.esoft.fulemanagementsystem.dto.MainFuelStockDTO;
import lk.esoft.fulemanagementsystem.entity.MainFuelStock;
import lk.esoft.fulemanagementsystem.repository.MainFuelStockRepository;
import lk.esoft.fulemanagementsystem.service.MainFuelStockService;
import lk.esoft.fulemanagementsystem.util.VarList.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MainFuelStockServiceImpl implements MainFuelStockService {
    @Autowired
    private MainFuelStockRepository mainFuelStockRepository;


    @Autowired
    private ModelMapper modelMapper;

    public int saveMainFuelStock(MainFuelStockDTO mainFuelStockDTO) {
        if (mainFuelStockRepository.existsById(mainFuelStockDTO.getMfs_id())) {
            return VarList.Not_Found;
        } else {
            mainFuelStockRepository.save(modelMapper.map(mainFuelStockDTO, MainFuelStock.class));
            return VarList.Created;
        }
    }
}
