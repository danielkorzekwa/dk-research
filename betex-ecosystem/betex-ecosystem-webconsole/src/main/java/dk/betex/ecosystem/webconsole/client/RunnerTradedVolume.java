package dk.betex.ecosystem.webconsole.client;

import java.io.Serializable;
import java.util.List;

/**Represents traded volume at each price on the given runner in a particular market.
 * 
 * @author korzekwad
 *
 */
public class RunnerTradedVolume implements Serializable{

	private final int selectionId;
	private final List<PriceTradedVolume> priceTradedVolume;

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
