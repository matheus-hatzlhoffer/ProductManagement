/**
 * 
 */
package br.com.product_management.model;

import com.google.gson.JsonObject;

/**
 * @author mhatzlhoffer
 * This is a generic class to englobe the product and the quantity in the inventory
 * Help return all the data when searching a product
 */
public class GenerictProductQuantity <T extends Model> implements Model {
	public final int quantity;
	public final T object;

	public GenerictProductQuantity(int quantity, T object) {
		this.quantity = quantity;
		this.object = object;
	}
	
	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = object.toJson();
		jsonObject.addProperty("inventory_quantity", quantity);
		return jsonObject;
	}

	public static boolean jsonIsObejct(JsonObject jsonObject) {
		//TODO Verificar se há uma forma de fazer isso mais genérico
		return (Product.jsonIsObejct(jsonObject) && jsonObject.has("quantity"));
	}

}
