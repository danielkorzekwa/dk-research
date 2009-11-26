package dk.betex.ecosystem.webconsole.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dk.betex.ecosystem.webconsole.client.MarketTradedVolume;
import dk.betex.ecosystem.webconsole.client.MarketTradedVolumeService;

/** Returns traded volume at each price on all of the runners in a particular market.
 * 
 * @author korzekwad
 *
 */
public class MarketTradedVolumeServiceImpl extends RemoteServiceServlet implements MarketTradedVolumeService{

	@Override
	public MarketTradedVolume getMarketTradedVolume(int marketId) {
		// TODO Auto-generated method stub
		return null;
	}



}
