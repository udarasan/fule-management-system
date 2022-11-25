package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Udara San
 * @TimeStamp 1:48 PM | 11/23/2022 | 2022
 * @ProjectDetails fule-management-system
 */

@Entity
@Table(name = "systemuser")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    @Id
    private String username;
    private String password;
    private String status;
    private String phoneNo;
    private String idPhoto;
    private String email;
    private String name;

    @OneToOne
    @JoinColumn(name = "roleCode", referencedColumnName = "UserRoleCode")
    private UserRole roleCode;

}
