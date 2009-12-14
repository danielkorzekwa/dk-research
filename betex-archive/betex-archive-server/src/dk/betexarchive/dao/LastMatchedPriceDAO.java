package dk.betexarchive.dao;

import java.util.List;

import dk.betexarchive.domain.LastMatchedPriceJDO;

public interface LastMatchedPriceDAO {
	
	public List<LastMatchedPriceJDO> getPrices(long marketId, long selectionId);
	
	public void storePrices(List<LastMatchedPriceJDO> prices);
}
