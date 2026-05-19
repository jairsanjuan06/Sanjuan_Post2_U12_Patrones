package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;

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
