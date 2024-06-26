package uni.isw.repository;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uni.isw.model.Pedido;
import uni.isw.model.OrdenVenta;
import uni.isw.model.Inventario;
import uni.isw.model.Cliente;
import uni.isw.repositories.PedidoRepository;
import uni.isw.repositories.OrdenVentaRepository;
import uni.isw.repositories.InventarioRepository;
import uni.isw.repositories.ClienteRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private OrdenVentaRepository ordenVentaRepository;

    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    private OrdenVenta ordenVenta;
    private Inventario inventario;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada test
        pedidoRepository.deleteAll();
        ordenVentaRepository.deleteAll();
        inventarioRepository.deleteAll();
        clienteRepository.deleteAll();

        // Crear un cliente necesario para la orden de venta
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setNombres("John");
        cliente.setApellidos("Doe");
        cliente.setDireccion("123 Main St");
        cliente.setTelefono(5551234L);
        cliente.setCorreo("john.doe@example.com");
        clienteRepository.save(cliente);

        // Preparar datos de prueba comunes a todos los tests
        ordenVenta = new OrdenVenta();
        ordenVenta.setDni(12345678);
        ordenVenta.setEstado(true);
        ordenVentaRepository.save(ordenVenta);

        inventario = new Inventario();
        inventario.setNombre_medicamento("Paracetamol");
        inventario.setStock(100);
        inventario.setPrecio_unitario(1.5);
        inventario.setUbicacion_del_medicamento("A1");
        inventarioRepository.save(inventario);

        Pedido pedido1 = new Pedido(null, ordenVenta.getId_orden_venta(), inventario.getId_medicamento(), 10, 1.5, ordenVenta, inventario);
        Pedido pedido2 = new Pedido(null, ordenVenta.getId_orden_venta(), inventario.getId_medicamento(), 5, 1.5, ordenVenta, inventario);

        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
    }

    @Test
    public void PedidoRepository_Listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        Assertions.assertThat(pedidos).isNotNull();
        Assertions.assertThat(pedidos.size()).isEqualTo(2);
    }

    @Test
    public void PedidoRepository_Insert() {
        Pedido pedido = new Pedido(null, ordenVenta.getId_orden_venta(), inventario.getId_medicamento(), 20, 1.0, ordenVenta, inventario);

        Pedido newPedido = pedidoRepository.save(pedido);
        
        Assertions.assertThat(newPedido).isNotNull();
        Assertions.assertThat(newPedido.getId_pedido()).isNotNull();
        Assertions.assertThat(newPedido.getCantidad()).isEqualTo(20);
    }

    @Test
    public void PedidoRepository_Buscar() {
        Pedido pedido = pedidoRepository.findAll().get(0);
        Optional<Pedido> foundPedido = pedidoRepository.findById(pedido.getId_pedido());
        
        Assertions.assertThat(foundPedido).isPresent();
        Assertions.assertThat(foundPedido.get().getId_pedido()).isEqualTo(pedido.getId_pedido());
    }
    
    @Test
    public void PedidoRepository_Actualizar() {
        Pedido pedido = pedidoRepository.findAll().get(0);
        pedido.setCantidad(15);

        Pedido updatedPedido = pedidoRepository.save(pedido);
        Assertions.assertThat(updatedPedido.getCantidad()).isEqualTo(15);
    }

    @Test
    public void PedidoRepository_Eliminar() {
        Pedido pedido = pedidoRepository.findAll().get(0);
        assertNotNull(pedido);

        pedidoRepository.delete(pedido);
        
        Pedido deletedPedido = pedidoRepository.findById(pedido.getId_pedido()).orElse(null);
        assertNull(deletedPedido);
    }

    @Test
    public void PedidoRepository_ListarPorOrdenVenta() {
        List<Pedido> pedidos = pedidoRepository.findByIdOrdenVenta(ordenVenta.getId_orden_venta());
        Assertions.assertThat(pedidos).isNotNull();
        Assertions.assertThat(pedidos.size()).isEqualTo(2);
    }
}
