package ipartek.formacion.odei.Controladores;

import ipartek.formacion.odei.DAL.ProductosDAL;
import ipartek.formacion.odei.Tipos.Carrito;
import ipartek.formacion.odei.Tipos.Producto;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		ProductosDAL dalProd = (ProductosDAL) application.getAttribute("dalProd");
		HttpSession session = request.getSession();
		String iniciado = (String) session.getAttribute("iniciado");
		if (iniciado == null) {
			((HttpServletResponse) response).sendRedirect("login");
		}
		log.info("El usuario es: " + iniciado);
		int ultimoId = 0;
		try {
			ultimoId = (Integer) application.getAttribute("ultimoId");
		} catch (NullPointerException npe) {
			application.setAttribute("ultimoId", 0);
		}

		String op = request.getParameter("op");

		if (op == null) {

			Producto[] productos = dalProd.buscarTodosLosProductos();

			request.setAttribute("productos", productos);

			request.getRequestDispatcher(RUTA_TIENDA).forward(request, response);

		} else {

			if (ultimoId == 0 || dalProd.existeProducto(ultimoId)) {
				ultimoId = dalProd.idNuevo();
				application.setAttribute("ultimoId", ultimoId);
			}

			int id = 0;
			Producto producto;

			switch (op) {
			case "modificar":

			case "borrar":
				id = Integer.parseInt(request.getParameter("id"));
			case "alta":
				if (op.equals("alta"))

					producto = new Producto(ultimoId, "", "", 0);

				else
					producto = dalProd.buscarPorId(id);
				request.setAttribute("producto", producto);
				request.getRequestDispatcher(RUTA_FORMULARIO).forward(request, response);

				break;
			case "comprar":

				id = Integer.parseInt(request.getParameter("id"));
				Carrito carrito = (Carrito) session.getAttribute("carrito");
				carrito.comprar(id);

				break;
			default:
				request.getRequestDispatcher(RUTA_TIENDA).forward(request, response);
			}
		}
	}
}
