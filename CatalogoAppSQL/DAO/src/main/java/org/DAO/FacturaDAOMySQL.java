package org.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.TIposSQL.Carrito;
import org.TIposSQL.Factura;
import org.TIposSQL.Usuario;

public class FacturaDAOMySQL extends IpartekDAOMySQL implements FacturaDAO {

	private final static String FIND_ALL = "SELECT * FROM facturas";
	private final static String FIND_BY_ID = "SELECT * FROM facturas WHERE numero_factura=?";
	private final static String FIND_PROD_BY_ID = "SELECT * FROM facturas_productos WHERE id_facturas=?";
	private final static String INSERT = "INSERT INTO facturas (fecha, id_usuario, numero_factura) VALUES (?, ?, ?)";
	private final static String FIND_ID = "SELECT id FROM facturas WHERE numero_factura=?";
	private final static String INSERT_PROD_BY_ID = "INSERT INTO facturas_productos  (id_facturas, id_productos, cantidad) VALUES (?, ?, ?)";
	private final static String UPDATE = "UPDATE facturas_productos SET id_producto=?, cantidad=? WHERE numero_factura = ?";
	private final static String FIND_LAST = "SELECT max(numero_factura) FROM facturas";

	private PreparedStatement psFindAll, psFindById, psFindId, psFindProdById, psInsert, psInsertProdById, psUpdate, psLastId;

	public FacturaDAOMySQL(String url, String mysqlUser, String mysqlPass) {
		super(url, mysqlUser, mysqlPass);
	}

	public Factura[] findAll() {

		ArrayList<Factura> facturas = new ArrayList<Factura>();

		ResultSet rs = null, rsp = null;
		try {
			psFindAll = con.prepareStatement(FIND_ALL);
			rs = psFindAll.executeQuery();

			psFindProdById = con.prepareStatement(FIND_PROD_BY_ID);

			Factura factura;
			Carrito carrito;
			HashMap<Integer, Integer> lista;
			Usuario usuario;
			int id, userId;
			Calendar fecha;

			while (rs.next()) {

				factura = new Factura(0, null, null);
				factura.setNumeroFactura(rs.getInt("numero_factura"));

				id = rs.getInt("id");
				userId = rs.getInt("id_usuarios");

				psFindProdById.setInt(1, id);

				rsp = psFindProdById.executeQuery();
				carrito = new Carrito();
				lista = new HashMap<Integer, Integer>();

				while (rsp.next()) {

					lista.put(rsp.getInt("id_prod"), rsp.getInt("cantidad"));

					// for (int i = 0; i < rsp.getInt("cantidad"); i++) {
					// carrito.comprar(rsp.getInt("id_prod"));
					// }
				}

				carrito.setLista(lista);

				factura.setCarrito(carrito);

				fecha = (Calendar) rsp.getObject("fecha");
				factura.setFecha(fecha);

				// factura.setCarrito(rs.getString("nombre"));
				// factura.setDescripcion(rs.getString("descripcion"));
				// factura.setPrecio(rs.getDouble("precio"));
				UsuarioDAO daoUser = DAOFactory.getUsuarioDAO();
				usuario = daoUser.finById(userId);
				factura.setUsuario(usuario);

				facturas.add(factura);
			}

		} catch (SQLException e) {
			throw new DAOException("Error al findAll", e);
		} finally {
			cerrar(psFindAll, rs);
		}

		return facturas.toArray(new Factura[facturas.size()]);
	}

