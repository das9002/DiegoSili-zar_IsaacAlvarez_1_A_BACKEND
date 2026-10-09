package SystemITR.DiegoIsaac1A.modulos.vehiculos.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long id_vehiculo;
    @Column(name = "marca", nullable = false)
    private String marca;
    @Column(name = "modelo", nullable = false)
    private String modelo;
    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;
    @Column(name = "precio_dia", nullable = false)
    private BigDecimal precio_dia;
    @Column(name = "placa", unique = true, nullable = false)
    private String placa;
}
