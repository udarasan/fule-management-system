package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
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
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;
    private String paymentStatus;
    private Date paymentDate;

}
