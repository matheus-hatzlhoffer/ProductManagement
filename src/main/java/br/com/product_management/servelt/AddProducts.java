package br.com.product_management.servelt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.product_management.errors.ErrorService;
import br.com.product_management.model.Product;
import br.com.product_management.model.Sizes;
import br.com.product_management.model.Werehouse;
import br.com.product_management.services.EnumService;
import br.com.product_management.services.FormDataService;

/**
 * Servlet implementation class AddProducts
 */
@WebServlet("/products/add-products")
@MultipartConfig
public class AddProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * Add a product using Post method and CSV file in form data 
	 * Columns Template:
	 * NAME,VALUE,BRAND,SIZE,QUANTITY
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String csvInputString = FormDataService.csvReader(request);
		
		if(csvInputString.isEmpty()) {
			ErrorService.addErrorResponse("Error: CSV not found", response);
	        return;
		}
		
		String[] csvLines = csvInputString.split("\n");
		for (int i = 1; i < csvLines.length; i++) {
			String[] objectLine = csvLines[i].split(",");;
			String name = null;
			double value = 0;
			String brand = null;
			Sizes size = null;
			int quantity = 0;
			try {
				name = objectLine[0];
				value = Double.parseDouble(objectLine[1].replaceAll("\"", ""));
				brand = objectLine[2];
				size = EnumService.searchEnum(Sizes.class, objectLine[3]);
				quantity = Integer.parseInt(objectLine[4].trim());
			} catch (IllegalArgumentException e) {
				ErrorService.addErrorResponse("Error: CSV types or values are invalid", response);
		        return;
			}
			Product product = new Product(name, value, brand, size);
			Werehouse werehouse = new Werehouse();
			werehouse.addProduct(product, quantity);
		}
		
		PrintWriter out = response.getWriter();
		response.setStatus(201);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        out.print(csvInputString);
        out.flush();
	}



}
