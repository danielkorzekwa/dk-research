package dk.geomap.reports.bfworldmap;

import java.util.Date;

/**
 * Generates liquidity geo map for BetFair markets
 * 
 * @author korzekwad
 * 
 */
public interface MarketsLiquidityWorldMap {

	/**
	 * Generates liquidity geo map for BetFair markets.
	 * 
	 * @return html page
	 */
	public String generate(Date from, Date to);

}
