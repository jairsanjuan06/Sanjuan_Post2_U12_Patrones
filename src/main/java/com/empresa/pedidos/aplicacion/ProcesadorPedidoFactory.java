package com.empresa.pedidos.aplicacion;

import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProcesadorPedidoFactory {

    private final Map<TipoPedido, ProcesadorPedido> procesadores;

    /**
     * Constructor: Spring inyecta la lista completa de implementaciones.
     *
     * @param lista todas las implementaciones de {@link ProcesadorPedido}
     */
    public ProcesadorPedidoFactory(List<ProcesadorPedido> lista) {
        this.procesadores = lista.stream()
                .collect(Collectors.toMap(
                        ProcesadorPedido::getTipo,
                        Function.identity()));
    }

    /**
     * Retorna el procesador correspondiente al tipo de pedido.
     *
     * @param tipo tipo del pedido a procesar
     * @return implementación de {@link ProcesadorPedido} para ese tipo
     * @throws IllegalArgumentException si el tipo no tiene procesador registrado
     */
    public ProcesadorPedido obtener(TipoPedido tipo) {
        ProcesadorPedido procesador = procesadores.get(tipo);
        if (procesador == null) {
            throw new IllegalArgumentException(
                    "Tipo de pedido no soportado: " + tipo);
        }
        return procesador;
    }
}
