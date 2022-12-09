package lk.esoft.fulemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditDTO {
    private int aid;
    private Date date;
    private String function;
    private String message;
}
