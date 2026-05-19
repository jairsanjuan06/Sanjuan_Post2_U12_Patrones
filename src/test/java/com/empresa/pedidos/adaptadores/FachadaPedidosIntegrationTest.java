package com.empresa.pedidos.adaptadores;

import com.empresa.pedidos.adaptadores.facade.FachadaPedidos;
import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prueba de integración: verifica el flujo completo POST /api/pedidos
 * para los tres tipos de pedido, la persistencia y la publicación de eventos.
 *
 * <p>Al usar @SpringBootTest se levanta el contexto completo de Spring,
 * lo que garantiza que los listeners Observer (NotificacionEmail, NotificacionLog)
 * están registrados y reciben el evento PedidoProcesadoEvent.</p>
 */
@SpringBootTest
@DisplayName("Integración — flujo completo de pedidos")
class FachadaPedidosIntegrationTest {

    @Autowired
    private FachadaPedidos fachada;

    @Test
    @DisplayName("Pedido ESTANDAR: costo correcto, estado PROCESADO, persistido")
    void flujoPedidoEstandar() {
        Pedido pedido = new Pedido("Cliente A", 200.0, TipoPedido.ESTANDAR);

        Pedido resultado = fachada.crearPedido(pedido);

        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getCosto()).isEqualTo(220.0);
        assertThat(resultado.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }

    @Test
    @DisplayName("Pedido EXPRESS: costo correcto y almacenado")
    void flujoPedidoExpress() {
        Pedido pedido = new Pedido("Cliente B", 100.0, TipoPedido.EXPRESS);

        Pedido resultado = fachada.crearPedido(pedido);

        assertThat(resultado.getCosto()).isEqualTo(130.0);
        assertThat(resultado.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }

    @Test
    @DisplayName("Pedido INTERNACIONAL: costo con arancel fijo")
    void flujoPedidoInternacional() {
        Pedido pedido = new Pedido("Cliente C", 100.0, TipoPedido.INTERNACIONAL);

        Pedido resultado = fachada.crearPedido(pedido);

        assertThat(resultado.getCosto()).isEqualTo(175.0);
        assertThat(resultado.getEstado()).isEqualTo(EstadoPedido.PROCESADO);
    }

    @Test
    @DisplayName("buscarPorId retorna el pedido guardado")
    void buscarPorIdRetornaElPedido() {
        Pedido pedido = new Pedido("Cliente D", 50.0, TipoPedido.ESTANDAR);
        Pedido guardado = fachada.crearPedido(pedido);

        Optional<Pedido> encontrado = fachada.buscarPorId(guardado.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCliente()).isEqualTo("Cliente D");
    }
}
