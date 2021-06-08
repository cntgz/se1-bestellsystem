package system;

import datamodel.Order;

final class OrderProcessor implements Components.OrderProcessor {
	
	private InventoryManager inventoryManager;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long vat(long grossValue, int rateIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

}
