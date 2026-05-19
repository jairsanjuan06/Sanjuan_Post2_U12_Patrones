package com.empresa.pedidos.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad de dominio que representa un pedido.
 * No depende de ninguna librería de infraestructura más allá de JPA,
 * cuya anotación es aceptable aquí al ser el modelo de persistencia principal.
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cliente;

    @Column(nullable = false)
    private double subtotal;

    private double costo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPedido tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    /** Constructor vacío requerido por JPA. */
    public Pedido() {
        this.estado = EstadoPedido.PENDIENTE;
    }

    /** Constructor de conveniencia para pruebas y creación manual. */
    public Pedido(String cliente, double subtotal, TipoPedido tipo) {
        this.cliente  = cliente;
        this.subtotal = subtotal;
        this.tipo     = tipo;
        this.estado   = EstadoPedido.PENDIENTE;
    }

    // ── Getters y Setters ────────────────────────────────────────────

    public Long getId() { return id; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public TipoPedido getTipo() { return tipo; }
    public void setTipo(TipoPedido tipo) { this.tipo = tipo; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", cliente='" + cliente + "', tipo=" + tipo
                + ", estado=" + estado + ", costo=" + costo + "}";
    }
}
