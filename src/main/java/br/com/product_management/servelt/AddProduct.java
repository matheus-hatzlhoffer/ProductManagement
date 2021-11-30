package br.com.product_management.servelt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.com.product_management.errors.ErrorService;
import br.com.product_management.model.GenerictProductQuantity;
import br.com.product_management.model.Product;
import br.com.product_management.model.Sizes;
import br.com.product_management.model.Werehouse;
import br.com.product_management.services.BodyService;
import br.com.product_management.services.EnumService;

/**
 * Servlet implementation class AddProduct
 */
@WebServlet("/products/add-product")
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 *  Add a product using Post method and json body (proprety without * is optional)
	 *  {
     *     *"name":"Camista Lacoste",
     *     *"value": 122.22,
     *     *"brand": "Lacoste",
     *     *"size" : "p",
     *     *"quantity" : 9,
     *     "categories": [
     *       "azul",
     *       "de marca"
     *     ]
     *  }
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject jsonObject = BodyService.getBodyObject(request);
		
		if(jsonObject == null ) {
			ErrorService.addErrorResponse("Error: Invalid Body", response);
	        return;
		}
		
		if(!GenerictProductQuantity.jsonIsObejct(jsonObject) ) {
			ErrorService.addErrorResponse("Error: Body has not the right properties", response);
	        return;
		}
		
		String name = null;
		double value = 0;
		String brand = null;
		Sizes size = null;
		int quantity = 0;
		
		try {
			name = jsonObject.get("name").getAsString();
			value = jsonObject.get("value").getAsDouble();
			brand = jsonObject.get("brand").getAsString();
			size = EnumService.searchEnum(Sizes.class, jsonObject.get("size").getAsString());
			quantity = jsonObject.get("quantity").getAsInt();
		} catch (IllegalArgumentException e) {
			ErrorService.addErrorResponse("Error: Body properties have wrongs types or values", response);
	        return;
		}
		Product product = new Product(name, value, brand, size);
		
		if(jsonObject.has("categories")) {
			JsonArray jsonArray = jsonObject.get("categories").getAsJsonArray();
			for (JsonElement jsonElement : jsonArray) {
				String category =  jsonElement.getAsString();
				product.addCategory(category);
			}
		}
		
		Werehouse werehouse = new Werehouse();
		werehouse.addProduct(product, quantity);
		
		PrintWriter out = response.getWriter();
		response.setStatus(201);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(product.toJson());
        out.flush();
		
	}

}
