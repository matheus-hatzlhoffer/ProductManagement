/**
 * @author mhatzlhoffer
 * 
 * Class with standard procedure for error handling.
 *
 */

package br.com.product_management.errors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ErrorService {
	public static void addErrorResponse(String errorMessage, HttpServletResponse response) throws IOException {
		DefautError defautError = new DefautError();
		defautError.addMessage(errorMessage);
		PrintWriter out = response.getWriter();
		response.setStatus(400);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(defautError.toJson());
        out.flush();
	}

}
