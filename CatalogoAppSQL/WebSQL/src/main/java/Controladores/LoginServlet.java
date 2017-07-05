package Controladores;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.DAO.DAOFactory;
import org.DAO.UsuarioDAO;
import org.TIposSQL.Carrito;
import org.TIposSQL.Usuario;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(LoginServlet.class);

	/* package */static final String RUTA = "/WEB-INF/vistas/";
	private static final String RUTA_LOGIN = RUTA + "login.jsp";
	private static final String RUTA_TIENDA = "/tiendacrud";

	public static final int TIEMPO_INACTIVIDAD = 30 * 60;

	public static final int MINIMO_CARACTERES = 4;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recoger datos de vistas
		String nombre = request.getParameter("username");
		String pass = request.getParameter("pass");

		String opcion = request.getParameter("opcion");

		// Crear modelos en base a los datos
		Usuario usuario = new Usuario();
		usuario.setUsername(nombre);
		usuario.setPassword(pass);

		// Llamada a lógica de negocio
		ServletContext application = request.getServletContext();

		UsuarioDAO usuarioDAO = (UsuarioDAO) application.getAttribute("daoUser");

		if (usuarioDAO == null) {
			usuarioDAO = DAOFactory.getUsuarioDAO();
		}

		// Sólo para crear una base de datos falsa con el
		// contenido de un usuario "javi", "lete"
		// usuarioDAO.alta(new Usuario("javi", "lete"));

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(TIEMPO_INACTIVIDAD);
		Carrito carrito = new Carrito();
		session.setAttribute("carrito", carrito);

		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setMaxAge(TIEMPO_INACTIVIDAD);
		response.addCookie(cookie);

		// for (Cookie cookie : request.getCookies()) {
		// if ("JSESSIONID".equals(cookie.getName())) {
		// cookie.setMaxAge(TIEMPO_INACTIVIDAD);
		// response.addCookie(cookie);
		// }
		// }

		// ESTADOS

		usuarioDAO.abrir();

		boolean esValido = usuarioDAO.validar(usuario);

		boolean sinParametros = usuario.getUsername() == null;

		boolean esUsuarioYaRegistrado = session.getAttribute("usuario") != null;

		boolean quiereSalir = "logout".equals(opcion);

		boolean nombreValido = usuario.getUsername() != null && usuario.getUsername().length() >= MINIMO_CARACTERES;
		boolean passVaLlido = !(usuario.getPassword() == null || usuario.getPassword().length() < MINIMO_CARACTERES);

		String iniciado = usuario.getUsername();
		// Redirigir a una nueva vista
		if (quiereSalir) {
			session.invalidate();
			request.getRequestDispatcher(RUTA_LOGIN).forward(request, response);
		} else if (esUsuarioYaRegistrado) {
			session.setAttribute("iniciado", iniciado);
			request.getRequestDispatcher(RUTA_TIENDA).forward(request, response);
		} else if (sinParametros) {
			request.getRequestDispatcher(RUTA_LOGIN).forward(request, response);
		} else if (!nombreValido || !passVaLlido) {
			usuario.setErrores("EL nombre  y la pass deben tener como mínimo " + MINIMO_CARACTERES + " caracteres y son ambos requeridos");
			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher(RUTA_LOGIN).forward(request, response);
		} else if (esValido) {
			session.setAttribute("iniciado", iniciado);
			session.setAttribute("usuario", usuario);
			response.sendRedirect("tiendacrud");
			// request.getRequestDispatcher(RUTA_TIENDA).forward(request, response);
		} else {
			session.setAttribute("iniciado", "iniciado");
			log.info("Usuario usuario:" + usuario);
			// session.setAttribute("usuario", usuario);
			usuario.setErrores("El usuario y contraseña introducidos no son válidos");
			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher(RUTA_LOGIN).forward(request, response);
		}

		usuarioDAO.cerrar();
	}
}
