package dk.betex.ecosystem.webconsole.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.betex.ecosystem.webconsole.client.model.MarketTradedVolume;
import dk.betex.ecosystem.webconsole.client.model.PriceTradedVolume;
import dk.betex.ecosystem.webconsole.client.model.RunnerTradedVolume;
import dk.bot.betfairservice.BetFairUtil;
import dk.bot.betfairservice.model.BFMarketTradedVolume;
import dk.bot.betfairservice.model.BFPriceTradedVolume;
import dk.bot.betfairservice.model.BFRunnerTradedVolume;

/**
 * Creates MarketTradedVolume object.
 * 
 * @author korzekwad
 * 
 */
public class MarketTradedVolumeFactory {

	public static MarketTradedVolume create(BFMarketTradedVolume bfMarketTradedVolume) {

		List<RunnerTradedVolume> runnerTradedVolume = new ArrayList<RunnerTradedVolume>();
		for (BFRunnerTradedVolume bfRunnerTradedVolume : bfMarketTradedVolume.getRunnerTradedVolume()) {

			List<PriceTradedVolume> priceTradedVolume = new ArrayList<PriceTradedVolume>();
			for (BFPriceTradedVolume bfPRiceTradedVolume : bfRunnerTradedVolume.getPriceTradedVolume()) {
				priceTradedVolume.add(new PriceTradedVolume(bfPRiceTradedVolume.getPrice(), bfPRiceTradedVolume
						.getTradedVolume()));
			}

			runnerTradedVolume.add(new RunnerTradedVolume(bfRunnerTradedVolume.getSelectionId(), priceTradedVolume));
		}

		MarketTradedVolume marketTradedVolume = new MarketTradedVolume(bfMarketTradedVolume.getMarketId(),
				runnerTradedVolume);
		return marketTradedVolume;
	}
	
	/**Add missing prices for all runners, so every runner has the same number of all possible betfair prices. Missing prices will be added with 0 traded volume. 
	 * 
	 * @param marketTradedVolume
	 * @return
	 */
	public static MarketTradedVolume createNormalized(MarketTradedVolume marketTradedVolume) {
	List<Double> allPrices = BetFairUtil.getAllPricesForPriceRanges(BetFairUtil.getPriceRanges());
	
		/**Add prices with 0 traded volume for all runners.*/
		List<RunnerTradedVolume> normalizedRunnerTradedVolume = new ArrayList<RunnerTradedVolume>();
		for(RunnerTradedVolume runnerTradedVolume: marketTradedVolume.getRunnerTradedVolume()) {
			
			/**key - price, value - traded volume*/
			Map<Double,Double> pricesTradedVolumeMap = new HashMap<Double, Double>();
			for(PriceTradedVolume priceTradedVolume: runnerTradedVolume.getPriceTradedVolume()) {
				pricesTradedVolumeMap.put(priceTradedVolume.getPrice(), priceTradedVolume.getTradedVolume());
			}
			
			List<PriceTradedVolume> normalizedPriceTradedVolume = new ArrayList<PriceTradedVolume>();
			for(Double price: allPrices) {
				Double tradedVolume = pricesTradedVolumeMap.get(price);
				if(tradedVolume==null) {tradedVolume=0d;}
				normalizedPriceTradedVolume.add(new PriceTradedVolume(price,tradedVolume));	
			}
			
			normalizedRunnerTradedVolume.add(new RunnerTradedVolume(runnerTradedVolume.getSelectionId(), normalizedPriceTradedVolume));
		}
		
		MarketTradedVolume normalizedMarketTradedVolume = new MarketTradedVolume(marketTradedVolume.getMarketId(), normalizedRunnerTradedVolume);
		return normalizedMarketTradedVolume;
		
	}
}
