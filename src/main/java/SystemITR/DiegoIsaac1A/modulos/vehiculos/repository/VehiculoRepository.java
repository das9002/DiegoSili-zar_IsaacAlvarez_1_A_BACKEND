package SystemITR.DiegoIsaac1A.modulos.vehiculos.repository;

import SystemITR.DiegoIsaac1A.modulos.vehiculos.model.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}
