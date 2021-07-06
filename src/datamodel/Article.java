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
		if(description == null) {
			setDescription("");
		}
		return description;
	}
	
	public long getUnitPrice() {
		if(unitPrice <= 0 || unitPrice > 99999999) {
			setUnitPrice(0);
		}
		return unitPrice;
	}
	
	public int getUnitsInStore() {
		if(unitsInStore <= 0 || unitsInStore > 99999999) {
			setUnitsInStore(0);
		}
		return unitsInStore;
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
