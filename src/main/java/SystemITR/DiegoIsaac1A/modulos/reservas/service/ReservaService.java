package SystemITR.DiegoIsaac1A.modulos.reservas.service;

import SystemITR.DiegoIsaac1A.exceptions.BusinessRuleException;
import SystemITR.DiegoIsaac1A.exceptions.DataNotFoundException;
import SystemITR.DiegoIsaac1A.modulos.clientes.model.entity.Cliente;
import SystemITR.DiegoIsaac1A.modulos.clientes.repository.ClienteRepository;
import SystemITR.DiegoIsaac1A.modulos.reservas.model.dto.ReservaRequestDTO;
import SystemITR.DiegoIsaac1A.modulos.reservas.model.dto.ReservaResponseDTO;
import SystemITR.DiegoIsaac1A.modulos.reservas.model.entity.Reserva;
import SystemITR.DiegoIsaac1A.modulos.reservas.repository.ReservaRepository;
import SystemITR.DiegoIsaac1A.modulos.vehiculos.model.entity.Vehiculo;
import SystemITR.DiegoIsaac1A.modulos.vehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservaService {

    private final ReservaRepository repo;
    private final ClienteRepository clienteRepo;
    private final VehiculoRepository vehiculoRepo;

    private static final String ESTADO_INICIAL = "CONFIRMADA";
    private static final List<String> ESTADOS_ACTIVOS = List.of("PENDIENTE", "CONFIRMADA");
    private static final BigDecimal TOTAL_MAXIMO = new BigDecimal("999999.99");

    private void copiarDatos(ReservaRequestDTO dto, Reserva entity){
        Cliente cliente = clienteRepo.findById(dto.getId_cliente())
                .orElseThrow(() -> new DataNotFoundException("No se encontro el cliente con id " + dto.getId_cliente()));
        Vehiculo vehiculo = vehiculoRepo.findById(dto.getId_vehiculo())
                .orElseThrow(() -> new DataNotFoundException("No se encontro el vehiculo con id " + dto.getId_vehiculo()));

        if (dto.getCantidad_pasajeros() > vehiculo.getCapacidad()) {
            throw new BusinessRuleException("La cantidad de pasajeros (" + dto.getCantidad_pasajeros()
                    + ") supera la capacidad maxima del vehiculo (" + vehiculo.getCapacidad() + ")");
        }

        String nombre = dto.getNombre_reserva().trim();

        if (repo.existeReservaDuplicada(cliente.getId_cliente(), nombre, dto.getFecha_reserva(), entity.getId_reserva())) {
            throw new BusinessRuleException("El cliente ya tiene una reserva con ese nombre en la fecha " + dto.getFecha_reserva());
        }

        String estado;
        if (entity.getId_reserva() == null) {
            estado = ESTADO_INICIAL;
        } else {
            estado = dto.getEstado() != null ? dto.getEstado() : entity.getEstado();
        }

        if (ESTADOS_ACTIVOS.contains(estado)
                && repo.existeReservaActiva(vehiculo.getId_vehiculo(), dto.getFecha_reserva(), ESTADOS_ACTIVOS, entity.getId_reserva())) {
            throw new BusinessRuleException("El vehiculo ya tiene una reserva activa en la fecha " + dto.getFecha_reserva());
        }

        BigDecimal total = vehiculo.getPrecio_dia().multiply(BigDecimal.valueOf(dto.getCantidad_dias()));

        if (total.compareTo(TOTAL_MAXIMO) > 0) {
            throw new BusinessRuleException("El total a pagar supera el maximo permitido de " + TOTAL_MAXIMO);
        }

        entity.setCliente(cliente);
        entity.setVehiculo(vehiculo);
        entity.setNombre_reserva(nombre);
        entity.setFecha_reserva(dto.getFecha_reserva());
        entity.setCantidad_pasajeros(dto.getCantidad_pasajeros());
        entity.setCantidad_dias(dto.getCantidad_dias());
        entity.setEstado(estado);
        entity.setTotal_pago(total);
    }

    private ReservaResponseDTO convertirADTOResponse(Reserva r){
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId_reserva(r.getId_reserva());
        dto.setId_cliente(r.getCliente().getId_cliente());
        dto.setNombre_cliente(r.getCliente().getNombre() + " " + r.getCliente().getApellido());
        dto.setId_vehiculo(r.getVehiculo().getId_vehiculo());
        dto.setVehiculo(r.getVehiculo().getMarca() + " " + r.getVehiculo().getModelo() + " (" + r.getVehiculo().getPlaca() + ")");
        dto.setNombre_reserva(r.getNombre_reserva());
        dto.setFecha_reserva(r.getFecha_reserva());
        dto.setCantidad_pasajeros(r.getCantidad_pasajeros());
        dto.setCantidad_dias(r.getCantidad_dias());
        dto.setEstado(r.getEstado());
        dto.setTotal_pago(r.getTotal_pago());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> obtenerTodos(){
        List<Reserva> entidades = repo.findAll();

        List<ReservaResponseDTO> dtos = new ArrayList<>();

        for(Reserva entity : entidades){
            dtos.add(convertirADTOResponse(entity));
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public ReservaResponseDTO obtenerPorId (Long id){
        Reserva entidad = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No se encontro la reserva con id " + id));
        return convertirADTOResponse(entidad);
    }

    @Transactional
    public ReservaResponseDTO nuevaReserva (ReservaRequestDTO dto){
        Reserva entidad = new Reserva();
        copiarDatos(dto, entidad);
        return convertirADTOResponse(repo.saveAndFlush(entidad));
    }

    @Transactional
    public ReservaResponseDTO actualizarData(ReservaRequestDTO dto, Long id) {
        Reserva entidad = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No se encontro la reserva con id " + id));
        copiarDatos(dto, entidad);
        return convertirADTOResponse(repo.saveAndFlush(entidad));
    }

    @Transactional
    public void eliminar (Long id){
        if (!repo.existsById(id)){
            throw new DataNotFoundException("No se encontro la reserva con id " + id);
        }
        repo.deleteById(id);
    }
}
