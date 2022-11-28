package lk.esoft.fulemanagementsystem.dto;
/**
 * @author Udara San
 * @TimeStamp 11:43 AM | 11/9/2022 | 2022
 * @ProjectDetails ecom-api
 */
import lk.esoft.fulemanagementsystem.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String username;
    private String password;
    private String status;
    private String phoneNo;
    private String idPhoto;
    private String email;
    private String name;
    private UserRoleDTO roleCode;
    private List<VehicleDTO> vehicleNo;
}