package dk.geomap.visualization.liquidityworldmap;

/**Data model foor country liquidity.
 * 
 * @author korzekwad
 *
 */
public class CountryLiquidity {
	
	/**two letter country code (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2), e.g. GB but not GBR*/
	private final String countryCode;
	private double totalMatched = 0;
	
	public CountryLiquidity(String countryCode) {
		this.countryCode = countryCode;
	}
	public CountryLiquidity(String countryCode, double totalMatched) {
		this.countryCode = countryCode;
		this.totalMatched = totalMatched;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public double getTotalMatched() {
		return totalMatched;
	}

	public void setTotalMatched(double totalMatched) {
		this.totalMatched = totalMatched;
	}
}
