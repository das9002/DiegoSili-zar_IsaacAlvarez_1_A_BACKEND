package SystemITR.DiegoIsaac1A.modulos.vehiculos.service;

import SystemITR.DiegoIsaac1A.exceptions.DataNotFoundException;
import SystemITR.DiegoIsaac1A.modulos.vehiculos.model.dto.VehiculoResponseDTO;
import SystemITR.DiegoIsaac1A.modulos.vehiculos.model.entity.Vehiculo;
import SystemITR.DiegoIsaac1A.modulos.vehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VehiculoService {

    private final VehiculoRepository repo;

    private VehiculoResponseDTO convertirADTOResponse(Vehiculo v){
        VehiculoResponseDTO dto = new VehiculoResponseDTO();
        dto.setId_vehiculo(v.getId_vehiculo());
        dto.setMarca(v.getMarca());
        dto.setModelo(v.getModelo());
        dto.setCapacidad(v.getCapacidad());
        dto.setPrecio_dia(v.getPrecio_dia());
        dto.setPlaca(v.getPlaca());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> obtenerTodos(){
        List<Vehiculo> entidades = repo.findAll();

        List<VehiculoResponseDTO> dtos = new ArrayList<>();

        for(Vehiculo entity : entidades){
            dtos.add(convertirADTOResponse(entity));
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public VehiculoResponseDTO obtenerPorId (Long id){
        Vehiculo entidad = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No se encontro el vehiculo con id " + id));
        return convertirADTOResponse(entidad);
    }
}
