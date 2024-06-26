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
import uni.isw.model.Inventario;
import uni.isw.repositories.InventarioRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InventarioRepositoryTest {

    @Autowired
    private InventarioRepository inventarioRepository;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada test
        inventarioRepository.deleteAll();

        // Preparar datos de prueba comunes a todos los tests
        Inventario inventario1 = Inventario.builder()
                .nombre_medicamento("Paracetamol")
                .stock(100)
                .precio_unitario(1.5)
                .ubicacion_del_medicamento("A1")
                .build();
        
        Inventario inventario2 = Inventario.builder()
                .nombre_medicamento("Ibuprofeno")
                .stock(50)
                .precio_unitario(2.0)
                .ubicacion_del_medicamento("B2")
                .build();

        inventarioRepository.save(inventario1);
        inventarioRepository.save(inventario2);
    }

    @Test
    public void InventarioRepository_Listar() {
        List<Inventario> inventarios = inventarioRepository.findAll();
        Assertions.assertThat(inventarios).isNotNull();
        Assertions.assertThat(inventarios.size()).isEqualTo(2);
    }

    @Test
    public void InventarioRepository_Insert() {
        Inventario inventario = Inventario.builder()
                .nombre_medicamento("Amoxicilina")
                .stock(200)
                .precio_unitario(0.75)
                .ubicacion_del_medicamento("C3")
                .build();

        Inventario newInventario = inventarioRepository.save(inventario);
        
        Assertions.assertThat(newInventario).isNotNull();
        Assertions.assertThat(newInventario.getId_medicamento()).isNotNull();
        Assertions.assertThat(newInventario.getNombre_medicamento()).isEqualTo("Amoxicilina");
    }

    @Test
    public void InventarioRepository_Buscar() {
    Inventario inventario = inventarioRepository.findById(inventarioRepository.findAll().get(0).getId_medicamento()).orElse(null);
    assertNotNull(inventario);
    Assertions.assertThat(inventario.getNombre_medicamento()).isNotNull();
    Assertions.assertThat(inventario.getStock()).isGreaterThan(0);
    }

    @Test
    public void InventarioRepository_Actualizar() {
        Inventario inventario = inventarioRepository.findAll().get(0);
        inventario.setUbicacion_del_medicamento("D4");

        Inventario updatedInventario = inventarioRepository.save(inventario);
        Assertions.assertThat(updatedInventario.getUbicacion_del_medicamento()).isEqualTo("D4");
    }

    @Test
    public void InventarioRepository_Eliminar() {
        Inventario inventario = inventarioRepository.findAll().get(0);
        assertNotNull(inventario);

        inventarioRepository.delete(inventario);
        
        Inventario deletedInventario = inventarioRepository.findById(inventario.getId_medicamento()).orElse(null);
        assertNull(deletedInventario);
    }
}
