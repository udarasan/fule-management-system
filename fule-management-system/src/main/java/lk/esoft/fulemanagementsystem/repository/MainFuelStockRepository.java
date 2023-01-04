package lk.esoft.fulemanagementsystem.repository;

import lk.esoft.fulemanagementsystem.dto.MainFuelStockDTO;
import lk.esoft.fulemanagementsystem.entity.MainFuelStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainFuelStockRepository extends JpaRepository<MainFuelStock,Integer> {
    List<MainFuelStockDTO> getMainStockDetails();
}
