package uni.isw.controllers;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.isw.model.Cliente;
import uni.isw.services.ClienteService;

@RestController
@RequestMapping(path = "api/v1/cliente")
public class ClienteController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> listaClientes = null;
        try {
            listaClientes = clienteService.getClientes();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listaClientes, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> searchCliente(@RequestBody Optional<Cliente> cliente) {
        try {
            cliente = clienteService.getCliente(cliente.get().getDni());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> insertCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.saveOrUpdateCliente(cliente);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.saveOrUpdateCliente(cliente);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> deleteCliente(@RequestBody Optional<Cliente> cliente) {
        try {
            cliente = clienteService.getCliente(cliente.get().getDni());
            if (cliente.isPresent())
                clienteService.deleteCliente(cliente.get().getDni());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
    }
}
