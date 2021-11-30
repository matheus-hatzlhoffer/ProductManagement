package br.com.product_management.model;

import com.google.gson.JsonObject;

/**
 * @author mhatzlhoffer
 * Interface to help improve web/REST api responsiveness 
 */
public interface Model {
	public JsonObject toJson();
	public static boolean jsonIsObejct(JsonObject jsonObject) {
		return false;
	}
}
