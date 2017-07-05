package Controladores;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.DAO.DAOException;
import org.DAO.DAOFactory;
import org.DAO.UsuarioDAO;
import org.TIposSQL.Usuario;

/**
 * Servlet implementation class ALtaServlet
 */
@WebServlet("/altaSDASD")
public class AltaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/* package */static final String RUTA_ALTA = LoginServlet.RUTA + "alta.jsp";
	/* package */static final String USUARIO_DAO = "usuarioDAO";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nombre = request.getParameter("nombre");
		String pass = request.getParameter("pass");
		String pass2 = request.getParameter("pass2");

		int id_roles = 2;
		String nombre_completo = null;

		Usuario usuario = new Usuario(0, id_roles, nombre_completo, pass, nombre);

		boolean hayDatos = nombre != null && pass != null && pass2 != null;
		boolean datosCorrectos = validarCampo(nombre) && validarCampo(pass) && validarCampo(pass2);
		boolean passIguales = pass != null && pass.equals(pass2);

		if (hayDatos) {
			if (!datosCorrectos) {
				usuario.setErrores("Todos los campos son requeridos y con un minimo " + LoginServlet.MINIMO_CARACTERES + " caracteres");
				request.setAttribute("usuario", usuario);

			} else if (!passIguales) {
				usuario.setErrores("Las contraseñas han de ser iguales.");
				request.setAttribute("usuario", usuario);

			} else {
				ServletContext application = request.getServletContext();

				UsuarioDAO usuarioDAO = (UsuarioDAO) application.getAttribute(USUARIO_DAO);

				if (usuarioDAO == null) {
					usuarioDAO = DAOFactory.getUsuarioDAO();
				}

				try {
					usuarioDAO.insert(usuario);
				} catch (DAOException de) {
					usuario.setUsername("");
					usuario.setErrores("El usuario ya existe. Elige otro");
					request.setAttribute("usuario", usuario);
				}

				application.setAttribute(USUARIO_DAO, usuarioDAO);
			}
		}
		request.getRequestDispatcher(RUTA_ALTA).forward(request, response);
	}

	private boolean validarCampo(String campo) {
		return campo != null && campo.length() >= LoginServlet.MINIMO_CARACTERES;
	}

}
