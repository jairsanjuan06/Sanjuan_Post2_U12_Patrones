package com.empresa.pedidos.infraestructura.notificaciones;

import com.empresa.pedidos.aplicacion.PedidoProcesadoEvent;
import com.empresa.pedidos.dominio.puertos.ServicioNotificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Suscriptor Observer para notificaciones por correo electrónico.
 *
 * <p>Escucha el evento {@link PedidoProcesadoEvent} publicado por la Facade.
 * Al recibir el evento, simula el envío de un email (log en esta versión).
 * En producción se reemplazaría el body con JavaMailSender sin tocar el dominio.</p>
 *
 * <p>Patrón: Observer — suscriptor independiente del publicador.</p>
 */
@Component
public class NotificacionEmail implements ServicioNotificacion {

    private static final Logger log =
            LoggerFactory.getLogger(NotificacionEmail.class);

    /**
     * Recibe el evento de pedido procesado y envía la notificación por email.
     *
     * @param evento evento con los datos del pedido procesado
     */
    @EventListener
    @Override
    public void notificar(PedidoProcesadoEvent evento) {
        log.info("[EMAIL] Pedido #{} de cliente '{}' procesado. Costo: ${}",
                evento.pedido().getId(),
                evento.pedido().getCliente(),
                evento.pedido().getCosto());
    }
}
