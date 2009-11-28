package dk.betex.ecosystem.webconsole.client.model;

import java.io.Serializable;

/**
 * Represents traded volume for the given price on the given runner in a particular market.
 * 
 * @author korzekwad
 * 
 */
public class PriceTradedVolume implements Serializable{

	private final double price;
	private final double tradedVolume;

	/**
	 * 
	 * @param price
	 * @param tradedVolume
	 *            The total amount matched for the given price
	 */
	public PriceTradedVolume(double price, double tradedVolume) {
		this.price = price;
		this.tradedVolume = tradedVolume;
	}

	public double getPrice() {
		return price;
	}

	public double getTradedVolume() {
		return tradedVolume;
	}

	@Override
	public String toString() {
		return "PriceTradedVolume [price=" + price + ", tradedVolume=" + tradedVolume + "]";
	}
}
