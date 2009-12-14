package dk.betexarchive.servlet;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dk.betexarchive.dao.LastMatchedPriceDAO;
import dk.betexarchive.domain.LastMatchedPriceJDO;
import dk.betexarchive.domain.LastMatchedPriceParser;

/**
 * Returns last matched prices for a market selection.
 * 
 * @author daniel
 * 
 */
@Singleton
public class GetPricesServlet extends HttpServlet {

	@Inject
	private LastMatchedPriceDAO lastMatchedPriceDAO;

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

		try {
			resp.setContentType("text/plain");
			List<LastMatchedPriceJDO> prices = lastMatchedPriceDAO.getPrices(marketIdValue, selectionIdValue);
			String pricesData = new LastMatchedPriceParser().parseLastMatchedPrices(prices);
			resp.getWriter().print(pricesData);

		} catch (Exception e) {
			resp.getWriter().print("ERROR. " + e.getLocalizedMessage());
			return;
		}
	}
}
