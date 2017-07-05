package ipartek.formacion.odei.Controladores;

import ipartek.formacion.odei.DAL.DALException;
import ipartek.formacion.odei.DAL.UsuariosDAL;
import ipartek.formacion.odei.Tipos.Usuario;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String nombre = request.getParameter("nombre");
		String pass = request.getParameter("pass");
		String pass2 = request.getParameter("pass2");

		RequestDispatcher rutaListado = request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_SERVLET_LISTADO);
		RequestDispatcher rutaFormulario = request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_FORMULARIO);
		String listado = "../usuariocrud";

		// response.setContentType("text/plain");
		// PrintWriter out = response.getWriter();
		// out.println(op);
		// out.println(nombre);
		// out.println(pass);
		// out.println(pass2);

		log.info(op);
		if (op == null) {
			request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_LISTADO).forward(request, response);
			return;
		}

		Usuario usuario = new Usuario(nombre, pass);

		ServletContext application = request.getServletContext();
		UsuariosDAL dal = (UsuariosDAL) application.getAttribute("dalUser");

		switch (op) {

		case "registro":

		case "alta":

			if (pass == null) {
				request.getRequestDispatcher(UsuarioCRUDServlet.RUTA_FORMULARIO).forward(request, response);
			} else {
				if (pass.equals(pass2)) {
					dal.alta(usuario);
					request.getParameterMap().remove("op");// TODO arreglar esto
					rutaListado.forward(request, response);
				} else {
					usuario.setErrores("lascontraseñas no coinciden");
					request.setAttribute("usuario", usuario);
					rutaFormulario.forward(request, response);
				}

				response.sendRedirect(listado);
			}
			break;
		case "modificar":
			if (pass.equals(pass2)) {
				try {
					dal.modificar(usuario);
				} catch (DALException de) {
					usuario.setErrores("Usuario no en el limbo, resucitelo para poder usarlo");
					request.setAttribute("usuario", usuario);
					rutaListado.forward(request, response);
				}
				rutaListado.forward(request, response);
			} else {
				usuario.setErrores("Las contraseñas no coinciden");
				request.setAttribute("usuario", usuario);
				rutaFormulario.forward(request, response);
			}

			response.sendRedirect(listado);
			break;
		case "borrar":

			dal.borrar(usuario);
			response.sendRedirect(listado);

			break;

		default:

			response.sendRedirect(listado);
			break;
		}
	}
}
