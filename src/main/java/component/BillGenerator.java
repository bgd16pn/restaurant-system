package component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.Restaurant;
import model.BaseProduct;
import model.MenuItem;
import model.Order;

public class BillGenerator {

	protected static final Logger LOGGER = Logger.getLogger(BillGenerator.class.getName());

	private BillGenerator() {

	}

	public static void generateBill(Restaurant restaurant, Order order, String outputPath) {
		File fout = new File(outputPath);
		try (FileOutputStream fos = new FileOutputStream(fout)) {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("Restaurant " + restaurant.getName());
			bw.newLine();

			bw.write("Date: " + order.getDate());
			bw.newLine();

			bw.write("Table: " + order.getTableId());
			bw.newLine();
			bw.newLine();

			bw.write("\t\t\t\tOrder #" + order.getId());
			bw.newLine();
			bw.write("=======================================================================");
			bw.newLine();
			bw.newLine();

			bw.write("Products:");
			bw.newLine();

			int index = 1;
			for (MenuItem item : order.getOrderedItems()) {
				bw.write(index + ". " + ((BaseProduct) item).getDescription() + "......................"
						+ item.computePrice());
				bw.newLine();
			}

			bw.close();
			LOGGER.log(Level.INFO, "Bill was generated");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

}
