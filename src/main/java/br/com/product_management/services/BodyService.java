package br.com.product_management.services;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * @author mhatzlhoffer
 * 
 * Service to help process the body of the request
 *
 */
public final class BodyService {

	public static JsonObject getBodyObject(HttpServletRequest request) throws IOException {
		Gson gson = new Gson();
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    try {
	    	JsonObject jsonObject = gson.fromJson(buffer.toString(), JsonObject.class);
	    	return jsonObject;	
		} catch (JsonSyntaxException e) {
			return null;
		}	
	}
}
