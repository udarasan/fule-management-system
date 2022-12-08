package lk.esoft.fulemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "main_fuel_stock")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MainFuelStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mfs_id;
    private String status;
    private int available_limit;
    private int requested_limit;
    private int main_stock;
}
