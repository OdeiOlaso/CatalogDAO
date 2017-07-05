package ipartek.formacion.odei.DAL;

import ipartek.formacion.odei.Tipos.Producto;

import java.util.Map;
import java.util.TreeMap;

public class ProductosDALColeccion implements ProductosDAL {

	private Map<Integer, Producto> productos = new TreeMap<Integer, Producto>();
	public static int ultimoId;

	public boolean existeProducto(int id) {

		return productos.containsKey(id);
	}

	public int idNuevo() {
		return ++ultimoId;
	}

	public void alta(Producto producto) {
		if (productos.containsKey(producto.getId()))
			throw new ProductoYaExistenteDALException("Ya existe el producto " + producto.getNombre());
		productos.put(producto.getId(), producto);

	}

	public boolean validar(Producto producto) {

		return productos.containsValue(producto);
	}

	public void modificar(Producto producto) {
		if (!productos.containsKey(producto.getId()))
			throw new DALException("Intento de modificar usuario no existe " + producto);

		productos.put(producto.getId(), producto);
	}

	public void borrar(Producto producto) {
		productos.remove(producto.getId());
	}

	public Producto buscarPorId(Integer id) {
		return productos.get(id);
	}

	public Producto[] buscarTodosLosProductos() {

		return productos.values().toArray(new Producto[productos.size()]);
	}

}
