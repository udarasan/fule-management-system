package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "audit")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aid;
    private Date date;
    private String function;
    private String message;
}
