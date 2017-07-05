package org.TIposSQL;

import java.util.Calendar;

public class Factura {

	int numeroFactura;
	Carrito carrito;
	Usuario usuario;
	Calendar fecha;

	public Factura(int numeroFactura, Carrito carrito, Usuario usuario) {
		super();
		this.numeroFactura = numeroFactura;
		this.carrito = carrito;
		this.usuario = usuario;
	}

	public int getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(int numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Carrito getCarrito() {
		return carrito;
	}

	public void setCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

}
