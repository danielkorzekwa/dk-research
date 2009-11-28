package dk.betex.ecosystem.webconsole.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dk.betex.ecosystem.webconsole.client.model.MarketTradedVolume;
import dk.betex.ecosystem.webconsole.client.model.PriceTradedVolume;
import dk.betex.ecosystem.webconsole.client.model.RunnerTradedVolume;
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
}
