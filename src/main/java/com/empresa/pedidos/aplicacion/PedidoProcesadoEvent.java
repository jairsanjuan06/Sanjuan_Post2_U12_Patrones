package com.empresa.pedidos.aplicacion;

import com.empresa.pedidos.dominio.Pedido;

/**
 * Evento de dominio publicado cuando un pedido es procesado exitosamente.
 *
 * <p>Implementa el patrón Observer a través del mecanismo de Spring Events:
 * el publicador ({@code FachadaPedidos}) no conoce a los suscriptores
 * ({@code NotificacionEmail}, {@code NotificacionLog}).</p>
 *
 * <p>Se usa {@code record} de Java 16+ para inmutabilidad y concisión.</p>
 *
 * @param pedido pedido que fue procesado
 */
public record PedidoProcesadoEvent(Pedido pedido) {}
