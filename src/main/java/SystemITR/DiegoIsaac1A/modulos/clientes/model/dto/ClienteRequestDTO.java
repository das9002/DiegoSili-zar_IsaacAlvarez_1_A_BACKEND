package SystemITR.DiegoIsaac1A.modulos.clientes.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre no puede ir vacio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede ir vacio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;

    @NotBlank(message = "El telefono no puede ir vacio")
    private String telefono;

    @NotBlank(message = "El email no puede ir vacio")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    @Size(max = 200, message = "La direccion no puede exceder 200 caracteres")
    private String direccion;
}
