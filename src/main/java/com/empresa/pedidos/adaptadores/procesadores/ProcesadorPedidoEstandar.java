package com.empresa.pedidos.adaptadores.procesadores;

import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import org.springframework.stereotype.Component;

/**
 * Estrategia de procesamiento para pedidos de tipo ESTÁNDAR.
 *
 * <p>Regla de negocio: costo = subtotal × 1.1 (10% de recargo).</p>
 *
 * <p>Patrón: Strategy — encapsula el algoritmo de cálculo estándar,
 * eliminando el bloque {@code if} del servicio legacy.</p>
 */
@Component
public class ProcesadorPedidoEstandar implements ProcesadorPedido {

    @Override
    public TipoPedido getTipo() {
        return TipoPedido.ESTANDAR;
    }

    @Override
    public void procesar(Pedido pedido) {
        pedido.setCosto(pedido.getSubtotal() * 1.1);
        pedido.setEstado(EstadoPedido.PROCESADO);
    }
}
