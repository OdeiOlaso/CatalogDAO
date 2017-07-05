package Controladores;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.DAO.DAOFactory;
import org.DAO.ProductoDAO;
import org.DAO.UsuarioDAO;
import org.TIposSQL.Producto;

/**
 * Application Lifecycle Listener implementation class Inicializacion
 *
 */
public class Inicializacion implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();

		ProductoDAO daoProd;// = (ProductoDAO) application.getAttribute("daoProd");
		UsuarioDAO daoUser;// = (UsuarioDAO) application.getAttribute("daoUser");

		daoProd = DAOFactory.getProductoDAO();
		daoUser = DAOFactory.getUsuarioDAO();

		// daoUser.abrir();
		//
		// daoUser.insert(new Usuario(0, 1, null, "admin", "admin"));
		// daoUser.cerrar();

		daoProd.abrir();
		// System.out.println("daoProd:" + daoProd);

		String result = "", clave = "Procusto      ";
		char c, d;
		int e, u;

		for (int i = 1; i <= 13; i++) {
			result = "";

			u = (int) Math.floor(Math.random() * i);

			c = clave.charAt(i);
			d = clave.charAt(clave.length() - i - 1);

			e = c * (clave.length() - u) * c * c * (c * d / 2) / (d * d * (u + 1));

			System.out.println(e);

			result = e + result;
			// System.out.println(result);

			byte[] a = result.getBytes(StandardCharsets.UTF_8);

			final String vuelta = Base64.getEncoder().encodeToString(a);
			// System.out.println(vuelta);

			daoProd.insert(new Producto(0, vuelta + " " + i, vuelta + " Descripcion " + i, (i * u) / 4.7));
			// daoUser.insert(new Usuario(i, i, "usuario" + i, "pass" + i, null));
		}
		daoProd.cerrar();

		// System.out.println("POst FOR daoProd:" + daoProd);
		application.setAttribute("daoProd", daoProd);
		application.setAttribute("daoUser", daoUser);

		// System.out.println("POst all daoProd:" + daoProd);

	}

	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
