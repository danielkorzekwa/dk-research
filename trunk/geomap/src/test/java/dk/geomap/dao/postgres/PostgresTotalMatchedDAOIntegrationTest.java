package dk.geomap.dao.postgres;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
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
import dk.geomap.dao.TotalMatchedDAO;
import dk.geomap.dao.model.TotalMatched;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-geomap-dao.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@TransactionConfiguration(transactionManager = "txManager")
@Transactional
public class PostgresTotalMatchedDAOIntegrationTest {

	@Resource
	private TotalMatchedDAO totalMatchedDao;

	@BeforeClass
	public static void setUp() {
		System.setProperty("jdbc.user", "postgres");
		System.setProperty("jdbc.password", "postgres");
		System.setProperty("jdbc.url", "jdbc:postgresql://localhost/geomapTest");
	}

	
	@Test
	public void testAdd() {
		totalMatchedDao.add(BettingExchangeEnum.BETFAIR, new Date(System.currentTimeMillis()), 100);
	}
	
	@Test
	public void testGetTotalMatched() {
		DateTime dateMidnight = new DateMidnight().toDateTime();
		totalMatchedDao.add(BettingExchangeEnum.BETFAIR,dateMidnight.toDate(), 10);
		totalMatchedDao.add(BettingExchangeEnum.BETFAIR,dateMidnight.plusHours(1).toDate(), 20);
		totalMatchedDao.add(BettingExchangeEnum.BETFAIR,dateMidnight.plusHours(2).toDate(), 30);
		
		/**Do not return these values*/
		totalMatchedDao.add(BettingExchangeEnum.BETFAIR,dateMidnight.plusHours(25).toDate(), 100);
		totalMatchedDao.add(BettingExchangeEnum.BETFAIR,dateMidnight.minusHours(1).toDate(), 200);
		totalMatchedDao.add(BettingExchangeEnum.BETDAQ,dateMidnight.plusHours(1).toDate(), 300);
		
		List<TotalMatched> totalMatched = totalMatchedDao.getTotalMatched(BettingExchangeEnum.BETFAIR, dateMidnight.plusHours(12).toDate());
		
		assertEquals(3, totalMatched.size());
		assertEquals(10, totalMatched.get(0).getTotalMatched(),0);
		assertEquals(20, totalMatched.get(1).getTotalMatched(),0);
		assertEquals(30, totalMatched.get(2).getTotalMatched(),0);
	}

}
