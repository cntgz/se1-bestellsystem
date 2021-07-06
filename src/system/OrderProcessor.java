package system;

import datamodel.Order;
import datamodel.RawDataFactory;
import datamodel.RawDataFactory.RawDataFactoryIntf;

final class OrderProcessor implements Components.OrderProcessor {
	
	private static InventoryManager inventoryManager;
	private static OrderProcessor instance = null;

	OrderProcessor(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	@Override
	public boolean accept(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long orderValue(Order order) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long vat(long grossValue) {
		return vat(grossValue,1);
	}

	@Override
	public long vat(long grossValue, int rateIndex) {
		double mwst = 0;
		double full = 0;
		switch(rateIndex) {
		case 1: mwst = 0.19; full = 1.19; break;
		case 2: mwst = 0.07; full = 1.07; break;
		}
		long steuerbetrag = Math.round((grossValue/ full) * mwst);
		
		return steuerbetrag;
	}
	
	public static OrderProcessor getInstance() {
		if( instance == null) {
			instance = new OrderProcessor(inventoryManager);
			return instance;
		}
		return null;
	}

}
