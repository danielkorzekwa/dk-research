package dk.betex.ecosystem.webconsole.server;

import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dk.betex.ecosystem.webconsole.client.model.MarketTradedVolume;
import dk.betex.ecosystem.webconsole.client.service.MarketTradedVolumeService;
import dk.bot.betfairservice.BetFairService;
import dk.bot.betfairservice.DefaultBetFairServiceFactoryBean;
import dk.bot.betfairservice.model.BFMarketTradedVolume;

/**
 * Returns traded volume at each price on all of the runners in a particular market.
 * 
 * @author korzekwad
 * 
 */
public class MarketTradedVolumeServiceImpl extends RemoteServiceServlet implements MarketTradedVolumeService {

	private DefaultBetFairServiceFactoryBean betfairServiceFactoryBean;
	private BetFairService betfairService;

	@Override
	public void init() throws ServletException {

		DefaultBetFairServiceFactoryBean betfairServiceFactoryBean = new DefaultBetFairServiceFactoryBean();
		try {
			betfairService = (BetFairService) betfairServiceFactoryBean.getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void login(String user, String password, int productId) {
		betfairServiceFactoryBean.setUser(user);
		betfairServiceFactoryBean.setPassword(password);
		betfairServiceFactoryBean.setProductId(productId);
		betfairServiceFactoryBean.login();
	}
	
	@Override
	public MarketTradedVolume getMarketTradedVolume(int marketId) {
		BFMarketTradedVolume bfMarketTradedVolume = betfairService.getMarketTradedVolume(marketId);
		MarketTradedVolume marketTradedVolume = MarketTradedVolumeFactory.create(bfMarketTradedVolume);
		
		return marketTradedVolume;
	}

}
