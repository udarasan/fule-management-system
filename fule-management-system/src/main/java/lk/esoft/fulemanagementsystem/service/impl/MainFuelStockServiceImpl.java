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
import java.util.List;

@Service
@Transactional
public class MainFuelStockServiceImpl implements MainFuelStockService {
    @Autowired
    private MainFuelStockRepository mainFuelStockRepository;

    @Autowired
    private AuditServiceImpl auditService;
    @Autowired
    private ModelMapper modelMapper;

    public int saveMainFuelStock(MainFuelStockDTO mainFuelStockDTO) {
        if (mainFuelStockRepository.existsById(mainFuelStockDTO.getMfs_id())) {
            auditService.saveAudit("saveMainFuelStock","PASS:Save Main Fuel Stock "+mainFuelStockDTO.getMain_stock());
            return VarList.Not_Found;
        } else {
            mainFuelStockRepository.save(modelMapper.map(mainFuelStockDTO, MainFuelStock.class));
            auditService.saveAudit("saveMainFuelStock","FAIL:Save Main Fuel Stock "+mainFuelStockDTO.getMain_stock());
            return VarList.Created;
        }
    }
    public MainFuelStockDTO getMainFuelStock() {
         MainFuelStock mainFuelStock=mainFuelStockRepository.findById(1).orElse(null);
         return modelMapper.map(mainFuelStock,MainFuelStockDTO.class);
    }
}
