package lk.esoft.fulemanagementsystem.dto;

import lk.esoft.fulemanagementsystem.entity.FuelStation;
import lk.esoft.fulemanagementsystem.entity.Payment;
import lk.esoft.fulemanagementsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Udara San
 * @TimeStamp 10:42 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelTokenDTO {
    private Integer tid;
    private Integer vehicleRegNo;
    private String status;
    private Date tokenExpDate;
    private Integer requestQuota;
    private Date fillingTimeAndDate;
    private User usernameFk;
    private Payment pidFk;
    private FuelStation fuelStationFk;



}
