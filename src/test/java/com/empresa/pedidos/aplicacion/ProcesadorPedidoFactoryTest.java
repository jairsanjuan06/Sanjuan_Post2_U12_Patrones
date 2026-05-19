package com.empresa.pedidos.aplicacion;

import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoEstandar;
import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoExpress;
import com.empresa.pedidos.adaptadores.procesadores.ProcesadorPedidoInternacional;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Prueba unitaria del patrón Factory.
 * Verifica que cada TipoPedido devuelve la estrategia correcta
 * y que un tipo desconocido lanza excepción.
 */
@DisplayName("ProcesadorPedidoFactory — selección de estrategia")
class ProcesadorPedidoFactoryTest {

    private ProcesadorPedidoFactory factory;

    @BeforeEach
    void setUp() {
        List<ProcesadorPedido> procesadores = List.of(
                new ProcesadorPedidoEstandar(),
                new ProcesadorPedidoExpress(),
                new ProcesadorPedidoInternacional()
        );
        factory = new ProcesadorPedidoFactory(procesadores);
    }

    @Test
    @DisplayName("ESTANDAR debe retornar ProcesadorPedidoEstandar")
    void debeRetornarProcesadorEstandar() {
        ProcesadorPedido procesador = factory.obtener(TipoPedido.ESTANDAR);
        assertThat(procesador).isInstanceOf(ProcesadorPedidoEstandar.class);
    }

    @Test
    @DisplayName("EXPRESS debe retornar ProcesadorPedidoExpress")
    void debeRetornarProcesadorExpress() {
        ProcesadorPedido procesador = factory.obtener(TipoPedido.EXPRESS);
        assertThat(procesador).isInstanceOf(ProcesadorPedidoExpress.class);
    }

    @Test
    @DisplayName("INTERNACIONAL debe retornar ProcesadorPedidoInternacional")
    void debeRetornarProcesadorInternacional() {
        ProcesadorPedido procesador = factory.obtener(TipoPedido.INTERNACIONAL);
        assertThat(procesador).isInstanceOf(ProcesadorPedidoInternacional.class);
    }

    @Test
    @DisplayName("Tipo null debe lanzar IllegalArgumentException")
    void tipoNullDebeLanzarExcepcion() {
        assertThatThrownBy(() -> factory.obtener(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
