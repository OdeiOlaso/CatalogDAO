package org.DAO;

import java.sql.Connection;

public class HibernateDAO {
	protected Connection con;
	private String url = "jdbc:mysql://localhost/ipartek";
	private String musqlUser = "root";
	private String mysqlPass = "";

	public HibernateDAO(String url, String musqlUser, String mysqlPass) {

	}

	public HibernateDAO() {

	}

	public void abrir() {

	}

	public void cerrar() {

	}

	public void iniciarTransaccion() {

	}

	public void confirmarTransaccion() {

	}

	public void deshacerTransaccion() {

	}

}
