package application;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;
import system.ComponentFactory;
import system.Components;


public class Application_2 {

	private List<Article> catalog = new ArrayList<Article>();


	/**
	 * main() to launch application.
	 * 
	 * @param args command line arguments
	 */
	public static void main( String[] args ) {
		Application_2 a2 = new Application_2();
		a2.main_();
		
		System.out.println( "SE1-Bestellsystem" );
	    ComponentFactory componentFactory = ComponentFactory.getInstance();
	    Components.DataFactory dataFactory = componentFactory.getDataFactory();
	    
	    /*
	     * Erzeugung der Kunden, Artikel und Bestellungen über die DataFactory...
	     */


	}
}
	private void main_() {
		//
		loadKitchenItems();
		loadPhotographyItems( 0	 );		// load 0..871 items below
		//
//		printInventory_1();
//		printInventory_2();
//		printInventory_map();
//		printInventory_forEach();

//		printInventory( true );
		printInventorySortedByInventoryValue();
		
		

		long invValue = inventoryValue();
		//
		System.out.println( "\\\\\nInventory value: -------> "
				+ pad( fmtPrice( invValue, "", " €" ), 14, true ) );
		
		System.out.println("\n---------------------------------------------------------------------------\n");
			
//		/**
//		 * TODO: uncomment for tasks B2(3,4)
//		 * /
		
		loadPhotographyItems( 1000	);
		invValue = inventoryValue();
		System.out.println( "\\\\\nInventory value: -------> "
				+ pad( fmtPrice( invValue, "", " €" ), 14, true ) );
		//
		
		int topN = 10;
		System.out.println( "\nTop " + topN + " inventory by value:" );
		printHdr( "\\\\\n" );
		inventoryTopRanksByValue( topN )
			.forEach( a -> print( a ) );

		int lessThanN = 10;
		long lessThanNUnits = inventoryAtOrLess( lessThanN ).count();
		
		System.out.println( "\\\\\nInventory with less than " + lessThanN + " units: --> " + lessThanNUnits );
		
	}


	/**
	 * TODO: Aufgabe B2(2)
	 * 
	 * @return inventory value in Cent
	 */
	long inventoryValue() {
		long value = 0;
		
		//1. Variante
//		for(Article a : catalog) {
//			value += a.getUnitsInStore() * a.getUnitPrice();
//		}
		
		//2. Variante
		value = catalog.stream()
				.mapToLong(a -> a.getUnitsInStore() * a.getUnitPrice())
				.sum();
		
		//3. Variante
//		value = catalog.stream()
//			.collect(Collectors.summingLong(a -> a.getUnitsInStore() * a.getUnitPrice()));
		
		return value;			// value in Cent
	}

	void printInventorySortedByInventoryValue() {
		printHdr( "\n" );
		
		catalog.stream()
//			.map(a -> a.getUnitsInStore() * a.getUnitPrice())
			.sorted(Comparator.comparing(Article::getPresentValue).reversed())
			.forEach(a -> {
				print(a);
			});

	}
	
	/**
	 * TODO: Aufgabe B2(3)
	 * 
	 * @param sortedByInventoryValue flag to sort printed list by inventory value
	 */
	void printInventory( boolean sortedByInventoryValue ) {
		printHdr( "\n" );
		/*
		 * Implement sortedByInventoryValue flag
		 */
		catalog.stream()
			.forEach( a -> {
				print( a );
			});
	}	
	


	/**
	 * TODO: Aufgabe B2(4)
	 * 
	 * @param units condition for lower bound for unitsInStore in inventory
	 * @return stream of inventory articles that matches condition
	 */
	Stream<Article> inventoryAtOrLess( int units ) {
		return catalog.stream()
				.filter(a -> a.getUnitsInStore() <= units );
//		return catalog.stream().limit( units );
	}

	/**
	 * TODO: Aufgabe B2(4)
	 * 
	 * @param n cut resulting list to n articles with highest inventory value
	 * @return n articles with highest inventory value
	 */
	Stream<Article> inventoryTopRanksByValue( int n ) {
		return catalog.stream()
				.sorted(Comparator.comparing(Article::getPresentValue).reversed())
				.limit(n);
//		return catalog.stream().limit( n );
	}



	/*
	 * Demo code below:
	 */

	void printInventory_1() {	// print inventory with indexed for-loop
		printHdr( "\n" );
		for( int i=0; i < catalog.size(); i++ ) {
			Article a = catalog.get( i );
			print( a );
		}
	}

	void printInventory_2() {	// print inventory with indexed for-loop
		printHdr( "\n" );
		for( Article a : catalog ) {
			print( a );
		}
	}

	void printInventory_map() {	// print inventory as stream with map()
		printHdr( "\n" );
		Long count = catalog.stream()
			.map( a -> print( a ) )				// map is not a terminal stream function
			.collect( Collectors.counting() );	// streams end with terminal functions
		//										// here: count elements -> long
		System.out.println( count );
	}

	void printInventory_forEach() {	// print inventory as stream with forEach()
		printHdr( "\n" );
		catalog.stream()
			.map( a -> a )			// map a -> a is a redundant function
			.forEach( a -> print( a ) );
	}


	private void printHdr( String prefix ) {
		System.out.println( (prefix != null? prefix : "") +
			pad( "Unit Price", 13, true )
			+ pad( "avail.", 9, true )
			+ pad( "Invent. Value €", 18, true )
			+ pad( "   Item Description", 15, false )
		);
		System.out.println(
			"-------------  ------    --------------- + " +
			"---------------------------------------------"
		);
	}

	private Article print( Article a ) {
		System.out.println(
			" -> "
			+ pad( fmtPrice( a.getUnitPrice(), "", "  x " ), 13, true )
			+ pad( String.valueOf( a.getUnitsInStore() ), 4, true ) + "  ="
			+ pad( fmtPrice( a.getUnitPrice() * a.getUnitsInStore(), "", " €" ), 16, true )
			+ " | " + a.getDescription() );
		return a;
	}

	private List<Article> loadKitchenItems() {
		catalog.add( new Article( "SKU-458362", "Tasse", 299, 2000 ) );
		catalog.add( new Article( "SKU-693856", "Becher", 149, 8400 ) );
		catalog.add( new Article( "SKU-518957", "Kanne", 2000, 2400 ) );
		catalog.add( new Article( "SKU-638035", "Teller", 649, 7000 ) );
		catalog.add( new Article( "SKU-123456", "Kaffemaschine", 2999, 500 ) );
		catalog.add( new Article( "SKU-789012", "Teekocher", 1999, 2000 ) );

		return catalog;
	}

	private List<Article> loadPhotographyItems( int nItems ) {
		/*
		 * 871 photography items
		 */
		List<Article> newItems = Arrays.asList(
			new Article( "4549292052893", "PowerShot G5 X", 74900, 68 ),
			new Article( "4549292100655", "PowerShot G1 X Mark III", 119900, 50 ),
			new Article( "4549292082524", "PowerShot SX730 HS - Schwarz", 35900, 41 ),
			new Article( "4549292083446", "PowerShot SX430 IS", 22900, 82 ),
			new Article( "8714574651620", "PowerShot SX730 HS - Schwarz Travel Kit", 37900, 19 ),
			new Article( "4549292082616", "PowerShot SX730 HS - Silber", 35900, 23 ),
			new Article( "8714574651651", "PowerShot SX730 HS - Silber Travel Kit", 37900, 39 ),
			new Article( "4549292081039", "PowerShot G9 X Mark II - Schwarz", 44900, 72 ),
			new Article( "4549292081107", "PowerShot G9 X Mark II - Silber", 44900, 29 ),
			new Article( "8714574643373", "PowerShot SX620 HS - Weiß Essentials Kit", 22900, 27 ),
			new Article( "4549292034615", "PowerShot G3 X", 89900, 54 ),
			new Article( "4549292057423", "PowerShot SX620 HS - Weiß", 20900, 32 ),
			new Article( "8714574643380", "PowerShot SX620 HS - Rot Essentials Kit", 22900, 0 ),
			new Article( "4549292057362", "PowerShot SX620 HS - Rot", 20900, 64 ),
			new Article( "8714574643366", "PowerShot SX620 HS - Schwarz Essentials Kit", 22900, 71 ),
			new Article( "4549292057300", "PowerShot SX620 HS - Schwarz", 20900, 53 ),
			new Article( "4549292056426", "PowerShot SX540 HS - Schwarz", 27900, 66 ),
			new Article( "8714574639543", "PowerShot G7 X Mark II Premium Kit", 65900, 25 ),
			new Article( "4549292056365", "PowerShot G7 X Mark II", 62900, 18 ),
			new Article( "4549292017380", "PowerShot SX60 HS", 44900, 6 ),
			new Article( "4549292083149", "IXUS 185 - Rot", 10900, 7 ),
			new Article( "4549292057591", "IXUS 285 HS - Lila", 18900, 85 ),
			new Article( "8714574636030", "IXUS 285 HS - Silber Essentials Kit", 19900, 85 ),
			new Article( "4549292057539", "IXUS 285 HS - Silber", 18900, 77 ),
			new Article( "8714574636009", "IXUS 285 HS - Schwarz Essentials Kit", 19900, 20 ),
			new Article( "4549292057478", "IXUS 285 HS - Schwarz", 18900, 49 ),
			new Article( "4549292083415", "IXUS 190 - Schwarz", 16900, 41 ),
			new Article( "8714574648125", "IXUS 190 - Schwarz Essentials Kit", 17900, 31 ),
			new Article( "4549292082937", "IXUS 190 - Silber", 16900, 70 ),
			new Article( "8714574648095", "IXUS 185 - Rot Essentials Kit", 11900, 93 ),
			new Article( "8714574636061", "IXUS 285 HS - Lila Essentials Kit", 19900, 55 ),
			new Article( "8714574648156", "IXUS 190 - Silber Essentials Kit", 17900, 30 ),
			new Article( "4549292082999", "IXUS 190 - Blau", 16900, 71 ),
			new Article( "8714574648187", "IXUS 190 - Blau Essentials Kit", 17900, 65 ),
			new Article( "4549292083088", "IXUS 185 - Schwarz", 10900, 23 ),
			new Article( "8714574648217", "IXUS 185 - Schwarz Essentials Kit", 11900, 73 ),
			new Article( "4549292083200", "IXUS 185 - Silber", 10900, 60 ),
			new Article( "8714574648248", "IXUS 185 - Silber Essentials Kit", 11900, 29 ),
			new Article( "4960999782232", "HF-DC2 ext. Slave-flash", 17500, 5 ),
			new Article( "4960999989952", "NB-6LH Akku", 7500, 53 ),
			new Article( "4960999676586", "NB-9L Akku", 7500, 10 ),
			new Article( "4549292020502", "NB-13L Akku", 9900, 0 ),
			new Article( "4960999242439", "NB-4L Akku", 7500, 49 ),
			new Article( "4960999216409", "NB-2LH", 7900, 53 ),
			new Article( "4960999620909", "NB-7L Akku", 10900, 75 ),
			new Article( "4549292009781", "NB-12L Akku", 10900, 14 ),
			new Article( "4549292004472", "NB-11LH Akku", 8500, 54 ),
			new Article( "4960999355160", "NB-5L Akku", 7500, 66 ),
			new Article( "4960999352343", "NB4-300 Akku", 2600, 44 ),
			new Article( "4960999661407", "NB-8L Akku", 4800, 90 ),
			new Article( "4960999796093", "NB-10L Akku", 9900, 84 ),
			new Article( "4960999846194", "WP-DC46", 32900, 3 ),
			new Article( "4960999917627", "WP-DC48", 33500, 66 ),
			new Article( "4960999654652", "WP-DC34", 32500, 52 ),
			new Article( "4960999780757", "WP-DC41 (40m)", 26900, 25 ),
			new Article( "4960999819808", "WP-DC44", 39500, 89 ),
			new Article( "4960999979656", "WP-DC49", 33500, 88 ),
			new Article( "4549292063097", "WP-DC55", 29900, 11 ),
			new Article( "4549292009842", "WP-DC53", 39500, 62 ),
			new Article( "4960999844503", "WP-DC340L", 9500, 2 ),
			new Article( "4549292100884", "Unterwassergehäuse WP-DC56", 39900, 0 ),
			new Article( "4960999246727", "WW-DC 1 Waterweight", 4700, 51 ),
			new Article( "4960999675343", "WP-DC38", 26900, 55 ),
			new Article( "4960999786063", "WP-DC42 (40m)", 32500, 99 ),
			new Article( "4960999844336", "WP-DC330L", 9500, 77 ),
			new Article( "4549292020649", "WP-DC54", 35900, 62 ),
			new Article( "4960999918242", "WP-DC47", 33500, 9 ),
			new Article( "4960999989617", "WP-DC51", 33500, 36 ),
			new Article( "4960999840178", "MLA-DC1 Macro Light Adapter", 4200, 71 ),
			new Article( "4960999677187", "FA-DC67A", 4700, 61 ),
			new Article( "4549292013054", "FA-DC58E", 5900, 38 ),
			new Article( "4960999676524", "FA-DC58B", 8500, 78 ),
			new Article( "4960999840154", "FA-DC58C", 5900, 41 ),
			new Article( "4960999840192", "LH-DC70 Obj.-Adapter", 4200, 29 ),
			new Article( "4549292017755", "LH-DC90 Obj.-Adapter", 4700, 34 ),
			new Article( "4549292100914", "Objektivdeckel LH-DC110", 5000, 41 ),
			new Article( "4960999677163", "LH-DC60 Obj.-Adapter", 3900, 61 ),
			new Article( "4549292013030", "LH-DC80 Obj.-Adapter", 4700, 68 ),
			new Article( "4549292054378", "IFC-600PCU", 2300, 85 ),
			new Article( "4960999570754", "AVC-DC400ST", 3500, 29 ),
			new Article( "4960999209906", "IFC-400PCU", 3500, 66 ),
			new Article( "4960999917702", "LA-DC58L Obj.-Adapter", 8500, 53 ),
			new Article( "4549292038040", "LH-DC100 FA-DC67B", 4600, 58 ),
			new Article( "4549292012422", "Schwimmer Float FT-DC1", 2300, 30 ),
			new Article( "4549292012439", "Silicone Jacket SJ-DC1", 3500, 85 ),
			new Article( "4960999844794", "AKT-DC2", 15500, 31 ),
			new Article( "8714574617268", "Neckstrap NS-100", 2300, 96 ),
			new Article( "4549292009941", "ACK-DC100 Netzadapter", 10900, 77 ),
			new Article( "4549292020694", "ACK-DC110 Netzadapter", 10900, 58 ),
			new Article( "4960999620855", "ACK-DC50 Netzgerät", 7900, 75 ),
			new Article( "4960999676623", "CB-2LBE Akkuladegerät", 5900, 5 ),
			new Article( "4960999676692", "ACK-DC70 Netzgerät", 7900, 44 ),
			new Article( "4960999795935", "ACK-DC80 Netzgerät", 7900, 65 ),
			new Article( "4960999355221", "ACK-DC30 Netzgerät", 7900, 81 ),
			new Article( "4960999782959", "DR-DC10", 3500, 84 ),
			new Article( "4960999242453", "CB-2LVE Akkuladegerät", 5900, 17 ),
			new Article( "4960999661223", "ACK-DC60 Netzgerät", 7900, 20 ),
			new Article( "4960999044910", "ACK-800 Netzgerät", 6900, 51 ),
			new Article( "4549292020625", "CB-2LHE Akkuladegerät", 5900, 14 ),
			new Article( "4549292054446", "Netzadapter CA-DC30E", 5900, 51 ),
			new Article( "4960999977324", "CB-2LFE Akkuladegerät", 5900, 56 ),
			new Article( "4960999571706", "ACK-DC40 Netzgerät", 7900, 100 ),
			new Article( "4960999796130", "CB-2LCE Akkuladegerät", 5900, 92 ),
			new Article( "4960999571676", "CB-2LYE Akkuladegerät", 5900, 77 ),
			new Article( "4960999112237", "CA-PS 700 E Ladegerät", 6900, 23 ),
			new Article( "4960999355191", "CB-2LXE Akkuladegerät", 5900, 95 ),
			new Article( "4960999844169", "ACK-DC90 Netzadapter", 7900, 61 ),
			new Article( "4549292009828", "CB-2LGE Akkuladegerät", 5900, 65 ),
			new Article( "4960999844756", "SC-DC80", 2400, 36 ),
			new Article( "8714574592725", "DCC-1620 Tasche", 8500, 49 ),
			new Article( "4960999654683", "SC-DC65A", 10900, 49 ),
			new Article( "8714574600123", "DCC-420", 2300, 62 ),
			new Article( "8714574560380", "DCC-850", 4700, 54 ),
			new Article( "8714574549088", "DCC-1500 Tasche", 3500, 21 ),
			new Article( "8714574600116", "DCC-1550 Tasche", 8500, 52 ),
			new Article( "8714574637396", "DCC-1880", 3000, 44 ),
			new Article( "8714574632506", "DCC-1890 Tasche", 3100, 11 ),
			new Article( "8714574632490", "DCC-1850 Tasche", 4200, 62 ),
			new Article( "4549292072020", "DCC-2400", 3000, 59 ),
			new Article( "8714574628974", "DCC-2300", 5900, 99 ),
			new Article( "8714574617244", "DCC-1570 Tasche", 3500, 85 ),
			new Article( "8714574617237", "DCC-1370", 3500, 31 ),
			new Article( "8714574610337", "DCC-1920 - SCHWARZ", 7500, 82 ),
			new Article( "8714574610320", "DCC-2500", 4700, 91 ),
			new Article( "8714574606026", "DCC-2000", 4200, 99 ),
			new Article( "8714574605012", "DCC-1320 Tasche", 3500, 43 ),
			new Article( "8714574558738", "DCC-1450 Tasche", 4700, 83 ),
			new Article( "8714574580845", "DCC-1800 Tasche", 8900, 33 ),
			new Article( "8714574583167", "ACC Organiser", 2500, 36 ),
			new Article( "8714574592114", "DCC-970", 8500, 68 ),
			new Article( "8714574527642", "DCC-750", 2500, 8 ),
			new Article( "4960999819921", "SC-DC75", 15500, 20 ),
			new Article( "8714574600130", "DCC-1350", 4700, 53 ),
			new Article( "8714574592121", "DCC-950", 4700, 62 ),
			new Article( "4960999620794", "TC-DC58D Telekonverter", 19900, 42 ),
			new Article( "4960999917658", "TC-DC58E Telekonverter", 32500, 94 ),
			new Article( "8714574657387", "EOS 2000D Gehäuse + EF-S 18-135mm f/3.5-5.6 IS STM Kit", 89900, 88 ),
			new Article( "4549292111859", "EOS 2000D Gehäuse + EF-S 18-55mm f/3.5-5.6 IS II Kit", 49900, 0 ),
			new Article( "4549292091328", "EOS 200D Gehäuse - Schwarz", 54900, 53 ),
			new Article( "4549292091434", "EOS 200D Gehäuse - Weiß + EF-S 18-55mm f/4-5.6 IS STM - Silber", 64900, 91 ),
			new Article( "8714574657295", "EOS 2000D Gehäuse + EF-S 18-55mm f/3.5-5.6 IS II Value Up Kit", 52900, 95 ),
			new Article( "8714574657264", "EOS 2000D Gehäuse + EF-S 18-55mm f/3.5-5.6 IS II Battery Kit", 51900, 48 ),
			new Article( "8714574653563", "EOS 200D Gehäuse - Schwarz + EF-S 18-55mm f/3.5-5.6 III + EF 75-300mm f/4-5.6 III", 91900, 30 ),
			new Article( "4549292116571", "EOS 4000D Gehäuse + EF-S 18-55mm f/3.5-5.6 III Kit", 39900, 77 ),
			new Article( "8714574657448", "EOS 2000D Gehäuse + EF-S 18-55mm f/3.5-5.6 IS II + EF 50mm f/1.8 STM Kit", 63900, 38 ),
			new Article( "8714574657394", "EOS 2000D Gehäuse + EF-S EF-S 18-55mm f/3.5-5.6 IS II + EF 75-300mm f/4-5.6 III Kit", 79900, 56 ),
			new Article( "8714574657325", "EOS 4000 Gehäuse + EF-S 18-55mm f/3.5-5.6 III + EF 75-300mm f/4-5.6 III Kit", 69900, 98 ),
			new Article( "8714574657356", "EOS 4000D Gehäuse + EF-S 18-55mm f/3.5-5.6 III Value Up Kit", 42900, 60 ),
			new Article( "4549292091335", "EOS 200D Gehäuse - Schwarz + EF-S 18-55mm f/4-5.6 IS STM - Schwarz", 64900, 96 ),
			new Article( "8714574653594", "EOS 200D Gehäuse - Schwarz + EF-S 18-135mm f/3.5-5.6 IS STM", 104900, 7 ),
			new Article( "4549292111835", "EOS 2000D Gehäuse", 39900, 75 ),
			new Article( "8714574653501", "EOS 200D Gehäuse - Schwarz + EF-S 18-55mm f/3.5-5.6 III + 100EG + 16GB + Objektivreinigungstuch", 64900, 47 ),
			new Article( "4549292116564", "EOS 4000D Gehäuse", 34900, 61 ),
			new Article( "8714574653532", "EOS 200D Gehäuse - Schwarz + EF-S 18-55mm f/4-5.6 IS STM + EF 50mm f/1.8 STM", 78900, 48 ),
			new Article( "4549292092707", "EOS 200D Gehäuse - Schwarz + EF-S 18-55mm f/3.5-5.6 III", 61900, 6 ),
			new Article( "4549292091502", "EOS 200D Gehäuse - Silber + EF-S 18-55mm f/4-5.6 IS STM - Silber", 64900, 56 ),
			new Article( "8714574645957", "EOS 5D Mark IV Gehäuse", 349900, 56 ),
			new Article( "4549292083583", "EOS 77D Gehäuse", 84900, 40 ),
			new Article( "4549292083590", "EOS 77D Gehäuse + EF-S 18-135mm f/3.5-5.6 IS USM (NANO)", 124900, 21 ),
			new Article( "4549292096255", "EOS 77D Gehäuse + EF-S 18-55mm f/4-5.6 IS STM", 94900, 94 ),
			new Article( "8714574636351", "EOS 80D Gehäuse", 118500, 10 ),
			new Article( "8714574636429", "EOS 80D Gehäuse + EF-S 18-55mm f/3.5-5.6 IS STM", 128500, 0 ),
			new Article( "8714574636504", "EOS 80D Gehäuse + EF-S 18-135mm f/3.5-5.6 IS USM (NANO)", 158500, 30 ),
			new Article( "8714574651705", "EOS 80D Gehäuse + EF-S 18-200mm f/3.5-5.6 IS", 163500, 40 ),
			new Article( "8714574647975", "EOS 7D Mark II Gehäuse + W-E1 DE", 161500, 98 ),
			new Article( "8714574650647", "EOS 77D Gehäuse + EF-S 18-135mm f/3.5-5.6 IS USM (NANO) Value Up Kit", 127900, 42 ),
			new Article( "4549292083552", "EOS 800D Gehäuse", 74900, 53 ),
			new Article( "4549292083736", "EOS 800D Gehäuse + EF-S 18-55mm f/4-5.6 IS STM", 84900, 31 ),
			new Article( "8714574650494", "EOS 800D Gehäuse + EF-S 18-200mm f/3.5-5.6 IS", 119900, 93 ),
			new Article( "4549292083903", "EOS 6D Mark II Gehäuse", 199900, 28 ),
			new Article( "4549292084085", "EOS 6D Mark II Gehäuse + EF 24-105mm f/3.5-5.6 IS STM", 239900, 97 ),
			new Article( "8714574635309", "EOS 1D X Mark II Gehäuse", 629900, 57 ),
			new Article( "8714574627861", "EOS 5Ds Gehäuse", 379900, 95 ),
			new Article( "8714574628080", "EOS 5Ds R Gehäuse", 399900, 69 ),
			new Article( "4549292008302", "Li-lon Akku LP-E6N", 13500, 58 ),
			new Article( "4960999819907", "Li-lon Akku LP-E4 N", 18900, 87 ),
			new Article( "4960999670720", "Li-lon Akku LP-E8", 6500, 25 ),
			new Article( "4960999612225", "Li-lon Akku LP-E5", 6900, 27 ),
			new Article( "4549292060560", "LP-E19", 21900, 80 ),
			new Article( "4960999204451", "BP 511A", 8900, 19 ),
			new Article( "4549292020984", "Li-lon Akku LP-E17", 6500, 50 ),
			new Article( "4960999911625", "LP-E12", 6500, 28 ),
			new Article( "4960999688855", "Li-lon Akku LP-E10", 6500, 0 ),
			new Article( "4549292059540", "CP-E4N", 29900, 52 ),
			new Article( "4960999301143", "BP 200", 8500, 51 ),
			new Article( "4960999581446", "BM-1", 7500, 5 ),
			new Article( "4960999505503", "Augenkorrekturlinsen EOS E ohne Augenmuschel . 4 Dioptrien", 2600, 50 ),
			new Article( "4960999505480", "Augenkorrekturlinsen EOS E ohne Augenmuschel . 2 Dioptrien", 2600, 91 ),
			new Article( "4960999505466", "Augenkorrekturlinsen EOS E ohne Augenmuschel 0 Dioptrien", 2600, 73 ),
			new Article( "4960999505459", "Augenkorrekturlinsen EOS E ohne Augenmuschel + 0.5 Dioptrien", 2600, 48 ),
			new Article( "4960999505442", "Augenkorrekturlinsen EOS E ohne Augenmuschel + 1 Dioptrie", 2600, 29 ),
			new Article( "4960999505435", "Augenkorrekturlinsen EOS E ohne Augenmuschel + 1.5 Dioptrien", 2600, 24 ),
			new Article( "4960999505428", "Augenkorrekturlinsen EOS E ohne Augenmuschel + 2 Dioptrien", 2600, 91 ),
			new Article( "4960999505411", "Augenkorrekturlinsen EOS E ohne Augenmuschel + 3 Dioptrien", 2600, 84 ),
			new Article( "4549292025002", "Augenmuschel Sucherlupe MG-EB", 4900, 44 ),
			new Article( "4549292025019", "Augenmuschel Sucherlupe MG-EF", 4900, 28 ),
			new Article( "4960999174525", "Gummirahmen Ef", 1900, 65 ),
			new Article( "4960999174280", "Augenmuschel Typ Ef", 1900, 80 ),
			new Article( "4960999415932", "Augenmuschel Typ Eg", 2900, 22 ),
			new Article( "4960999613130", "Augenmuschel EP-EX 15II", 3500, 49 ),
			new Article( "4960999540092", "Augenmuschel Winkelsucher C", 30900, 73 ),
			new Article( "4960999509518", "Anti-Beschlag-Okular ED", 9900, 56 ),
			new Article( "4960999509501", "Anti-Beschlag-Okular EC", 9900, 39 ),
			new Article( "4960999505756", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ee . 4 Dioptrien", 5900, 7 ),
			new Article( "4960999455808", "Augenkorrekturlinsen EOS Eg mit Augenmuschel Eg +3 Dioptrien", 6900, 7 ),
			new Article( "4960999455815", "Augenkorrekturlinsen EOS Eg mit Augenmuschel Eg +2 Dioptrien", 6900, 55 ),
			new Article( "4960999455822", "Augenkorrekturlinsen EOS Eg mit Augenmuschel Eg +1 Dioptrien", 6900, 30 ),
			new Article( "4960999455839", "Augenkorrekturlinsen EOS Eg mit Augenmuschel Eg 0 Dioptrien", 6900, 78 ),
			new Article( "4960999455853", "Augenkorrekturlinsen EOS Eg mit Augenmuschel Eg -3 Dioptrien", 6900, 16 ),
			new Article( "4960999455860", "Augenkorrekturlinsen EOS Eg mit Augenmuschel Eg -4 Dioptrien", 6900, 58 ),
			new Article( "4960999455877", "Anti-Beschlag-Okular EG", 6900, 72 ),
			new Article( "4960999505749", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ee . 3 Dioptrien", 5900, 36 ),
			new Article( "4960999505732", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ee . 2 Dioptrien", 5900, 22 ),
			new Article( "4960999505701", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ee + 0.5 Dioptrien", 5900, 93 ),
			new Article( "4960999505671", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ee + 2 Dioptrien", 5900, 97 ),
			new Article( "4960999505657", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ed + 0.5 Dioptrien", 5900, 35 ),
			new Article( "4960999505640", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ed + 1 Dioptrie", 5900, 55 ),
			new Article( "4960999505633", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ed + 1.5 Dioptrien", 5900, 71 ),
			new Article( "4960999505626", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ed + 2 Dioptrien", 5900, 86 ),
			new Article( "4960999505619", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ed + 3 Dioptrien", 5900, 55 ),
			new Article( "4960999509112", "Augenmuschel Typ EC II", 6900, 0 ),
			new Article( "4960999509969", "Augenmuschel Typ EB", 1900, 32 ),
			new Article( "4960999509082", "Augenmuschel Typ ED", 2500, 33 ),
			new Article( "4960999505602", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ed . 4 Dioptrien", 5900, 54 ),
			new Article( "4960999505596", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ed . 3 Dioptrien", 5900, 44 ),
			new Article( "4960999505589", "Augenkorrekturlinsen EOS Ed mit Augenmuschel Ed . 2 Dioptrien", 5900, 44 ),
			new Article( "4960999505534", "Augenkorrekturlinsen EOS Eg mit Augenmuschel Gummirahmen Eb", 1900, 71 ),
			new Article( "4960999505497", "Augenkorrekturlinsen EOS E ohne Augenmuschel . 3 Dioptrien", 2600, 84 ),
			new Article( "4960999811079", "BG-E11", 41900, 9 ),
			new Article( "4549292025071", "BG-E18", 18900, 80 ),
			new Article( "4549292001945", "BG-E16", 30900, 5 ),
			new Article( "4549292087512", "BG-E21", 19900, 67 ),
			new Article( "4960999981000", "BG-E14", 23900, 5 ),
			new Article( "4549292075946", "BG-E20", 43900, 66 ),
			new Article( "4960999501550", "GR-E2", 11900, 3 ),
			new Article( "4960999964799", "BG-E13", 28500, 34 ),
			new Article( "4960999670409", "BG-E8", 21500, 0 ),
			new Article( "4960999810232", "Speedlite Transmitter ST-E3-RT", 31900, 57 ),
			new Article( "8714574654652", "Macro Twin Lite MT-26EX-RT", 124900, 28 ),
			new Article( "8714574632926", "Speedlite 430 EX III-RT", 32900, 66 ),
			new Article( "4549292038293", "Colour Filter SCF-E2", 1500, 30 ),
			new Article( "4549292038309", "Bounce Adapter SBA-E2", 1500, 3 ),
			new Article( "4960999501260", "TTL-Verteilerplatte", 8500, 54 ),
			new Article( "4960999581538", "Speedlite Transmitter ST-E2", 33900, 36 ),
			new Article( "4549292084658", "Speedlite 470EX-AI", 49900, 12 ),
			new Article( "4960999417257", "Batteriemagazin CPM-E4 f. CP-E4", 10900, 74 ),
			new Article( "4960999417264", "Blitzschiene SB-E2 für 580 EX II", 31500, 42 ),
			new Article( "4960999417271", "Externes Blitzkabel OC-E3", 9500, 30 ),
			new Article( "4960999501284", "Synchron-Verlängerungskabel, 300 cm", 7500, 8 ),
			new Article( "4549292007749", "Macro Ring Lite MR-14EX II", 81500, 0 ),
			new Article( "4960999635323", "Macro Ring Lite-Adapter 67", 7500, 24 ),
			new Article( "4960999501277", "Synchron-Verlängerungskabel, 60 cm", 6500, 56 ),
			new Article( "4549292059441", "Speedlite 600EX II-RT", 69900, 83 ),
			new Article( "4960999501543", "OA-2", 6900, 97 ),
			new Article( "4960999581132", "Macro Ring Lite-Adapter 58 C", 2900, 78 ),
			new Article( "4960999581125", "Macro Ring Lite-Adapter 52 C", 2900, 94 ),
			new Article( "4549292058833", "Remote Controller Adapter RA-E3", 4500, 22 ),
			new Article( "4960999783246", "Speedlite 270 EX II", 21900, 25 ),
			new Article( "4960999501420", "TTL-Mittenkontakt-Adapter 3", 10900, 20 ),
			new Article( "4960999420394", "Macro Ring Lite-Adapter 72 C", 7500, 75 ),
			new Article( "4960999581668", "Abdeckung T", 1000, 76 ),
			new Article( "4960999669304", "RC-6", 4500, 72 ),
			new Article( "4960999290102", "LC-5 Set", 63900, 99 ),
			new Article( "4960999581606", "RA-N3", 6900, 10 ),
			new Article( "4960999581569", "Timer TC-80 N 3", 20900, 89 ),
			new Article( "4960999581576", "RS-80N 3", 6900, 68 ),
			new Article( "4960999581354", "RS-60 E3", 3900, 72 ),
			new Article( "4549292087864", "BR-E1 Ferbedienung", 4900, 25 ),
			new Article( "4960999686202", "Hand Strap E2", 5500, 46 ),
			new Article( "4549292035131", "EW-100 DBV", 2900, 7 ),
			new Article( "4549292063714", "CAMERA NECK STRAP EW-400D", 3000, 77 ),
			new Article( "4960999840048", "CAMERA NECK STRAP L7", 2500, 3 ),
			new Article( "4960999416014", "IFC-200 U", 5900, 51 ),
			new Article( "4960999175027", "IFC-450D4", 13900, 23 ),
			new Article( "4960999175102", "IFC-200D4", 11900, 95 ),
			new Article( "4960999659275", "AVC-DC400ST", 4900, 55 ),
			new Article( "4549292002089", "IFC-40AB II", 4900, 55 ),
			new Article( "4960999416021", "IFC-500 U", 8500, 31 ),
			new Article( "4549292002065", "IFC-150AB-II", 4900, 65 ),
			new Article( "4549292002041", "IFC-500U II", 5900, 30 ),
			new Article( "4549292002027", "IFC-150U II", 4900, 61 ),
			new Article( "4549292001983", "Selbstwechselbare Laser-Mattscheiben Typ Eh Focusing Screen EH-S", 6900, 86 ),
			new Article( "4549292001969", "Selbstwechselbare Laser-Mattscheiben Typ Eh Focusing Screen EH-A", 6900, 46 ),
			new Article( "4960999297378", "Selbstwechselbare Laser-Mattscheiben Typ Ee Einsatz Screen Ee-D", 7500, 94 ),
			new Article( "4960999297361", "Selbstwechselbare Laser-Mattscheiben Typ Ee Einsatz Screen Ee-A", 7500, 72 ),
			new Article( "4960999297767", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-S", 7500, 66 ),
			new Article( "4960999530215", "Selbstwechselbare Laser-Mattscheiben Typ Ef Focusing Screen Ef-A", 6900, 95 ),
			new Article( "4960999530222", "Selbstwechselbare Laser-Mattscheiben Typ Ef Focusing Screen Ef-D", 6900, 14 ),
			new Article( "4960999627717", "Selbstwechselbare Laser-Mattscheiben Typ Eg Focusing Screen Eg-A", 8900, 19 ),
			new Article( "4960999627731", "Selbstwechselbare Laser-Mattscheiben Typ Eg Focusing Screen Eg-D", 8900, 54 ),
			new Article( "4960999530239", "Selbstwechselbare Laser-Mattscheiben Typ Ef Focusing Screen Ef-S", 6900, 46 ),
			new Article( "4960999627748", "Selbstwechselbare Laser-Mattscheiben Typ Eg Focusing Screen Eg-S", 8900, 73 ),
			new Article( "4960999415949", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-C IV", 7500, 40 ),
			new Article( "4960999520179", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-R", 6500, 50 ),
			new Article( "4960999581651", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-N", 6900, 4 ),
			new Article( "4960999520032", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-L", 6900, 13 ),
			new Article( "4960999520025", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-I", 6900, 10 ),
			new Article( "4960999520018", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-H", 6900, 29 ),
			new Article( "4960999520001", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-D", 6900, 82 ),
			new Article( "4960999519982", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-B", 6900, 88 ),
			new Article( "4549292060539", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-C6", 6900, 70 ),
			new Article( "4960999964812", "Selbstwechselbare Laser-Mattscheiben Typ Eg Focusing Screen Eg-AII", 7500, 22 ),
			new Article( "4960999840109", "Selbstwechselbare Laser-Mattscheibe Typ Ec Einsatz Ec-C V", 3900, 42 ),
			new Article( "4960999297385", "Selbstwechselbare Laser-Mattscheiben Typ Ee Einsatz Screen Ee-S", 7500, 97 ),
			new Article( "4549292059519", "Bounce Adapter SBA-E3", 2100, 1 ),
			new Article( "4549292077568", "WI-FI Adaper W-E1", 5000, 34 ),
			new Article( "4549292065732", "Stereo-Richtmikrofon DM-E1", 35900, 0 ),
			new Article( "4960999819945", "LC-E4N", 47900, 0 ),
			new Article( "4960999688947", "LC-E10", 6900, 97 ),
			new Article( "4960999688985", "DR-E10 DC Kuppler", 2900, 4 ),
			new Article( "4960999612485", "CBC-E5 Autoladegerät", 13900, 28 ),
			new Article( "4960999627502", "LC-E6", 7500, 69 ),
			new Article( "4960999913247", "LC-E12", 6900, 62 ),
			new Article( "4960999670874", "LC-E8", 6900, 24 ),
			new Article( "4960999670829", "DR-E8 DC Kuppler", 3500, 100 ),
			new Article( "4549292060591", "LC-E19", 49900, 56 ),
			new Article( "4549292068795", "AC-E19", 43900, 100 ),
			new Article( "4549292066050", "DR-E19", 20900, 44 ),
			new Article( "4960999415864", "ACK-E4", 17900, 74 ),
			new Article( "4960999983608", "DR-E15", 2899, 70 ),
			new Article( "4960999398709", "CB-5L", 6900, 7 ),
			new Article( "4549292021011", "LC-E17 E", 6900, 98 ),
			new Article( "4960999627601", "DR-E6 DC Kuppler", 12900, 3 ),
			new Article( "4960999913278", "DR-E12 DC Kuppler", 3500, 96 ),
			new Article( "4549292030723", "DR-E18", 4900, 9 ),
			new Article( "4960999186054", "CB-570 Kabel", 9900, 22 ),
			new Article( "4549292065619", "AC-E6N", 12900, 73 ),
			new Article( "4960999612478", "LC-E5", 7500, 58 ),
			new Article( "4960999039312", "NP-E3", 15500, 49 ),
			new Article( "4960999613116", "DR-E5 DC Kuppler", 5500, 25 ),
			new Article( "4960999627540", "CBC-E6 Autoladegerät", 29500, 74 ),
			new Article( "4549292079975", "Rain Cover ERC-E5M", 13900, 74 ),
			new Article( "4549292030761", "EH26-L", 7900, 15 ),
			new Article( "4549292030785", "EH27-L", 5900, 67 ),
			new Article( "4549292062656", "CANON TEXTILE BAG SHOULDER SB100 BLACK", 3999, 55 ),
			new Article( "4549292062687", "CANON TEXTILE BAG BACKPACK BP100 BLACK", 8500, 83 ),
			new Article( "4549292062694", "CANON TEXTILE BAG MS10", 8900, 51 ),
			new Article( "4549292062717", "CANON TEXTILE BAG BP10", 10900, 92 ),
			new Article( "4549292073218", "CANON TEXTILE BAG HOLSTER HL100 BLACK", 3499, 27 ),
			new Article( "4549292079951", "Rain Cover ERC-E5S", 13900, 54 ),
			new Article( "4549292079999", "Rain Cover ERC-E5L", 13900, 25 ),
			new Article( "4549292083392", "Protecting Cloth PC-E1", 3000, 28 ),
			new Article( "4549292099706", "Canon Schutztuch PC-E2", 2999, 6 ),
			new Article( "4960999661827", "EH20-L", 13900, 47 ),
			new Article( "4960999686035", "EH21-L", 13900, 25 ),
			new Article( "4960999913315", "Camera Cover RF 4", 1000, 37 ),
			new Article( "4960999983585", "EH25-L", 9900, 89 ),
			new Article( "4960999848808", "GP-E1", 32900, 75 ),
			new Article( "4549292066487", "WFT-E8B", 54900, 43 ),
			new Article( "4960999848358", "GP-E2 GPS Receiver", 27900, 4 ),
			new Article( "4549292023329", "WFT-E7B Vers.2", 75900, 79 ),
			new Article( "4549292063455", "EF-M 18-150mm f/3.5-6.3 IS STM - Schwarz", 49900, 66 ),
			new Article( "4549292063561", "EF-M 18-150mm f/3.5-6.3 IS STM - Silber", 49900, 24 ),
			new Article( "4960999921624", "EF-M 11-22mm f/4-5.6 IS STM", 39900, 35 ),
			new Article( "4549292009873", "EF-M 55-200mm f/4.5-6.3 IS STM - Schwarz", 32900, 94 ),
			new Article( "4960999841120", "EF-M 22mm f/2 STM", 24900, 57 ),
			new Article( "4549292063127", "EF-M 28mm f/3.5 Makro IS STM", 34900, 90 ),
			new Article( "4549292038507", "EF-M 15-45mm f/3.5-6.3 IS STM - Silber", 29900, 75 ),
			new Article( "4549292056655", "EF-M 55-200mm f/4.5-6.3 IS STM - Silber", 32900, 40 ),
			new Article( "4549292037715", "EF-M 15-45mm f/3.5-6.3 IS STM - Schwarz", 29900, 14 ),
			new Article( "4960999841137", "Mount Adapter EF-EOS M", 12900, 57 ),
			new Article( "4960999271255", "EF-S 60mm f/2.8 Macro USM", 50900, 0 ),
			new Article( "4960999214238", "EF 100mm f/2.8 Macro USM", 59900, 0 ),
			new Article( "4549292090130", "EF-S 35mm f/2.8 IS Macro STM", 42900, 49 ),
			new Article( "4960999213842", "EF 180mm f/3.5L Macro USM", 146900, 8 ),
			new Article( "4960999214214", "MP-E 65mm f/2.8 1-5fach Lupenobjektiv", 111900, 24 ),
			new Article( "4960999635170", "EF 100mm f/2.8L IS Macro USM", 103900, 94 ),
			new Article( "4960999354972", "EF 50mm f/1.2L USM", 154900, 8 ),
			new Article( "4960999213019", "EF 50mm f/1.4 USM", 44900, 85 ),
			new Article( "4960999845715", "EF 40mm f/2.8 STM", 23900, 60 ),
			new Article( "4549292037692", "EF 50mm f/1.8 STM", 13900, 24 ),
			new Article( "4960999845807", "EF 24-70mm f/4L IS USM", 92900, 81 ),
			new Article( "4549292063615", "EF 24-105mm f/4L IS II USM", 119900, 4 ),
			new Article( "4960999780719", "EF 24-70mm f/2.8L II USM", 201900, 84 ),
			new Article( "4549292075199", "EF-S 18-55mm f/4-5.6 IS STM", 24900, 58 ),
			new Article( "4960999689500", "EF-S 18-55mm f/3.5-5.6 IS II", 19900, 93 ),
			new Article( "4960999970295", "EF-S 18-55mm f/3.5-5.6 IS STM", 24900, 92 ),
			new Article( "4549292010176", "EF 24-105mm f/3.5-5.6 IS STM", 47900, 56 ),
			new Article( "8714574625614", "EF-S 18-135mm f/3.5-5.6 IS STM + EW 73B + LC Kit", 49900, 78 ),
			new Article( "4960999575056", "EF-S 18-200mm f/3.5-5.6 IS ", 58500, 96 ),
			new Article( "4960999841113", "EF-S 18-135mm f/3.5-5.6 IS STM", 49900, 74 ),
			new Article( "4960999635262", "EF-S 15-85mm f/3.5-5.6 IS USM", 79900, 17 ),
			new Article( "4549292061383", "EF-S 18-135mm f/3.5-5.6 IS USM", 54900, 16 ),
			new Article( "4960999354798", "EF-S 17-55mm f/2.8 IS USM", 91900, 22 ),
			new Article( "4960999977270", "EF 400mm f/4 DO IS II USM", 702900, 32 ),
			new Article( "4960999575025", "EF 800mm f/5.6L IS USM ", 1414900, 7 ),
			new Article( "4960999213804", "EF 300mm f/4L IS USM", 146900, 90 ),
			new Article( "4960999213767", "EF 200mm f/2.8L II USM ", 82900, 19 ),
			new Article( "4960999213828", "EF 400mm f/5.6L USM", 144900, 92 ),
			new Article( "4960999213743", "EF 135mm f/2L USM ", 110900, 59 ),
			new Article( "4960999212906", "EF 85mm f/1.8 USM", 47900, 83 ),
			new Article( "4960999212821", "EF 100mm f/2 USM ", 52900, 14 ),
			new Article( "4960999483283", "EF 200mm f/2L IS USM", 630900, 86 ),
			new Article( "4960999326429", "EF 85mm f/1.2L II USM", 223900, 76 ),
			new Article( "4960999664842", "EF 300mm f/2.8L IS II USM", 649900, 62 ),
			new Article( "4960999664859", "EF 400mm f/2.8L IS II USM", 1101900, 76 ),
			new Article( "4549292091656", "EF 85mm f/1.4L IS USM", 159900, 100 ),
			new Article( "4960999780672", "EF 500mm f/4L IS II USM", 997900, 9 ),
			new Article( "4960999780696", "EF 600mm f/4L IS II USM", 1263900, 33 ),
			new Article( "4549292037708", "EF 70-300mm f/4-5.6 IS II USM", 53900, 11 ),
			new Article( "4960999354989", "EF 70-200mm f/4L IS USM", 140900, 40 ),
			new Article( "4960999207322", "EF 28-300mm f/3.5-5.6L IS USM", 265900, 65 ),
			new Article( "4549292010350", "EF 100-400mm f/4.5-5.6L IS II USM ", 237900, 73 ),
			new Article( "4960999214085", "EF 75-300mm f/4-5.6 III USM", 36900, 45 ),
			new Article( "4960999214078", "EF 75-300mm f/4-5.6 III", 29900, 55 ),
			new Article( "4960999665023", "EF 70-300mm f/4-5.6L IS USM", 142900, 67 ),
			new Article( "8714574625621", "EF-S 55-250mm f/4-5.6 IS STM + ET 63 + LC Kit", 34900, 16 ),
			new Article( "4960999979373", "EF-S 55-250mm f/4-5.6 IS STM", 34900, 99 ),
			new Article( "4960999575070", "EF 70-200mm f/2.8L IS II USM", 229900, 16 ),
			new Article( "4960999214207", "EF 70-200mm f/4L USM", 68900, 98 ),
			new Article( "4960999213941", "EF 70-200mm f/2.8L USM", 157900, 26 ),
			new Article( "4960999780726", "EF 200-400mm f/4L IS USM + Extender 1.4x", 1169900, 1 ),
			new Article( "4549292091984", "TS-E 50mm f/2.8L Macro", 254900, 69 ),
			new Article( "4549292091991", "TS-E 90mm f/2.8L Macro", 254900, 73 ),
			new Article( "4549292092004", "TS-E 135mm f/4L Macro", 254900, 38 ),
			new Article( "4960999635156", "TS-E 17mm f/4 L", 235900, 15 ),
			new Article( "4960999635163", "TS-E 24mm f/3.5L II", 209900, 100 ),
			new Article( "4960999450070", "EF 14mm f/2.8L II USM", 222900, 8 ),
			new Article( "4960999212883", "EF 20mm f/2.8 USM", 56500, 29 ),
			new Article( "4960999845753", "EF 24mm f/2.8 IS USM", 59900, 90 ),
			new Article( "4960999845746", "EF 28mm f/2.8 IS USM", 54900, 83 ),
			new Article( "4960999575063", "EF 24mm f/1.4L II USM", 164900, 94 ),
			new Article( "4960999845791", "EF 35mm f/2 IS USM", 59900, 79 ),
			new Article( "4549292010237", "EF 35mm f/1.4L II USM", 191900, 24 ),
			new Article( "4549292010220", "EF-S 24mm f/2.8 STM", 17900, 57 ),
			new Article( "4960999213286", "EF 28mm f/1.8 USM", 53900, 14 ),
			new Article( "4960999665030", "EF 8-15mm f/4L USM Fisheye", 132900, 65 ),
			new Article( "4549292009903", "EF 16-35mm f/4L IS USM", 115900, 97 ),
			new Article( "4549292037722", "EF 16-35mm f/2.8L III USM", 230900, 89 ),
			new Article( "4960999189949", "EF 17-40mm f/4L USM", 81900, 33 ),
			new Article( "4960999246796", "EF-S 10-22mm f/3.5-4.5 USM", 68900, 60 ),
			new Article( "4549292010152", "EF-S 10-18mm f/4.5-5.6 IS STM", 27900, 85 ),
			new Article( "8714574625638", "EF-S 10-18mm f/4.5-5.6 IS STM + EW 73 + LC Kit", 27900, 45 ),
			new Article( "4549292010169", "EF 11-24mm f/4L USM", 323900, 70 ),
			new Article( "4960999430935", "Schutzfilter 52mm", 2900, 40 ),
			new Article( "4960999975696", "Schutzfilter 55mm", 4500, 0 ),
			new Article( "4549292059472", "Color Filter SCF-E3", 2200, 87 ),
			new Article( "4960999430621", "ND 4-L 52MM Filter", 3900, 79 ),
			new Article( "4960999430638", "ND 8-L 52MM Filter", 3500, 91 ),
			new Article( "4960999430942", "Schutzfilter 58mm", 3500, 66 ),
			new Article( "4960999455792", "Circular-Polfilter PL-C B (77mm)", 20900, 77 ),
			new Article( "4960999455785", "Circular-Polfilter PL-C B (72mm)", 17900, 20 ),
			new Article( "4960999455778", "Circular-Polfilter PL-C B (67mm)", 17500, 68 ),
			new Article( "4960999455761", "Circular-Polfilter PL-C B (58mm)", 11500, 43 ),
			new Article( "4960999455754", "Circular-Polfilter PL-C B (52mm)", 10900, 57 ),
			new Article( "4960999430997", "Drop-In Gelatine Filter Halter 52mm", 8900, 22 ),
			new Article( "4960999417325", "Schutzfilter 82mm", 11900, 21 ),
			new Article( "4960999417301", "Circular-Polfilter PL-C B (82mm)", 32900, 64 ),
			new Article( "4960999430652", "ND Filter ND4-L 58mm ", 4900, 98 ),
			new Article( "4960999430669", "ND Filter ND 8-L 58mm", 4900, 4 ),
			new Article( "4960999430973", "Schutzfilter 67mm", 6900, 45 ),
			new Article( "4960999430959", "Schutzfilter 72mm", 5900, 57 ),
			new Article( "4960999845937", "Camera Filter Protect 43 MM", 4500, 11 ),
			new Article( "4549292037890", "Schutzfilter 49mm", 5000, 36 ),
			new Article( "4960999677873", "Adapter für Gelatine-Filter-Halter TYP WII 52mm", 9900, 3 ),
			new Article( "4960999677897", "Drop-In Screw Filter-Halter 52mm (WII)", 13900, 37 ),
			new Article( "4960999677927", "Drop-In Circular Polarising 52mm (WII)", 27900, 1 ),
			new Article( "4960999430713", "ND 4-L 72MM Filter", 9900, 23 ),
			new Article( "4960999430720", "ND 8-L 72MM Filter", 9900, 6 ),
			new Article( "4960999430911", "Schutzfilter 77mm", 6900, 73 ),
			new Article( "4960999431000", "Drop-In Screw Filter Halter 52mm", 13900, 17 ),
			new Article( "4960999420387", "Adapter für Gelatine-Filter-Halter Typ IV 77mm", 6500, 7 ),
			new Article( "4960999420417", "Adapter für Gelatine-Filter-Halter Typ IV 67mm", 5900, 66 ),
			new Article( "4960999431017", "Drop-In Filter-Halter 52mm", 27900, 97 ),
			new Article( "4960999440729", "EW-63 II Streulichtblende", 5500, 58 ),
			new Article( "4960999211060", "EW-83G Streulichtblende ", 8900, 87 ),
			new Article( "4549292061413", "EW-73D Streulichtblende", 7900, 9 ),
			new Article( "4960999845913", "EW-43 Streulichtblende ", 3000, 88 ),
			new Article( "4960999440965", "ET-155 Streulichtblende", 82900, 68 ),
			new Article( "4960999440934", "ET-83C Streulichtblende", 7500, 26 ),
			new Article( "4960999440873", "ET-83B II Streulichtblende", 8900, 85 ),
			new Article( "4960999678375", "EW-77 Streulichtblende ", 5500, 23 ),
			new Article( "4549292063578", "Lens Hood ES-22 Streulichtblende", 3000, 33 ),
			new Article( "4960999243665", "EW-73B Streulichtblende", 7500, 43 ),
			new Article( "4960999294070", "EW-83H Streulichtblende", 7500, 13 ),
			new Article( "4960999405551", "ES-78 Streulichtblende", 6500, 54 ),
			new Article( "4549292010541", "ET-83D Streulichtblende ", 11500, 68 ),
			new Article( "4549292010534", "EW-77B Streulichtblende ", 7500, 11 ),
			new Article( "4960999845906", "EW-54 Streulichtblende ", 2000, 38 ),
			new Article( "4960999114552", "EW-83F Streulichtblende ", 7900, 31 ),
			new Article( "4960999780573", "ET-160 (WII) Streulichtblende ", 89900, 17 ),
			new Article( "4960999975702", "EW-60E Streulichtblende ", 2000, 3 ),
			new Article( "4960999977287", "EW-63C Streulichtblende ", 3500, 9 ),
			new Article( "4960999440859", "EW-79B II Streulichtblende", 6900, 26 ),
			new Article( "4960999780610", "EW-88C Streulichtblende ", 6900, 26 ),
			new Article( "4960999780627", "ES-52 Streulichtblende ", 3000, 58 ),
			new Article( "4960999780665", "EW-72 Streulichtblende ", 8900, 91 ),
			new Article( "4960999780689", "EW-65B Streulichtblende ", 5900, 32 ),
			new Article( "4549292010503", "EW-83M Streulichtblende", 4900, 75 ),
			new Article( "4549292010497", "EW-73C Streulichtblende", 3900, 50 ),
			new Article( "4960999845920", "EW-83L", 8900, 29 ),
			new Article( "4960999487564", "ET-120B Streulichtblende", 59900, 86 ),
			new Article( "4960999211077", "ET-65B Streulichtblende ", 9900, 54 ),
			new Article( "4549292010480", "EW-82 Streulichtblende", 8900, 79 ),
			new Article( "4960999440866", "ET-83 II Streulichtblende", 8500, 10 ),
			new Article( "4549292090147", "ES-27 Streulichtblende", 6500, 76 ),
			new Article( "4960999440804", "EW-75B II Streulichtblende", 5900, 45 ),
			new Article( "4960999980720", "ET-63 Streulichtblende ", 2900, 8 ),
			new Article( "4960999440712", "EW-60 II Streulichtblende", 6500, 26 ),
			new Article( "4960999440477", "ET-54 II Streulichtblende", 2900, 92 ),
			new Article( "4960999780559", "ET-138 (WII)", 67900, 98 ),
			new Article( "4549292010442", "ET-54B Streulichtblende", 2900, 45 ),
			new Article( "4960999440811", "ET-78 II Streulichtblende", 9500, 89 ),
			new Article( "4960999440798", "EW-75 II Streulichtblende", 10900, 96 ),
			new Article( "4960999354804", "EW-83J Streulichtblende", 8500, 29 ),
			new Article( "4960999440989", "ET-74 Streulichtblende", 8500, 65 ),
			new Article( "4960999440781", "EW-73 II Streulichtblende", 5500, 47 ),
			new Article( "4960999272337", "ET-67B Streulichtblende", 6500, 65 ),
			new Article( "4960999623993", "EW-83K Streulichtblende", 9900, 89 ),
			new Article( "4960999039503", "MP-E65 Streulichtblende", 6500, 14 ),
			new Article( "4960999634739", "ET-87 Streulichtblende", 11900, 89 ),
			new Article( "4960999440828", "EW-78 II Streulichtblende", 10500, 59 ),
			new Article( "4960999440217", "EW-68A Streulichtblende", 5500, 60 ),
			new Article( "4960999440767", "EW-65 II Streulichtblende", 5500, 91 ),
			new Article( "4960999440576", "EW-60C Streulichtblende", 3500, 32 ),
			new Article( "4960999635309", "EW-88B Streulichtblende ", 4500, 70 ),
			new Article( "4960999635316", "ET-73 Streulichtblende ", 8900, 64 ),
			new Article( "4960999002163", "ET-86 Streulichtblende ", 8900, 52 ),
			new Article( "4960999644967", "EW-78E Streulichtblende ", 8500, 25 ),
			new Article( "4960999417288", "EW-88 Streulichtblende", 8900, 22 ),
			new Article( "4549292063592", "EW-60F Streulichtblende", 2900, 65 ),
			new Article( "4960999440835", "EW-78B II Streulichtblende", 6500, 40 ),
			new Article( "4960999664941", "ET-120 (WII) Streulichtblende ", 56900, 0 ),
			new Article( "4960999664965", "ET-155 (WII) Streulichtblende ", 82900, 56 ),
			new Article( "4960999440774", "ES-71 II Streulichtblende", 5900, 3 ),
			new Article( "4960999440736", "ET-64 II Streulichtblende", 7500, 5 ),
			new Article( "4960999665047", "ET-73B Streulichtblende ", 8900, 3 ),
			new Article( "4960999440880", "EW-83 II Streulichtblende", 6500, 2 ),
			new Article( "4960999440910", "EW-83D I StreulichtblendeI", 10500, 3 ),
			new Article( "4960999440750", "ET-65 III Streulichtblende", 5900, 19 ),
			new Article( "4549292092035", "ET-88 Streulichtblende ", 5900, 11 ),
			new Article( "4549292092028", "ES-84 Streulichtblende", 5900, 12 ),
			new Article( "4960999035499", "EW-83E Streulichtblende ", 7900, 80 ),
			new Article( "4549292092011", "ET-83E Streulichtblende (Ersatz)", 5900, 54 ),
			new Article( "4960999440422", "ES-62 Streulichtblende", 4900, 58 ),
			new Article( "4960999440743", "ES-65 III Streulichtblende", 6900, 75 ),
			new Article( "4960999440996", "ET-67 Streulichtblende ", 8500, 60 ),
			new Article( "4960999441009", "EW-78D Streulichtblende ", 7500, 95 ),
			new Article( "4960999440927", "EW-78C Streulichtblende", 7500, 99 ),
			new Article( "4960999440491", "ET-60 III Streulichtblende", 2900, 57 ),
			new Article( "4960999440941", "ET-120 Streulichtblende", 56900, 59 ),
			new Article( "4960999440842", "ES-79 II Streulichtblende", 10900, 58 ),
			new Article( "4960999114729", "EW-63B Streulichtblende ", 4500, 57 ),
			new Article( "4549292037739", "ES-68 Streulichtblende", 3000, 56 ),
			new Article( "4960999440958", "ET-138 Streulichtblende", 67900, 35 ),
			new Article( "4549292037746", "ET-74B Streulichtblende ", 9900, 100 ),
			new Article( "4549292037869", "EW-53 Streulichtblende", 2100, 22 ),
			new Article( "4549292037876", "EW-88D Streulichtblende", 9500, 62 ),
			new Article( "4960999581088", "Rückdeckel E", 800, 53 ),
			new Article( "4960999845869", "E-67II", 1000, 98 ),
			new Article( "4960999780597", "E-185B", 10900, 86 ),
			new Article( "4549292063585", "Lens Cap EF-M28", 1000, 7 ),
			new Article( "4960999903156", "E-72II", 1000, 85 ),
			new Article( "4960999845890", "Lens Dust Cap EB", 800, 76 ),
			new Article( "4549292010589", "Lens Cap 11-24", 2500, 12 ),
			new Article( "4960999487557", "E-145B", 14500, 76 ),
			new Article( "4960999664958", "E-145C", 9900, 92 ),
			new Article( "4960999664972", "E-180D", 10900, 49 ),
			new Article( "4549292037883", "E-49", 800, 31 ),
			new Article( "4960999795898", "E-82II", 1200, 76 ),
			new Article( "4960999795904", "E-58II", 700, 25 ),
			new Article( "4960999665078", "Lens Cap 8-15", 1700, 55 ),
			new Article( "4960999532424", "Lens Cap 14", 2500, 65 ),
			new Article( "4960999581101", "E-73", 4500, 33 ),
			new Article( "4960999780566", "E-163B", 10900, 61 ),
			new Article( "4549292090154", "EF-S35", 1600, 90 ),
			new Article( "4960999845883", "E-77II", 1200, 98 ),
			new Article( "4960999975689", "Lens Cap E-55", 700, 32 ),
			new Article( "4960999845852", "E-52 II", 700, 28 ),
			new Article( "4960999845876", "Lens Cap E-43", 600, 2 ),
			new Article( "4960999635279", "Lens Cap 17", 5500, 32 ),
			new Article( "4960999581033", "RF 3", 1000, 62 ),
			new Article( "4960999581521", "Extender Cap E II", 1200, 87 ),
			new Article( "4960999204307", "Lens Ext. Tube EF-25 II", 16900, 69 ),
			new Article( "4960999543307", "250D (52mm) Nahlinse, mehrgliedriger Achromat", 10900, 49 ),
			new Article( "4960999543420", "Stativadapter W/USM zu EF100mm f/2.8 Macro", 19900, 1 ),
			new Article( "4960999543338", "500D (58mm) Nahlinse, mehrgliedriger Achromat", 12900, 6 ),
			new Article( "4960999543352", "500D (77mm) Nahlinse, mehrgliedriger Achromat", 22500, 52 ),
			new Article( "4960999302171", "AE-B1", 19900, 38 ),
			new Article( "4960999543345", "500D (72mm) Nahlinse", 19900, 27 ),
			new Article( "4960999543321", "500D (52mm) Nahlinse, mehrgliedriger Achromat", 10500, 59 ),
			new Article( "4960999543314", "250D (58mm) Nahlinse, mehrgliedriger Achromat", 12900, 56 ),
			new Article( "4960999665054", "Stativadapter C (W II) zu EF 70-300mm 4-5.6L", 19900, 19 ),
			new Article( "4960999677859", "Breiter Trageriemen B", 3900, 92 ),
			new Article( "4960999204291", "Lens Ext. Tube EF-12 II", 8500, 24 ),
			new Article( "4549292061772", "Power Zoom Adapter PZ-E1", 14900, 65 ),
			new Article( "4960999430607", "Weichzeichner II (52mm)", 6500, 53 ),
			new Article( "4960999635330", "Stativadapter D (zu EF100mm 2.8 Macro IS USM)", 19900, 63 ),
			new Article( "4960999405797", "Stativschelle A, schwarz", 19900, 75 ),
			new Article( "4960999405773", "Stativschelle A, weiss", 19900, 77 ),
			new Article( "4960999470894", "LZ 1324", 11900, 86 ),
			new Article( "4960999575186", "Lens Case 800", 99900, 20 ),
			new Article( "4960999470870", "LZ 1128", 12500, 3 ),
			new Article( "4960999470740", "LP 1214", 5500, 26 ),
			new Article( "4960999470757", "LP 1116", 5500, 75 ),
			new Article( "4960999470764", "LP 1014", 5500, 88 ),
			new Article( "4960999470771", "LP 1016", 5500, 21 ),
			new Article( "4960999470788", "LP 811", 3000, 63 ),
			new Article( "4960999470795", "LP 814", 4900, 26 ),
			new Article( "4960999470801", "LP 816", 4900, 96 ),
			new Article( "4960999470849", "LP 1019", 4900, 82 ),
			new Article( "4960999470856", "LP 1022", 5500, 87 ),
			new Article( "4960999470863", "LP1216", 5500, 70 ),
			new Article( "4960999470832", "LP 1219", 5900, 43 ),
			new Article( "4960999470825", "LP 1222", 5900, 71 ),
			new Article( "4960999035505", "LP 1319", 6500, 13 ),
			new Article( "4960999470993", "LP 1224", 6900, 86 ),
			new Article( "4960999470917", "Lens Case 300", 40900, 31 ),
			new Article( "4960999634746", "LZ-1326", 11900, 17 ),
			new Article( "4960999664989", "Lens Case 300B", 42900, 81 ),
			new Article( "4960999664996", "Lens Case 400C", 60900, 17 ),
			new Article( "4960999664828", "Extender EF 1.4x III", 47900, 75 ),
			new Article( "4960999664835", "Extender EF 2.0x III", 47900, 41 ),
			new Article( "4960999420509", "EF Life Size-Konverter", 32900, 0 ),
			new Article( "4549292099928", "LEGRIA HF G26 ", 99900, 36 ),
			new Article( "8714574657158", "LEGRIA HF G26 Power Kit", 104900, 77 ),
			new Article( "4549292088304", "LEGRIA HF R88 - Schwarz", 34900, 29 ),
			new Article( "4549292088311", "LEGRIA HF R87 - Schwarz", 34900, 80 ),
			new Article( "4549292088328", "LEGRIA HF R86 - Schwarz", 32900, 84 ),
			new Article( "8714574648811", "LEGRIA HF R87 - Schwarz Premium Kit", 37900, 56 ),
			new Article( "8714574648835", "LEGRIA HF R86 - Schwarz Premium Kit", 37900, 60 ),
			new Article( "4549292088359", "LEGRIA HF R806 - Schwarz", 24900, 60 ),
			new Article( "4549292088366", "LEGRIA HF R806 - Weiß", 24900, 0 ),
			new Article( "8714574648859", "LEGRIA HF R806 - Schwarz Essential Kit", 28900, 11 ),
			new Article( "8714574648880", "LEGRIA HF R806 - Weiß Essential Kit", 28900, 40 ),
			new Article( "4549292089943", "LEGRIA GX10", 268800, 17 ),
			new Article( "4549292105704", "XF405", 370500, 30 ),
			new Article( "4549292091052", "EOS Cinema C100 Mark II + EF 24-105mm f/3.5-5.6 IS STM", 517900, 2 ),
			new Article( "4549292090710", "ESO Cinema C200 + EF 24-105mm f/4 L IS II USM", 996500, 8 ),
			new Article( "4549292089806", "EOS Cinema C200", 892500, 86 ),
			new Article( "4549292081411", "EOS Cinema C700 GS PL", 3447500, 1 ),
			new Article( "8714574658179", "EOS Cinema C200 + CN-E 18-80mm T4.4 L IS KAS S", 1325900, 100 ),
			new Article( "4960999665580", "XF305", 459900, 22 ),
			new Article( "4960999665627", "XF300", 409900, 4 ),
			new Article( "4960999682167", "XF105", 249900, 59 ),
			new Article( "4960999685199", "XF100", 219900, 73 ),
			new Article( "4549292118377", "EOS Cinema C700 FF PL", 3622500, 77 ),
			new Article( "4549292070101", "ME200S-SH", 542900, 93 ),
			new Article( "4549292068054", "EOS Cinema C700 PL", 3229900, 56 ),
			new Article( "4549292067125", "XC15", 229900, 31 ),
			new Article( "4549292067064", "EOS Cinema C700 EF", 3229900, 71 ),
			new Article( "4960999846293", "EOS Cinema C100", 273900, 15 ),
			new Article( "4549292016727", "EOS Cinema C100 DAF", 297500, 97 ),
			new Article( "8714574623306", "EOS Cinema C100 DAF + EF-S 18-135mm f/3.5-5.6 IS USM (NANO)", 337900, 49 ),
			new Article( "4549292056877", "EOS Cinema C300 Mark II PL", 1189900, 27 ),
			new Article( "8714574648385", "XA30 BP-820 Power Kit", 159900, 56 ),
			new Article( "4549292053524", "XA30", 159900, 44 ),
			new Article( "4549292059823", "ME20F-SH", 2260900, 74 ),
			new Article( "4549292015584", "XF205", 319900, 84 ),
			new Article( "4549292015621", "XF200", 269900, 48 ),
			new Article( "4549292047097", "EOS Cinema C300 Mark II", 1189900, 6 ),
			new Article( "4549292089912", "XA11", 149900, 92 ),
			new Article( "4549292089882", "XA15", 199900, 19 ),
			new Article( "4549292089769", "XF400", 320500, 68 ),
			new Article( "8714574631097", "XC10 + CFast-Karte 128 GB Kit", 194900, 72 ),
			new Article( "8714574631080", "XC10 + CFast-Karte 64 GB Kit", 229900, 49 ),
			new Article( "8714574657127", "XA11 BP-820 Power Kit", 154900, 94 ),
			new Article( "4549292040517", "XC10", 164900, 45 ),
			new Article( "8714574652276", "EOS Cinema C100 Mark II + Atomos", 434900, 71 ),
			new Article( "8714574635132", "EOS Cinema C100 Mark II + EF-S 18-135mm f/3.5-5.6 IS STM", 477500, 39 ),
			new Article( "4549292027846", "EOS Cinema C100 Mark II", 434900, 93 ),
			new Article( "4549292024982", "EOS Cinema C300 EF DAF", 594900, 60 ),
			new Article( "4549292114997", "ME20F-SHN", 2379900, 89 ),
			new Article( "4549292118346", "EOS Cinema C700 FF", 3622500, 30 ),
			new Article( "4960999840024", "BP-727", 13900, 80 ),
			new Article( "4960999984520", "BP-828 Akku", 15900, 42 ),
			new Article( "4549292047523", "BP-A60", 49900, 67 ),
			new Article( "4549292047493", "BP-A30", 29900, 78 ),
			new Article( "4960999579696", "BP-808 Akku", 9900, 100 ),
			new Article( "4960999984490", "BP-820Akku", 11900, 83 ),
			new Article( "4960999671406", "BP-955 Akku", 24900, 4 ),
			new Article( "4960999671444", "BP-975 Akku", 33900, 54 ),
			new Article( "4960999321820", "BP-970 Akku", 30900, 75 ),
			new Article( "4960999819990", "BP-718", 9900, 94 ),
			new Article( "4960999781327", "BP-110 OTH Akku", 8900, 28 ),
			new Article( "4549292047943", "Microphone Adapter MA-400", 49900, 92 ),
			new Article( "4549292105391", "TL-U58", 64900, 92 ),
			new Article( "4960999639154", "TL-H58", 47900, 75 ),
			new Article( "4549292105407", "WA-U58 ", 64900, 62 ),
			new Article( "4960999984544", "WA-H58", 54900, 17 ),
			new Article( "4960999682747", "WD-H58W", 58900, 10 ),
			new Article( "4960999459950", "WD-H43", 31900, 65 ),
			new Article( "4960999184784", "CA-570E Netzadapter", 7900, 53 ),
			new Article( "4549292100310", "CG-A20", 45500, 39 ),
			new Article( "4549292055597", "CA-935", 13900, 66 ),
			new Article( "4960999612188", "CG-800", 9900, 70 ),
			new Article( "4549292047585", "CG-A10", 59900, 86 ),
			new Article( "4960999847719", "CG-700", 9900, 9 ),
			new Article( "4549292084245", "CA-946", 24900, 41 ),
			new Article( "4960999781402", "CA-110 (E)", 7900, 100 ),
			new Article( "4549292047554", "CA-A10 AC Adapter", 39900, 84 ),
			new Article( "4960999297897", "CB-2LWE Akkuladegerät", 6900, 13 ),
			new Article( "4549292072037", "Video Soft Case DVA-VC-E70", 3000, 33 ),
			new Article( "4549292080483", "SU-15 Shoulder Support Unit ", 209900, 22 ),
			new Article( "4960999605913", "SS-600 Schultergurt", 2900, 36 ),
			new Article( "4549292080476", "SG-1 Shoulder Style Grip Unit ", 259900, 58 ),
			new Article( "4549292080506", "Remote Operation Unit Cable UC-V1000", 49900, 25 ),
			new Article( "4549292080513", "Remote Operation Unit OU-700", 309900, 92 ),
			new Article( "4549292061703", "RR-10", 24900, 57 ),
			new Article( "4960999848556", "WL-D89", 1900, 36 ),
			new Article( "4549292014013", "RC-V100 Fernbedienung", 299900, 50 ),
			new Article( "4960999796253", "UA-100", 1900, 2 ),
			new Article( "4549292047974", "RD-1 ROD CLAMP", 19900, 18 ),
			new Article( "8714574652283", "Video Codex C700 Recorder Module (V)", 743900, 40 ),
			new Article( "8714574652290", "Video Codex C700 Recorder Module (A)", 743900, 40 ),
			new Article( "4549292080490", "Remote Operation Unit Cable UC-V75", 39900, 62 ),
			new Article( "4549292014440", "CT-V1 Schwenkbare Docking-Station ", 9900, 4 ),
			new Article( "4549292080469", "OLED Electronic View Finder EVF-V70", 619900, 15 ),
			new Article( "4549292047967", "Unit Cable UN-10", 34900, 1 ),
			new Article( "4549292061710", "RR-100", 99900, 69 ),
			new Article( "4549292100358", "Clamp base CL-V2", 49900, 46 ),
			new Article( "4549292047950", "Unit Cable UN-5", 29900, 84 ),
			new Article( "4549292088144", "Clamp Base CL-V1", 49900, 31 ),
			new Article( "4549292052831", "GR-E3 Brown", 4200, 34 ),
			new Article( "4549292013061", "Electronic Viewfinder EVF-DC1", 29900, 49 ),
			new Article( "4549292085228", "Electronic Viewfinder EVF-DC2", 26900, 71 ),
			new Article( "4549292021257", "CAMERA NECK STRAP EM-E2", 7500, 50 ),
			new Article( "4549292078602", "CAMERA NECK STRAP EM-300DB Black Nylon", 2400, 91 ),
			new Article( "4549292078596", "CAMERA NECK STRAP EM-E2 Black PU Leather", 3600, 45 ),
			new Article( "4549292065800", "DR-E17", 4700, 95 ),
			new Article( "4549292097245", "EH31-FJ Schutzhülle Plastik navy", 4700, 60 ),
			new Article( "4549292085112", "EH30-CJ BW Kamerahülle", 5500, 9 ),
			new Article( "4549292055870", "CAMERA CASE EH28-FJ Himbeerfarben", 4200, 20 ),
			new Article( "4549292055849", "CAMERA FACE JACKET EH28-FJ BG", 4200, 10 ),
			new Article( "4549292055863", "CAMERA FACE JACKET EH28-FJ OL", 4200, 0 ),
			new Article( "4549292055856", "CAMERA CASE EH28-FJ NavyBlue", 4200, 84 ),
			new Article( "4549292078619", "BODY JACKET EH29-CJ Black", 5500, 30 ),
			new Article( "4549292078626", "BODY JACKET EH29-CJ Brown", 5500, 100 ),
			new Article( "4549292055825", "CAMERA FACE JACKET EH28-FJ BD", 4200, 20 ),
			new Article( "4549292055887", "CAMERA FACE JACKET EH28-FJ YL", 4200, 7 ),
			new Article( "4549292052794", "CAMERA CASE EH28-CJ Braun", 4200, 28 ),
			new Article( "4549292021233", "BODY JACKET EH27-CJ", 10900, 1 ),
			new Article( "4549292055801", "CAMERA FACE JACKET EH28-FJ HZ", 4200, 31 ),
			new Article( "4549292085099", "EH30-CJ BLK Kamerahülle", 5500, 21 ),
			new Article( "4549292097153", "EH31-FJ Schutzhülle Leder hellbraun", 5500, 10 ),
			new Article( "4549292097207", "EH31-FJ Schutzhülle Plastik gelb", 4700, 43 ),
			new Article( "4549292100587", "EH31-FJ Schutzhülle Plastik rot", 4700, 48 ),
			new Article( "4549292097269", "EH31-FJ Schutzhülle Plastik grün", 4700, 3 ),
			new Article( "4549292097283", "EH31-FJ Schutzhülle Plastik blau/weiss", 4700, 45 ),
			new Article( "4549292097184", "EH31-FJ Schutzhülle Leder dunkelbraun", 5500, 65 ),
			new Article( "4549292097221", "EH31-FJ Schutzhülle Plastik gold", 4700, 50 ),
			new Article( "4549292097306", "EH31-FJ Schutzhülle Plastik schwarz/gelb", 4700, 41 ),
			new Article( "4960999302157", "Binocular 18x50 IS", 174500, 43 ),
			new Article( "4960999256498", "Binocular 10x42 L IS WP", 201500, 87 ),
			new Article( "4960999042350", "Binocular 8x25 IS", 39900, 80 ),
			new Article( "4549290000000", "Binocular 10x30 IS II", 55500, 47 ),
			new Article( "4549290000001", "Binocular 12x36 IS III", 83900, 29 ),
			new Article( "4549292063462", "Binocular 10x32 IS", 139900, 75 ),
			new Article( "4549292063479", "Binocular 12x32 IS", 144900, 20 ),
			new Article( "4549292063486", "Binocular 14x32 IS", 149900, 41 ),
			new Article( "4960999302140", "Binocular 15x50 IS", 140900, 99 ),
			new Article( "4960999901473", "CanoScan 9000F Mark II", 22900, 87 ),
			new Article( "4549292013405", "CanoScan LiDE 220", 9900, 96 ),
			new Article( "4549292013344", "CanoScan LiDE 120", 7900, 67 ),
			new Article( "8714574630489", "SELPHY CP1000 - Weiß", 9900, 66 ),
			new Article( "4549292091182", "SELPHY CP1300 - Weiß", 12900, 6 ),
			new Article( "4549292090512", "SELPHY CP1300 - Schwarz", 12900, 57 ),
			new Article( "4549292091243", "SELPHY CP1300 - Pink", 12900, 99 ),
			new Article( "4960999047065", "KC-18IL Tinten- und Papiersatz", 2400, 6 ),
			new Article( "4960999047072", "KC-18IF Tinten- und Papiersatz", 2400, 35 ),
			new Article( "4960999047034", "KP-36IP Tinten- und Papiersatz", 2500, 39 ),
			new Article( "4960999354897", "E-P25BW Easy Photo Pack", 2000, 89 ),
			new Article( "4960999357003", "E-P 100 Easy Photo Pack", 3800, 3 ),
			new Article( "4960999922171", "KC-18IS Tinten- und Papiersatz", 2400, 22 ),
			new Article( "4960999047058", "KC-36IP Tinten- und Papiersatz", 2400, 75 ),
			new Article( "4960999980034", "RP-108 Tinten- und Papiersatz", 3800, 0 ),
			new Article( "4960999980065", "RP-1080V", 35900, 10 ),
			new Article( "4960999846941", "PCC-CP400 Papierkassette", 3500, 62 ),
			new Article( "8714574532271", "DCC-CP2 Transporttasche - Weiß", 4700, 22 ),
			new Article( "8714574577135", "DCC-CP2 Transporttasche - Grau", 4700, 44 ),
			new Article( "8714574617275", "DPC-CP3 Transporttasche - Schwarz", 8500, 65 ),
			new Article( "4549292045055", "NB-CP2LH Akku", 5500, 51 ),
			new Article( "4960999905723", "CG-CP200 Ladeadapter", 8500, 52 ),
			new Article( "4960999848471", "DPC-CP100 Staubabdeckung", 2300, 92 ),
			new Article( "4549292051063", "MAXIFY MB2750", 17500, 7 ),
			new Article( "4549292077360", "MAXIFY MB5455", 27500, 5 ),
			new Article( "4549292077339", "MAXIFY MB5155", 20500, 94 ),
			new Article( "8714574643519", "MAXIFY MB 2150 AT", 13900, 99 ),
			new Article( "8714574643564", "MAXIFY MB2750 AT", 16900, 50 ),
			new Article( "8714574643786", "MAXIFY MB2755 AT", 16900, 17 ),
			new Article( "8714574643830", "MAXIFY MB5155 AT", 19900, 99 ),
			new Article( "8714574643731", "MAXIFY MB2155 AT", 13900, 5 ),
			new Article( "8714574643663", "MAXIFY MB 5450 AT", 27900, 25 ),
			new Article( "4549292052299", "MAXIFY MB5150", 20500, 30 ),
			new Article( "4549292052572", "MAXIFY MB5450", 27500, 99 ),
			new Article( "4549292051223", "MAXIFY MB2150", 15500, 42 ),
			new Article( "4549292077506", "MAXIFY MB2755", 17500, 84 ),
			new Article( "4549292052466", "MAXIFY IB4150", 15500, 72 ),
			new Article( "4549292023473", "PIXMA MX495 - Schwarz", 9900, 84 ),
			new Article( "4960999990224", "PIXMA MX475", 9900, 87 ),
			new Article( "4549292066517", "PIXMA TS5050 - Schwarz", 11500, 15 ),
			new Article( "4549292077599", "PIXMA TS5055 - Schwarz", 11500, 46 ),
			new Article( "4549292066630", "PIXMA TS5051 - Weiß", 11500, 43 ),
			new Article( "4549292066913", "PIXMA TS5053 - Grau", 11500, 96 ),
			new Article( "8714574647067", "PIXMA TS5055 - Schwarz + Papier PP-201", 11500, 59 ),
			new Article( "4549292072419", "PIXMA MG2555S", 7500, 17 ),
			new Article( "4549292092875", "PIXMA TS3150 - Schwarz", 7500, 75 ),
			new Article( "4549292092967", "PIXMA TS3150 - Weiß", 7500, 10 ),
			new Article( "4549292090598", "PIXMA TS6150 - Schwarz", 13900, 22 ),
			new Article( "4549292093308", "PIXMA TS8151 - Weiß", 19500, 85 ),
			new Article( "4549292091014", "PIXMA TR7550 - Schwarz", 16900, 51 ),
			new Article( "4549292093209", "PIXMA TS8150 - Schwarz", 19500, 56 ),
			new Article( "4549292093391", "PIXMA TS8152 - Rot", 19500, 52 ),
			new Article( "4549292091090", "PIXMA TR8550 - Schwarz", 19900, 91 ),
			new Article( "4549292090659", "PIXMA TS6151 - Weiß", 13900, 85 ),
			new Article( "4549292090840", "PIXMA TS5151 - Weiß", 10500, 88 ),
			new Article( "4549292090741", "PIXMA TS5150 - Schwarz", 10500, 84 ),
			new Article( "4549292036640", "PIXMA MG3650 - Rot", 8500, 23 ),
			new Article( "4549292036473", "PIXMA MG3650 - Weiß", 8500, 70 ),
			new Article( "4549292036305", "PIXMA MG3650 - Schwarz", 8500, 56 ),
			new Article( "4549292041354", "PIXMA MX495 - Weiß", 9900, 61 ),
			new Article( "4549292012477", "PIXMA iP110", 25500, 26 ),
			new Article( "4549292021950", "PIXMA PRO-100S", 49900, 70 ),
			new Article( "4549292039702", "imagePROGRAPH Pro-1000", 129900, 17 ),
			new Article( "4549292022070", "PIXMA PRO-10S", 70500, 31 ),
			new Article( "4960999847832", "PIXMA iP7250", 8500, 35 ),
			new Article( "8714574608488", "PIXMA iP7250 + Papier PP-201", 8900, 32 ),
			new Article( "4549292012842", "PIXMA iP110 mit Akku", 30500, 11 ),
			new Article( "4960999992167", "PIXMA iP8750", 35500, 90 ),
			new Article( "4549292096132", "PIXMA TS205", 4900, 33 ),
			new Article( "4549292096026", "PIXMA TS305", 5900, 17 ),
			new Article( "4960999991214", "PIXMA iX6850", 20500, 64 ),
			new Article( "4549292078664", "EOS M5 Gehäuse - Schwarz", 97900, 16 ),
			new Article( "4549292084832", "EOS M6 Gehäuse - Silber + EF-M 18-150mm f/3.5-6.3 IS STM Kit", 104900, 78 ),
			new Article( "4549292084764", "EOS M6 Gehäuse - Silber + EF-M 15-45mm f/3.5-6.3 IS STM Kit", 79900, 46 ),
			new Article( "4549292084702", "EOS M6 Gehäuse - Silber", 67900, 9 ),
			new Article( "4549292109412", "EOS M50 Gehäuse - Schwarz", 57900, 41 ),
			new Article( "4549292108910", "EOS M50 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM Kit", 69900, 86 ),
			new Article( "4549292109498", "EOS M50 Gehäuse - Schwarz + EF-M 18-150mm f/3.5-6.3 IS STM Kit", 94900, 81 ),
			new Article( "8714574657660", "EOS M50 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM Value Up Kit", 72900, 21 ),
			new Article( "4549292109009", "EOS M50 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM + EF-M 55-200mm f/4-6.3 IS STM Kit", 92900, 2 ),
			new Article( "4549292109467", "EOS M50 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM + EF-M 22mm f/2 STM Kit", 84900, 76 ),
			new Article( "8714574657639", "EOS M50 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM + Mount Adapter EF-EOS M + EF 50mm f/1. ", 96900, 65 ),
			new Article( "4549292109528", "EOS M50 Gehäuse - Wei.", 57900, 0 ),
			new Article( "4549292109177", "EOS M50 Gehäuse - Wei. + EF-M 15-45mm f/3.5-6.3 IS STM Kit", 69900, 81 ),
			new Article( "4549292109580", "EOS M50 Gehäuse - Wei. + EF-M 18-150mm f/3.5-6.3 IS STM Kit", 94900, 5 ),
			new Article( "4549292094206", "EOS M6 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM + EF-M 55-200mm f/4-6.3 IS STM Kit", 102900, 52 ),
			new Article( "4549292084511", "EOS M6 Gehäuse - Schwarz + EF-M 18-150mm f/3.5-6.3 IS STM Kit", 104900, 4 ),
			new Article( "4549292084429", "EOS M6 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM Kit", 79900, 25 ),
			new Article( "4549292084344", "EOS M6 Gehäuse - Schwarz", 67900, 96 ),
			new Article( "8714574654553", "EOS M100 Gehäuse - Grau + EF-M 15-45mm f/3.5-6.3 IS STM Kit", 49900, 71 ),
			new Article( "8714574654539", "EOS M100 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM Kit", 49900, 66 ),
			new Article( "4549292096903", "EOS M100 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM + EF-M 22mm f/2 STM Kit", 64900, 22 ),
			new Article( "4549292093759", "EOS M100 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM + EF-M 55-200mm f/4-6.3 IS STM Kit", 72900, 57 ),
			new Article( "4549292093650", "EOS M100 Gehäuse - Schwarz", 37900, 32 ),
			new Article( "4549292093896", "EOS M100 Gehäuse - Weiß + EF-M 15-45mm f/3.5-6.3 IS STM + EF-M 55-200mm f/4-6.3 IS STM Kit", 72900, 2 ),
			new Article( "4549292094022", "EOS M100 Gehäuse - Grau + EF-M 15-45mm f/3.5-6.3 IS STM + EF-M 55-200mm f/4-6.3 IS STM Kit", 72900, 69 ),
			new Article( "4549292093940", "EOS M100 Gehäuse - Grau", 37900, 63 ),
			new Article( "4549292093810", "EOS M100 Gehäuse - Wei.", 37900, 51 ),
			new Article( "4549292094244", "EOS M6 Gehäuse - Silber + EF-M 15-45mm f/3.5-6.3 IS STM + EF-M 55-200mm f/4-6.3 IS STM Kit", 102900, 59 ),
			new Article( "4549292078718", "EOS M5 Gehäuse - Schwarz + EF-M 15-45mm f/3.5-6.3 IS STM Kit", 109900, 21 ),
			new Article( "8714574646589", "EOS M5 Gehäuse - Schwarz + EF-M 18-150mm f/3.5-6.3 IS STM + Mount Adapter EF-EOS M Kit", 134900, 48 ),
			new Article( "4549292078787", "EOS M5 Gehäuse - Schwarz + EF-M 18-150mm f/3.5-6.3 IS STM Kit", 134900, 63 ),
			new Article( "8714574654546", "EOS M100 Gehäuse - Weiß + EF-M 15-45mm f/3.5-6.3 IS STM Kit", 49900, 27 ),
			new Article( "4549292077421", "CN-E 18-80mm T4.4 L IS KAS S", 618900, 34 ),
			new Article( "", "CN-E 20mm T1.5 L F (M)", 511900, 38 ),
			new Article( "4960999936024", "CN-E 24mm L F (M)", 498900, 43 ),
			new Article( "4960999975061", "CN-E 14mm T3.1 L F (M)", 533700, 86 ),
			new Article( "", "CN-E 20mm T1.5 L F (F)", 511900, 100 ),
			new Article( "4960999975078", "CN-E 135mm T2.2 L F (F)", 510500, 97 ),
			new Article( "4960999941257", "CN-E 85mm L F (M)", 452500, 53 ),
			new Article( "4960999904214", "CN-E 24mm L F (F)", 498900, 89 ),
			new Article( "4960999975085", "CN-E 135mm T2.2 L F (M)", 510500, 66 ),
			new Article( "4960999904238", "CN-E 85mm L F (F)", 452500, 84 ),
			new Article( "4960999944586", "CN-E 50mm L F (M)", 452500, 79 ),
			new Article( "4549292002676", "CN-E 35mm F1.5 L F (M)", 452500, 97 ),
			new Article( "4549292002669", "CN-E 35mm F1.5 L F (F)", 452500, 50 ),
			new Article( "4960999904221", "CN-E 50mm L F (F)", 452500, 59 ),
			new Article( "4549292106404", "CN-E 70-200mm T4.4 L IS", 513500, 97 ),
			new Article( "4960999975054", "CN-E 14mm T3.1 L F (F)", 533700, 68 ),
			new Article( "4549292080186", "ZSG-C10 Zoomgriff", 54500, 24 )

		).stream()
			.limit( nItems )
			.collect( Collectors.toList());

		catalog.addAll( newItems );

		return catalog;
	}


	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter and
	 * prepend prefix and append postfix String.
	 * For example, 299, "(", ")" -> "(2,99)"
	 * 
	 * @param price price as long in 1/100 (cents)
	 * @param prefix String to prepend before price
	 * @param postfix String to append after price
	 * @return price as String
	 */
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


	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter.
	 * For example, 299 -> "2,99"
	 * 
	 * @param sb StringBuffer to which price is added
	 * @param price price as long in 1/100 (cents)
	 * @return StringBuffer with added price
	 */
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


	/**
	 * Pad string to minimum width, either right-aligned or left-aligned
	 * 
	 * @param str String to pad
	 * @param width minimum width to which result is padded
	 * @param rightAligned flag to chose left- or right-alignment
	 * @return padded String
	 */
	private String pad( String str, int width, boolean rightAligned ) {
		String fmtter = ( rightAligned? "%" : "%-" ) + width + "s";
		String padded = String.format( fmtter, str );
		return padded;
	}

}


