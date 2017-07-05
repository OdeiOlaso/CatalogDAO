package ipartek.formacion.odei.Controladores;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/admin/*" })
public class FiltroAdmin implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();

		// Usuario usuario = (Usuario) session.getAttribute("usuario");
		String iniciado = (String) session.getAttribute("iniciado");

		if ("admin".equals(iniciado)) {

			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendRedirect("../login");

		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
