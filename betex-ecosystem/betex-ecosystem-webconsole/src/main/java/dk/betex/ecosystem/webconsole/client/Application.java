package dk.betex.ecosystem.webconsole.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.PieChart;

import dk.betex.ecosystem.webconsole.client.visualizations.BioHeatMap;
import dk.betex.ecosystem.webconsole.client.visualizations.BioHeatMap.Options;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Create a callback to be called when the visualization API
		// has been loaded.
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				Panel panel = RootPanel.get();

				final Options options = BioHeatMap.Options.create();
				options.setHeight(50);
				options.setWidth(50);
				options.setWidth(300);
				options.setHeight(500);
				final BioHeatMap bioHeatMap = new BioHeatMap(createTable(), options);
				panel.add(bioHeatMap);

				final Timer timer = new Timer() {

					@Override
					public void run() {
						bioHeatMap.draw(createTable(), options);

					}

				};
				
				Button start = new Button("start");
				panel.add(start);
				start.addClickListener(new ClickListener() {
				
					@Override
					public void onClick(Widget arg0) {
						timer.scheduleRepeating(1000/10);
					}
				
				});
				
			}
		};

		// Load the visualization api, passing the onLoadCallback to be called
		// when loading is done.
		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);

	}

	private AbstractDataTable createTable() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Price");
		for (int runnerId = 0; runnerId < 10; runnerId++) {
			data.addColumn(ColumnType.NUMBER, "Runner " + runnerId);
		}

		data.addRows(100);
		for (int price = 0; price < 100; price++) {
			data.setValue(price, 0, "" + price);
			for (int runnerId = 0; runnerId < 10; runnerId++) {
				data.setValue(price, runnerId + 1, Random.nextInt(100));
			}
		}

		return data;
	}
}
