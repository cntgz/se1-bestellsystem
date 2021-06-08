package datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	
	private final long id;
	private final Date date;
	private final Customer customer;
	private final List<OrderItem> items = new ArrayList<OrderItem>();;
	
	protected Order(long id, Date date, Customer customer) {
		this.id = id;
		this.date = date;
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<OrderItem> getItems() {
		return items;
	}
	
	public int count() {
		return items.size();
	}
	
	public Order addItem(OrderItem item) {
		items.add(item);
		return this;
	}
	
//	public Order removeItem(OrderItem item) {
//		items.remove(item);
//		return this;
//	}
//	
//	public Order clearItems() {
//		items.clear();
//		return this;
//	}
	
}
