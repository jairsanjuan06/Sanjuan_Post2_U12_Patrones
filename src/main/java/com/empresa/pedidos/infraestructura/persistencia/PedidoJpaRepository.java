package com.empresa.pedidos.infraestructura.persistencia;

import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA interno.
 * Solo debe ser conocido por {@link RepositorioPedidosJpa}.
 * El dominio nunca importa esta interfaz directamente.
 */
public interface PedidoJpaRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(EstadoPedido estado);
}
