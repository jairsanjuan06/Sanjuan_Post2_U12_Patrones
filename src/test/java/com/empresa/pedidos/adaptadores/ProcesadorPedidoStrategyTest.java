package com.empresa.pedidos.adaptadores;

import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoEstandar;
import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoExpress;
import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoInternacional;
import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas unitarias del patrón Strategy.
 * Verifica que cada estrategia aplica correctamente su fórmula de costo
 * y transiciona el pedido al estado PROCESADO.
 */
@DisplayName("Procesadores Strategy — cálculo de costo")
class ProcesadorPedidoStrategyTest {

    @Test
    @DisplayName("Estandar: costo = subtotal * 1.1")
    void procesadorEstandarCalculaCostoCorrectamente() {
        Pedido pedido = new Pedido("Carlos", 100.0, TipoPedido.ESTANDAR);
        new ProcesadorPedidoEstandar().procesar(pedido);

        assertThat(pedido.getCosto()).isEqualTo(110.0);
        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }

    @Test
    @DisplayName("Express: costo = subtotal * 1.3")
    void procesadorExpressCalculaCostoCorrectamente() {
        Pedido pedido = new Pedido("Ana", 100.0, TipoPedido.EXPRESS);
        new ProcesadorPedidoExpress().procesar(pedido);

        assertThat(pedido.getCosto()).isEqualTo(130.0);
        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }

    @Test
    @DisplayName("Internacional: costo = subtotal * 1.5 + 25.0")
    void procesadorInternacionalCalculaCostoCorrectamente() {
        Pedido pedido = new Pedido("Luis", 100.0, TipoPedido.INTERNACIONAL);
        new ProcesadorPedidoInternacional().procesar(pedido);

        assertThat(pedido.getCosto()).isEqualTo(175.0);
        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }
}
