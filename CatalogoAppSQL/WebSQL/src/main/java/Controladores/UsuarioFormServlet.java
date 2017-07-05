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
import org.DAO.UsuarioDAO;
import org.TIposSQL.Usuario;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class UsuarioFormServlet
 */
@WebServlet({ "/admin/usuarioform", "alta" })
public class UsuarioFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UsuarioFormServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		String user = request.getParameter("username");
		String nombre = request.getParameter("nombre");
		String pass = request.getParameter("pass");
		String pass2 = request.getParameter("pass2");
		String password = request.getParameter("password");
		int rol = 2;

		System.out.println("getparameter: " + user + "...");

		rol = Integer.parseInt(request.getParameter("rol"));

		RequestDispatcher rutaListado = request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_SERVLET_LISTADO);
		RequestDispatcher rutaFormulario = request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_FORMULARIO);
		String listado = "../usuariocrud";

		// response.setContentType("text/plain");
		// PrintWriter out = response.getWriter();
		// out.println(op);
		// out.println(nombre);
		// out.println(pass);
		// out.println(pass2);

		System.out.println("OP: " + op);
		log.info(op);
		if (op == null) {
			request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_LISTADO).forward(request, response);
			return;
		}
		if (rol != 1 || rol != 2)
			rol = 2;

		Usuario usuario = new Usuario(0, rol, nombre, pass, user);

		System.err.println("Usuario: " + usuario);

		ServletContext application = request.getServletContext();
		UsuarioDAO dao = (UsuarioDAO) application.getAttribute("daoUser");

		dao.abrir();
		switch (op) {

		case "registro":
			System.out.println("CASE REGISTRO");
			rutaListado = request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_LOGIN);

		case "alta":

			System.out.println("CASE ALTA");
			if (pass == null) {

				System.out.println("IF PASS null");
				request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_FORMULARIO).forward(request, response);
			} else {

				System.out.println("ELSE PASS NOT null");
				if (pass.equals(pass2) && !pass.equals("")) {

					System.out.println("IF PASS EQUALS");
					dao.insert(usuario);
					System.out.println(usuario);
					// request.getParameterMap().remove("op");// TODO arreglar esto
					rutaListado.forward(request, response);
				} else {

					System.out.println("ELSE PASS not EQUALS");
					usuario.setErrores("lascontraseñas no coinciden");
					request.setAttribute("usuario", usuario);
					rutaFormulario.forward(request, response);
				}

				response.sendRedirect(listado);
			}
			break;
		case "modificar":
			if (pass.equals(pass2)) {
				if (pass.equals(""))
					pass = password;
				try {
					dao.update(usuario);
				} catch (DAOException de) {
					usuario.setErrores("Usuario no en el limbo, resucitelo para poder usarlo");
					request.setAttribute("usuario", usuario);
					rutaListado.forward(request, response);
				}
				op = null;
				rutaListado.forward(request, response);
			} else {
				usuario.setErrores("Las contraseñas no coinciden");
				request.setAttribute("usuario", usuario);
				rutaFormulario.forward(request, response);
			}

			response.sendRedirect(listado);
			break;
		case "borrar":

			dao.delete(usuario);
			response.sendRedirect(listado);

			break;

		default:

			response.sendRedirect(listado);
			break;
		}
		dao.cerrar();
	}
}
