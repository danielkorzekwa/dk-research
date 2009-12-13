package dk.geomap.dao.model;

import java.util.Date;

import dk.geomap.dao.BettingExchangeEnum;

/**
 * Bean for market in database
 * @author korzekwad
 *
 */
public class Market {

	private final BettingExchangeEnum bettingExchange;
	private final long marketId;
	private final Date marketTime;
	private final double totalMatched;

	public Market(BettingExchangeEnum bettingExchange, long marketId, Date marketTime, double totalMatched) {
		this.bettingExchange = bettingExchange;
		this.marketId = marketId;
		this.marketTime = marketTime;
		this.totalMatched = totalMatched;
	}

	public BettingExchangeEnum getBettingExchange() {
		return bettingExchange;
	}

	public long getMarketId() {
		return marketId;
	}

	public Date getMarketTime() {
		return marketTime;
	}

	public double getTotalMatched() {
		return totalMatched;
	}
}
