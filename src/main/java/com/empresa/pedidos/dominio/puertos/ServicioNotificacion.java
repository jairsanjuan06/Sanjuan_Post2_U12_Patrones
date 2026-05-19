package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.aplicacion.PedidoProcesadoEvent;

/**
 * Puerto de dominio para el envío de notificaciones.
 *
 * <p>Cada implementación representa un canal de notificación distinto
 * (email, log, SMS, push). El dominio no conoce los canales concretos.</p>
 *
 * <p>Patrón: Observer — cada implementación es un suscriptor
 * independiente del evento {@link PedidoProcesadoEvent}.</p>
 */
public interface ServicioNotificacion {

    /**
     * Procesa el evento de pedido procesado y envía la notificación
     * correspondiente según el canal implementado.
     *
     * @param evento evento con los datos del pedido procesado
     */
    void notificar(PedidoProcesadoEvent evento);
}
