package system;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;

final class OutputProcessor implements Components.OutputProcessor {

	private InventoryManager inventoryManager;
	private OrderProcessor orderProcessor;

	OutputProcessor(InventoryManager inventoryManager, OrderProcessor orderProcessor) {

	}

	@Override
	public void printOrders(List<Order> orders, boolean printVAT) {
		int printLineWidth = 95;
		
		
		StringBuffer sbAllOrders = new StringBuffer( "-------------" );
		StringBuffer sbLineItem = new StringBuffer();
		
		//id, customer, items(list)
		int saldo = 0;
		for(Order order : orders) {
			Customer customer = order.getCustomer();
			String customerName = splitName(customer, customer.getFirstName() + " " + customer.getLastName()); // " Eric Schulz-Mueller"
																											//	"Eric Schulz-Mueller"
			String bestellung = "#" + order.getId() + ", " + customerName + "'s Bestellung:";
			int orderPrice = 0;
			for(OrderItem item : order.getItems()) {
				orderPrice += item.getUnitsOrdered() * item.getArticle().getUnitPrice(); 
				bestellung += " " + item.getUnitsOrdered() + "x " + item.getDescription();
			}
			saldo += orderPrice;
			sbLineItem = fmtLine(bestellung, fmtPrice(orderPrice, "", " EUR" ), printLineWidth);
			sbAllOrders.append("\n" + sbLineItem);
		}
		sbAllOrders.append( "\n" )
		.append( fmtLine( "-------------", "------------- -------------", printLineWidth ) + "\n")
		.append( fmtLine( "Gesamtwert aller Bestellungen:", fmtPrice(saldo, "", " EUR" ), printLineWidth));
		
		System.out.println(sbAllOrders.toString());
	}

	@Override
	public void printInventory(boolean sortedByInventoryValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String fmtPrice(long price, String currency) {
		String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
		return fmtPrice;
	}

	@Override
	public String fmtPrice(long price, String currency, int width) {
		String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
		return fmtPrice;
	}

	@Override
	public StringBuffer fmtLine(String leftStr, String rightStr, int totalWidth) {
		StringBuffer sb = new StringBuffer( leftStr );
		int shiftable = 0;		// leading spaces before first digit
		for( int i=1; rightStr.charAt( i ) == ' ' && i < rightStr.length(); i++ ) {
			shiftable++;
		}
		final int tab1 = totalWidth - rightStr.length() + 1;	// - ( seperator? 3 : 0 );
		int sbLen = sb.length();
		int excess = sbLen - tab1 + 1;
		int shift2 = excess - Math.max( 0, excess - shiftable );
		if( shift2 > 0 ) {
			rightStr = rightStr.substring( shift2, rightStr.length() );
			excess -= shift2;
		}
		if( excess > 0 ) {
			switch( excess ) {
			case 1:	sb.delete( sbLen - excess, sbLen ); break;
			case 2: sb.delete( sbLen - excess - 2 , sbLen ); sb.append( ".." ); break;
			default: sb.delete( sbLen - excess - 3, sbLen ); sb.append( "..." ); break;
			}
		}
		String strLineItem = String.format( "%-" + ( tab1 - 1 ) + "s%s", sb.toString(), rightStr );
		sb.setLength( 0 );
		sb.append( strLineItem );
		return sb;
	}

	@Override
	public String splitName(Customer customer, String name) {
		String firstName = "";
		String lastName = "";
		
		if(name.startsWith(" ")) {
			name = name.substring(1);
		}
		
		if(name.contains(",")) {
			String token[] = name.split(", ");
			firstName = token[1];
			lastName = token[0];
		}
		else if(name.contains(" ")) {
			String token[] = name.split(" ");
			if(token.length == 3) {
				firstName = token[0] + " " + token[1];
				lastName = token[2];
			}
			else {
				firstName = token[0];
				lastName = token[1];
			}
		}
		
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		
		return singleName(customer);
	}

	@Override
	public String singleName(Customer customer) {
		return customer.getLastName() + ", " + customer.getFirstName();
	}
	
	//private methods
	private String pad( String str, int width, boolean rightAligned ) {
		String fmtter = ( rightAligned? "%" : "%-" ) + width + "s";
		String padded = String.format( fmtter, str );
		return padded;
	}

	private String fmtPrice( long price, String prefix, String postfix ) {
		StringBuffer fmtPriceSB = new StringBuffer();
		if( prefix != null ) {
			fmtPriceSB.append( prefix );
		}

		fmtPriceSB = fmtPrice( fmtPriceSB, price );

		if( postfix != null ) {
			fmtPriceSB.append( postfix );
		}
		return fmtPriceSB.toString();
	}
	
	private StringBuffer fmtPrice( StringBuffer sb, long price ) {
		if( sb == null ) {
			sb = new StringBuffer();
		}
		double dblPrice = ( (double)price ) / 100.0;			// convert cent to Euro
		DecimalFormat df = new DecimalFormat( "#,##0.00",
			new DecimalFormatSymbols( new Locale( "de" ) ) );	// rounds double to 2 digits

		String fmtPrice = df.format( dblPrice );				// convert result to String in DecimalFormat
		sb.append( fmtPrice );
		return sb;
	}


}
