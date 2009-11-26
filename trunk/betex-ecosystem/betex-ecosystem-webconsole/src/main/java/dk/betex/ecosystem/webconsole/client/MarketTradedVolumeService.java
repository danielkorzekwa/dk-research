package dk.betex.ecosystem.webconsole.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/** Returns traded volume at each price on all of the runners in a particular market.
 * 
 * @author korzekwad
 *
 */

@RemoteServiceRelativePath( "MarketTradedVolume" )
public interface MarketTradedVolumeService extends RemoteService{

	/**Returns traded volume at each price on all of the runners in a particular market
	 * 
	 * @param marketId
	 * @return
	 */
	public MarketTradedVolume getMarketTradedVolume(int marketId);
}
