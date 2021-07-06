package datamodel;

public class Customer {
	
	final private String id;
	private String firstName;
	private String lastName;
	private String contact;
	
	protected Customer(String id, String name, String contact) {
		this.id = id;
		this.firstName = "";
		if(name != null || contact != null) {
			this.lastName = name;
			this.contact = contact;
		}
		else {
			this.lastName = "";
			this.contact = "";
		}
		
	
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName != null) {
			this.firstName = firstName;
		}
		else {
			firstName = "";
		}

	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName != null) {
			this.lastName = lastName;
		}
		else {
			lastName = "";
		}
	}
	

	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		if(contact != null) {
			this.contact = contact;
		}
		else {
			contact = "";
		}
	}

	public String getId() {
		return id;
	}

}
