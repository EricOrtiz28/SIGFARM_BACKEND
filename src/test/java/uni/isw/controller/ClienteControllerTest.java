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
import uni.isw.services.ClienteService;
import uni.isw.controllers.ClienteController;

@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente1, cliente2;

    @BeforeEach
    public void init() {
        cliente1 = Cliente.builder()
                .dni(12345678)
                .nombres("Ana")
                .apellidos("Garcia")
                .direccion("Calle Fuego 123")
                .telefono(921654321L)
                .correo("ana@correo.com")
                .build();

        cliente2 = Cliente.builder()
                .dni(87654321)
                .nombres("Luis")
                .apellidos("Morales")
                .direccion("Avenida Agua 456")
                .telefono(124356789L)
                .correo("luis@correo.com")
                .build();
    }

    @Test
    public void ClienteController_Insert() throws Exception {
        // Simular el método saveOrUpdateCliente que es void
        doNothing().when(clienteService).saveOrUpdateCliente(ArgumentMatchers.any(Cliente.class));

        ResultActions response = mockMvc.perform(post("/api/v1/cliente/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ClienteController_Listar() throws Exception {
        List<Cliente> clienteList = new ArrayList<>();
        clienteList.add(cliente1);
        clienteList.add(cliente2);

        when(clienteService.getClientes()).thenReturn(clienteList);

        ResultActions response = mockMvc.perform(get("/api/v1/cliente/listar")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(clienteList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }

    @Test
    public void ClienteController_Buscar() throws Exception {
        given(clienteService.getCliente(ArgumentMatchers.anyInt())).willReturn(Optional.of(cliente1));

        ResultActions response = mockMvc.perform(get("/api/v1/cliente/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni", CoreMatchers.is(cliente1.getDni())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombres", CoreMatchers.is(cliente1.getNombres())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellidos", CoreMatchers.is(cliente1.getApellidos())));
    }

    @Test
    public void ClienteController_Actualizar() throws Exception {
        // Simular el método saveOrUpdateCliente que es void
        doNothing().when(clienteService).saveOrUpdateCliente(ArgumentMatchers.any(Cliente.class));

        cliente1.setDireccion("Calle Luna 987");

        ResultActions response = mockMvc.perform(post("/api/v1/cliente/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion", CoreMatchers.is(cliente1.getDireccion())));
    }

    @Test
    public void ClienteController_Eliminar() throws Exception {
        given(clienteService.getCliente(ArgumentMatchers.anyInt())).willReturn(Optional.of(cliente1));
        doNothing().when(clienteService).deleteCliente(ArgumentMatchers.anyInt());

        ResultActions response = mockMvc.perform(delete("/api/v1/cliente/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
