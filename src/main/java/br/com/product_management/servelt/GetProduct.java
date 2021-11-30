package br.com.product_management.servelt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.product_management.errors.ErrorService;
import br.com.product_management.model.GenerictProductQuantity;
import br.com.product_management.model.Product;
import br.com.product_management.model.Werehouse;
import br.com.product_management.services.URIService;

/**
 * Servlet implementation class GetProduct
 */
@WebServlet("/product/*")
public class GetProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Get a product by Id
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		
		if(URIService.checkURILength(uri) != 3) {
			ErrorService.addErrorResponse("Error: Invalid URL", response);
	        return;
		}
		
		Integer productId = URIService.getLastURIInteger(uri);
		
		if(productId == null) {
			ErrorService.addErrorResponse("Error: Invalid URL", response);
	        return;
		}
		
		GenerictProductQuantity<Product> product = Werehouse.checkProductInInventory(productId);
		
		if(product == null) {
			ErrorService.addErrorResponse("Error: Product not found", response);
	        return;
		}
		
		PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(product.toJson());
        out.flush();
	}

}
