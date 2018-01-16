package app.servlets.cliente.relatorios;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet("/relatorioHistorico")
public class RelatorioHistorico extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RelatorioHistorico() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbSistemaDOR", "root", "");
			String jasper = request.getContextPath() + "/report1Dac.jasper";

			String host = "http://" + request.getServerName() + ":" + request.getServerPort();

			URL jasperURL = new URL(host + jasper);

			HashMap params = new HashMap();
			params.put("cliente_id", Integer.parseInt(request.getParameter("cliente")));

			byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params, conn);

			if (bytes != null) {

				// A página será mostrada em PDF

				response.setContentType("application/pdf");

				// Envia o PDF para o Cliente

				OutputStream ops = null;

				ops = response.getOutputStream();

				ops.write(bytes);

			}
		} catch (ClassNotFoundException | SQLException | JRException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
