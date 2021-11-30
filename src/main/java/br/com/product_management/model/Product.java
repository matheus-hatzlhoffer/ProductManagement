package br.com.product_management.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author mhatzlhoffer
 *
 */
public class Product implements Model {
	private final int productId;
	private String name;
	private double value;
	private String brand;
	private Sizes size;
	private List<String> categoryList = new ArrayList<String>();
	private List<Discount> discounts = new ArrayList<Discount>();
	private static int idCounter = 1;
	
	public Product(String name, double value, String brand, Sizes size) {
		this.name = name;
		this.value = value;
		this.brand = brand;
		this.size = size;
		this.productId = idCounter++;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public Sizes getSize() {
		return size;
	}
	
	public void setSize(Sizes size) {
		this.size = size;
	}
	
	public List<String> getCategoryList() {
		return categoryList;
	}
	
	public void addCategory(String newCategory) {
		if(categoryList.contains(newCategory)) {
			return;
		}
		categoryList.add(newCategory);
	}
	
	public void removeCategory(String category) {
		categoryList.remove(category);
	}
	
	boolean checkProductCategory(String category) {
		return categoryList.contains(category);
	}
	
	public Discount seachDiscount(int discountId) {
		for (Discount discount : discounts) {
			if(discount.getDiscountId() == discountId) {
				return discount;
			}
		}
		return null;
	}
	
	public void addDiscount(Discount newDiscount) {
		Discount discount = seachDiscount(newDiscount.getDiscountId());
		if(discount == null) {			
			discounts.add(newDiscount);
		}
	}
	
	public void removeDiscount(int discountId) {
		Discount discount = seachDiscount(discountId);
		discounts.remove(discount);
	}
	
	@Override
	public JsonObject toJson() {
		Gson gson = new Gson();
		JsonObject jsonObject = (JsonObject) gson.toJsonTree(this);
		jsonObject.addProperty("size", size.getSize());
		return jsonObject;
	}

	public static boolean jsonIsObejct(JsonObject jsonObject) {
		if(!jsonObject.has("name")) {
			return false;
		}
		if(!jsonObject.has("value")) {
			return false;
		}
		if(!jsonObject.has("brand")) {
			return false;
		}
		if(!jsonObject.has("size")) {
			return false;
		}
		return true;
	}
}
