package dk.geomap.updatetotalmatched;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import dk.bot.betdaq.BetDaq;
import dk.bot.betdaq.model.BDEvent;
import dk.bot.betdaq.model.BDMarket;
import dk.bot.betdaq.model.BDMarketHelper;
import dk.bot.betdaq.model.BDMarketWithPrices;
import dk.bot.betfairservice.BetFairService;
import dk.bot.betfairservice.model.BFMarketDetails;
import dk.geomap.dao.BettingExchangeEnum;
import dk.geomap.dao.MarketDAO;
import dk.geomap.dao.TotalMatchedDAO;
import dk.geomap.dao.model.Market;

/**
 * Retrieve betdaq HR_UK markets for today and update them in database.
 * 
 * @author korzekwad
 * 
 */
public class BDUpdateTotalMatched implements UpdateTotalMatched {

	private final BetDaq betDaq;
	private final MarketDAO marketDao;

	/** Markets already existing in database. Key - marketId, Value - latest total matched. */
	private Map<Long, Double> marketsInDb = new HashMap<Long, Double>();
	
	private final TotalMatchedDAO totalMatchedDao;

	public BDUpdateTotalMatched(BetDaq betDaq, MarketDAO marketDao,TotalMatchedDAO totalMatchedDao) {
		this.betDaq = betDaq;
		this.marketDao = marketDao;
		this.totalMatchedDao = totalMatchedDao;
	}

	public void update() {
		DateMidnight dayStart = new DateMidnight();
		DateTime dayEnd = dayStart.plusDays(1).toDateTime().minus(1);
		
		/**Get markets from betfair.com.*/
		List<BDMarketWithPrices> markets = getBDMarkets();

		/**Update markets in db.*/
		for (BDMarketWithPrices market : markets) {
			if (market.getTotalMatchedAmount() != null) {

				/** If market doesn't exist in db then add it. */
				Double lastTotalMatched = marketsInDb.get(market.getId());
				if (lastTotalMatched == null) {
					Market marketFromDb = marketDao.getMarket(BettingExchangeEnum.BETDAQ, market.getId());
					if (marketFromDb == null) {
						marketDao.addMarket(new Market(BettingExchangeEnum.BETDAQ, market.getId(), market
								.getStartTime(), market.getTotalMatchedAmount().doubleValue()));
						
						lastTotalMatched = new Double(market.getTotalMatchedAmount().doubleValue());
					}
					else {
						lastTotalMatched = marketFromDb.getTotalMatched();
					}
					marketsInDb.put(market.getId(), lastTotalMatched);
				}

				/** Update total matched for market. */
				if (market.getTotalMatchedAmount().doubleValue() < lastTotalMatched) {
					System.out.println("BETDAQ: New total matched is < than last total matched."
							+ market.getTotalMatchedAmount().doubleValue() + " < " + lastTotalMatched);
				} else if (market.getTotalMatchedAmount().doubleValue() > lastTotalMatched) {
					marketDao.updateTotalMatched(BettingExchangeEnum.BETDAQ, market.getId(), market.getTotalMatchedAmount()
							.doubleValue());
				}
				
			}
		}
		
		/**Update total matched for betting exchange in db*/
		/**Update total matched for betting exchange in db*/
		double totalMatched = marketDao.getTotalMatched(BettingExchangeEnum.BETDAQ, dayStart.toDate(),dayEnd.toDate());
		totalMatchedDao.add(BettingExchangeEnum.BETDAQ, new Date(System.currentTimeMillis()), totalMatched);
		
	}

	/**
	 * Get markets with total matched from betdaq.com
	 * 
	 * @return
	 */
	private List<BDMarketWithPrices> getBDMarkets() {
		List<BDEvent> events = betDaq.getEventSubTreeNoSelections(Arrays.asList(100004l), false);

		List<BDMarket> markets = new ArrayList<BDMarket>();
		for (BDEvent event : events) {
			markets.addAll(BDMarketHelper.getAllMarkets(event));
		}

		List<BDMarketWithPrices> marketsWithPrices = new ArrayList<BDMarketWithPrices>();

		List<Long> marketIds = new ArrayList<Long>();
		for (int i = 0; i < markets.size(); i++) {
			marketIds.add(markets.get(i).getId());

			if (marketIds.size() == 50) {
				List<BDMarketWithPrices> fiftyMarkets = betDaq.getMarketsWithPrices(marketIds, 0, 0, 0, false);
				marketsWithPrices.addAll(fiftyMarkets);
				marketIds.clear();
			}
		}

		/** Get remaining markets */
		if (marketIds.size() > 0) {
			List<BDMarketWithPrices> remainingMarkets = betDaq.getMarketsWithPrices(marketIds, 0, 0, 0, false);
			marketsWithPrices.addAll(remainingMarkets);
		}

		return marketsWithPrices;
	}

}
