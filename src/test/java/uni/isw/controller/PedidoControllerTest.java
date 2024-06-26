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
import uni.isw.model.OrdenVenta;
import uni.isw.model.Pedido;
import uni.isw.services.PedidoService;
import uni.isw.controllers.PedidoController;

@WebMvcTest(controllers = PedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido pedido1, pedido2;
    private OrdenVenta ordenVenta;
    private Inventario inventario;

    @BeforeEach
    public void init() {
        ordenVenta = new OrdenVenta(1L, 12345678, true, null);
        inventario = new Inventario(1L, "Paracetamol", 100, 1.5, "A1");

        pedido1 = new Pedido(1L, 1L, 1L, 10, 1.5, ordenVenta, inventario);
        pedido2 = new Pedido(2L, 1L, 1L, 20, 1.5, ordenVenta, inventario);
    }

    @Test
    public void PedidoController_Insert() throws Exception {
        // Simular el método saveOrUpdatePedido que es void
        doNothing().when(pedidoService).saveOrUpdatePedido(ArgumentMatchers.any(Pedido.class));

        ResultActions response = mockMvc.perform(post("/api/v1/pedido/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void PedidoController_Listar() throws Exception {
        List<Pedido> pedidoList = new ArrayList<>();
        pedidoList.add(pedido1);
        pedidoList.add(pedido2);

        when(pedidoService.getPedidos()).thenReturn(pedidoList);

        ResultActions response = mockMvc.perform(get("/api/v1/pedido/listar")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(pedidoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }

    @Test
    public void PedidoController_Buscar() throws Exception {
        given(pedidoService.getPedido(ArgumentMatchers.anyLong())).willReturn(Optional.of(pedido1));

        ResultActions response = mockMvc.perform(get("/api/v1/pedido/search")
                .param("id_pedido", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_pedido", CoreMatchers.is(pedido1.getId_pedido().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cantidad", CoreMatchers.is(pedido1.getCantidad())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_unitario", CoreMatchers.is(pedido1.getPrecio_unitario())));
    }

    @Test
    public void PedidoController_Actualizar() throws Exception {
        // Simular el método saveOrUpdatePedido que es void
        doNothing().when(pedidoService).saveOrUpdatePedido(ArgumentMatchers.any(Pedido.class));

        pedido1.setCantidad(15);

        ResultActions response = mockMvc.perform(post("/api/v1/pedido/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cantidad", CoreMatchers.is(pedido1.getCantidad())));
    }

    @Test
    public void PedidoController_Eliminar() throws Exception {
        given(pedidoService.getPedido(ArgumentMatchers.anyLong())).willReturn(Optional.of(pedido1));
        doNothing().when(pedidoService).deletePedido(ArgumentMatchers.anyLong());

        ResultActions response = mockMvc.perform(delete("/api/v1/pedido/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void PedidoController_ListarPorOrdenVenta() throws Exception {
        List<Pedido> pedidoList = new ArrayList<>();
        pedidoList.add(pedido1);
        pedidoList.add(pedido2);

        when(pedidoService.getPedidosPorOrdenVenta(ArgumentMatchers.anyLong())).thenReturn(pedidoList);

        ResultActions response = mockMvc.perform(get("/api/v1/pedido/listarPorOrdenVenta/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(pedidoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }
}

