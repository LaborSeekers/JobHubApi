package thelaborseekers.jobhubapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryChartDTO {
    private Object label; // Cambiado a Object para manejar diferentes tipos (String o Enum)
    private Long count;
}
