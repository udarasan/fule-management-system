package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Udara San
 * @TimeStamp 3:08 PM | 11/28/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vehicle")
public class Vehicle {
    @Id
    private int vehicle_no;
    private int available_quota;
    private String username_Fk;
}
