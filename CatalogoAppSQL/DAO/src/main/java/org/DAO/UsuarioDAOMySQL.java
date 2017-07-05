package org.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.TIposSQL.Usuario;
import org.Utilidades.Cifrador;

public class UsuarioDAOMySQL extends IpartekDAOMySQL implements UsuarioDAO {

	private final static String FIND_ALL = "SELECT * FROM usuarios";
	private final static String FIND_BY_ID = "SELECT * FROM usuarios WHERE id=?";
	private final static String FIND_BY_USER = "SELECT * FROM usuarios WHERE username=?";
	private final static String INSERT = "INSERT INTO usuarios (username, password, nombre_completo, id_roles) VALUES (?, ?, ?, ?)";
	private final static String UPDATE = "UPDATE usuarios SET username=?, password=?, nombre_completo=?, id_roles=? WHERE id = ?";
	private final static String DELETE = "DELETE FROM usuarios WHERE id=?";

	private PreparedStatement psFindAll, psFindById, psFindByUser, psInsert, psUpdate, psDelete;

	public UsuarioDAOMySQL(String url, String musqlUser, String mysqlPass) {
		super(url, musqlUser, mysqlPass);
	}

	public boolean validar(Usuario usuario) {

		ResultSet rs = null;
		boolean valido = false;

		try {
			psFindByUser = con.prepareStatement(FIND_BY_USER);
			psFindByUser.setString(1, usuario.getUsername());

			rs = psFindByUser.executeQuery();

			if (rs.next()) {

				String clave = usuario.getPassword();
				clave = Cifrador.cifrar(clave);

				System.out.println(clave);
				System.out.println(rs.getString("password"));

				valido = clave.equals(rs.getString("password"));

			}

		} catch (SQLException e) {
			throw new DAOException("Error al Validar", e);
		} finally {
			cerrar(psFindByUser, rs);
		}

		return valido;
	}

	public Usuario[] findAll() {

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		ResultSet rs = null;
		try {
			psFindAll = con.prepareStatement(FIND_ALL);
			rs = psFindAll.executeQuery();

			Usuario usuario;

			while (rs.next()) {
				// System.out.println(rs.getString("username"));
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setId_roles(rs.getInt("id_roles"));
				usuario.setNombre_completo(rs.getString("nombre_completo"));
				usuario.setPassword(rs.getString("password"));
				usuario.setUsername(rs.getString("username"));

				usuarios.add(usuario);

			}

		} catch (SQLException e) {
			throw new DAOException("Error al findAll", e);
		} finally {
			cerrar(psFindAll, rs);
		}

		return usuarios.toArray(new Usuario[usuarios.size()]);
	}

	public Usuario finById(int id) {
		Usuario usuario = null;
		ResultSet rs = null;
		try {
			psFindById = con.prepareStatement(FIND_BY_ID);

			psFindById.setInt(1, id);

			rs = psFindById.executeQuery();

			if (rs.next()) {

				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setId_roles(rs.getInt("id_roles"));
				usuario.setNombre_completo(rs.getString("nombre_completo"));
				usuario.setPassword(rs.getString("password"));
				usuario.setUsername(rs.getString("username"));
			}

		} catch (SQLException e) {
			throw new DAOException("Error al finById", e);
		} finally {
			cerrar(psFindById, rs);
		}
		return usuario;
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

	public int insert(Usuario usuario) {

		try {

			String clave = usuario.getPassword();

			clave = Cifrador.cifrar(clave);

			psInsert = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			psInsert.setString(1, usuario.getUsername());
			psInsert.setString(2, clave);
			psInsert.setString(3, usuario.getNombre_completo());
			psInsert.setInt(4, usuario.getId_roles());

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

	public void update(Usuario usuario) {
		try {
			psUpdate = con.prepareStatement(UPDATE);

			psUpdate.setString(1, usuario.getUsername());
			psUpdate.setString(2, usuario.getPassword());
			psUpdate.setString(3, usuario.getNombre_completo());
			psUpdate.setInt(4, usuario.getId_roles());
			psUpdate.setInt(5, usuario.getId());

			int res = psUpdate.executeUpdate();

			if (res != 1)
				throw new DAOException("La actualizacion ha devuelto un valor " + res);
		} catch (SQLException e) {
			throw new DAOException("Error al update", e);
		} finally {
			cerrar(psUpdate);
		}

	}

	public void delete(Usuario usuario) {

		int id = usuario.getId();
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
