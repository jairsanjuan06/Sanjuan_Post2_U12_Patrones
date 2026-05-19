package com.empresa.pedidos.infraestructura.notificaciones;

import com.empresa.pedidos.aplicacion.PedidoProcesadoEvent;
import com.empresa.pedidos.dominio.puertos.ServicioNotificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Suscriptor Observer que registra en el log del sistema cada pedido procesado.
 *
 * <p>Actúa como auditoría pasiva: persiste el rastro de cada operación
 * sin interferir con el flujo principal de negocio.</p>
 *
 * <p>Patrón: Observer — suscriptor independiente; se puede agregar o
 * remover sin modificar ninguna otra clase.</p>
 */
@Component
public class NotificacionLog implements ServicioNotificacion {

    private static final Logger log =
            LoggerFactory.getLogger(NotificacionLog.class);

    /**
     * Recibe el evento y registra la auditoría del pedido.
     *
     * @param evento evento con los datos del pedido procesado
     */
    @EventListener
    @Override
    public void notificar(PedidoProcesadoEvent evento) {
        log.info("[AUDIT] Pedido procesado — id={}, tipo={}, estado={}, costo={}",
                evento.pedido().getId(),
                evento.pedido().getTipo(),
                evento.pedido().getEstado(),
                evento.pedido().getCosto());
    }
}
