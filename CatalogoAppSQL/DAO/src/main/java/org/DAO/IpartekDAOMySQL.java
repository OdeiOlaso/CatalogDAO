package org.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IpartekDAOMySQL {

	protected Connection con;
	private String url = "jdbc:mysql://localhost/ipartek";
	private String musqlUser = "root";
	private String mysqlPass = "";

	public IpartekDAOMySQL(String url, String musqlUser, String mysqlPass) {
		super();
		this.url = url;
		this.musqlUser = musqlUser;
		this.mysqlPass = mysqlPass;
	}

	public IpartekDAOMySQL() {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			throw new DAOException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new DAOException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new DAOException("No se ha encontrado el driver de MySQL", e);
		} catch (Exception e) {
			throw new DAOException("Error, ERROR NO ESPERADO, Error, ERROR, Error, ERROR, ErRoR, ErRoR, ErRoR, ErRoR!!!!! ", e);
		} finally {

		}
	}

	public void abrir() {

		try {
			con = DriverManager.getConnection(url, musqlUser, mysqlPass);
		} catch (SQLException e) {
			throw new DAOException("Error de conexion a la base de datos", e);
		} catch (Exception e) {
			throw new DAOException("Error, ERROR NO ESPERADO, Error, ERROR, Error, ERROR, ErRoR, ErRoR, ErRoR, ErRoR!!!!! ", e);
		}
	}

	public void cerrar() {

		try {
			if (con != null && !con.isClosed())
				con.close();

			con = null;
		} catch (SQLException e) {
			throw new DAOException("Error al cerrar la conexion a la base de datos", e);
		} catch (Exception e) {
			throw new DAOException("Error, ERROR NO ESPERADO, Error, ERROR, Error, ERROR, ErRoR, ErRoR, ErRoR, ErRoR!!!!! ", e);
		}
	}

	public void iniciarTransaccion() {

		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DAOException("Error al iniciar la transaccion", e);
		}
	}

	public void confirmarTransaccion() {
		try {
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DAOException("Error al confrimar la transaccion", e);

		}

	}

	public void deshacerTransaccion() {
		try {
			con.rollback();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DAOException("Error al deshacer la transaccion", e);

		}

	}

}
