package SystemITR.DiegoIsaac1A.modulos.reservas.repository;

import SystemITR.DiegoIsaac1A.modulos.reservas.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.vehiculo.id_vehiculo = :idVehiculo AND r.fecha_reserva = :fecha AND r.estado IN :estados AND (:idReserva IS NULL OR r.id_reserva <> :idReserva)")
    boolean existeReservaActiva(@Param("idVehiculo") Long idVehiculo,
                                @Param("fecha") LocalDate fecha,
                                @Param("estados") Collection<String> estados,
                                @Param("idReserva") Long idReserva);

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.cliente.id_cliente = :idCliente AND r.nombre_reserva = :nombre AND r.fecha_reserva = :fecha AND (:idReserva IS NULL OR r.id_reserva <> :idReserva)")
    boolean existeReservaDuplicada(@Param("idCliente") Long idCliente,
                                   @Param("nombre") String nombre,
                                   @Param("fecha") LocalDate fecha,
                                   @Param("idReserva") Long idReserva);

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.cliente.id_cliente = :idCliente")
    boolean existenReservasDeCliente(@Param("idCliente") Long idCliente);
}
