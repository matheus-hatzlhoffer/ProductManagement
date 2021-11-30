/**
 * 
 */
package br.com.product_management.model;

import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * @author mhatzlhoffer
 * In memory data storage using a hashmap
 */
public class Werehouse implements Model {
	private final int werehouseId;
	private int maxCapacity;
	private int inventoryQuantity;
	private static HashMap<Product, Integer> inventoryHashMap = new HashMap<Product, Integer>();
	private static int werehouseIdCounter = 1;
	
	public Werehouse() {
		this.maxCapacity = 500;
		this.werehouseId = werehouseIdCounter++;
		this.inventoryQuantity = 0;
	}
	
	public int getWerehouseId() {
		return werehouseId;
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}
	
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	
	public static HashMap<Product, Integer> getInventoryHashMap() {
		return inventoryHashMap;
	}
	
	public int remainigCapacity() {
		return this.maxCapacity - this.inventoryQuantity;
	}
	
	public Product GetProduct(int productId) {
		for (Product product : Werehouse.inventoryHashMap.keySet()) {
			if(product.getProductId() == productId) {
				return product;
			}
		}
		return null;
	}
	
	public GenerictProductQuantity<Product> checkProductInInventory(Product product) {
		if(inventoryHashMap.containsKey(product)) {
			GenerictProductQuantity<Product> productQuantity = new GenerictProductQuantity<Product>(inventoryHashMap.get(product), product);
			return productQuantity;
		}
		return null;
	}
	
	public static GenerictProductQuantity<Product> checkProductInInventory(int productId) {
		for (Product product : Werehouse.inventoryHashMap.keySet()) {
			if(product.getProductId() == productId) {
				GenerictProductQuantity<Product> productQuantity = new GenerictProductQuantity<Product>(inventoryHashMap.get(product), product);
				return productQuantity;
			}
		}
		return null;
	}
	
	public String addProduct(Product product, int quantity) {
		if(this.inventoryQuantity+quantity > this.maxCapacity) {
			return "Error: Maximum amount reached. Remainig capacity: " + this.remainigCapacity();
		}
		
		GenerictProductQuantity<Product> productQuantity = checkProductInInventory(product.getProductId());
		
		if(productQuantity == null) {
			inventoryHashMap.put(product, quantity);
		} else {
			inventoryHashMap.put(product, productQuantity.quantity + quantity);
		}
		
		return "Product added succesfully!";
	}
	
	public String deleteProduct(Product product, int quantity) {
		if(this.inventoryQuantity-quantity < 0) {
			return "Error: Maximum amount reached. Remainig capacity: " + this.remainigCapacity();
		}
		
		GenerictProductQuantity<Product> productQuantity = checkProductInInventory(product.getProductId());
		
		if(productQuantity == null) {
			return "Product not found.";
		} else {
			if(productQuantity.quantity - quantity < 0) {
				return "Error: There are not enough products for this operation";
			}
			inventoryHashMap.put(product, productQuantity.quantity - quantity);
		}
		
		return "Product removed succesfully!";
	}
	
	@Override
	public JsonObject toJson() {
		JsonArray jsonArray = new JsonArray();
		for (Product product : Werehouse.getInventoryHashMap().keySet()) {
			JsonObject productJsonObject = new JsonObject();
			productJsonObject.addProperty("id", product.getProductId());
			productJsonObject.addProperty("name", product.getName());
			productJsonObject.addProperty("value", product.getValue());
			productJsonObject.addProperty("brand", product.getBrand());
			productJsonObject.addProperty("inventory_quantity", inventoryHashMap.get(product));
			jsonArray.add(productJsonObject);
		}
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("Objects", jsonArray);
		return jsonObject;
	}

	public static boolean jsonIsObejct(JsonObject jsonObject) {
		if(jsonObject.has("Objects")) {			
			return false;
		}
		JsonArray objectsJsonArray = jsonObject.getAsJsonArray("Objects");
		for (int i = 0; i < objectsJsonArray.size(); i++) {
			JsonObject object = objectsJsonArray.get(i).getAsJsonObject();
			if(!GenerictProductQuantity.jsonIsObejct(object)){
				return false;
			}
		}
		return true;
	}

	public void addDiscountByCategory(Discount discount) {
		for (Product product : Werehouse.getInventoryHashMap().keySet()) {
			if(product.checkProductCategory(discount.getCategory())) {
				product.addDiscount(discount);
			}
		}
		
	}
}
