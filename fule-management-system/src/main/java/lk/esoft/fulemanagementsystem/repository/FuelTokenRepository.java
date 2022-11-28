package lk.esoft.fulemanagementsystem.repository;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.entity.FuelToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Udara San
 * @TimeStamp 11:15 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
public interface FuelTokenRepository extends JpaRepository<FuelToken,Integer> {


    boolean existsByVehicleRegNo(Integer vehicleRegNo);

}
