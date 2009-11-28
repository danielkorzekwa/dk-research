package dk.betex.ecosystem.webconsole.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.betex.ecosystem.webconsole.client.MarketTradedVolume;
import dk.betex.ecosystem.webconsole.client.PriceTradedVolume;
import dk.betex.ecosystem.webconsole.client.RunnerTradedVolume;
import dk.bot.betfairservice.model.BFMarketTradedVolume;
import dk.bot.betfairservice.model.BFPriceTradedVolume;
import dk.bot.betfairservice.model.BFRunnerTradedVolume;

public class MarketTradedVolumeFactoryTest {

	private BFMarketTradedVolume bfMarketTradedVolume;
	
	@Before
	public void setUp() {
		List<BFRunnerTradedVolume> runnersTradedVolume = new ArrayList<BFRunnerTradedVolume>();
		
		List<BFPriceTradedVolume> pricesTradedVolume = new ArrayList<BFPriceTradedVolume>();
		pricesTradedVolume.add(new BFPriceTradedVolume(2.1, 35.32));
		pricesTradedVolume.add(new BFPriceTradedVolume(2.2, 765.56));
		runnersTradedVolume.add(new BFRunnerTradedVolume(105,pricesTradedVolume));
		
		pricesTradedVolume = new ArrayList<BFPriceTradedVolume>();
		pricesTradedVolume.add(new BFPriceTradedVolume(3.4, 43.24));
		pricesTradedVolume.add(new BFPriceTradedVolume(3.6, 65.12));
		runnersTradedVolume.add(new BFRunnerTradedVolume(106,pricesTradedVolume));
		
		bfMarketTradedVolume = new BFMarketTradedVolume(12, runnersTradedVolume );
	}
	
	@Test
	public void testCreate() {
		MarketTradedVolume marketTradedVolume = MarketTradedVolumeFactory.create(bfMarketTradedVolume);
		
		assertEquals(bfMarketTradedVolume.getMarketId(), marketTradedVolume.getMarketId());
		
		assertEquals(bfMarketTradedVolume.getRunnerTradedVolume().size(), marketTradedVolume.getRunnerTradedVolume().size());
	
		for(int runnerIndex=0;runnerIndex<bfMarketTradedVolume.getRunnerTradedVolume().size();runnerIndex++) {
			BFRunnerTradedVolume bfRunnerTradedVolume = bfMarketTradedVolume.getRunnerTradedVolume().get(runnerIndex);
			RunnerTradedVolume runnerTradedVolume = marketTradedVolume.getRunnerTradedVolume().get(runnerIndex);
			
			assertEquals(bfRunnerTradedVolume.getSelectionId(), runnerTradedVolume.getSelectionId());
			assertEquals(bfRunnerTradedVolume.getPriceTradedVolume().size(), runnerTradedVolume.getPriceTradedVolume().size());
			
			for(int priceIndex=0;priceIndex<bfRunnerTradedVolume.getPriceTradedVolume().size();priceIndex++) {
				BFPriceTradedVolume bfPriceTradedVolume = bfRunnerTradedVolume.getPriceTradedVolume().get(priceIndex);
				PriceTradedVolume priceTradedVolume = runnerTradedVolume.getPriceTradedVolume().get(priceIndex);
				
				assertEquals(bfPriceTradedVolume.getPrice(), priceTradedVolume.getPrice(),0);
				assertEquals(bfPriceTradedVolume.getTradedVolume(), priceTradedVolume.getTradedVolume(),0);
			}
			
		}
	}

}
