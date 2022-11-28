package lk.esoft.fulemanagementsystem.dto;

import lk.esoft.fulemanagementsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Udara San
 * @TimeStamp 3:08 PM | 11/28/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO {
    private int vehicle_no;
    private int available_quota;
    private String username_Fk;
}
