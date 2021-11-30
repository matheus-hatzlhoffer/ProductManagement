package br.com.product_management.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * @author mhatzlhoffer
 * 
 * Service to help process the form data of the request
 *
 */
public class FormDataService {
    private static final int BUFFER_SIZE = 2048;

	public static String csvReader(HttpServletRequest request) throws IOException, ServletException {
		String csvString="";
		for (Part part : request.getParts()) {
            csvString = csvString + getTextFromPart(part) + "\n";
        }
		return csvString;
	}
	
    private static String getTextFromPart(Part part) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
        StringBuilder value = new StringBuilder();
        char[] buffer = new char[BUFFER_SIZE];
        for (int length = 0; (length = reader.read(buffer)) > 0; ) {
            value.append(buffer, 0, length);
        }
        return value.toString();
    }

}
