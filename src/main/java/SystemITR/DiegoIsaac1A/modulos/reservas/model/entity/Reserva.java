package SystemITR.DiegoIsaac1A.modulos.reservas.model.entity;

import SystemITR.DiegoIsaac1A.modulos.clientes.model.entity.Cliente;
import SystemITR.DiegoIsaac1A.modulos.vehiculos.model.entity.Vehiculo;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id_reserva;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;
    @Column(name = "nombre_reserva", nullable = false)
    private String nombre_reserva;
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fecha_reserva;
    @Column(name = "cantidad_pasajeros", nullable = false)
    private Integer cantidad_pasajeros;
    @Column(name = "cantidad_dias", nullable = false)
    private Integer cantidad_dias;
    @Column(name = "estado", nullable = false)
    private String estado;
    @Column(name = "total_pago", nullable = false)
    private BigDecimal total_pago;
}
