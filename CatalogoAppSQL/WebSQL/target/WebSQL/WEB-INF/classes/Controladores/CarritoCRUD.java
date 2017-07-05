package Controladores;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.DAO.ProductoDAO;
import org.TIposSQL.Carrito;
import org.TIposSQL.Producto;

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
		log.info("El usuario es: " + iniciado);
		int ultimoId = 0;
		try {
			ultimoId = (Integer) application.getAttribute("ultimoId");
		} catch (NullPointerException npe) {
			application.setAttribute("ultimoId", 0);
		}

		Carrito carrito = (Carrito) session.getAttribute("carrito");


		daoProd.abrir();

		
			for (int i = 0; i < carrito.length; i++) {
				
			//TODO acabar esto lista de productos desde id de carrito
			Producto[] productos = daoProd.finById(i);

			request.setAttribute("productos", productos);


			daoProd.cerrar();
			}
	}

}
