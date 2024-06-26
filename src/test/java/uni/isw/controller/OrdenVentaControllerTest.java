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
import uni.isw.model.Cliente;
import uni.isw.model.OrdenVenta;
import uni.isw.services.OrdenVentaService;
import uni.isw.controllers.OrdenVentaController;

@WebMvcTest(controllers = OrdenVentaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OrdenVentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenVentaService ordenVentaService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrdenVenta ordenVenta1, ordenVenta2;
    private Cliente cliente;

    @BeforeEach
    public void init() {
        cliente = Cliente.builder()
                .dni(12345678)
                .nombres("Ana")
                .apellidos("Garcia")
                .direccion("Calle Fuego 123")
                .telefono(921654321L)
                .correo("ana@correo.com")
                .build();

        ordenVenta1 = OrdenVenta.builder()
                .id_orden_venta(1L)
                .dni(12345678)
                .estado(true)
                .cliente(cliente)
                .build();

        ordenVenta2 = OrdenVenta.builder()
                .id_orden_venta(2L)
                .dni(12345678)
                .estado(false)
                .cliente(cliente)
                .build();
    }

    @Test
    public void OrdenVentaController_Insert() throws Exception {
        // Simular el método saveOrUpdateOrdenVenta que es void
        doNothing().when(ordenVentaService).saveOrUpdateOrdenVenta(ArgumentMatchers.any(OrdenVenta.class));

        ResultActions response = mockMvc.perform(post("/api/v1/orden_venta/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordenVenta1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void OrdenVentaController_Listar() throws Exception {
        List<OrdenVenta> ordenVentaList = new ArrayList<>();
        ordenVentaList.add(ordenVenta1);
        ordenVentaList.add(ordenVenta2);

        when(ordenVentaService.getOrdenesVenta()).thenReturn(ordenVentaList);

        ResultActions response = mockMvc.perform(get("/api/v1/orden_venta/listar")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(ordenVentaList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }

    @Test
    public void OrdenVentaController_Buscar() throws Exception {
        given(ordenVentaService.getOrdenVenta(ArgumentMatchers.anyLong())).willReturn(Optional.of(ordenVenta1));

        ResultActions response = mockMvc.perform(post("/api/v1/orden_venta/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordenVenta1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_orden_venta", CoreMatchers.is(ordenVenta1.getId_orden_venta().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni", CoreMatchers.is(ordenVenta1.getDni())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado", CoreMatchers.is(ordenVenta1.getEstado())));
    }

    @Test
    public void OrdenVentaController_Actualizar() throws Exception {
        // Simular el método saveOrUpdateOrdenVenta que es void
        doNothing().when(ordenVentaService).saveOrUpdateOrdenVenta(ArgumentMatchers.any(OrdenVenta.class));

        ordenVenta1.setEstado(false);

        ResultActions response = mockMvc.perform(post("/api/v1/orden_venta/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordenVenta1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado", CoreMatchers.is(ordenVenta1.getEstado())));
    }

    @Test
    public void OrdenVentaController_Eliminar() throws Exception {
        given(ordenVentaService.getOrdenVenta(ArgumentMatchers.anyLong())).willReturn(Optional.of(ordenVenta1));
        doNothing().when(ordenVentaService).deleteOrdenVenta(ArgumentMatchers.anyLong());

        ResultActions response = mockMvc.perform(delete("/api/v1/orden_venta/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordenVenta1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
