package dk.betexarchive.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import dk.betexarchive.domain.LastMatchedPriceJDO;

/**
 * DAO object for last matched prices on a market runner.
 * 
 * @author daniel
 * 
 */
public class LastMatchedPriceDAOImpl implements LastMatchedPriceDAO {

	@Override
	public List<LastMatchedPriceJDO> getPrices(long marketId, long selectionId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<LastMatchedPriceJDO> prices = (List<LastMatchedPriceJDO>) pm.newQuery(
					"SELECT FROM " + LastMatchedPriceJDO.class.getName()
							+ " WHERE selectionId==:selectionIdValue && marketId==:marketIdValue").execute(selectionId,
					marketId);
			
			return (List<LastMatchedPriceJDO>)pm.detachCopyAll(prices);
		} finally {
			pm.close();
		}
	}

	@Override
	public void storePrices(List<LastMatchedPriceJDO> prices) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistentAll(prices);
		} finally {
			pm.close();
		}
	}
}
