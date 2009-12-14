package dk.betexarchive.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses LastMatchedPrice in a comma separated string format.
 * 
 * Format: marketId,selectionId, lastMatchedPrice, avgPrice15MinSlope,lastMatchedDate(yyyy.MM.dd HH:mm:ss) Example: 1,2,2.34,0.04,2009.05.16
 * 22:12:45 Not thread-safe.
 * 
 * @author daniel
 * 
 */
public class LastMatchedPriceParser {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	/**
	 * 
	 * @param lastMatchedPrice
	 *            Contains lastMatchedPrice in a comma separated format.
	 * @return
	 */
	public LastMatchedPriceJDO parseLastMatchedPrice(String lastMatchedPrice) {

		try {
			String[] split = lastMatchedPrice.split(",");

			LastMatchedPriceJDO obj = new LastMatchedPriceJDO();
			obj.setMarketId(Long.parseLong(split[0]));
			obj.setSelectionId(Long.parseLong(split[1]));
			obj.setLastMatchedPrice(Double.parseDouble(split[2]));
			obj.setAvgPrice15MinSlope(Double.parseDouble(split[3]));
			obj.setLastMatchedDate(df.parse(split[4]));

			return obj;
		} catch (Exception e) {
			throw new IllegalArgumentException("Can't parse lastMatchedPrice data: " + lastMatchedPrice, e);
		}
	}

	/**
	 * 
	 * @param lastMatchedPrice
	 *            Contains array of lastMatchedPrice in a comma separated format. Records are separated by "\n"
	 * @return
	 */
	public List<LastMatchedPriceJDO> parseLastMatchedPrices(String lastMatchedPrices) {
		if (lastMatchedPrices.length() == 0) {
			return new ArrayList<LastMatchedPriceJDO>();
		}

		String[] split = lastMatchedPrices.split("\n");

		List<LastMatchedPriceJDO> prices = new ArrayList<LastMatchedPriceJDO>(split.length);
		for (String priceText : split) {
			prices.add(parseLastMatchedPrice(priceText));
		}

		return prices;
	}

	/**
	 * 
	 * @param lastMatchedPrice
	 *            object to parse
	 * @return lastMatchedPrice in a comma separated format
	 */
	public String parseLastMatchedPrice(LastMatchedPriceJDO lastMatchedPrice) {
		StringBuilder builder = new StringBuilder();
		String parsed = builder
		.append(lastMatchedPrice.getMarketId()).append(",")
		.append(lastMatchedPrice.getSelectionId()).append(",")
		.append(round(lastMatchedPrice.getLastMatchedPrice(),2)).append(",")
		.append(round(lastMatchedPrice.getAvgPrice15MinSlope(),2)).append(",")
		.append(df.format(lastMatchedPrice.getLastMatchedDate())).toString();
		return parsed;
	}

	/**
	 * 
	 * @param lastMatchedPrice
	 *            object to parse
	 * @return lastMatchedPrice in a comma separated format. Records are separated by "\n"
	 */
	public String parseLastMatchedPrices(List<LastMatchedPriceJDO> lastMatchedPrices) {

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < lastMatchedPrices.size(); i++) {
			LastMatchedPriceJDO lastMatchedPrice = lastMatchedPrices.get(i);
			builder.append(parseLastMatchedPrice(lastMatchedPrice));
			if (i < lastMatchedPrices.size() - 1) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	  /**
     * Round the given value to the specified number of decimal places. The
     * value is rounded using the given method which is any method defined in
     * {@link BigDecimal}.
     * 
     * @param x the value to round.
     * @param scale the number of digits to the right of the decimal point.
     * @param roundingMethod the rounding method as defined in
     *        {@link BigDecimal}.
     * @return the rounded value.
     * @since 1.1
     */
    private static double round(double x, int scale) {
        try {
            return (new BigDecimal
                   (Double.toString(x))
                   .setScale(scale, BigDecimal.ROUND_HALF_UP))
                   .doubleValue();
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(x)) {
                return x;          
            } else {
                return Double.NaN;
            }
        }
    }

}
