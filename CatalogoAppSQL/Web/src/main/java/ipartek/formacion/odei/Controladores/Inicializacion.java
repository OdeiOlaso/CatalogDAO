package ipartek.formacion.odei.Controladores;

import ipartek.formacion.odei.DAL.DALFactory;
import ipartek.formacion.odei.DAL.ProductosDAL;
import ipartek.formacion.odei.DAL.UsuariosDAL;
import ipartek.formacion.odei.Tipos.Producto;
import ipartek.formacion.odei.Tipos.Usuario;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class Inicializacion
 *
 */
public class Inicializacion implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();

		ProductosDAL dalProd = (ProductosDAL) application.getAttribute("dalProd");
		UsuariosDAL dalUser = (UsuariosDAL) application.getAttribute("dalUser");

		dalProd = DALFactory.getProductosDAL();
		dalUser = DALFactory.getUsuariosDAL();

		dalUser.alta(new Usuario("admin", "admin"));

		for (int i = 1; i <= 13; i++) {

			dalProd.alta(new Producto(dalProd.idNuevo(), "Procusto" + i, "Descripcion" + i, i * 4.7));
			dalUser.alta(new Usuario("usuario" + i, "pass" + i));
		}

		application.setAttribute("dalProd", dalProd);
		application.setAttribute("dalUser", dalUser);

	}

	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
