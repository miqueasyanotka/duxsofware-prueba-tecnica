package com.duxsoftware.services;

import com.duxsoftware.entities.EquipoEntity;
import com.duxsoftware.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public ResponseEntity<Object> guardarEquipo(EquipoEntity equipoEntity) {
        try {
            EquipoEntity equipoEntityGuardado = equipoRepository.save(equipoEntity);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(equipoEntityGuardado);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("mensaje", "La solicitud es inválida");
            errorBody.put("codigo", HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorBody);
        }
    }

    @Override
    public List<EquipoEntity> obenterEquipos() {
        return equipoRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> obtenerEquipoPorId(Long id) {
        Optional<EquipoEntity> equipoOptional = equipoRepository.findById(id);

        if (equipoOptional.isPresent()){
            return buildSuccessResponse(HttpStatus.OK, equipoOptional.get());

        } else {
            return buildSNotFoundResponse(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> obtenerEquipoPorNombre(String nombre) {
        Optional<EquipoEntity> equipoOptional = equipoRepository.findEquipoEntityByNombre(nombre);

        if (equipoOptional.isPresent()){
            return buildSuccessResponse(HttpStatus.OK, equipoOptional.get());

        } else {
            return buildSNotFoundResponse(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> actualizarEquipo(Long id, EquipoEntity equipoEntityActualizado) {

        Optional<EquipoEntity> equipoOptional = equipoRepository.findById(id);

        if (equipoOptional.isPresent()){
            EquipoEntity equipoEntityExistente = equipoOptional.get();

            equipoEntityExistente.setNombre(equipoEntityActualizado.getNombre());
            equipoEntityExistente.setPais(equipoEntityActualizado.getPais());
            equipoEntityExistente.setLiga(equipoEntityActualizado.getLiga());

            EquipoEntity equipoEntityGuardado = equipoRepository.save(equipoEntityExistente);

            return buildSuccessResponse(HttpStatus.OK, equipoEntityGuardado);

        } else {
            return buildSNotFoundResponse(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> eliminarEquipo(Long id) {
        Optional<EquipoEntity> equipoOptional = equipoRepository.findById(id);

        if (equipoOptional.isPresent()) {
            EquipoEntity equipoEntityExistente = equipoOptional.get();
            equipoRepository.delete(equipoEntityExistente);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } else {
            return buildSNotFoundResponse(HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Object> buildSuccessResponse(HttpStatus status,EquipoEntity equipoEntity) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", equipoEntity.getId());
        responseBody.put("nombre", equipoEntity.getNombre());
        responseBody.put("liga", equipoEntity.getLiga());
        responseBody.put("pais", equipoEntity.getPais());

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody);
    }

    private ResponseEntity<Object> buildSNotFoundResponse(HttpStatus status) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("mensaje", "Equipo no encontrado");
        errorBody.put("codigo", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorBody);
    }

}