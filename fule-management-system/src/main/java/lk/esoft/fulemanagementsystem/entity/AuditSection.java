package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "audit_section")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aids;
    private String dates;
    private String functions;
    private String messages;
}
