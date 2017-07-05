package org.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.TIposSQL.Producto;

public class ProductoDAOMySQL extends IpartekDAOMySQL implements ProductoDAO {

	private final static String FIND_ALL = "SELECT * FROM productos";
	private final static String FIND_BY_ID = "SELECT * FROM productos WHERE id=?";
	private final static String INSERT = "INSERT INTO productos (nombre, descripcion, precio) VALUES (?, ?, ?)";
	private final static String UPDATE = "UPDATE productos SET nombre=?, descripcion=?, precio=? WHERE id = ?";
	private final static String DELETE = "DELETE FROM productos WHERE id=?";

	private PreparedStatement psFindAll, psFindById, psInsert, psUpdate, psDelete;

	public ProductoDAOMySQL(String url, String mysqlUser, String mysqlPass) {
		super(url, mysqlUser, mysqlPass);
	}

	public Producto[] findAll() {

		ArrayList<Producto> productos = new ArrayList<Producto>();

		ResultSet rs = null;
		try {
			psFindAll = con.prepareStatement(FIND_ALL);
			rs = psFindAll.executeQuery();

			Producto producto;

			while (rs.next()) {

				producto = new Producto();
				producto.setId(rs.getInt("id"));
				producto.setNombre(rs.getString("nombre"));
				producto.setDescripcion(rs.getString("descripcion"));
				producto.setPrecio(rs.getDouble("precio"));

				productos.add(producto);

			}

		} catch (SQLException e) {
			throw new DAOException("Error al findAll", e);
		} finally {
			cerrar(psFindAll, rs);
		}

		return productos.toArray(new Producto[productos.size()]);
	}

	public Producto finById(int id) {
		Producto producto = null;
		ResultSet rs = null;
		try {
			psFindById = con.prepareStatement(FIND_BY_ID);

			psFindById.setInt(1, id);

			rs = psFindById.executeQuery();

			if (rs.next()) {

				producto = new Producto();
				producto.setId(rs.getInt("id"));
				producto.setNombre(rs.getString("nombre"));
				producto.setDescripcion(rs.getString("descripcion"));
				producto.setPrecio(rs.getDouble("precio"));

			}

		} catch (SQLException e) {
			throw new DAOException("Error al finById", e);
		} finally {
			cerrar(psFindById, rs);
		}
		return producto;
	}

	private void cerrar(PreparedStatement ps) {
		cerrar(ps, null);
	}

	private void cerrar(PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			throw new DAOException("Error en el cierre de ps o rs", e);
		}
	}

	public int insert(Producto producto) {

		try {

			psInsert = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			psInsert.setString(1, producto.getNombre());
			psInsert.setString(2, producto.getDescripcion());
			psInsert.setDouble(3, producto.getPrecio());

			int res = psInsert.executeUpdate();

			if (res != 1)
				throw new DAOException("La insercion ha devuelto un valor " + res);

			ResultSet generatedKeys = psInsert.getGeneratedKeys();
			if (generatedKeys.next())
				return generatedKeys.getInt(1);
			else
				throw new DAOException("No se ha recibido la clave generada");

		} catch (SQLException e) {
			throw new DAOException("Error al insert", e);
		} finally {
			cerrar(psInsert);
		}
	}

	public void update(Producto producto) {
		try {
			System.out.println(UPDATE);
			psUpdate = con.prepareStatement(UPDATE);

			System.out.println(psUpdate);

			psUpdate.setString(1, producto.getNombre());
			psUpdate.setString(2, producto.getDescripcion());
			psUpdate.setDouble(3, producto.getPrecio());
			psUpdate.setInt(4, producto.getId());

			int res = psUpdate.executeUpdate();

			if (res != 1)
				throw new DAOException("La actualizacion ha devuelto un valor " + res);
		} catch (SQLException e) {
			throw new DAOException("Error al update", e);
		} finally {
			cerrar(psUpdate);
		}

	}

	public void delete(Producto producto) {

		int id = producto.getId();
		delete(id);

	}

	public void delete(int id) {
		try {

			psDelete = con.prepareStatement(DELETE);

			psDelete.setInt(1, id);

			int res = psDelete.executeUpdate();

			if (res != 1)
				throw new DAOException("La deletizacion ha devuelto un valor " + res);
		} catch (SQLException e) {
			throw new DAOException("Error al delete", e);
		} finally {
			cerrar(psDelete);
		}

	}

}
