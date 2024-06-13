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
import uni.isw.model.Pedido;
import uni.isw.services.PedidoService;

@RestController
@RequestMapping(path = "api/v1/pedido")
public class PedidoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PedidoService pedidoService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pedido>> getPedidos() {
        List<Pedido> listaPedidos = null;
        try {
            listaPedidos = pedidoService.getPedidos();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(listaPedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> searchPedido(@RequestBody Optional<Pedido> pedido) {
        try {
            pedido = pedidoService.getPedido(pedido.get().getId_pedido());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> insertPedido(@RequestBody Pedido pedido) {
        try {
            pedidoService.saveOrUpdatePedido(pedido);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido) {
        try {
            pedidoService.saveOrUpdatePedido(pedido);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> deletePedido(@RequestBody Optional<Pedido> pedido) {
        try {
            pedido = pedidoService.getPedido(pedido.get().getId_pedido());
            if (pedido.isPresent())
                pedidoService.deletePedido(pedido.get().getId_pedido());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
    }

@RequestMapping(value = "/listarPorOrdenVenta/{id_orden_venta}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Pedido>> getPedidosPorOrdenVenta(@PathVariable Long id_orden_venta) {
    List<Pedido> listaPedidos = null;
    try {
        listaPedidos = pedidoService.getPedidosPorOrdenVenta(id_orden_venta);
    } catch (Exception e) {
        logger.error("Error inesperado", e);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(listaPedidos, HttpStatus.OK);
}

}

