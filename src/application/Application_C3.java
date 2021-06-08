package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;
import system.ComponentFactory;
import system.Components;

public class Application_C3 {

	public static void main(String[] args) {
		
		System.out.println("SE1-Bestellsystem");

	    ComponentFactory componentFactory = ComponentFactory.getInstance();
	    
	    Components.OutputProcessor outputProcessor = componentFactory.getOutputProcessor();
	    Components.DataFactory dataFactory = componentFactory.getDataFactory();
	    Components.OrderProcessor orderProcessor = componentFactory.getOrderProcessor();

	    List<Order> orders = getList(dataFactory);
//	    printList(orders);
//	    printID(orders);
	    
	    outputProcessor.printOrders(orders, false);
	    
	}
	
	
	public static List<Order> getList(Components.DataFactory dataFactory) {
		
		//Kunden
		Customer cEric = dataFactory.createCustomer("Eric Schulz-Mueller", "eric2346@gmail.com");
		Customer cAnne = dataFactory.createCustomer("Meyer, Anne", "+4917223524");
		Customer cNadine = dataFactory.createCustomer("Nadine Ulla Blumenfeld", "+4915292454");
		Customer cTimo = dataFactory.createCustomer("Timo Werner", "tw@gmail.com");
		Customer cSandra = dataFactory.createCustomer("Müller, Sandra", "samue62@gmx.de");

		//Artikeln
		Article aTasse = dataFactory.createArticle("Tasse", 299, 2000);
		Article aBecher = dataFactory.createArticle("Becher", 149, 8400);
		Article aKanne = dataFactory.createArticle("Kanne", 2000, 2400);
		Article aTeller = dataFactory.createArticle("Teller", 649, 7000 );
		Article aKaffeemaschine = dataFactory.createArticle("Kaffeemaschine", 2999, 500);
		Article aTeekocher = dataFactory.createArticle("Teekocher", 1999, 2000);
		
		// Eric's 1st order
		Order o1234 = dataFactory.createOrder(cEric);
		OrderItem oi1 = dataFactory.createOrderItem(aKanne.getDescription(), aKanne, 1);
		o1234.addItem(oi1);
		
		//Eric's 2nd order
		Order o5678 = dataFactory.createOrder(cEric);
		OrderItem oi2 = dataFactory.createOrderItem(aTeller.getDescription(), aTeller, 4);
		OrderItem oi3 = dataFactory.createOrderItem(aBecher.getDescription(), aBecher, 8);
		OrderItem oi4 = dataFactory.createOrderItem(aTasse.getDescription(), aTasse, 4);
		o5678.addItem(oi2).addItem(oi3).addItem(oi4);
		
		//Anne's order
		Order o3563 = dataFactory.createOrder(cAnne);
		OrderItem oi5 = dataFactory.createOrderItem(aKanne.getDescription(), aKanne, 1);
		o3563.addItem(oi5);
		
		//Nadine Ulla's order
		Order o6135 = dataFactory.createOrder(cNadine);
		OrderItem oi6 = dataFactory.createOrderItem(aTeller.getDescription(), aTeller, 12);
		o6135.addItem(oi6);
		
		//Timo's order
		Order o6743 = dataFactory.createOrder(cTimo);
		OrderItem oi7 = dataFactory.createOrderItem(aKaffeemaschine.getDescription(), aKaffeemaschine, 1);
		OrderItem oi8 = dataFactory.createOrderItem(aTasse.getDescription(), aTasse, 6);
		o6743.addItem(oi7).addItem(oi8);
		
		//Sandra's order		
		Order o2678 = dataFactory.createOrder(cSandra);
		OrderItem oi9 = dataFactory.createOrderItem(aTeekocher.getDescription(), aTeekocher, 1);
		OrderItem oi10 = dataFactory.createOrderItem(aBecher.getDescription(), aBecher, 4);
		OrderItem oi11 = dataFactory.createOrderItem(aTeller.getDescription(), aTeller, 4);
		o2678.addItem(oi9).addItem(oi10).addItem(oi11);
				
		return new ArrayList<Order>(List.of(o1234, o5678, o3563, o6135, o6743, o2678));
	}
	
//	public static List<Order> getList2(){
//		Customer cEric = new Customer( "C86516", "Eric Schulz-Mueller", "eric2346@gmail.com" );
//		Customer cAnne = new Customer( "C64327", "Meyer, Anne", "+4917223524" );
//		Customer cNadine = new Customer( "C12396", "Nadine Ulla Blumenfeld", "+4915292454" );
//		//zusaetzlich
//		Customer cTimo = new Customer("C77777", "Timo Werner", "tw@gmail.com");
//		Customer cSandra = new Customer("C88888", "Müller, Sandra", "samue62@gmx.de");
//
//		Article aTasse = new Article( "SKU-458362", "Tasse", 299, 2000 );
//		Article aBecher = new Article( "SKU-693856", "Becher", 149, 8400 );
//		Article aKanne = new Article( "SKU-518957", "Kanne", 2000, 2400 );
//		Article aTeller = new Article( "SKU-638035", "Teller", 649, 7000 );
//		//zusaetzlich
//		Article aKaffeemaschine = new Article("SKU-565656", "Kaffeemaschine", 2999, 500);
//		Article aTeekocher = new Article("SKU-676767", "Teekocher", 1999, 2000);
//		
//		//Bestellungen Timo Werner und Sandra Müller
//		Order o1234 = new Order(6342332132L, new Date(), cTimo);
//		Order o5678 = new Order(1236742321L, new Date(), cSandra);
//		o1234.addItem(
//				new OrderItem(aKaffeemaschine.getDescription(), aKaffeemaschine, 1)
//				).addItem(new OrderItem(aTasse.getDescription(), aTasse, 6));
//		
//		o5678.addItem(new OrderItem(aTeekocher.getDescription(), aTeekocher, 1)
//				).addItem(new OrderItem(aBecher.getDescription(),aBecher, 4)
//				).addItem(new OrderItem(aTeller.getDescription(), aTeller, 4));
//		//------
//		
//		// Eric's 1st order
//		Order o5234 = new Order( 5234968294L, new Date(), cEric );
//		OrderItem oi1 = new OrderItem( aKanne.getDescription(), aKanne, 1 );	// 1x Kanne
//		o5234.addItem( oi1 );	// add OrderItem to Order 5234968294L
//
//		// Eric's 2nd order
//		Order o8592 = new Order( 8592356245L, new Date(), cEric );
//		o8592.addItem(	// add three OrderItems to Order 8592356245L
//			new OrderItem( aTeller.getDescription(), aTeller, 4 )		// 4x Teller
//		).addItem(
//			new OrderItem( aBecher.getDescription(), aBecher, 8 )		// 8x Becher
//		).addItem(
//			new OrderItem( "passende Tassen", aTasse, 4 )				// 4x passende Tassen
//		);
//
//		// Anne's order
//		Order o3563 = new Order( 3563561357L, new Date(), cAnne );
//		o3563.addItem(
//			new OrderItem( aKanne.getDescription() + " aus Porzellan", aKanne, 1 )
//		);
//
//		// Nadine's order
//		Order o6135 = new Order( 6135735635L, new Date(), cNadine );
//		o6135.addItem(													// 12x Teller
//			new OrderItem( aTeller.getDescription() + " blau/weiss Keramik", aTeller, 12 )
//		);
//
//		return new ArrayList<Order>( List.of( o5234, o8592, o3563, o6135, o1234, o5678 ) );
//	}

	public static void printList(List<Order> orders) {
		orders.forEach(e -> {
			System.out.println(e.getCustomer().getLastName());
		});
	}

	public static void printID(List<Order> orders) {
		orders.forEach(e -> {
			System.out.println(e.getCustomer().getId());
		});
	}
}
