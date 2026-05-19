package com.empresa.pedidos.adaptadores.facade;

import com.empresa.pedidos.aplicacion.ServicioPedidos;
import com.empresa.pedidos.dominio.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FachadaPedidos {

    private final ServicioPedidos servicio;

    public FachadaPedidos(ServicioPedidos servicio) {
        this.servicio = servicio;
    }

    /**
     * Crea y procesa un pedido completo: calcula costo, persiste y notifica.
     *
     * @param pedido pedido recibido del cliente REST
     * @return pedido guardado con costo y estado actualizados
     */
    public Pedido crearPedido(Pedido pedido) {
        return servicio.procesarPedido(pedido);
    }

    /**
     * Busca un pedido por su ID.
     *
     * @param id identificador del pedido
     * @return Optional con el pedido si existe
     */
    public Optional<Pedido> buscarPorId(Long id) {
        return servicio.buscarPorId(id);
    }

    /**
     * Lista todos los pedidos en estado PENDIENTE.
     *
     * @return lista de pedidos pendientes
     */
    public List<Pedido> listarPendientes() {
        return servicio.listarPendientes();
    }
}
