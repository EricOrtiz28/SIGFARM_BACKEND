package uni.isw.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.isw.model.Inventario;
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}

