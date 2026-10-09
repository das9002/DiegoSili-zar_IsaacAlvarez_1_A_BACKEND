package SystemITR.DiegoIsaac1A.modulos.clientes.repository;

import SystemITR.DiegoIsaac1A.modulos.clientes.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE LOWER(c.email) = LOWER(:email) AND (:idCliente IS NULL OR c.id_cliente <> :idCliente)")
    boolean existeEmail(@Param("email") String email, @Param("idCliente") Long idCliente);
}
