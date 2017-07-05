package ipartek.formacion.odei.Controladores;

import ipartek.formacion.odei.DAL.DALException;
import ipartek.formacion.odei.DAL.DALFactory;
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
 * Servlet implementation class ALtaServlet
 */
@WebServlet("/alta")
public class AltaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/* package */static final String RUTA_ALTA = LoginServlet.RUTA + "alta.jsp";
	/* package */static final String USUARIOS_DAL = "usuariosDAL";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nombre = request.getParameter("nombre");
		String pass = request.getParameter("pass");
		String pass2 = request.getParameter("pass2");

		Usuario usuario = new Usuario(nombre, pass);

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

				UsuariosDAL usuariosDAL = (UsuariosDAL) application.getAttribute(USUARIOS_DAL);

				if (usuariosDAL == null) {
					usuariosDAL = DALFactory.getUsuariosDAL();
				}

				try {
					usuariosDAL.alta(usuario);
				} catch (DALException de) {
					usuario.setNombre("");
					usuario.setErrores("El usuario ya existe. Elige otro");
					request.setAttribute("usuario", usuario);
				}

				application.setAttribute(USUARIOS_DAL, usuariosDAL);
			}
		}
		request.getRequestDispatcher(RUTA_ALTA).forward(request, response);
	}

	private boolean validarCampo(String campo) {
		return campo != null && campo.length() >= LoginServlet.MINIMO_CARACTERES;
	}

}
