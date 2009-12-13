package dk.geomap.dao;

import java.util.Date;
import java.util.List;

import dk.geomap.dao.model.TotalMatched;

/**DAO object for total amount of money matched on a betting exchange at a given time.
 * 
 * @author korzekwad
 *
 */
public interface TotalMatchedDAO {
	
	/**
	 * 
	 * @param exchange
	 * @param timestamp Point of time that totalMatched was calculated.
	 * @param totalMatched
	 */
	public void add(BettingExchangeEnum exchange, Date timestamp, double totalMatched);
	
	public List<TotalMatched> getTotalMatched(BettingExchangeEnum exchange, Date day);
}
