package br.com.product_management.services;

/**
 * @author mhatzlhoffer
 * 
 * Service to help process the URL of the request
 */
public final class URIService {
	public static Integer checkURILength(String uri) {
		if(uri == null) {
			return null;
		}
		return uri.split("/").length;
	}
	
	public static Integer getLastURIInteger(String uri) {
		if(uri == null) {
			return null;
		}
		String lastUriElement = uri.split("/")[uri.split("/").length-1];
		try {
			Integer lastUrInteger = Integer.parseInt(lastUriElement);
			return lastUrInteger;
		} catch (NumberFormatException e) {
			System.out.println(e);
			return null;
		}
	}

}
