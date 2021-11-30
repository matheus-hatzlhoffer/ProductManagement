package br.com.product_management.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonObject;

/**
 * @author mhatzlhoffer
 * 
 */
public class Discount implements Model {
	private final int discountId;
	private Double percentage;
	private String category;
	private Integer productId;
	LocalDate startDate;
	LocalDate endDate;
	private static int idCounter = 1;
	
	public Discount() {
		discountId = idCounter++;
	}
	
	public int getDiscountId() {
		return discountId;
	}
	
	public double getPercentage() {
		return percentage;
	}
	
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public boolean isActive() {
		return (LocalDate.now().isAfter(startDate) && LocalDate.now().isBefore(endDate));
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();
		if(LocalDate.now().isAfter(startDate) && LocalDate.now().isBefore(endDate)) {
			jsonObject.addProperty("isActive", true);
		}
		else {
			jsonObject.addProperty("isActive", false);			
		}
		jsonObject.addProperty("percentage", percentage);
		jsonObject.addProperty("startDate", startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		jsonObject.addProperty("endDate", endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		if(!category.isEmpty()) {
			jsonObject.addProperty("category", category);
		}
		if(productId != null) {
			jsonObject.addProperty("productId", productId);
		}
		return jsonObject;
	}
	
	public static boolean jsonIsObejct(JsonObject jsonObject) {
		if(!jsonObject.has("percentage")) {
			return false;
		}
		if(!jsonObject.has("startDate")) {
			return false;
		}
		if(!jsonObject.has("endDate")) {
			return false;
		}
		if(!jsonObject.has("productId") && !jsonObject.has("category")) {
			return false;
		}
		return true;
	}
}
