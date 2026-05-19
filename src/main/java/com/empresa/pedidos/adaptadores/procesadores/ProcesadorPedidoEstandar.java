package com.empresa.pedidos.adaptadores.procesadores;

import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Component
public class ProcesadorPedidoEstandar implements ProcesadorPedido {

    @Override
    public TipoPedido getTipo() {
        return TipoPedido.ESTANDAR;
    }

    @Override
    public void procesar(Pedido pedido) {
        BigDecimal subtotal = BigDecimal.valueOf(pedido.getSubtotal());
        BigDecimal costo = subtotal.multiply(BigDecimal.valueOf(1.1))
                .setScale(2, RoundingMode.HALF_UP);
        pedido.setCosto(costo.doubleValue());
        pedido.setEstado(EstadoPedido.PROCESADO);
    }
}
