package lk.esoft.fulemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Udara San
 * @TimeStamp 10:44 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelStationDTO {
    private Integer fid;
    private String stationName;
    private String city;
    private String district;
    private int max_limit;
    private int available_limit;
    private int customer_requested_limit;
    private String status;
    private int station_requested_limit;

}
