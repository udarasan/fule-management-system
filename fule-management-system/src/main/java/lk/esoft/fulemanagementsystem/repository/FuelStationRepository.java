package lk.esoft.fulemanagementsystem.repository;

import lk.esoft.fulemanagementsystem.entity.FuelStation;
import lk.esoft.fulemanagementsystem.entity.FuelToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

/**
 * @author Udara San
 * @TimeStamp 11:41 PM | 11/28/2022 | 2022
 * @ProjectDetails fule-management-system
 */
public interface FuelStationRepository extends JpaRepository<FuelStation,Integer> {

    @Query(value = "select available_limit from fuel_station where fid=?1",nativeQuery = true)
    int getAvailableBalance(Integer fid);

    @Modifying
    @Query(value = "update fuel_station set available_limit=?1 , customer_requested_limit=?2 where fid =?3",nativeQuery = true)
    void updateFuelStationBalances(int available_limit,int cus_req_limit,int fid);

    @Query(value = "select customer_requested_limit from fuel_station where fid=?1",nativeQuery = true)
    int getCustomerRequestQuota(Integer fid);

    @Query(value = "select status,station_requested_limit from fuel_station where fid=?1",nativeQuery = true)
    Object[][] checkFuelRequestAvailabilityCheck(int fid);

    default HashMap<String,Object> checkFuelRequestAvailability(int fid){
        HashMap<String, Object> dataMap = new HashMap<>();
        Object[][] obj=checkFuelRequestAvailabilityCheck(fid);
        if(obj!=null){
            dataMap.put("status", obj[0][0]);
            dataMap.put("station_requested_limit", obj[0][1]);
        }
        return dataMap;
    }


    @Query(value = "select fid from fuel_station where username_fk=?1",nativeQuery = true)
    int getFidByUserName(String username);

    @Modifying
    @Query(value = "update fuel_station set station_requested_limit=?2,status='REQUESTED' where fid =?1",nativeQuery = true)
    void requestFuel(int fid, int quota);

    @Modifying
    @Query(value = "update fuel_station set status=?1 where fid =?2",nativeQuery = true)
    void requestFuelStatusChange(String status, int fid);
}
