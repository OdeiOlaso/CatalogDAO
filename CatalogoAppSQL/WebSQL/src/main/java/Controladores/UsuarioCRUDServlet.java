package Controladores;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.DAO.UsuarioDAO;
import org.TIposSQL.Usuario;

/**
 * Servlet implementation class UsuarioCRUDServlet
 */
@WebServlet("/admin/usuariocrud")
public class UsuarioCRUDServlet extends HttpServlet {
	static final String RUTA_FORMULARIO = "/WEB-INF/vistas/usuarioform.jsp";
	static final String RUTA_LISTADO = "/WEB-INF/vistas/usuariocrud.jsp";
	static final String RUTA_SERVLET_LISTADO = "/admin/usuariocrud";
	static final String RUTA_LOGIN = "/login";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getServletContext();
		UsuarioDAO daoUser = (UsuarioDAO) application.getAttribute("daoUser");

		String op = request.getParameter("op");
		daoUser.abrir();
		if (op == null) {

			Usuario[] usuarios = daoUser.findAll();

			request.setAttribute("usuarios", usuarios);

			request.getRequestDispatcher(RUTA_LISTADO).forward(request, response);

		} else {
			int idUser = Integer.parseInt(request.getParameter("id"));
			Usuario usuario;
			switch (op) {
			case "modificar":

			case "borrar":
				usuario = daoUser.finById(idUser);
				request.setAttribute("usuario", usuario);

			case "alta":
				request.getRequestDispatcher(RUTA_FORMULARIO).forward(request, response);

				break;
			default:
				request.getRequestDispatcher(RUTA_LISTADO).forward(request, response);
			}
			daoUser.cerrar();
		}
	}
}
