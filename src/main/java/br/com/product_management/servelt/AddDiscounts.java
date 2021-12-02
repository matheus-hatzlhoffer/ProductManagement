package br.com.product_management.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.product_management.errors.ErrorService;
import br.com.product_management.model.Discount;
import br.com.product_management.model.Product;
import br.com.product_management.model.Werehouse;
import br.com.product_management.services.FormDataService;

/**
 * Servlet implementation class AddDiscounts
 */
@WebServlet("/discounts/add-discounts")
@MultipartConfig
public class AddDiscounts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String csvInputString = FormDataService.csvReader(request);
		
		if(csvInputString.isEmpty()) {
			ErrorService.addErrorResponse("Error: CSV not found", response);
	        return;
		}
		
		String[] csvLines = csvInputString.split("\n");
		for (int i = 1; i < csvLines.length; i++) {
			String[] objectLine = csvLines[i].split(",");
			double percentage = 0;
			LocalDate startDate = null;
			LocalDate endDate = null;
			int productId = 0;
			String category = null;
			String type = null;
			try {
				percentage = Double.parseDouble(objectLine[0].replaceAll("\"", ""));
				System.out.println(objectLine[1]);
				System.out.println(objectLine[2]);
				startDate = LocalDate.parse(objectLine[1],DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				startDate = LocalDate.parse(objectLine[2],DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				type = objectLine[3];
				
				if(type.equals("category")) {
					category = objectLine[4];
				} 
				else if (type.equals("product")) {
					System.out.println(objectLine[4]);
					System.out.println(objectLine[4].getClass());
					productId = Integer.parseInt(objectLine[4].trim());
				}
			} catch (IllegalArgumentException e) {
				ErrorService.addErrorResponse("Error: CSV types or values are invalid", response);
		        return;
			}
			
			Discount discount = new Discount();
			discount.setPercentage(percentage);
			discount.setStartDate(startDate);
			discount.setEndDate(endDate);
			
			Werehouse werehouse = new Werehouse();
			
			if(type.equals("category")) {
				discount.setCategory(category);
				werehouse.addDiscountByCategory(discount);
			}
			else if(type.equals("product")) {
				discount.setProductId(productId);
				Product product = werehouse.GetProduct(productId);
				if(product == null) {
					ErrorService.addErrorResponse("Error: Id does not exist", response);
			        return;
				}
				product.addDiscount(discount);
			}
		}
		
		PrintWriter out = response.getWriter();
		response.setStatus(201);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        out.print(csvInputString);
        out.flush();

	}

}
