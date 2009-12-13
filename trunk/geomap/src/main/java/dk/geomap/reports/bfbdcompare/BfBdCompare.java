package dk.geomap.reports.bfbdcompare;

import java.util.Date;

/**Compare total matched between BetFair and BetDaq during a day.
 * 
 * @author korzekwad
 *
 */
public interface BfBdCompare {

	/**Generate html report.
	 * 
	 */
	public String generate(Date day);
}
