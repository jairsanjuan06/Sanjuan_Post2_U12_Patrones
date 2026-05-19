package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.aplicacion.PedidoProcesadoEvent;

public interface ServicioNotificacion {

    /**
     * Procesa el evento de pedido procesado y envía la notificación
     * correspondiente según el canal implementado.
     *
     * @param evento evento con los datos del pedido procesado
     */
    void notificar(PedidoProcesadoEvent evento);
}
