package com.empresa.pedidos.aplicacion;

import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.puertos.RepositorioPedidos;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioPedidos {

    private final ProcesadorPedidoFactory factory;
    private final RepositorioPedidos repositorio;
    private final ApplicationEventPublisher publisher;

    public ServicioPedidos(ProcesadorPedidoFactory factory,
            RepositorioPedidos repositorio,
            ApplicationEventPublisher publisher) {
        this.factory = factory;
        this.repositorio = repositorio;
        this.publisher = publisher;
    }

    /**
     * Procesa y persiste un pedido, luego publica el evento de dominio.
     *
     * @param pedido pedido a procesar
     * @return pedido guardado con costo y estado actualizados
     */
    public Pedido procesarPedido(Pedido pedido) {
        factory.obtener(pedido.getTipo()).procesar(pedido);
        Pedido guardado = repositorio.guardar(pedido);
        publisher.publishEvent(new PedidoProcesadoEvent(guardado));
        return guardado;
    }

    /**
     * Busca un pedido por ID.
     *
     * @param id identificador del pedido
     * @return Optional con el pedido si existe
     */
    public Optional<Pedido> buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    /**
     * Retorna todos los pedidos en estado PENDIENTE.
     *
     * @return lista de pedidos pendientes
     */
    public List<Pedido> listarPendientes() {
        return repositorio.buscarPendientes();
    }
}
