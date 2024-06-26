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
import uni.isw.repositories.ClienteRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada test
        clienteRepository.deleteAll();

        // Preparar datos de prueba comunes a todos los tests
        Cliente cliente1 = Cliente.builder()
                .dni(12345678)
                .nombres("Ana")
                .apellidos("Garcia")
                .direccion("Calle Fuego 123")
                .telefono(921654321L)
                .correo("ana@correo.com")
                .build();
        
        Cliente cliente2 = Cliente.builder()
                .dni(87654321)
                .nombres("Luis")
                .apellidos("Morales")
                .direccion("Avenida Agua 456")
                .telefono(124356789L)
                .correo("luis@correo.com")
                .build();

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
    }

    @Test
    public void ClienteRepository_Listar() {
        List<Cliente> clientes = clienteRepository.findAll();
        Assertions.assertThat(clientes).isNotNull();
        Assertions.assertThat(clientes.size()).isEqualTo(2);
    }

    @Test
    public void ClienteRepository_Insert() {
        Cliente cliente = Cliente.builder()
                .dni(33445566)
                .nombres("Carlos")
                .apellidos("Gomez")
                .direccion("Calle Tierra 789")
                .telefono(654321987L)
                .correo("carlos@correo.com")
                .build();
        
        Cliente newCliente = clienteRepository.save(cliente);
        
        Assertions.assertThat(newCliente).isNotNull();
        Assertions.assertThat(newCliente.getDni()).isEqualTo(33445566);
    }
    
    @Test
    public void ClienteRepository_Buscar() {
    Cliente cliente = clienteRepository.findById(12345678).orElse(null);
    assertNotNull(cliente);
    Assertions.assertThat(cliente.getDni()).isEqualTo(12345678);
    Assertions.assertThat(cliente.getNombres()).isEqualTo("Ana");
    Assertions.assertThat(cliente.getApellidos()).isEqualTo("Garcia");
    }


    @Test
    public void ClienteRepository_Actualizar() {
        Cliente cliente = clienteRepository.findById(12345678).orElse(null);
        assertNotNull(cliente);
        cliente.setDireccion("Calle Venus 123");
        
        Cliente updatedCliente = clienteRepository.save(cliente);
        Assertions.assertThat(updatedCliente.getDireccion()).isEqualTo("Calle Venus 123");
    }

    @Test
    public void ClienteRepository_Eliminar() {
        Cliente cliente = clienteRepository.findById(87654321).orElse(null);
        assertNotNull(cliente);
        
        clienteRepository.delete(cliente);
        
        Cliente deletedCliente = clienteRepository.findById(87654321).orElse(null);
        assertNull(deletedCliente);
    }
}
