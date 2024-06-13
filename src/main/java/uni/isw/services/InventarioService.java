package uni.isw.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.Inventario;
import uni.isw.repositories.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // Obtener todos los inventarios
    public List<Inventario> getInventarios() {
        return inventarioRepository.findAll();
    }

    // Obtener un inventario por ID
    public Optional<Inventario> getInventario(Long id_medicamento) {
        return inventarioRepository.findById(id_medicamento);
    }

    // Guardar o actualizar un inventario
    public void saveOrUpdateInventario(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

    // Eliminar un inventario por ID
    public void deleteInventario(Long id_medicamento) {
        inventarioRepository.deleteById(id_medicamento);
    }
}
