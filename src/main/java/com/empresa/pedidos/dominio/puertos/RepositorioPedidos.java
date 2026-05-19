package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.Pedido;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de dominio para persistencia de pedidos.
 * El dominio solo conoce esta interfaz; la implementación concreta
 * (JPA, MongoDB, etc.) reside en la capa de infraestructura.
 */
public interface RepositorioPedidos {

    /**
     * Persiste o actualiza un pedido y retorna la instancia guardada.
     *
     * @param pedido pedido a guardar
     * @return pedido guardado con ID asignado
     */
    Pedido guardar(Pedido pedido);

    /**
     * Busca un pedido por su identificador.
     *
     * @param id identificador del pedido
     * @return Optional con el pedido si existe, vacío si no
     */
    Optional<Pedido> buscarPorId(Long id);

    /**
     * Retorna todos los pedidos en estado PENDIENTE.
     *
     * @return lista de pedidos pendientes
     */
    List<Pedido> buscarPendientes();
}
