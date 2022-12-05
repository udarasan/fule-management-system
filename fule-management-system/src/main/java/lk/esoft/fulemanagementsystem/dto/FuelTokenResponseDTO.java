package lk.esoft.fulemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelTokenResponseDTO {
    private byte[]qrString;
    private int availableQuota;
    private int fullQuota=20;
}
