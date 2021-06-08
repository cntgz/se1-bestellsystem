package system;

import java.util.Optional;
import java.util.stream.Stream;

import datamodel.Article;

final class InventoryManager implements Components.InventoryManager {
	
	InventoryManager(){
		
	}

	@Override
	public long inventoryValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Stream<Article> inventoryAtOrLess(int units) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Article> inventoryTopRanksByValue(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsArticle(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Article> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Article> get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public system.Components.InventoryManager add(Article article) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public system.Components.InventoryManager remove(Article article) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
