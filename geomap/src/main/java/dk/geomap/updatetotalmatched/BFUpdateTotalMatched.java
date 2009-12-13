package dk.geomap.updatetotalmatched;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import dk.bot.betfairservice.BetFairService;
import dk.bot.betfairservice.model.BFMarketData;
import dk.bot.betfairservice.model.BFMarketDetails;
import dk.geomap.dao.BettingExchangeEnum;
import dk.geomap.dao.MarketDAO;
import dk.geomap.dao.TotalMatchedDAO;
import dk.geomap.dao.model.Market;

/**
 * Retrieve betfair HR_UK markets for today and update them in database.
 * 
 * @author korzekwad
 * 
 */
public class BFUpdateTotalMatched implements UpdateTotalMatched {

	private final BetFairService betFair;
	private final MarketDAO marketDao;

	/** Markets already existing in database. Key - marketId, Value - latest total matched. */
	private Map<Integer, Double> marketsInDb = new HashMap<Integer, Double>();
	private final TotalMatchedDAO totalMatchedDao;

	public BFUpdateTotalMatched(BetFairService betFair, MarketDAO marketDao, TotalMatchedDAO totalMatchedDao) {
		this.betFair = betFair;
		this.marketDao = marketDao;
		this.totalMatchedDao = totalMatchedDao;
	}

	public void update() {
		try {

			/** Get today markets from betfair.com */
			DateMidnight dayStart = new DateMidnight();
			DateTime dayEnd = dayStart.plusDays(1).toDateTime().minus(1);
			Set<Integer> hrEvent = new HashSet<Integer>();
			hrEvent.add(7);
			List<BFMarketData> markets = betFair.getMarkets(dayStart.toDate(), dayEnd.toDate(), hrEvent);

			/** Update markets in db. */
			for (BFMarketData market : markets) {
				if (market.getEventHierarchy().startsWith("/7/298251")) {
					/** If market doesn't exist in db then add it. */
					Double lastTotalMatched = marketsInDb.get(market.getMarketId());
					if (lastTotalMatched == null) {

						Market marketFromDb = marketDao.getMarket(BettingExchangeEnum.BETFAIR, market.getMarketId());
						if (marketFromDb == null) {
							/** Wait one second before calling API to not interfere b application. */
							try {
								Thread.sleep(1000);

								BFMarketDetails marketDetails = betFair.getMarketDetails(market.getMarketId());
								marketDao.addMarket(new Market(BettingExchangeEnum.BETFAIR, market.getMarketId(),
										marketDetails.getMarketTime(), market.getTotalAmountMatched()));

								lastTotalMatched = new Double(market.getTotalAmountMatched());

							} catch (Exception e) {
								e.printStackTrace();
							}

						} else {
							lastTotalMatched = marketFromDb.getTotalMatched();
						}

						marketsInDb.put(market.getMarketId(), lastTotalMatched);
					}

					/** Update total matched for market. */
					if (market.getTotalAmountMatched() < lastTotalMatched) {
						System.out.println("BETFAIR: New total matched is < than last total matched."
								+ market.getTotalAmountMatched() + " < " + lastTotalMatched);
					} else if (market.getTotalAmountMatched() > lastTotalMatched) {
						marketDao.updateTotalMatched(BettingExchangeEnum.BETFAIR, market.getMarketId(), market
								.getTotalAmountMatched());
					}
				}
			}

			/** Update total matched for betting exchange in db */
			double totalMatched = marketDao.getTotalMatched(BettingExchangeEnum.BETFAIR, dayStart.toDate(), dayEnd
					.toDate());
			totalMatchedDao.add(BettingExchangeEnum.BETFAIR, new Date(System.currentTimeMillis()), totalMatched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
