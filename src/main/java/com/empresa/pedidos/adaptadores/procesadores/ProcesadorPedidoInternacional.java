package com.empresa.pedidos.adaptadores.procesadores;

import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import org.springframework.stereotype.Component;

/**
 * Estrategia de procesamiento para pedidos de tipo INTERNACIONAL.
 *
 * <p>Regla de negocio: costo = subtotal × 1.5 + $25.00 (50% recargo + arancel fijo).</p>
 *
 * <p>Patrón: Strategy — el arancel fijo adicional es una variación real del
 * algoritmo que justifica una implementación separada.</p>
 */
@Component
public class ProcesadorPedidoInternacional implements ProcesadorPedido {

    private static final double RECARGO     = 1.5;
    private static final double ARANCEL_FIJO = 25.0;

    @Override
    public TipoPedido getTipo() {
        return TipoPedido.INTERNACIONAL;
    }

    @Override
    public void procesar(Pedido pedido) {
        pedido.setCosto(pedido.getSubtotal() * RECARGO + ARANCEL_FIJO);
        pedido.setEstado(EstadoPedido.PROCESADO);
    }
}
