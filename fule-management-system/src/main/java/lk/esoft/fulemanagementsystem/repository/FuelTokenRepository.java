package lk.esoft.fulemanagementsystem.repository;

import lk.esoft.fulemanagementsystem.dto.FuelTokenDTO;
import lk.esoft.fulemanagementsystem.entity.FuelToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Udara San
 * @TimeStamp 11:15 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
public interface FuelTokenRepository extends JpaRepository<FuelToken,Integer> {


    boolean existsByVehicleRegNo(Integer vehicleRegNo);

    @Query(value = "select * from fuel_token where username_fk=?1",nativeQuery = true)
    List<FuelToken> getAllTokenByUsername(String username);

    @Query(value = "select * from fuel_token where username_fk=?1 ORDER BY tid DESC Limit 1",nativeQuery = true)
    FuelToken getQRGeneratingToken(String username);

    @Query(value = "select * from fuel_token where fuel_station_fk=?1 and status ='REQUESTED' ORDER BY tid",nativeQuery = true)
    List<FuelToken> getAllTokenByFid(int fid);

    @Modifying
    @Query(value = "UPDATE fuel_token SET status = ?2 WHERE tid = ?1",nativeQuery = true)
    void changePaymentStatus(int tid, String status);
}
