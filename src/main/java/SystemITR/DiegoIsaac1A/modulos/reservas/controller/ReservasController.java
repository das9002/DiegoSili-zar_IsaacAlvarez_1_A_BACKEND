package SystemITR.DiegoIsaac1A.modulos.reservas.controller;

import SystemITR.DiegoIsaac1A.exceptions.BusinessRuleException;
import SystemITR.DiegoIsaac1A.exceptions.DataNotFoundException;
import SystemITR.DiegoIsaac1A.modulos.reservas.model.dto.ReservaRequestDTO;
import SystemITR.DiegoIsaac1A.modulos.reservas.model.dto.ReservaResponseDTO;
import SystemITR.DiegoIsaac1A.modulos.reservas.service.ReservaService;
import SystemITR.DiegoIsaac1A.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservas")
public class ReservasController {

    private final ReservaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservaResponseDTO>>> obtenerDatos (){
        try{
            List<ReservaResponseDTO> lista = service.obtenerTodos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Se han obtenido los datos con exito", lista));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudieron obtener los datos"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservaResponseDTO>> obtenerDatosPorId(@PathVariable Long id){
        try{
            ReservaResponseDTO dto = service.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Se han obtenido los datos con exito", dto));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudieron obtener los datos"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReservaResponseDTO>> nuevaReserva(@Valid @RequestBody ReservaRequestDTO json) {
        try {
            ReservaResponseDTO dto = service.nuevaReserva(json);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Dato creado con exito", dto));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (BusinessRuleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Los datos de la reserva no cumplen las restricciones de la base de datos"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo crear el dato"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservaResponseDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody ReservaRequestDTO dto){
        try {
            ReservaResponseDTO data = service.actualizarData(dto, id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Dato actualizado con exito", data));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (BusinessRuleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Los datos de la reserva no cumplen las restricciones de la base de datos"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo actualizar el dato"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminarDatos(@PathVariable Long id){
        try {
            service.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Dato eliminado con exito"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo eliminar el dato"));
        }
    }
}
