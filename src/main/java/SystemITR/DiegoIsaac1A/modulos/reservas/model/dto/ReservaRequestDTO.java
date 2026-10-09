package SystemITR.DiegoIsaac1A.modulos.reservas.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequestDTO {

    @NotNull(message = "ERR01: El id del cliente no puede ir nulo")
    @Positive(message = "ERR02: El id del cliente debe ser mayor a 0")
    private Long id_cliente;

    @NotNull(message = "ERR01: El id del vehiculo no puede ir nulo")
    @Positive(message = "ERR02: El id del vehiculo debe ser mayor a 0")
    private Long id_vehiculo;

    @NotBlank(message = "ERR01: El nombre de la reserva no puede ir vacio")
    @Size(max = 100, message = "ERR03: El nombre de la reserva no puede exceder 100 caracteres")
    private String nombre_reserva;

    @NotNull(message = "ERR01: La fecha de la reserva no puede ir nula")
    private LocalDate fecha_reserva;

    @NotNull(message = "ERR01: La cantidad de pasajeros no puede ir nula")
    @Positive(message = "ERR02: La cantidad de pasajeros debe ser mayor a 0")
    private Integer cantidad_pasajeros;

    @NotNull(message = "ERR01: La cantidad de dias no puede ir nula")
    @Positive(message = "ERR02: La cantidad de dias debe ser mayor a 0")
    @Max(value = 365, message = "ERR02: La cantidad de dias no puede exceder 365")
    private Integer cantidad_dias;

    @Pattern(regexp = "^(PENDIENTE|CONFIRMADA|CANCELADA|FINALIZADA)$", message = "ERR02: El estado debe ser PENDIENTE, CONFIRMADA, CANCELADA o FINALIZADA")
    private String estado;
}
