package lk.esoft.fulemanagementsystem.repository;

import lk.esoft.fulemanagementsystem.entity.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Udara San
 * @TimeStamp 11:41 PM | 11/28/2022 | 2022
 * @ProjectDetails fule-management-system
 */
public interface FuelStationRepository extends JpaRepository<FuelStation,Integer> {

    @Query(value = "select available_limit from fuel_station where fid=?1",nativeQuery = true)
    int getAvailableBalance(Integer fid);
}
