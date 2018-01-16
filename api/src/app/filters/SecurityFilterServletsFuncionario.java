package app.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.controllers.LoginController;
import br.ufpr.funcionario.Funcionario;

@WebFilter(filterName = "/SecurityFilterServlets", urlPatterns = { "/dashboard", "/clientes", "/instituicoes",
		"/profile", "/GerenciarClientes", "/GerenciarInstituicoes", "/relatorioHistorico", "/relatorioStatus" })
public class SecurityFilterServletsFuncionario implements Filter {

	private LoginController loginController;

	public SecurityFilterServletsFuncionario() {
		loginController = new LoginController();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Funcionario f = (Funcionario) session.getAttribute("funcionario");

		String URL = req.getRequestURI().split("/").length >= 3 ? req.getRequestURI().split("/")[2] : "login.jsp";

		if (f != null && f.getEmail() != null) {
			chain.doFilter(request, response);
		} else {
			Cookie[] cookies = req.getCookies();
			for (Cookie c : cookies) {
				if (c.getName().equals("token") && c.getValue() != null && !c.getValue().isEmpty()) {
					String value = c.getValue();
					Funcionario i = loginController.getByToken(value);
					if (i != null && i.getIsAtivo()) {
						session.setAttribute("funcionario", i);
						res.sendRedirect(URL.equals("login.jsp") ? "dashboard" : URL);
						return;
					} else if (i != null && !i.getIsAtivo()) {
						RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
						request.setAttribute("erro", "Esta conta foi inativada");
						rd.forward(request, response);
						return;
					}
				}
			}
			RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
			request.setAttribute("erro", "Realize o Login para prosseguir");
			rd.forward(request, response);
			return;
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
