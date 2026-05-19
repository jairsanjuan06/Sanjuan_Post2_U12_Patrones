package com.empresa.pedidos.dominio;

/**
 * Tipos de pedido soportados por el sistema.
 * Cada tipo tiene una estrategia de procesamiento distinta.
 */
public enum TipoPedido {
    ESTANDAR,
    EXPRESS,
    INTERNACIONAL
}
