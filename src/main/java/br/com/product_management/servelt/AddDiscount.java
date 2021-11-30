package br.com.product_management.servelt;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import br.com.product_management.errors.ErrorService;
import br.com.product_management.model.Discount;
import br.com.product_management.model.Product;
import br.com.product_management.model.Werehouse;
import br.com.product_management.services.BodyService;

/**
 * Servlet implementation class addDiscount
 */
@WebServlet("/discounts/add-discount")
public class AddDiscount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 *  Add a Dicount using Post method and json body (proprety without * is optional)
	 *  {
	 *    *"percentage" : 20.00,
     *    *"startDate" : "12/12/2020",
     *    *"endDate": "12/12/2022",
     *    *"type": "category",
     *    "category" : "azul"
     *    "productId" : 1
	 *  }
	 *  
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject jsonObject = BodyService.getBodyObject(request);
		
		if(jsonObject == null ) {
			ErrorService.addErrorResponse("Error: Invalid Body", response);
	        return;
		}
		
		if(!Discount.jsonIsObejct(jsonObject) ) {
			ErrorService.addErrorResponse("Error: Body has not the right properties", response);
	        return;
		}
		
		double percentage = 0;
		LocalDate startDate = null;
		LocalDate endDate = null;
		String category = null;
		int productId = 0;
		String type = null;
		
		try {
			percentage = jsonObject.get("percentage").getAsDouble();
			startDate = LocalDate.parse(jsonObject.get("startDate").getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			endDate = LocalDate.parse(jsonObject.get("endDate").getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			type = jsonObject.get("type").getAsString();
			if(type.equals("category")) {
				category = jsonObject.get("category").getAsString();
			}
			else if(type.equals("product")) {
				productId = jsonObject.get("productId").getAsInt();
			}
		} catch (IllegalArgumentException e) {
			ErrorService.addErrorResponse("Error: Body properties have wrongs types or values", response);
	        return;
		}

		Discount discount = new Discount();
		discount.setPercentage(percentage);
		discount.setStartDate(startDate);
		discount.setEndDate(endDate);
		discount.setCategory(category);
		discount.setProductId(productId);
		
		Werehouse werehouse = new Werehouse();
		
		if(type.equals("category")) {
			werehouse.addDiscountByCategory(discount);
		}
		else if(type.equals("product")) {
			Product product = werehouse.GetProduct(productId);
			product.addDiscount(discount);
		}
		
		PrintWriter out = response.getWriter();
		response.setStatus(201);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(discount.toJson());
        out.flush();
	}

}
