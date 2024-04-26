package com.duxsoftware.repositories;

import com.duxsoftware.entities.EquipoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<EquipoEntity, Long> {

    Optional<EquipoEntity> findEquipoEntityByNombre(String nombre);
}
