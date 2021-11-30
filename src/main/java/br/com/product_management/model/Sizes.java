package br.com.product_management.model;

/**
 * @author mhatzlhoffer
 * Possible Sizes for the products
 */
public enum Sizes {
	PP("PP"), P("P"), M("M"), G("G"), XG("XG"), XXG("XXG");
	
	private String size;

	Sizes(String size) {
		this.size = size;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
}
