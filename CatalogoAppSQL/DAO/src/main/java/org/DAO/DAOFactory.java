package org.DAO;

public class DAOFactory {

	public static UsuarioDAO getUsuarioDAO() {
		// return new UsuarioDAOUsuarioUnico();
		return new UsuarioDAOMySQL("jdbc:mysql://localhost/ipartek", "root", "");
	}

	public static ProductoDAO getProductoDAO() {
		// return new UsuarioDAOUsuarioUnico();
		return new ProductoDAOMySQL("jdbc:mysql://localhost/ipartek", "root", "");
	}

	public static FacturaDAO getFacturaDAO() {
		// return new UsuarioDAOUsuarioUnico();
		return (FacturaDAO) new FacturaDAOMySQL("jdbc:mysql://localhost/ipartek", "root", "");
	}

}
