package SystemITR.DiegoIsaac1A.modulos.vehiculos.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehiculoResponseDTO {
    private Long id_vehiculo;

    private String marca;

    private String modelo;

    private Integer capacidad;

    private BigDecimal precio_dia;

    private String placa;
}
