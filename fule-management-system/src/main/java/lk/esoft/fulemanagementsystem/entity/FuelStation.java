package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Udara San
 * @TimeStamp 10:44 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@Entity
@Table(name = "fuel_station")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fid;
    private String stationName;
    private String city;
    private String district;
    private int max_limit;
    private int available_limit;
    private int customer_requested_limit;
    private String status;
    private int station_requested_limit;
    private String username_fk;

}
