package uni.isw.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.isw.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByIdOrdenVenta(Long id_orden_venta);
}
