package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;

/**
 * Puerto Strategy para el algoritmo de procesamiento de pedidos.
 *
 * <p>Cada implementación encapsula las reglas de cálculo de costo y
 * estado para un tipo de pedido específico. La selección dinámica
 * de implementación se delega a {@code ProcesadorPedidoFactory}.</p>
 *
 * <p>Patrón: Strategy — define el <em>qué</em> se calcula,
 * desacoplado del <em>cómo</em> se selecciona.</p>
 */
public interface ProcesadorPedido {

    /**
     * Retorna el tipo de pedido que esta estrategia maneja.
     *
     * @return tipo de pedido asociado
     */
    TipoPedido getTipo();

    /**
     * Aplica las reglas de cálculo al pedido y actualiza su estado.
     *
     * @param pedido pedido a procesar (se modifica in-place)
     */
    void procesar(Pedido pedido);
}
