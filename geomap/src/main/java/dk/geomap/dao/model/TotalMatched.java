package dk.geomap.dao.model;

import java.util.Date;

import dk.geomap.dao.BettingExchangeEnum;

/**Data model for total matched at exchange market.
 * 
 * @author korzekwad
 *
 */
public class TotalMatched  implements Comparable<TotalMatched>{

	private final BettingExchangeEnum exchange;
	private final Date timestamp;
	private final double totalMatched;

	public TotalMatched(BettingExchangeEnum exchange, Date timestamp, double totalMatched) {
		this.exchange = exchange;
		this.timestamp = timestamp;
		this.totalMatched = totalMatched;
	}

	public BettingExchangeEnum getExchange() {
		return exchange;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public double getTotalMatched() {
		return totalMatched;
	}
	
	public int compareTo(TotalMatched o) {
		return this.timestamp.compareTo(o.getTimestamp());
	}
	
}