	public Factura finById(int id) {

		ResultSet rs = null, rsp = null;
		Factura factura = null;

		try {
			psFindById = con.prepareStatement(FIND_BY_ID);

			psFindById.setInt(1, id);

			rs = psFindById.executeQuery();

			psFindProdById = con.prepareStatement(FIND_PROD_BY_ID);

			Carrito carrito;
			HashMap<Integer, Integer> lista;
			Usuario usuario;
			int userId;
			Calendar fecha;

			if (rs.next()) {

				factura = new Factura(0, null, null);
				factura.setNumeroFactura(rs.getInt("numero_factura"));

				id = rs.getInt("id");
				userId = rs.getInt("id_usuarios");

				psFindProdById.setInt(1, id);

				rsp = psFindProdById.executeQuery();
				carrito = new Carrito();
				lista = new HashMap<Integer, Integer>();

				while (rsp.next()) {

					lista.put(rsp.getInt("id_prod"), rsp.getInt("cantidad"));

					// for (int i = 0; i < rsp.getInt("cantidad"); i++) {
					// carrito.comprar(rsp.getInt("id_prod"));
					// }
				}

				carrito.setLista(lista);

				factura.setCarrito(carrito);

				fecha = (Calendar) rsp.getObject("fecha");
				factura.setFecha(fecha);

				// factura.setCarrito(rs.getString("nombre"));
				// factura.setDescripcion(rs.getString("descripcion"));
				// factura.setPrecio(rs.getDouble("precio"));
				UsuarioDAO daoUser = DAOFactory.getUsuarioDAO();
				usuario = daoUser.finById(userId);
				factura.setUsuario(usuario);
			}

		} catch (SQLException e) {
			throw new DAOException("Error al finById", e);
		} finally {
			cerrar(psFindById, rs);
		}
		return factura;
	}

	public int findLastNumFact() {
		ResultSet rs = null;
		int ultimo = 0;
		try {
			psLastId = con.prepareStatement(FIND_LAST);

			rs = psLastId.executeQuery();

			if (rs.next()) {

				ultimo = rs.getInt("numro_facutra");
				// factura.setNombre(rs.getString("nombre"));
				// factura.setDescripcion(rs.getString("descripcion"));
				// factura.setPrecio(rs.getDouble("precio"));

			}

		} catch (SQLException e) {
			throw new DAOException("Error al finById", e);
		} finally {
			cerrar(psFindById, rs);
		}

		return ultimo;
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

	public int insert(Factura factura) {
		ResultSet rs = null;
		try {

			psInsert = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			psInsert.setObject(1, factura.getFecha());
			psInsert.setInt(2, factura.getUsuario().getId());
			psInsert.setInt(3, factura.getNumeroFactura());

			int res = psInsert.executeUpdate();

			if (res != 1)
				throw new DAOException("La insercion ha devuelto un valor " + res);

			ResultSet generatedKeys = psInsert.getGeneratedKeys();
			if (generatedKeys.next()) {

				psFindId = con.prepareStatement(FIND_ID);

				rs = psFindId.executeQuery();

				if (rs.next()) {
					int idFactura = rs.getInt("id");

					HashMap<Integer, Integer> lista = factura.getCarrito().getLista();
					for (int id : lista.keySet()) {
						psInsertProdById = con.prepareStatement(INSERT_PROD_BY_ID);

						psInsertProdById.setInt(1, idFactura);
						psInsertProdById.setInt(2, id);
						psInsertProdById.setInt(3, lista.get(id));

						res = psInsertProdById.executeUpdate();

						if (res != 1)
							throw new DAOException("La insercion ha devuelto un valor " + res);

					}
				}

				return generatedKeys.getInt(1);
			} else
				throw new DAOException("No se ha recibido la clave generada");

		} catch (SQLException e) {
			throw new DAOException("Error al insert", e);
		} finally {
			cerrar(psInsert);
		}
	}

	public void update(Factura factura) {
		try {
			System.out.println(UPDATE);
			psUpdate = con.prepareStatement(UPDATE);

			System.out.println(psUpdate);

			// psUpdate.setString(1, factura.getNombre());
			// psUpdate.setString(2, factura.getDescripcion());
			// psUpdate.setDouble(3, factura.getPrecio());
			psUpdate.setInt(4, factura.getNumeroFactura());

			int res = psUpdate.executeUpdate();

			if (res != 1)
				throw new DAOException("La actualizacion ha devuelto un valor " + res);
		} catch (SQLException e) {
			throw new DAOException("Error al update", e);
		} finally {
			cerrar(psUpdate);
		}

	}

}
