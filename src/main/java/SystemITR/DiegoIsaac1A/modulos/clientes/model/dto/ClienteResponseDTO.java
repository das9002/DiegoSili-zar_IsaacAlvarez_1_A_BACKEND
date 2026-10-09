package SystemITR.DiegoIsaac1A.modulos.clientes.model.dto;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Long id_cliente;

    private String nombre;

    private String apellido;

    private String telefono;

    private String email;

    private String direccion;
}
