package SystemITR.DiegoIsaac1A.modulos.reservas.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReservaResponseDTO {
    private Long id_reserva;

    private Long id_cliente;

    private String nombre_cliente;

    private Long id_vehiculo;

    private String vehiculo;

    private String nombre_reserva;

    private LocalDate fecha_reserva;

    private Integer cantidad_pasajeros;

    private Integer cantidad_dias;

    private String estado;

    private BigDecimal total_pago;
}
