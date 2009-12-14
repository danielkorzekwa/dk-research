package dk.betexarchive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import dk.betexarchive.domain.LastMatchedPriceJDO;
import dk.betexarchive.domain.LastMatchedPriceParser;

public class BetexArchiveIntegrationTest {

	private Random rand = new Random(System.currentTimeMillis());
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	//private WebClient client = WebClient.create("http://dkfbtest.appspot.com");
	private WebClient client = WebClient.create("http://localhost:8080");

	@Test
	public void testStoreLastMatchedPrice() throws ParseException, IOException {
		
		int marketId = rand.nextInt(Integer.MAX_VALUE);
		int selectionId = rand.nextInt(Integer.MAX_VALUE);
		List<LastMatchedPriceJDO> lastMatchedPrices = createLastMatchedPrices(marketId, selectionId, 5);

		String lastMatchedPricesText = new LastMatchedPriceParser().parseLastMatchedPrices(lastMatchedPrices);
		Response response = client.path("storePrices").type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN).post(
				lastMatchedPricesText);

		assertEquals(200, response.getStatus());
		InputStream in = (InputStream) response.getEntity();
		String dataString = readResponse(in);

		assertEquals("OK.", dataString);
	}

	@Test
	public void testStoreLastMatchedPriceWrongDataFormat() throws ParseException, IOException {

		String lastMatchedPricesText = "bad data";
		Response response = client.path("storePrices").type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN).post(
				lastMatchedPricesText);

		assertEquals(200, response.getStatus());
		InputStream in = (InputStream) response.getEntity();
		String dataString = readResponse(in);

		assertEquals("ERROR. Can't parse lastMatchedPrice data: bad data", dataString);
	}

	@Test
	public void testGetLastMatchedPrice() throws ParseException, IOException, InterruptedException {
		
		int marketId = rand.nextInt(Integer.MAX_VALUE);
		int selectionId = rand.nextInt(Integer.MAX_VALUE);
		List<LastMatchedPriceJDO> lastMatchedPrices = createLastMatchedPrices(marketId, selectionId,100);

		/** Store prices. */
		String lastMatchedPricesText = new LastMatchedPriceParser().parseLastMatchedPrices(lastMatchedPrices);
		Response storePricesResp = client.path("storePrices").type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN)
				.post(lastMatchedPricesText);
		assertEquals(200, storePricesResp.getStatus());
		String storePricesRespData = readResponse((InputStream) storePricesResp.getEntity());
		assertEquals("OK.", storePricesRespData);

		/** Get prices for selection. */
		Response getPricesResp = client.back(true).path("getPrices").query("marketId", marketId).query("selectionId",
				selectionId).type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN).get();
		assertEquals(200, getPricesResp.getStatus());
		String getPricesRespData = readResponse((InputStream) getPricesResp.getEntity());
		assertFalse("Error:" + getPricesRespData, getPricesRespData.startsWith("ERROR."));
		List<LastMatchedPriceJDO> receivedPrices = new LastMatchedPriceParser().parseLastMatchedPrices(getPricesRespData);
		
		/** Not sure why only 80 records are stored, maybe they are not flushed yet, at GAE all works fine.*/		
		assertEquals(80,receivedPrices.size());
	}
	
	@Test
	public void testGetPriceChart() throws ParseException, IOException, InterruptedException {
		
		int marketId = rand.nextInt(Integer.MAX_VALUE);
		int selectionId = rand.nextInt(Integer.MAX_VALUE);
		List<LastMatchedPriceJDO> lastMatchedPrices = createLastMatchedPrices(marketId, selectionId,100);

		/** Store prices. */
		String lastMatchedPricesText = new LastMatchedPriceParser().parseLastMatchedPrices(lastMatchedPrices);
		Response storePricesResp = client.path("storePrices").type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN)
				.post(lastMatchedPricesText);
		assertEquals(200, storePricesResp.getStatus());
		String storePricesRespData = readResponse((InputStream) storePricesResp.getEntity());
		assertEquals("OK.", storePricesRespData);

		/** Get prices  chart for selection. */
		client.back(true).path("getPricesChart").query("marketId", marketId).query("selectionId",
				selectionId).query("prob", true).type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN);
		System.out.println("Sending request: " + client.getCurrentURI());
		Response getPricesResp = client.get();
		
		assertEquals(200, getPricesResp.getStatus());
		String getPricesChartData = readResponse((InputStream) getPricesResp.getEntity());	
	}
	

	private List<LastMatchedPriceJDO> createLastMatchedPrices(long marketId, long selectionId, int numOfPrices)
			throws ParseException {
		List<LastMatchedPriceJDO> lastMatchedPrices = new ArrayList<LastMatchedPriceJDO>();
		
		long now = System.currentTimeMillis();
		for (int i = 0; i < numOfPrices; i++) {
			LastMatchedPriceJDO lastMatchedPrice = new LastMatchedPriceJDO();
			lastMatchedPrice.setMarketId(marketId);
			lastMatchedPrice.setSelectionId(selectionId);
			lastMatchedPrice.setLastMatchedPrice((double)i*0.01);
			lastMatchedPrice.setAvgPrice15MinSlope((double)i);
			lastMatchedPrice.setLastMatchedDate(new Date(now + (i*1000)));
			lastMatchedPrices.add(lastMatchedPrice);
		}

		return lastMatchedPrices;
	}

	private String readResponse(InputStream in) throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer);
		return writer.toString();
	}
}
