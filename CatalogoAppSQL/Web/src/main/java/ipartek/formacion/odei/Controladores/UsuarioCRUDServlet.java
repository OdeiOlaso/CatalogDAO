package ipartek.formacion.odei.Controladores;

import ipartek.formacion.odei.DAL.UsuariosDAL;
import ipartek.formacion.odei.Tipos.Usuario;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UsuarioCRUDServlet
 */
@WebServlet("/admin/usuariocrud")
public class UsuarioCRUDServlet extends HttpServlet {
	static final String RUTA_FORMULARIO = "/WEB-INF/vistas/usuarioform.jsp";
	static final String RUTA_LISTADO = "/WEB-INF/vistas/usuariocrud.jsp";
	static final String RUTA_SERVLET_LISTADO = "/admin/usuariocrud";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getServletContext();
		UsuariosDAL dalUser = (UsuariosDAL) application.getAttribute("dalUser");

		String op = request.getParameter("op");

		if (op == null) {

			Usuario[] usuarios = dalUser.buscarTodosLosUsuarios();

			request.setAttribute("usuarios", usuarios);

			request.getRequestDispatcher(RUTA_LISTADO).forward(request, response);

		} else {
			String idUser = request.getParameter("id");
			Usuario usuario;
			switch (op) {
			case "modificar":

			case "borrar":
				usuario = dalUser.buscarPorId(idUser);
				request.setAttribute("usuario", usuario);

			case "alta":
				request.getRequestDispatcher(RUTA_FORMULARIO).forward(request, response);

				break;
			default:
				request.getRequestDispatcher(RUTA_LISTADO).forward(request, response);
			}
		}
	}
}
