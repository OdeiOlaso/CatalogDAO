package org.DAO;

import org.TIposSQL.Producto;

public interface ProductoDAO extends IpartekDAO {

	public Producto[] findAll();

	public Producto finById(int id);

	public int insert(Producto producto);

	public void update(Producto producto);

	public void delete(Producto producto);

	public void delete(int id);

}
