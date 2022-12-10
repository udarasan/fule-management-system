package lk.esoft.fulemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditSectionDTO {
    private Integer aids;
    private String dates;
    private String functions;
    private String messages;
}
