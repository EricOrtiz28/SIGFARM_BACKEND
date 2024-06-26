package uni.isw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uni.isw.model.Inventario;
import uni.isw.services.InventarioService;
import uni.isw.controllers.InventarioController;

@WebMvcTest(controllers = InventarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Inventario inventario1, inventario2;

    @BeforeEach
    public void init() {
        inventario1 = Inventario.builder()
                .id_medicamento(1L)
                .nombre_medicamento("Paracetamol")
                .stock(100)
                .precio_unitario(1.5)
                .ubicacion_del_medicamento("A1")
                .build();

        inventario2 = Inventario.builder()
                .id_medicamento(2L)
                .nombre_medicamento("Ibuprofeno")
                .stock(50)
                .precio_unitario(2.0)
                .ubicacion_del_medicamento("B2")
                .build();
    }

    @Test
    public void InventarioController_Insert() throws Exception {
        // Simular el método saveOrUpdateInventario que es void
        doNothing().when(inventarioService).saveOrUpdateInventario(ArgumentMatchers.any(Inventario.class));

        ResultActions response = mockMvc.perform(post("/api/v1/inventario/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inventario1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void InventarioController_Listar() throws Exception {
        List<Inventario> inventarioList = new ArrayList<>();
        inventarioList.add(inventario1);
        inventarioList.add(inventario2);

        when(inventarioService.getInventarios()).thenReturn(inventarioList);

        ResultActions response = mockMvc.perform(get("/api/v1/inventario/listar")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(inventarioList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }

    @Test
    public void InventarioController_Buscar() throws Exception {
        given(inventarioService.getInventario(ArgumentMatchers.anyLong())).willReturn(Optional.of(inventario1));

        ResultActions response = mockMvc.perform(get("/api/v1/inventario/search")
                .param("id_medicamento", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inventario1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_medicamento", CoreMatchers.is(inventario1.getId_medicamento().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre_medicamento", CoreMatchers.is(inventario1.getNombre_medicamento())));
    }

    @Test
    public void InventarioController_Actualizar() throws Exception {
        // Simular el método saveOrUpdateInventario que es void
        doNothing().when(inventarioService).saveOrUpdateInventario(ArgumentMatchers.any(Inventario.class));

        inventario1.setUbicacion_del_medicamento("C3");

        ResultActions response = mockMvc.perform(post("/api/v1/inventario/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inventario1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ubicacion_del_medicamento", CoreMatchers.is(inventario1.getUbicacion_del_medicamento())));
    }

    @Test
    public void InventarioController_Eliminar() throws Exception {
        given(inventarioService.getInventario(ArgumentMatchers.anyLong())).willReturn(Optional.of(inventario1));
        doNothing().when(inventarioService).deleteInventario(ArgumentMatchers.anyLong());

        ResultActions response = mockMvc.perform(delete("/api/v1/inventario/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inventario1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}

