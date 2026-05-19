package com.empresa.pedidos.adaptadores.procesadores;

import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ProcesadorPedidoInternacional implements ProcesadorPedido {

    private static final double RECARGO = 1.5;
    private static final double ARANCEL_FIJO = 25.0;

    @Override
    public TipoPedido getTipo() {
        return TipoPedido.INTERNACIONAL;
    }

    @Override
    public void procesar(Pedido pedido) {
        BigDecimal subtotal = BigDecimal.valueOf(pedido.getSubtotal());
        BigDecimal costo = subtotal.multiply(BigDecimal.valueOf(RECARGO))
                .add(BigDecimal.valueOf(ARANCEL_FIJO))
                .setScale(2, RoundingMode.HALF_UP);
        pedido.setCosto(costo.doubleValue());
        pedido.setEstado(EstadoPedido.PROCESADO);
    }
}
