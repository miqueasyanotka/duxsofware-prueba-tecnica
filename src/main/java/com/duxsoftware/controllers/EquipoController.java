package com.duxsoftware.controllers;

import com.duxsoftware.entities.EquipoEntity;
import com.duxsoftware.services.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @Operation(
            tags = "Obtener equipos",
            description = "Endpoint para obtener la lista de equipos",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EquipoEntity.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @GetMapping
    public List<EquipoEntity> obtenerTodos() {
        return equipoService.obenterEquipos();
    }


    @Operation(
            tags = "Obtener equipos",
            description = "Endpoint para obtener el equipo por su id",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EquipoEntity.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            example = "{ \"mensaje\": \"Equipo no encontrado\", \"codigo\": 404 }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerEquipo(@PathVariable Long id) {
        return equipoService.obtenerEquipoPorId(id);
    }


    @Operation(
            tags = "Obtener equipos",
            description = "Endpoint para obtener el equipo por su nombre",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EquipoEntity.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            example = "{ \"mensaje\": \"Equipo no encontrado\", \"codigo\": 404 }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/buscar")
    public ResponseEntity<Object> obtenerEquipoPorNombre(@RequestParam String nombre) {
        return equipoService.obtenerEquipoPorNombre(nombre);
    }

    @Operation(
            tags = "Guardar equipo",
            description = "Endpoint para guardar un equipo",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EquipoEntity.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            example = "{ \"mensaje\": \"La solicitud es invalida\", \"codigo\": 400 }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Object> guardarEquipo(@RequestBody EquipoEntity equipoEntity) {
        return equipoService.guardarEquipo(equipoEntity);
    }


    @Operation(
            tags = "Actualizar equipo",
            description = "Endpoint para actualizar un equipo por su id",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            example = "{ \"mensaje\": \"Equipo no encontrado\", \"codigo\": 404 }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            example = "{ \"mensaje\": \"La solicitud es invalida\", \"codigo\": 400 }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarEquipo(@PathVariable Long id, @RequestBody EquipoEntity equipoEntity) {
        return equipoService.actualizarEquipo(id, equipoEntity);
    }


    @Operation(
            tags = "Eliminar equipo",
            description = "Endpoint para eliminar un equipo por su id",
            responses = {
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            example = "{ \"mensaje\": \"Equipo no encontrado\", \"codigo\": 404 }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarEquipo(@PathVariable Long id) {
        return equipoService.eliminarEquipo(id);
    }

}
