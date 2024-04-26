package com.duxsoftware.services;

import com.duxsoftware.entities.EquipoEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipoService {

    ResponseEntity<Object> guardarEquipo(EquipoEntity equipoEntity);
    List<EquipoEntity> obenterEquipos();
    ResponseEntity<Object> obtenerEquipoPorId(Long id);
    ResponseEntity<Object> obtenerEquipoPorNombre(String nombre);
    ResponseEntity<Object> actualizarEquipo(Long id, EquipoEntity equipoEntity);
    ResponseEntity<Object> eliminarEquipo(Long id);

}
