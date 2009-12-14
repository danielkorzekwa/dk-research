package dk.betexarchive.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dk.betexarchive.dao.LastMatchedPriceDAO;
import dk.betexarchive.domain.LastMatchedPriceJDO;
import dk.betexarchive.visualization.TimeChartData;
import dk.betexarchive.visualization.TimeChartDataValue;
import dk.betexarchive.visualization.TimeLineChart;

/**
 * Returns time line chart with last matched prices for market runner over the time.
 * 
 * @author daniel
 * 
 */
@Singleton
public class GetPricesChartServlet extends HttpServlet {

	@Inject
	private LastMatchedPriceDAO lastMatchedPriceDAO;

	/** Generates time line chart. */
	private TimeLineChart timeLineChart;

	@Override
	public void init() throws ServletException {
		timeLineChart = new TimeLineChart(new VelocityEngine());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String marketId = req.getParameter("marketId");
		String selectionId = req.getParameter("selectionId");
		if (marketId == null || selectionId == null) {
			resp.getWriter().print("ERROR. " + "The marketId/selectionId parameters are not provided.");
			return;
		}
		Long marketIdValue = null;
		Long selectionIdValue = null;
		try {
			marketIdValue = Long.parseLong(marketId);
			selectionIdValue = Long.parseLong(selectionId);
		} catch (NumberFormatException e) {
			resp.getWriter().print("ERROR. " + "The marketId/selectionId must be numbers.");
			return;
		}
		/** if true then show implied probability instead of decimal odds. */
		boolean showProbability = false;
		showProbability = new Boolean(req.getParameter("prob"));

		try {
			resp.setContentType("text/html");
			List<LastMatchedPriceJDO> prices = lastMatchedPriceDAO.getPrices(marketIdValue, selectionIdValue);
			String htmlReport = createHtmlChart(prices, showProbability);
			resp.getWriter().print(htmlReport);
		} catch (Exception e) {
			resp.getWriter().print("ERROR. " + e.getLocalizedMessage());
			return;
		}
	}

	private String createHtmlChart(List<LastMatchedPriceJDO> prices, boolean showProbability) {
		/** Create time chart html report for last matched prices for market runner. */
		String[] series = new String[] { "matchedPrice", "avgPrice15MinSlope" };
		TimeChartDataValue[] values = new TimeChartDataValue[prices.size()];
		for (int i = 0; i < prices.size(); i++) {
			LastMatchedPriceJDO price = prices.get(i);
			double value = showProbability ? 1 / price.getLastMatchedPrice() : price.getLastMatchedPrice();
			TimeChartDataValue chartData = new TimeChartDataValue(price.getLastMatchedDate(), new Double[] { value,
					price.getAvgPrice15MinSlope() != null ? price.getAvgPrice15MinSlope()*0.01 : 0 });
			values[i] = chartData;
		}
		TimeChartData chartData = new TimeChartData(series, values);
		String htmlReport = timeLineChart.generate(chartData);

		return htmlReport;
	}
}
