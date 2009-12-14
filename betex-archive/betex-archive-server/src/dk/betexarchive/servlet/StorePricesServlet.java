package dk.betexarchive.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dk.betexarchive.dao.LastMatchedPriceDAO;
import dk.betexarchive.domain.LastMatchedPriceJDO;
import dk.betexarchive.domain.LastMatchedPriceParser;

/**
 * Stores last matched prices for markets in a database.
 * 
 * POST data contains lastRunnerPrices in a comma separated text format. See LastMatchedPriceParser for details.
 * Example: 1,2,2.34,2009.05.16 22:12:45;4,5,3.76,2009.05.16 13:23:09
 * 
 * @author daniel
 * 
 */
@SuppressWarnings("serial")
@Singleton
public class StorePricesServlet extends HttpServlet {

	@Inject
	LastMatchedPriceDAO lastMatchedPriceDAO; 
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			resp.setContentType("text/plain");
			InputStream in = (InputStream) req.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(in, writer);
			String dataString = writer.toString();

			List<LastMatchedPriceJDO> prices = new LastMatchedPriceParser().parseLastMatchedPrices(dataString);
			lastMatchedPriceDAO.storePrices(prices);			
			resp.getWriter().print("OK.");
		} catch (Exception e) {
			resp.getWriter().print("ERROR. " + e.getLocalizedMessage());
		}
	}
}
