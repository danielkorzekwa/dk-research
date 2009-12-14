package dk.betexarchive.domain;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LastMatchedPriceParserTest {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	@Test
	public void testParseLastMatchedPriceToObj() throws ParseException {
		LastMatchedPriceJDO lastMatchedPrice = new LastMatchedPriceParser().parseLastMatchedPrice("1,2,2.34,0.04,2009.05.16 22:12:45");

		assertEquals(1, lastMatchedPrice.getMarketId().longValue());
		assertEquals(2, lastMatchedPrice.getSelectionId().longValue());
		assertEquals(2.34, lastMatchedPrice.getLastMatchedPrice(), 0);
		assertEquals(0.04, lastMatchedPrice.getAvgPrice15MinSlope(), 0);
		assertEquals(df.parse("2009.05.16 22:12:45"), lastMatchedPrice.getLastMatchedDate());
	}
	
	@Test
	public void testParseLastMatchedPricesToObj() throws ParseException {
		List<LastMatchedPriceJDO> lastMatchedPrices = new LastMatchedPriceParser().parseLastMatchedPrices("1,2,2.34,0.04,2009.05.16 22:12:45\n4,5,3.76,-0.12,2009.05.16 13:23:09");

		assertEquals(2, lastMatchedPrices.size());
		
		assertEquals(1, lastMatchedPrices.get(0).getMarketId().longValue());
		assertEquals(2, lastMatchedPrices.get(0).getSelectionId().longValue());
		assertEquals(2.34, lastMatchedPrices.get(0).getLastMatchedPrice(), 0);
		assertEquals(0.04, lastMatchedPrices.get(0).getAvgPrice15MinSlope(), 0);
		assertEquals(df.parse("2009.05.16 22:12:45"), lastMatchedPrices.get(0).getLastMatchedDate());
		
		assertEquals(4, lastMatchedPrices.get(1).getMarketId().longValue());
		assertEquals(5, lastMatchedPrices.get(1).getSelectionId().longValue());
		assertEquals(3.76, lastMatchedPrices.get(1).getLastMatchedPrice(), 0);
		assertEquals(-0.12, lastMatchedPrices.get(1).getAvgPrice15MinSlope(), 0);
		assertEquals(df.parse("2009.05.16 13:23:09"), lastMatchedPrices.get(1).getLastMatchedDate());
	
	
	}
	

	@Test
	public void testParseLastMatchedPriceToString() throws ParseException {
		LastMatchedPriceJDO lastMatchedPrice = new LastMatchedPriceJDO();
		lastMatchedPrice.setMarketId(1l);
		lastMatchedPrice.setSelectionId(2l);
		lastMatchedPrice.setLastMatchedPrice(2.34);
		lastMatchedPrice.setAvgPrice15MinSlope(0.06);
		lastMatchedPrice.setLastMatchedDate(df.parse("2009.05.16 22:12:45"));

		String parsed = new LastMatchedPriceParser().parseLastMatchedPrice(lastMatchedPrice);

		assertEquals("1,2,2.34,0.06,2009.05.16 22:12:45", parsed);

	}
	
	@Test
	public void testParseLastMatchedPricesToString() throws ParseException {
		
		List<LastMatchedPriceJDO> lastMatchedPrices = new ArrayList<LastMatchedPriceJDO>();
		
		LastMatchedPriceJDO lastMatchedPrice1 = new LastMatchedPriceJDO();
		lastMatchedPrice1.setMarketId(1l);
		lastMatchedPrice1.setSelectionId(2l);
		lastMatchedPrice1.setLastMatchedPrice(2.34);
		lastMatchedPrice1.setAvgPrice15MinSlope(-0.3);
		lastMatchedPrice1.setLastMatchedDate(df.parse("2009.05.16 22:12:45"));
		lastMatchedPrices.add(lastMatchedPrice1);
		
		LastMatchedPriceJDO lastMatchedPrice2 = new LastMatchedPriceJDO();
		lastMatchedPrice2.setMarketId(4l);
		lastMatchedPrice2.setSelectionId(5l);
		lastMatchedPrice2.setLastMatchedPrice(3.76);
		lastMatchedPrice2.setAvgPrice15MinSlope(0.01);
		lastMatchedPrice2.setLastMatchedDate(df.parse("2009.05.16 13:23:09"));
		lastMatchedPrices.add(lastMatchedPrice2);
		
		
		String parsed = new LastMatchedPriceParser().parseLastMatchedPrices(lastMatchedPrices);

		assertEquals("1,2,2.34,-0.3,2009.05.16 22:12:45\n4,5,3.76,0.01,2009.05.16 13:23:09", parsed);

	}
	
	

}
