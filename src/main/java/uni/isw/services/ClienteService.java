package uni.isw.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.model.Cliente;
import uni.isw.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todos los clientes
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    // Obtener un cliente por DNI
    public Optional<Cliente> getCliente(Integer dni) {
        return clienteRepository.findById(dni);
    }

    // Guardar o actualizar un cliente
    public void saveOrUpdateCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    // Eliminar un cliente por DNI
    public void deleteCliente(Integer dni) {
        clienteRepository.deleteById(dni);
    }
}

