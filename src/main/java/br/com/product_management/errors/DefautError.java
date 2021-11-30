package br.com.product_management.errors;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author mhatzlhoffer
 * Default error dispatch class in Java
 */
public class DefautError {
	List<String> errorMessages = new ArrayList<String>();
	
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	
	public void addMessage(String message) {
		this.errorMessages.add(message);
	}
	
	public JsonArray toJson() {
		JsonArray errorJsonArray = new JsonArray();
		for (String error : errorMessages) {
			JsonObject errorJsonObject = new JsonObject();
			errorJsonObject.addProperty("message", error);;
			errorJsonArray.add(errorJsonObject);
		}
		return errorJsonArray;
	}
}
