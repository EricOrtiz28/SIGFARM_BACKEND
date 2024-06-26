package uni.isw.repository;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uni.isw.model.Cliente;
import uni.isw.model.OrdenVenta;
import uni.isw.repositories.ClienteRepository;
import uni.isw.repositories.OrdenVentaRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrdenVentaRepositoryTest {

    @Autowired
    private OrdenVentaRepository ordenVentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada test
        ordenVentaRepository.deleteAll();
        clienteRepository.deleteAll();

        // Preparar datos de prueba comunes a todos los tests
        Cliente cliente = Cliente.builder()
                .dni(12345678)
                .nombres("Ana")
                .apellidos("Garcia")
                .direccion("Calle Fuego 123")
                .telefono(921654321L)
                .correo("ana@correo.com")
                .build();
        clienteRepository.save(cliente);

        OrdenVenta ordenVenta1 = OrdenVenta.builder()
                .dni(12345678)
                .estado(true)
                .cliente(cliente)
                .build();

        OrdenVenta ordenVenta2 = OrdenVenta.builder()
                .dni(12345678)
                .estado(false)
                .cliente(cliente)
                .build();

        ordenVentaRepository.save(ordenVenta1);
        ordenVentaRepository.save(ordenVenta2);
    }

    @Test
    public void OrdenVentaRepository_Listar() {
        List<OrdenVenta> ordenesVenta = ordenVentaRepository.findAll();
        Assertions.assertThat(ordenesVenta).isNotNull();
        Assertions.assertThat(ordenesVenta.size()).isEqualTo(2);
    }

    @Test
    public void OrdenVentaRepository_Insert() {
        Cliente cliente = clienteRepository.findById(12345678).orElse(null);
        assertNotNull(cliente);

        OrdenVenta ordenVenta = OrdenVenta.builder()
                .dni(12345678)
                .estado(true)
                .cliente(cliente)
                .build();

        OrdenVenta newOrdenVenta = ordenVentaRepository.save(ordenVenta);

        Assertions.assertThat(newOrdenVenta).isNotNull();
        Assertions.assertThat(newOrdenVenta.getId_orden_venta()).isNotNull();
        Assertions.assertThat(newOrdenVenta.getEstado()).isEqualTo(true);
    }
    
    @Test
    public void OrdenVentaRepository_Buscar() {
    OrdenVenta ordenVenta = ordenVentaRepository.findById(ordenVentaRepository.findAll().get(0).getId_orden_venta()).orElse(null);
    assertNotNull(ordenVenta);
    Assertions.assertThat(ordenVenta.getDni()).isEqualTo(12345678);
    Assertions.assertThat(ordenVenta.getCliente().getNombres()).isEqualTo("Ana");
    Assertions.assertThat(ordenVenta.getEstado()).isNotNull();
    }

    @Test
    public void OrdenVentaRepository_Actualizar() {
        OrdenVenta ordenVenta = ordenVentaRepository.findAll().get(0);
        ordenVenta.setEstado(false);

        OrdenVenta updatedOrdenVenta = ordenVentaRepository.save(ordenVenta);
        Assertions.assertThat(updatedOrdenVenta.getEstado()).isEqualTo(false);
    }

    @Test
    public void OrdenVentaRepository_Eliminar() {
        OrdenVenta ordenVenta = ordenVentaRepository.findAll().get(0);
        assertNotNull(ordenVenta);

        ordenVentaRepository.delete(ordenVenta);

        OrdenVenta deletedOrdenVenta = ordenVentaRepository.findById(ordenVenta.getId_orden_venta()).orElse(null);
        assertNull(deletedOrdenVenta);
    }
}
