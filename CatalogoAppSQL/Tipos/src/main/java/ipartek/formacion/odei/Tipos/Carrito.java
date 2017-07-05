package ipartek.formacion.odei.Tipos;

import java.util.HashMap;

public class Carrito {
	private HashMap<Integer, Integer> carrito;

	public void comprar(int idProducto) {
		if (carrito.get(idProducto) == null) {
			int cantidad = carrito.get(idProducto);
			carrito.put(idProducto, ++cantidad);
		} else {
			carrito.put(idProducto, 1);
		}

	}
}
