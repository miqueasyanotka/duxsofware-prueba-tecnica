package com.duxsoftware.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "equipos")
public class EquipoEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id del equipo", example = "1", type = "Integer")
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre del equipo", example = "Real Madrid", type = "String")
    private String nombre;

    @Column(name = "liga", nullable = false)
    @Schema(description = "Liga del equipo", example = "La Liga", type = "String")
    private String liga;

    @Column(name = "pais", nullable = false)
    @Schema(description = "País del equipo", example = "España", type = "String")
    private String pais;

}
