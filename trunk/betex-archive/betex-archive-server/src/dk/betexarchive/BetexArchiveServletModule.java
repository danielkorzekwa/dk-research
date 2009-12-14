package dk.betexarchive;

import com.google.inject.servlet.ServletModule;

import dk.betexarchive.servlet.GetPricesChartServlet;
import dk.betexarchive.servlet.GetPricesServlet;
import dk.betexarchive.servlet.StorePricesServlet;

public class BetexArchiveServletModule extends ServletModule{

	@Override
	protected void configureServlets() {
		serve("/storePrices").with(StorePricesServlet.class);
		serve("/getPrices").with(GetPricesServlet.class);
		serve("/getPricesChart").with(GetPricesChartServlet.class);
	}
	
	
}
