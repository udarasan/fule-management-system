package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * @author Udara San
 * @TimeStamp 10:42 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@Entity
@Table(name = "fuel_token")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;
    private Integer vehicleRegNo;
    private String status;
    private Date tokenExpDate;
    private Integer requestQuota;
    private Date fillingTimeAndDate;

    @OneToOne
    @JoinColumn(name = "username_fk", referencedColumnName = "username")
    private User usernameFk;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pid_fk", referencedColumnName = "pid")
    private Payment pidFk;

    @OneToOne
    @JoinColumn(name = "fuel_station_fk", referencedColumnName = "fid")
    private FuelStation fuelStationFk;



}
