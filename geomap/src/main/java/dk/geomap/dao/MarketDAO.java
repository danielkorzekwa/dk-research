package dk.geomap.dao;

import java.util.Date;

import dk.geomap.dao.model.Market;

/**
 * DAO object for for exchange market.
 * 
 * @author korzekwad
 * 
 */
public interface MarketDAO {

	/** Add market to database */
	public void addMarket(Market market);

	/**
	 * Update total matched for market on a betting exchange.
	 * 
	 * @param bettingExchangeId
	 * @param marketId
	 * @param totalMatched
	 */
	public void updateTotalMatched(BettingExchangeEnum bettingExchangeId, long marketId, double totalMatched);

	/**
	 * 
	 * @param bettingExchangeId
	 * @param marketId
	 * @return null if market not exist
	 */
	public Market getMarket(BettingExchangeEnum bettingExchangeId, long marketId);
	
	/**Returns total matched for all markets for a given period of time 
	 * 
	 * @param exchange
	 * @param marketTimeFrom
	 * @param marketTimeTo
	 * @return
	 */
	public double getTotalMatched(BettingExchangeEnum exchange, Date marketTimeFrom, Date marketTimeTo);
}
