package assign4;

import org.junit.Assert;
import org.junit.Test;

import model.BaseProduct;
import model.CompositeProduct;
import model.MenuItem;

public class TestMenuItems {

	@Test
	public void testItems() {

		MenuItem prod1 = new BaseProduct("Supa", 5.2);
		MenuItem prod2 = new BaseProduct("Snitel", 8.7);
		MenuItem prod3 = new BaseProduct("Piure", 4.4);
		MenuItem prod4 = new BaseProduct("Ceai", 6);
		MenuItem prod5 = new BaseProduct("Apa plata", 3.3);

		CompositeProduct comanda1 = new CompositeProduct();
		comanda1.addMenuItem(prod1);
		comanda1.addMenuItem(prod2);
		comanda1.addMenuItem(prod3);

		Assert.assertTrue(Math.abs(comanda1.computePrice() - 18.3) < 1e-5);
		
		CompositeProduct comanda2 = new CompositeProduct();
		comanda2.addMenuItem(prod4);
		comanda2.addMenuItem(prod5);
		
		Assert.assertTrue(Math.abs(comanda2.computePrice() - 9.3) < 1e-5);

		CompositeProduct comenzi = new CompositeProduct();
		comenzi.addMenuItem(comanda1);
		comenzi.addMenuItem(comanda2);

		Assert.assertTrue(Math.abs(comenzi.computePrice() - 27.6) < 1e-5);
	}

}
