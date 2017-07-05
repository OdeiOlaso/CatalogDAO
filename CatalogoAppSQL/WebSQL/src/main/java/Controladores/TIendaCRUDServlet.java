package Controladores;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.DAO.ProductoDAO;
import org.TIposSQL.Carrito;
import org.TIposSQL.Producto;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class TIendaCRUDServlet
 */
@WebServlet("/tiendacrud")
public class TIendaCRUDServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(TIendaCRUDServlet.class);
	static final String RUTA_FORMULARIO = "/WEB-INF/vistas/productoform.jsp";
	static final String RUTA_TIENDA = "/WEB-INF/vistas/tiendacrud.jsp";
	static final String RUTA_SERVLET_LISTADO = "/tiendacrud";
	static final String RUTA_SERVLET_FORMULARIO = "/admin/productoform";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getServletContext();
		ProductoDAO daoProd = (ProductoDAO) application.getAttribute("daoProd");
		System.out.println("daoProd es: " + daoProd);
		HttpSession session = request.getSession();
		String iniciado = (String) session.getAttribute("iniciado");
		if (iniciado == null) {
			((HttpServletResponse) response).sendRedirect("login");
		}
		// log.info("El usuario es: " + iniciado);
		// int ultimoId = 0;
		// try {
		// ultimoId = (Integer) application.getAttribute("ultimoId");
		// } catch (NullPointerException npe) {
		// application.setAttribute("ultimoId", 0);
		// }

		String op = request.getParameter("op");

		daoProd.abrir();

		if (op == null) {

			Producto[] productos = daoProd.findAll();

			request.setAttribute("productos", productos);

			request.getRequestDispatcher(RUTA_TIENDA).forward(request, response);

		} else {

			// if (ultimoId == 0 || daoProd.existeProducto(ultimoId)) {
			// ultimoId = daoProd.idNuevo();
			// application.setAttribute("ultimoId", ultimoId);
			// }

			int id = 0;
			Producto producto;

			switch (op) {
			case "modificar":

			case "borrar":
				id = Integer.parseInt(request.getParameter("id"));
			case "alta":
				if (op.equals("alta"))

					producto = new Producto(0, "", "", 0);

				else
					producto = daoProd.finById(id);
				request.setAttribute("producto", producto);
				request.getRequestDispatcher(RUTA_FORMULARIO).forward(request, response);

				break;
			case "comprar":

				id = Integer.parseInt(request.getParameter("id"));
				System.out.println("Id prod:" + id);
				Carrito carrito = (Carrito) session.getAttribute("carrito");

				System.out.println("ANTES carrito:" + carrito);
				carrito.comprar(id);
				System.out.println("DESPUES carrito:" + carrito);
				op = null;

				session.setAttribute("carrito", carrito);

				response.sendRedirect("tiendacrud");
				break;
			default:
				request.getRequestDispatcher(RUTA_TIENDA).forward(request, response);
			}

			daoProd.cerrar();
		}
	}
}
