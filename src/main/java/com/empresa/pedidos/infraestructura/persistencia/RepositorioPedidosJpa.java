package com.empresa.pedidos.infraestructura.persistencia;

import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.puertos.RepositorioPedidos;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia: implementa el puerto {@link RepositorioPedidos}
 * usando Spring Data JPA como mecanismo de almacenamiento.
 *
 * <p>El dominio solo conoce la interfaz {@code RepositorioPedidos}.
 * Cambiar a MongoDB o cualquier otra base implicaría únicamente crear
 * un nuevo adaptador en esta capa, sin tocar el dominio.</p>
 *
 * <p>Patrón: Adapter — traduce entre el modelo de dominio y la capa JPA.</p>
 */
@Repository
public class RepositorioPedidosJpa implements RepositorioPedidos {

    private final PedidoJpaRepository jpa;

    public RepositorioPedidosJpa(PedidoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        return jpa.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return jpa.findById(id);
    }

    @Override
    public List<Pedido> buscarPendientes() {
        return jpa.findByEstado(EstadoPedido.PENDIENTE);
    }
}
