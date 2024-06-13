package uni.isw.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.Pedido;
import uni.isw.repositories.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Obtener todos los pedidos
    public List<Pedido> getPedidos() {
        return pedidoRepository.findAll();
    }

    // Obtener un pedido por ID
    public Optional<Pedido> getPedido(Long id_pedido) {
        return pedidoRepository.findById(id_pedido);
    }

    // Guardar o actualizar un pedido
    public void saveOrUpdatePedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    // Eliminar un pedido por ID
    public void deletePedido(Long id_pedido) {
        pedidoRepository.deleteById(id_pedido);
    }
    // Obtener todos los pedidos por id_orden_venta
    public List<Pedido> getPedidosPorOrdenVenta(Long id_orden_venta) {
        return pedidoRepository.findByIdOrdenVenta(id_orden_venta);
    }
}


