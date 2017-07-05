package Controladores;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.DAO.ProductoDAO;
import org.TIposSQL.CantidadProductos;
import org.TIposSQL.Carrito;
import org.TIposSQL.Factura;
import org.TIposSQL.Usuario;

/**
 * Servlet implementation class CarritoCRUD
 */
public class CarritoCRUD extends HttpServlet {
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

		String op = request.getParameter("op");
		int idQuitar = 0, cantidad = 1;

		Carrito carrito = (Carrito) session.getAttribute("carrito");
		//
		// HashMap<Integer, Integer> compra = carrito.getCarrito();
		//
		// CantidadProductos[] productosCarrito = new CantidadProductos[compra.size()];
		// // TODO hacer clase cantidadProducto y meter en array
		// // parra llamar poner ${cantidadProducto.producto.id}
		// if (op == null) {
		// int e = 0;
		// daoProd.abrir();
		// for (int id : compra.keySet()) {
		//
		// productosCarrito[e++] = new CantidadProductos(daoProd.finById(id), compra.get(id));
		//
		// }
		//
		// daoProd.cerrar();
		//
		// request.setAttribute("productosCarrito", productosCarrito);
		//
		// request.getRequestDispatcher("/WEB-INF/vistas/carrito.jsp").forward(request, response);
		//
		// } else {
		System.out.println("OP: " + op);
		switch (op != null ? op : "NULO") {
		case "comprar":
			Usuario usuario = new Usuario();

			int numeroFactura = 0;

			Factura factura = new Factura(numeroFactura, carrito, usuario);

			break;
		case "quitar":
			System.out.println("quitar");
			cantidad = 0;

		case "menos":
			System.out.println("menos");
			idQuitar = Integer.parseInt(request.getParameter("id"));

			System.out.println("ID: " + idQuitar);
			System.out.println("cantidad: " + cantidad);

			System.out.println("Carrito: " + carrito);
			carrito.quitar(idQuitar, cantidad);
			System.out.println("Carrito: " + carrito);

			session.setAttribute("carrito", carrito);
		default:
			System.out.println("default");

			carrito = (Carrito) session.getAttribute("carrito");
			HashMap<Integer, Integer> compra = carrito.getCarrito();

			CantidadProductos[] productosCarrito = new CantidadProductos[compra.size()];
			int e = 0;
			daoProd.abrir();
			for (int id : compra.keySet()) {

				productosCarrito[e++] = new CantidadProductos(daoProd.finById(id), compra.get(id));

			}

			daoProd.cerrar();

			request.setAttribute("productosCarrito", productosCarrito);

			request.getRequestDispatcher("/WEB-INF/vistas/carrito.jsp").forward(request, response);
			break;
		}
		// }

	}
}
