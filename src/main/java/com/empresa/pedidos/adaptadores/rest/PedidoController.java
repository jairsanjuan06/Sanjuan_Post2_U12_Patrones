package com.empresa.pedidos.adaptadores.rest;

import com.empresa.pedidos.adaptadores.facade.FachadaPedidos;
import com.empresa.pedidos.dominio.Pedido;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la gestión de pedidos.
 *
 * <p>Única dependencia: {@link FachadaPedidos}. El controlador no conoce
 * factories, repositorios ni servicios internos, lo que mantiene el
 * acoplamiento de la capa web al mínimo necesario.</p>
 *
 * <p>Patrón: Facade — el controlador actúa como cliente de la fachada.</p>
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final FachadaPedidos fachada;

    public PedidoController(FachadaPedidos fachada) {
        this.fachada = fachada;
    }

    /**
     * POST /api/pedidos — crea y procesa un pedido nuevo.
     *
     * @param pedido datos del pedido en el body (JSON)
     * @return pedido procesado con costo y estado
     */
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(fachada.crearPedido(pedido));
    }

    /**
     * GET /api/pedidos/{id} — obtiene un pedido por ID.
     *
     * @param id identificador del pedido
     * @return 200 con el pedido o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long id) {
        return fachada.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/pedidos/pendientes — lista pedidos en estado PENDIENTE.
     *
     * @return lista de pedidos pendientes
     */
    @GetMapping("/pendientes")
    public ResponseEntity<List<Pedido>> pendientes() {
        return ResponseEntity.ok(fachada.listarPendientes());
    }
}
