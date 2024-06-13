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
import uni.isw.model.Inventario;
import uni.isw.services.InventarioService;

@RestController
@RequestMapping(path = "api/v1/inventario")
public class InventarioController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventarioService inventarioService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Inventario>> getInventarios() {
        List<Inventario> listaInventarios = null;
        try {
            listaInventarios = inventarioService.getInventarios();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listaInventarios, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Inventario> searchInventario(@RequestBody Optional<Inventario> inventario) {
        try {
            inventario = inventarioService.getInventario(inventario.get().getId_medicamento());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(inventario.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Inventario> insertInventario(@RequestBody Inventario inventario) {
        try {
            inventarioService.saveOrUpdateInventario(inventario);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Inventario> updateInventario(@RequestBody Inventario inventario) {
        try {
            inventarioService.saveOrUpdateInventario(inventario);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Inventario> deleteInventario(@RequestBody Optional<Inventario> inventario) {
        try {
            inventario = inventarioService.getInventario(inventario.get().getId_medicamento());
            if (inventario.isPresent())
                inventarioService.deleteInventario(inventario.get().getId_medicamento());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(inventario.get(), HttpStatus.OK);
    }
}
