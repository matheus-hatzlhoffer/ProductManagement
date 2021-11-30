package br.com.product_management.servelt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.product_management.model.Werehouse;

/**
 * Servlet implementation class GetProducts
 */
@WebServlet("/products")
public class GetProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Show all products
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Werehouse werehouse = new Werehouse();
		
		
		PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(werehouse.toJson());
        out.flush();
	}

}
