package datamodel;

public class Article {
	
	private String id;
	private String description;
	private long unitPrice;
	private int unitsInStore;
	
	protected Article(String id, String descr, long price, int units) {
		this.id = id;
		this.description = descr;
		this.unitPrice = price;
		this.unitsInStore = units;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public long getUnitPrice() {
		return unitPrice;
	}
	
	public int getUnitsInStore() {
		return unitsInStore;
	}
	
	public long getPresentValue() {
		return getUnitsInStore() * getUnitPrice();
	}

	public void setDescription(String descr) {
		this.description = descr;
	}
	
	public void setUnitPrice(long price) {
		this.unitPrice = price;
	}
	
	public void setUnitsInStore(int number) {
		this.unitsInStore = number;
	}
	
	

}
