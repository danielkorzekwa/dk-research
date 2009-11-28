package dk.betex.ecosystem.webconsole.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import dk.betex.ecosystem.webconsole.client.model.MarketTradedVolume;

/** Returns traded volume at each price on all of the runners in a particular market.
 * 
 * @author korzekwad
 *
 */

@RemoteServiceRelativePath( "MarketTradedVolume" )
public interface MarketTradedVolumeService extends RemoteService{

	/**Login to the betfair account.
	 * 
	 * @param user
	 * @param password
	 * @param productId
	 * @return true if login successfully, false if not (wrong user/pass/productId)
	 */
	public boolean login(String user,String password, int productId);
	
	/**Returns traded volume at each price on all of the runners in a particular market
	 * 
	 * @param marketId
	 * @return
	 */
	public MarketTradedVolume getMarketTradedVolume(int marketId);
}
