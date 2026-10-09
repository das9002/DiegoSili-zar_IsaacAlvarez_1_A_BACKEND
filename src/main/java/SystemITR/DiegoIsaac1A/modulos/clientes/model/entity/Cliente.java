package SystemITR.DiegoIsaac1A.modulos.clientes.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id_cliente;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @Column(name = "telefono", nullable = false)
    private String telefono;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "direccion", nullable = false)
    private String direccion;
}
