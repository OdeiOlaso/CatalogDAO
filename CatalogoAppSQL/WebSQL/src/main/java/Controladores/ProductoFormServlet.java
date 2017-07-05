package Controladores;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.DAO.DAOException;
import org.DAO.ProductoDAO;
import org.TIposSQL.Producto;

/**
 * Servlet implementation class ProductoFormServlet
 */
@WebServlet("/admin/productoform")
public class ProductoFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rutaTienda = request.getRequestDispatcher(TIendaCRUDServlet.RUTA_SERVLET_LISTADO);
		RequestDispatcher rutaFormulario = request.getRequestDispatcher(TIendaCRUDServlet.RUTA_FORMULARIO);
		String tienda = "../tiendacrud";

		Producto producto;

		String op = request.getParameter("ops");
		String idString = request.getParameter("id");
		int id = Integer.parseInt(idString);
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		double precioviejo;
		try {
			precioviejo = Double.parseDouble(request.getParameter("precioviejo"));
		} catch (NullPointerException e) {
			// precioviejo = Double.parseDouble(request.getParameter("precio"));
			precioviejo = 0;
		}

		double precio;
		try {
			precio = Double.parseDouble(request.getParameter("precio"));
			producto = new Producto(id, nombre, descripcion, precio);
		} catch (NumberFormatException ne) {
			producto = new Producto(id, nombre, descripcion, precioviejo);
			producto.setErrores("El precio a de no es numerico");

			request.setAttribute("producto", producto);
			rutaFormulario.forward(request, response);
			return;

		}

		if (op == null) {
			request.getRequestDispatcher(TIendaCRUDServlet.RUTA_TIENDA).forward(request, response);
			return;
		}

		ServletContext application = request.getServletContext();
		ProductoDAO dao = (ProductoDAO) application.getAttribute("daoProd");

		dao.abrir();
		switch (op) {
		case "alta":
			// dao.idNuevo();
			if (nombre != "" && precio > 0) {
				dao.insert(producto);

				response.sendRedirect(tienda);
			} else {
				rutaFormulario.forward(request, response);
			}

			break;
		case "modificar":

			try {
				dao.update(producto);
			} catch (DAOException de) {
				producto.setErrores("Producto en el limbo, resucitelo para poder usarlo");
				System.out.println(de);
				request.setAttribute("producto", producto);
				rutaFormulario.forward(request, response);
			}
			response.sendRedirect(tienda);

			break;
		case "borrar":
			dao.delete(producto);
			response.sendRedirect(tienda);
			// rutaListado.forward(request, response);
			break;

		default:

			response.sendRedirect(tienda);
			break;
		}

		dao.cerrar();
	}
}
