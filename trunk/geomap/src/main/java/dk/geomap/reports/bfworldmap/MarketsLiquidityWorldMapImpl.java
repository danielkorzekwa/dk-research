package dk.geomap.reports.bfworldmap;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;

import dk.bot.betfairservice.BetFairService;
import dk.bot.betfairservice.model.BFMarketData;
import dk.geomap.visualization.liquidityworldmap.CountryLiquidity;
import dk.geomap.visualization.liquidityworldmap.LiquidityWorldMap;

/**
 * Generates liquidity geo map for BetFair markets
 * 
 * @author korzekwad
 * 
 */
public class MarketsLiquidityWorldMapImpl implements MarketsLiquidityWorldMap {

	
	private final BetFairService betFairService;

	MarketsLiquidityWorldMapImpl(BetFairService betFairService) {
		this.betFairService = betFairService;
	}

	public String generate(Date from, Date to) {

			List<BFMarketData> markets = betFairService.getMarkets(from, to, null);
			Collection<CountryLiquidity> liquidity = calculateLiquidity(markets);

			String report = new LiquidityWorldMap(new VelocityEngine()).generate(liquidity, from, to);
			return report;
	}

	private Collection<CountryLiquidity> calculateLiquidity(List<BFMarketData> markets) {
		/** key - country code */
		Map<String, CountryLiquidity> liquididtyMap = new HashMap<String, CountryLiquidity>();

		for (BFMarketData market : markets) {
			CountryLiquidity countryLiquidity = liquididtyMap.get(market.getCountryCode());
			if (countryLiquidity == null) {
				countryLiquidity = new CountryLiquidity(market.getCountryCode());
			}
			countryLiquidity.setTotalMatched(countryLiquidity.getTotalMatched() + market.getTotalAmountMatched());
			liquididtyMap.put(market.getCountryCode(), countryLiquidity);
		}
		return liquididtyMap.values();
	}
}
