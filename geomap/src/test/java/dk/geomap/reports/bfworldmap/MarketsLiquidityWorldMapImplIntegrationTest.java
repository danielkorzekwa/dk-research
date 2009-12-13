package dk.geomap.reports.bfworldmap;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dk.bot.betfairservice.BetFairService;
import dk.bot.betfairservice.BetFairServiceFactoryBean;
import dk.geomap.reports.bfworldmap.MarketsLiquidityWorldMapImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-geomap.xml","/spring-geomap-dao.xml"})
public class MarketsLiquidityWorldMapImplIntegrationTest {

	@Resource
	MarketsLiquidityWorldMapImpl worldMap;
	
	
	@BeforeClass
	public static void setUp() {
		System.setProperty("jdbc.user", "postgres");
		System.setProperty("jdbc.password", "postgres");
		System.setProperty("jdbc.url", "jdbc:postgresql://localhost/geomap");
	}
	
	@Test
	public void testGenerate() {
	Date from = new Date(System.currentTimeMillis());
	Date to = new Date(from.getTime() + (1000*3600*24*7));
		
		String htmlMap = worldMap.generate(from,to);
		System.out.println(htmlMap);
	}

}
