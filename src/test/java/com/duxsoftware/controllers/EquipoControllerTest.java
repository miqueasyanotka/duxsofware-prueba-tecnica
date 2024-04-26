package com.duxsoftware.controllers;

import com.duxsoftware.entities.EquipoEntity;
import com.duxsoftware.jwt.JwtService;
import com.duxsoftware.repositories.EquipoRepository;
import com.duxsoftware.services.EquipoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(EquipoController.class)
class EquipoControllerTest {

    private static final String BASE_PATH = "/equipos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private EquipoRepository equipoRepository;

    @MockBean
    private EquipoService equipoService;

    @MockBean
    private JwtService jwtService;

    private ObjectMapper objectMapper;


    private final EquipoEntity equipo1 = EquipoEntity.builder()
            .id(1L)
            .nombre("Real Madrid")
            .liga("La Liga")
            .pais("España")
            .build();

    private final EquipoEntity equipo2 = EquipoEntity.builder()
            .id(2L)
            .nombre("FC Barcelona")
            .liga("La Liga")
            .pais("España")
            .build();


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .defaultRequest(get("/").with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .defaultRequest(post(BASE_PATH).with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .alwaysDo(print())
                .build();

        objectMapper = new ObjectMapper();

    }

    @AfterEach
    void tearDown() {
        equipoRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test", password = "12345")
    void obtenerTodos() throws Exception {
        equipoRepository.save(equipo1);
        equipoRepository.save(equipo2);
        List<EquipoEntity> equipos = Arrays.asList(equipo1, equipo2);

        when(equipoService.obenterEquipos()).thenReturn(equipos);

        mockMvc.perform(
                        get(BASE_PATH).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Real Madrid"))
                .andExpect(jsonPath("$[0].liga").value("La Liga"))
                .andExpect(jsonPath("$[0].pais").value("España"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nombre").value("FC Barcelona"))
                .andExpect(jsonPath("$[1].liga").value("La Liga"))
                .andExpect(jsonPath("$[1].pais").value("España"));

        verify(equipoService).obenterEquipos();
    }


    @Test
    @WithMockUser(username = "test", password = "12345")
    void obtenerEquipo() throws Exception {
        equipoRepository.save(equipo1);

        when(equipoService.obtenerEquipoPorId(1L)).thenReturn(
                ResponseEntity.of(Optional.of(equipo1))
        );

        mockMvc.perform(
                        get(BASE_PATH + "/{id}", equipo1.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Real Madrid"))
                .andExpect(jsonPath("$.liga").value("La Liga"))
                .andExpect(jsonPath("$.pais").value("España"));

        verify(equipoService).obtenerEquipoPorId(1L);
    }


    @Test
    @WithMockUser(username = "test", password = "12345")
    void actualizarEquipo() throws Exception {
        equipoRepository.save(equipo1);

        EquipoEntity equipo3 = EquipoEntity.builder()
                .nombre("Barcelona")
                .liga("La Liga")
                .pais("España")
                .build();

        when(equipoService.actualizarEquipo(1L, equipo3)).thenReturn(
                ResponseEntity.of(Optional.of(equipo1))
        );

        String equipo3Json = objectMapper.writeValueAsString(equipo3);

        mockMvc.perform(
                        put(BASE_PATH + "/{id}", equipo1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(equipo3Json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Real Madrid"))
                .andExpect(jsonPath("$.liga").value("La Liga"))
                .andExpect(jsonPath("$.pais").value("España"));

        verify(equipoService).actualizarEquipo(1L, equipo3);
    }

    @Test
    @WithMockUser(username = "test", password = "12345")
    public void obtenerEquipoPorNombre() throws Exception {
        EquipoEntity equipo5 = EquipoEntity.builder()
                .id(1L)
                .nombre("Juventus FC")
                .liga("Serie A")
                .pais("Italia")
                .build();

        equipoRepository.save(equipo5);
        when(equipoService.obtenerEquipoPorNombre("Juventus FC"))
                .thenReturn(ResponseEntity.ok(equipo5));

        mockMvc.perform(get(BASE_PATH + "/buscar")
                                .param("nombre", "Juventus FC")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juventus FC"))
                .andExpect(jsonPath("$.liga").value("Serie A"))
                .andExpect(jsonPath("$.pais").value("Italia"));

        verify(equipoService).obtenerEquipoPorNombre("Juventus FC");

    }


    @Test
    @WithMockUser(username = "test", password = "12345")
    public void crearEquipo() throws Exception {
        EquipoEntity nuevoEquipo = EquipoEntity.builder()
                .nombre("Chelsea")
                .liga("Premier League")
                .pais("Inglaterra")
                .build();

        String nuevoEquipoJson = objectMapper.writeValueAsString(nuevoEquipo);

        Mockito.when(equipoService.guardarEquipo(any()))
                .thenReturn(ResponseEntity.of(Optional.of(nuevoEquipo)));

        mockMvc.perform(post(BASE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(nuevoEquipoJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Chelsea"))
                .andExpect(jsonPath("$.liga").value("Premier League"))
                .andExpect(jsonPath("$.pais").value("Inglaterra"));

        verify(equipoService).guardarEquipo(nuevoEquipo);
    }

    @Test
    @WithMockUser(username = "test", password = "12345")
    public void eliminarEquipo() throws Exception {

        equipoRepository.save(equipo1);

        when(equipoService.eliminarEquipo(any()))
                .thenReturn(ResponseEntity.of(Optional.of(false)));

        mockMvc.perform(delete(BASE_PATH + "/{id}", equipo1.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(equipoService).eliminarEquipo(equipo1.getId());
    }


}