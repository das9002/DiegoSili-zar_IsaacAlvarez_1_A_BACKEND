package SystemITR.DiegoIsaac1A.modulos.clientes.service;

import SystemITR.DiegoIsaac1A.exceptions.BusinessRuleException;
import SystemITR.DiegoIsaac1A.exceptions.DataNotFoundException;
import SystemITR.DiegoIsaac1A.modulos.clientes.model.dto.ClienteRequestDTO;
import SystemITR.DiegoIsaac1A.modulos.clientes.model.dto.ClienteResponseDTO;
import SystemITR.DiegoIsaac1A.modulos.clientes.model.entity.Cliente;
import SystemITR.DiegoIsaac1A.modulos.clientes.repository.ClienteRepository;
import SystemITR.DiegoIsaac1A.modulos.reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository repo;
    private final ReservaRepository reservaRepo;

    private static final String DIRECCION_DEFAULT = "SIN ESPECIFICAR";

    private void copiarDatos(ClienteRequestDTO dto, Cliente entity){
        String email = dto.getEmail().trim();

        if (repo.existeEmail(email, entity.getId_cliente())) {
            throw new BusinessRuleException("Ya existe un cliente con el email " + email);
        }

        String direccion = dto.getDireccion() != null && !dto.getDireccion().isBlank()
                ? dto.getDireccion().trim() : DIRECCION_DEFAULT;

        entity.setNombre(dto.getNombre().trim());
        entity.setApellido(dto.getApellido().trim());
        entity.setTelefono(dto.getTelefono().trim());
        entity.setEmail(email);
        entity.setDireccion(direccion);
    }

    private ClienteResponseDTO convertirADTOResponse(Cliente c){
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId_cliente(c.getId_cliente());
        dto.setNombre(c.getNombre());
        dto.setApellido(c.getApellido());
        dto.setTelefono(c.getTelefono());
        dto.setEmail(c.getEmail());
        dto.setDireccion(c.getDireccion());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerTodos(){
        List<Cliente> entidades = repo.findAll();

        List<ClienteResponseDTO> dtos = new ArrayList<>();

        for(Cliente entity : entidades){
            dtos.add(convertirADTOResponse(entity));
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorId (Long id){
        Cliente entidad = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No se encontro el cliente con id " + id));
        return convertirADTOResponse(entidad);
    }

    @Transactional
    public ClienteResponseDTO nuevoCliente (ClienteRequestDTO dto){
        Cliente entidad = new Cliente();
        copiarDatos(dto, entidad);
        return convertirADTOResponse(repo.saveAndFlush(entidad));
    }

    @Transactional
    public ClienteResponseDTO actualizarData(ClienteRequestDTO dto, Long id) {
        Cliente entidad = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No se encontro el cliente con id " + id));
        copiarDatos(dto, entidad);
        return convertirADTOResponse(repo.saveAndFlush(entidad));
    }

    @Transactional
    public void eliminar (Long id){
        if (!repo.existsById(id)){
            throw new DataNotFoundException("No se encontro el cliente con id " + id);
        }
        if (reservaRepo.existenReservasDeCliente(id)){
            throw new BusinessRuleException("No se puede eliminar el cliente porque tiene reservas registradas");
        }
        repo.deleteById(id);
    }
}
