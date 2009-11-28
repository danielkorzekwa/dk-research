package dk.betex.ecosystem.webconsole.client;

import java.io.Serializable;
import java.util.List;

/**Represents traded volume at each price on all of the runners in a particular market
 * 
 * @author korzekwad
 *
 */
public class MarketTradedVolume implements Serializable{

	private final int marketId;
	private final List<RunnerTradedVolume> runnerTradedVolume;

	public MarketTradedVolume(int marketId,List<RunnerTradedVolume> runnerTradedVolume) {
		this.marketId = marketId;
		this.runnerTradedVolume = runnerTradedVolume;
	}

	public int getMarketId() {
		return marketId;
	}

	public List<RunnerTradedVolume> getRunnerTradedVolume() {
		return runnerTradedVolume;
	}

	@Override
	public String toString() {
		return "MarketTradedVolume [marketId=" + marketId + ", runnerTradedVolume=" + runnerTradedVolume + "]";
	}
}
