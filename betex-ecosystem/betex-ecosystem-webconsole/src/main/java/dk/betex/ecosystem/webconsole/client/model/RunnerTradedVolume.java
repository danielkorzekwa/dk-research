package dk.betex.ecosystem.webconsole.client.model;

import java.io.Serializable;
import java.util.List;

import com.betfair.publicapi.types.exchange.v5.RunnerPrices;

/**Represents traded volume at each price on the given runner in a particular market.
 * 
 * @author korzekwad
 *
 */
public class RunnerTradedVolume implements Serializable{

	private int selectionId;
	private List<PriceTradedVolume> priceTradedVolume;

	public RunnerTradedVolume() {
	}
	
	public RunnerTradedVolume(int selectionId, List<PriceTradedVolume> priceTradedVolume) {
		this.selectionId = selectionId;
		this.priceTradedVolume = priceTradedVolume;
	}

	public int getSelectionId() {
		return selectionId;
	}

	public List<PriceTradedVolume> getPriceTradedVolume() {
		return priceTradedVolume;
	}

	@Override
	public String toString() {
		return "RunnerTradedVolume [selectionId=" + selectionId + ", priceTradedVolume=" + priceTradedVolume + "]";
	}
}
