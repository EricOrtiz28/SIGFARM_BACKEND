package uni.isw.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.OrdenVenta;
import uni.isw.repositories.OrdenVentaRepository;

@Service
public class OrdenVentaService {

    @Autowired
    private OrdenVentaRepository ordenVentaRepository;

    // Obtener todas las órdenes de venta
    public List<OrdenVenta> getOrdenesVenta() {
        return ordenVentaRepository.findAll();
    }

    // Obtener una orden de venta por ID
    public Optional<OrdenVenta> getOrdenVenta(Long id_orden_venta) {
        return ordenVentaRepository.findById(id_orden_venta);
    }

    // Guardar o actualizar una orden de venta
    public void saveOrUpdateOrdenVenta(OrdenVenta ordenVenta) {
        ordenVentaRepository.save(ordenVenta);
    }

    // Eliminar una orden de venta por ID
    public void deleteOrdenVenta(Long id_orden_venta) {
        ordenVentaRepository.deleteById(id_orden_venta);
    }
}
