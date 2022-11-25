package lk.esoft.fulemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author Udara San
 * @TimeStamp 10:42 PM | 11/25/2022 | 2022
 * @ProjectDetails fule-management-system
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private Integer pid;
    private String paymentStatus;
    private Date paymentDate;

}
