package datamodel;

public class OrderItem {
	
	private String description;
	private final Article article;
	private int unitsOrdered;
	
	protected OrderItem(String descr, Article article, int units) {
		super();
		this.description = descr;
		this.article = article;
		this.unitsOrdered = units;
	}

	public String getDescription() {
		if(description == null) {
			setDescription("");
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUnitsOrdered() {
		if(unitsOrdered <= 0) {
			setUnitsOrdered(0);
		}
		return unitsOrdered;
	}

	public void setUnitsOrdered(int unitsOrdered) {
		this.unitsOrdered = unitsOrdered;
	}

	public Article getArticle() {
		return article;
	}
	

}
