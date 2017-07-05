package org.TIposSQL;

import java.util.HashMap;

public class Carrito {
	private HashMap<Integer, Integer> lista = new HashMap<Integer, Integer>();

	public void comprar(int idProducto) {
		if (getCarrito().get(idProducto) != null) {
			int cantidad = getCarrito().get(idProducto);
			getCarrito().put(idProducto, ++cantidad);
		} else {
			getCarrito().put(idProducto, 1);
		}

	}

	public void quitar(int idProducto, int restar) {
		System.out.println("idProducto: " + idProducto);
		int cantidad = getCarrito().get(idProducto);
		System.out.println("public void quitar cantidad ANTES: " + cantidad);
		if (getCarrito().get(idProducto) == null) {

			System.out.println("public void quitar cantidad IF: null" + cantidad);
		} else if (restar == 1 && cantidad > 1) {
			getCarrito().put(idProducto, --cantidad);

			System.out.println("public void quitar cantidad  IF: 1" + cantidad);
		} else {
			getCarrito().remove(idProducto);

			System.out.println("public void quitar cantidad ELSE1: " + cantidad);
		}

		System.out.println("public void quitar cantidad DESPUES: " + getCarrito().get(idProducto));
	}

	@Override
	public String toString() {
		return "Carrito [carrito=" + getCarrito() + "]";
	}

	public HashMap<Integer, Integer> getCarrito() {
		return lista;
	}

	public void setLista(HashMap<Integer, Integer> lista) {
		this.lista = lista;
	}

	public HashMap<Integer, Integer> getLista() {
		return lista;
	}

	public void listalista(int int1, int int2) {
		// TODO Auto-generated method stub

	}

}
