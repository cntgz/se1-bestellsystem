package datamodel;

public class Customer {
	
	final private String id;
	private String firstName;
	private String lastName;
	private String contact;
	
	protected Customer(String id, String name, String contact) {
		this.id = id;
		this.firstName = "";
		this.lastName = name;
		this.contact = contact;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getId() {
		return id;
	}

}
