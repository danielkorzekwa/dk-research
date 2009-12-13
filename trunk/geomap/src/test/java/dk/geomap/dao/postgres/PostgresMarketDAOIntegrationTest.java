package dk.geomap.dao.postgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import dk.geomap.dao.BettingExchangeEnum;
import dk.geomap.dao.MarketDAO;
import dk.geomap.dao.model.Market;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-geomap-dao.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@TransactionConfiguration(transactionManager = "txManager")
@Transactional
public class PostgresMarketDAOIntegrationTest {

	@Resource
	private MarketDAO marketDao;

	@BeforeClass
	public static void setUp() {
		System.setProperty("jdbc.user", "postgres");
		System.setProperty("jdbc.password", "postgres");
		System.setProperty("jdbc.url", "jdbc:postgresql://localhost/geomapTest");
	}

	@Test
	public void testAddGetMarket() {
		Market market = new Market(BettingExchangeEnum.BETDAQ, 1l, new Date(), 100);
		
		Market marketFromDb = marketDao.getMarket(market.getBettingExchange(), market.getMarketId());
		assertNull(marketFromDb);
		
		marketDao.addMarket(market);
		
		marketFromDb = marketDao.getMarket(market.getBettingExchange(), market.getMarketId());
		assertNotNull(marketFromDb);
		assertEquals(BettingExchangeEnum.BETDAQ, marketFromDb.getBettingExchange());
		assertEquals(market.getMarketId(), marketFromDb.getMarketId());
		assertEquals(market.getMarketTime(), marketFromDb.getMarketTime());
		assertEquals(market.getTotalMatched(), marketFromDb.getTotalMatched(),0);
		
	}

	@Test
	public void testUpdateTotalMatched() {
		Market market = new Market(BettingExchangeEnum.BETDAQ, 1l, new Date(), 100);
		marketDao.addMarket(market);
		
		Market marketFromDb = marketDao.getMarket(market.getBettingExchange(), market.getMarketId());
		assertEquals(market.getTotalMatched(), marketFromDb.getTotalMatched(),0);
		
		marketDao.updateTotalMatched(market.getBettingExchange(),market.getMarketId(), 200l);
		
		marketFromDb = marketDao.getMarket(market.getBettingExchange(), market.getMarketId());
		assertEquals(200, marketFromDb.getTotalMatched(),0);
		
	}
	
	@Test
	public void testGetTotalMatched() {
		double totalMatched = marketDao.getTotalMatched(BettingExchangeEnum.BETFAIR, new Date(200), new Date(1000));
		assertEquals(0,totalMatched, 0);
		
		List<Market> markets = new ArrayList<Market>();
		/**Markets used for total matched calculation.*/
		markets.add(new Market(BettingExchangeEnum.BETFAIR,1,new Date(200),10));
		markets.add(new Market(BettingExchangeEnum.BETFAIR,2,new Date(300),20));
		markets.add(new Market(BettingExchangeEnum.BETFAIR,3,new Date(999),30));
		
		/**Markets not used for total matched calculation.*/
		markets.add(new Market(BettingExchangeEnum.BETDAQ,4,new Date(400),100));
		markets.add(new Market(BettingExchangeEnum.BETFAIR,5,new Date(199),100));
		markets.add(new Market(BettingExchangeEnum.BETFAIR,6,new Date(1000),100));
		
		for(Market market: markets) {
			marketDao.addMarket(market);
		}
		
		totalMatched = marketDao.getTotalMatched(BettingExchangeEnum.BETFAIR, new Date(200), new Date(1000));
		assertEquals(60,totalMatched, 0);
	}

}
