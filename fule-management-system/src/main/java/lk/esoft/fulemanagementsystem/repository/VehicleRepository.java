package lk.esoft.fulemanagementsystem.repository;

import lk.esoft.fulemanagementsystem.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Udara San
 * @TimeStamp 10:47 PM | 11/28/2022 | 2022
 * @ProjectDetails fule-management-system
 */
public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {

    @Query(value = "select available_quota  from vehicle where vehicle_no=?1",nativeQuery = true)
    int getAvailableBalance(Integer vehicleNo);

    @Query(value = "select fulemgtsystem.vehicle.username_FK  from vehicle where vehicle_no=?1",nativeQuery = true)
    String getUserName(Integer vehicleRegNo);
}
