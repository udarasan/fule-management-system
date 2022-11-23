package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Udara San
 * @TimeStamp 2:38 PM | 11/10/2022 | 2022
 * @ProjectDetails ecom-api
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "userrole")
public class UserRole {
    @Id
    private int UserRoleCode;
    private String UserRoleDesc;
}
